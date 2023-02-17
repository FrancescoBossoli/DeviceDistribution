package it.epicode.be.devicedistribution.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.be.devicedistribution.models.Role;
import it.epicode.be.devicedistribution.models.RoleType;
import it.epicode.be.devicedistribution.models.User;
import it.epicode.be.devicedistribution.repositories.RoleRepository;
import it.epicode.be.devicedistribution.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService uS;
	@Autowired
	private RoleRepository rR;
	@Autowired
	private PasswordEncoder pE;
	
	@GetMapping("")
	public String index() {
		return "API HOME";
	}
	
	@GetMapping("/api/users")
	public ResponseEntity<Object> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2000") int size) {
		Pageable p = PageRequest.of(page, size);
		Page<User> users = uS.getAllUsers(p);		
		if (users.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);				
		else return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/api/users/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		Optional<User> u = uS.getUserById(id);
		if (u.isPresent()) return new ResponseEntity<>(u.get(), HttpStatus.OK);		
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/api/users")
	public ResponseEntity<Object> create(@RequestBody User x) {
		User us = x;
		us.setPassword(pE.encode(x.getPassword()));		
		if (x.getRoles() == null) {
			Role r = rR.findByName(RoleType.Employee).get();
			Set<Role> set = new HashSet<Role>();
			set.add(r);
			us.setRoles(set);
		}
		uS.save(us);
		User u = uS.getUserByUsername(x.getUsername()).get();		
		return new ResponseEntity<>(u, HttpStatus.CREATED);
	}
	
	@PutMapping("/api/users/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User x) {		
		Optional<User> uOpt = uS.getUserById(id);		
		if (uOpt.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); 		
		User u = uOpt.get();
		u.setId(id);
		if (x.getName() != null) u.setName(x.getName());
		if (x.getSurname() != null) u.setSurname(x.getSurname());
		if (x.getUsername() != null) u.setUsername(x.getUsername());
		if (x.getPassword() != null) u.setPassword(pE.encode(x.getPassword()));
		if (x.getRoles() != null) u.setRoles(x.getRoles());
		uS.save(u);		
		return new ResponseEntity<>(u, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/api/users/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<User> uOpt = uS.getUserById(id);
		if( uOpt.isPresent()) {
			uS.deleteUserById(id);
			return new ResponseEntity<Object>(String.format("The User with id %d has been deleted!", id), HttpStatus.OK);
		} else return new ResponseEntity<Object>(String.format("The User with id %d was not found!", id), HttpStatus.NOT_FOUND);
	}	
}
