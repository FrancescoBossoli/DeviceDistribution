package it.epicode.be.devicedistribution.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.epicode.be.devicedistribution.models.AccessDetails;
import it.epicode.be.devicedistribution.models.User;
import it.epicode.be.devicedistribution.repositories.UserRepository;

@Service
public class AccessDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository uR;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String x) throws UsernameNotFoundException {
		User u = uR.findUserByUsername(x)
				.orElseThrow(() -> new UsernameNotFoundException("No User with Username '" + x + "' was Found."));
		return AccessDetails.build(u);
	}
}