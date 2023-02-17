package it.epicode.be.devicedistribution.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.be.devicedistribution.models.Device;
import it.epicode.be.devicedistribution.models.Status;
import it.epicode.be.devicedistribution.repositories.DeviceRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeviceService {

	@Autowired
	private DeviceRepository dR;
	
	//This method saves a User in the database if username isn't already taken and if the email isn't already registered
	public void save(Device d) {
		dR.save(d);
		log.info("The Device has been saved in the Database.");	
	}
	
	public Optional<Device> getDeviceById(Long id) {
		return dR.findById(id);
	}
	
	public List<Device> getDevicesByStatus(Status s) {
		return dR.findDevicesByStatus(s);
	}
	
	public List<Device> getDevicesByUsername(String u) {
		return dR.findDevicesByUsername(u);
	}	
	
	public List<Device> getAllDevices(){
		return dR.findAll(PageRequest.of(0 , 2000)).getContent();
	}
	
	public Page<Device> getAllDevices(Pageable p) {
		return dR.findAll(p);
	}
	
	public void deleteDeviceById(Long id) {
		dR.deleteById(id);
	}
	
	public void printList(List<Device> list) {
		for (Device l : list)
			log.info(l.toString());
	}
}