package it.epicode.be.devicedistribution.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.be.devicedistribution.models.Role;
import it.epicode.be.devicedistribution.services.RoleService;

@RestController
public class RoleController {

	@Autowired
	private RoleService rS;
	
	//Roles can't be created, overwritten or deleted through Calls, since they are Enum-based. 
	//Get Calls are permitted with standard authorization.
	@GetMapping("/api/roles")
	public ResponseEntity<Object> getAllRoles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2000") int size) {
		Pageable p = PageRequest.of(page, size);
		Page<Role> roles = rS.getAllRoles(p);		
		if (roles.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);				
		else return new ResponseEntity<>(roles, HttpStatus.OK);
	}
	
	@GetMapping("/api/roles/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
		Optional<Role> r = rS.getRoleById(id);
		if (r.isPresent()) return new ResponseEntity<>(r.get(), HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
