package it.epicode.be.devicedistribution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import it.epicode.be.devicedistribution.models.Notebook;
import it.epicode.be.devicedistribution.models.Role;
import it.epicode.be.devicedistribution.models.RoleType;
import it.epicode.be.devicedistribution.models.Smartphone;
import it.epicode.be.devicedistribution.models.Tablet;
import it.epicode.be.devicedistribution.models.User;

@Configuration
public class BeanConfig {
	
	@Bean
	@Scope("prototype")
	public User newUser(String u, String e, String p, String fn, String ln) {
		return User.builder().username(u).email(e).password(p).name(fn).surname(ln).build();
	}
	
	@Bean
	@Scope("prototype")
	public Role newRole(RoleType r) {
		return Role.builder().name(r).build();
	}
	
	@Bean
	@Scope("prototype")
	public Tablet newTablet(String n) {		
		return new Tablet(n);
	}
	
	@Bean
	@Scope("prototype")
	public Smartphone newSmartphone(String n) {		
		return new Smartphone(n);
	}	
	
	@Bean
	@Scope("prototype")
	public Notebook newNotebook(String n) {		
		return new Notebook(n);
	}
}
