package it.epicode.be.devicedistribution.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.be.devicedistribution.models.Role;
import it.epicode.be.devicedistribution.models.RoleType;
import it.epicode.be.devicedistribution.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleService {
	
	@Autowired
	private RoleRepository rR;
	
	public void save(Role r) {
		Optional<Role> o = getRoleByName(r.getName());
		if (o.isEmpty()) {
			rR.save(r);
			log.info("The Role has been saved in the Database.");			
		}
		else log.info("This Role is already present in the Database.");
	}
	
	public Optional<Role> getRoleByName(RoleType n) {
		return rR.findByName(n);
	}
	
	public Optional<Role> getRoleById(Long id) {
		return rR.findById(id);
	}
		
	public List<Role> getAllRoles(){
		return rR.findAll(PageRequest.of(0 , 2000)).getContent();
	}
	
	public Page<Role> getAllRoles(Pageable p) {
		return rR.findAll(p);
	}
	
	public void deleteRoleById(Long id) {
		rR.deleteById(id);
	}
	
	public void printList(List<Role> list) {
		for (Role l : list)
			log.info(l.toString());
	}

}
