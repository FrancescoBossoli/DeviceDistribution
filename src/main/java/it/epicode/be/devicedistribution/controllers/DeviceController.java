package it.epicode.be.devicedistribution.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.be.devicedistribution.models.Device;
import it.epicode.be.devicedistribution.models.Notebook;
import it.epicode.be.devicedistribution.models.Smartphone;
import it.epicode.be.devicedistribution.models.Status;
import it.epicode.be.devicedistribution.models.Tablet;
import it.epicode.be.devicedistribution.models.User;
import it.epicode.be.devicedistribution.services.DeviceService;
import it.epicode.be.devicedistribution.services.NotebookService;
import it.epicode.be.devicedistribution.services.SmartphoneService;
import it.epicode.be.devicedistribution.services.TabletService;
import it.epicode.be.devicedistribution.services.UserService;

@RestController
public class DeviceController {

	@Autowired
	private DeviceService dS;
	@Autowired
	private TabletService tS;
	@Autowired
	private SmartphoneService sS;
	@Autowired
	private NotebookService nS;
	@Autowired
	private UserService uS;
	
	@GetMapping("/api/devices")
	public ResponseEntity<Object> getAllDevices(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2000") int size) {
		Pageable p = PageRequest.of(page, size);
		Page<Device> devices = dS.getAllDevices(p);		
		if (devices.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);				
		else return new ResponseEntity<>(devices, HttpStatus.OK);
	}
	
	@GetMapping("/api/tablets")
	public ResponseEntity<Object> getAllTablets(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2000") int size) {
		Pageable p = PageRequest.of(page, size);
		Page<Tablet> tablets = tS.getAllTablets(p);	
		if (tablets.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);				
		else return new ResponseEntity<>(tablets, HttpStatus.OK);
	}
	
	@GetMapping("/api/notebooks")
	public ResponseEntity<Object> getAllNotebooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2000") int size) {
		Pageable p = PageRequest.of(page, size);
		Page<Notebook> notebooks = nS.getAllNotebooks(p);	
		if (notebooks.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);				
		else return new ResponseEntity<>(notebooks, HttpStatus.OK);
	}
	
	@GetMapping("/api/smartphones")
	public ResponseEntity<Object> getAllSmartphones(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2000") int size) {
		Pageable p = PageRequest.of(page, size);
		Page<Smartphone> smartphones = sS.getAllSmartphones(p);	
		if (smartphones.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);				
		else return new ResponseEntity<>(smartphones, HttpStatus.OK);
	}
	
	@GetMapping("/api/devices/{id}")
	public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
		Optional<Device> d = dS.getDeviceById(id);
		if (d.isPresent()) return new ResponseEntity<>(d.get(), HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
	}
	
	@GetMapping("/api/tablets/{id}")
	public ResponseEntity<Tablet> getTabletById(@PathVariable Long id) {
		Optional<Tablet> t = tS.getTabletById(id);
		if (t.isPresent()) return new ResponseEntity<>(t.get(), HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
	}
	
	@GetMapping("/api/notebooks/{id}")
	public ResponseEntity<Notebook> getNotebookById(@PathVariable Long id) {
		Optional<Notebook> n = nS.getNotebookById(id);
		if (n.isPresent()) return new ResponseEntity<>(n.get(), HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
	}
	
	@GetMapping("/api/smartphones/{id}")
	public ResponseEntity<Smartphone> getById(@PathVariable Long id) {
		Optional<Smartphone> s = sS.getSmartphoneById(id);
		if (s.isPresent()) return new ResponseEntity<>(s.get(), HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
	}
	
	@PostMapping("/api/tablets")
	public ResponseEntity<Object> create(@RequestBody Tablet x) {
		return universalCreation(x);
	}
	
	@PostMapping("/api/notebooks")
	public ResponseEntity<Object> create(@RequestBody Notebook x) {
		return universalCreation(x);
	}
	
	@PostMapping("/api/smartphones")
	public ResponseEntity<Object> create(@RequestBody Smartphone x) {
		return universalCreation(x);
	}	
	
	@PutMapping({"/api/devices/{id}", "/api/tablets/{id}", "/api/notebooks/{id}", "/api/smartphones/{id}"})
	public ResponseEntity<Device> update(@PathVariable Long id, @RequestBody Device x) {		
		Optional<Device> dOpt = dS.getDeviceById(id);		
		if (dOpt.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);					
		Device d = dOpt.get();
		User u = null;
		if (x.getName() != null) d.setName(x.getName());
		if (x.getStatus() != null) d.setStatus(x.getStatus());
		d = itemParse(d, u, x);	
		return new ResponseEntity<>(d, HttpStatus.CREATED);
	}
		
	@DeleteMapping({"/api/devices/{id}", "/api/tablets/{id}", "/api/notebooks/{id}", "/api/smartphones/{id}"})
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<Device> dOpt = dS.getDeviceById(id);
		if( dOpt.isPresent()) {
			dS.deleteDeviceById(id);
			return new ResponseEntity<Object>(String.format("The Device with id %d has been deleted!", id), HttpStatus.OK);
		} else return new ResponseEntity<Object>(String.format("The Device with id %d was not found!", id), HttpStatus.NOT_FOUND);
	}
		
	public ResponseEntity<Object> universalCreation(Device x) {
		Device d = x;
		User u = null;
		d = itemParse(d, u, x);
		return new ResponseEntity<Object>(String.format("The Device has been successifully added to the Database"), HttpStatus.CREATED);
	}
	
	public Device itemParse(Device d, User u, Device x) {
		if (x.getUser() != null) {
			if (x.getUser().getId() != null) u = uS.getUserById(x.getUser().getId()).get();
			else if (x.getUser().getUsername() != null) u = uS.getUserByUsername(x.getUser().getUsername()).get();
			else if (x.getUser().getEmail() != null) u = uS.getUserByEmail(x.getUser().getEmail()).get();
			d.setStatus(Status.Taken);
		}
		d.setUser(u);
		dS.save(d);
		return d;
	}
}
