package it.epicode.be.devicedistribution.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.be.devicedistribution.models.User;
import it.epicode.be.devicedistribution.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository uR;
	
	//This method saves a User in the database if username isn't already taken and if the email isn't already registered
	public void save(User u) {
		uR.save(u);
		log.info("The User has been saved in the Database.");
	}
	
//	public void update(User u) {
//		uR.updateUserById(u.getId(), u);
//	}
	
	public Optional<User> getUserById(Long id) {
		return uR.findById(id);
	}
	
	public Optional<User> getUserByUsername(String u) {
		return uR.findUserByUsername(u);
	}
	
	public Optional<User> getUserByEmail(String e) {
		return uR.findUserByEmail(e);
	}	
	
	public List<User> getAllUsers(){
		return uR.findAll(PageRequest.of(0 , 2000)).getContent();
	}
	
	public Page<User> getAllUsers(Pageable p) {
		return uR.findAll(p);
	}
	
	public void deleteUserById(Long id) {
		uR.deleteById(id);
	}
	
	public void printList(List<User> list) {
		for (User l : list)
			log.info(l.toString());
	}
}
