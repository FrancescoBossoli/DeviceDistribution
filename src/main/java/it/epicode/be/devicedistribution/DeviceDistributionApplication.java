package it.epicode.be.devicedistribution;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.epicode.be.devicedistribution.config.BeanConfig;
import it.epicode.be.devicedistribution.models.Device;
import it.epicode.be.devicedistribution.models.Notebook;
import it.epicode.be.devicedistribution.models.Role;
import it.epicode.be.devicedistribution.models.RoleType;
import it.epicode.be.devicedistribution.models.Smartphone;
import it.epicode.be.devicedistribution.models.Status;
import it.epicode.be.devicedistribution.models.Tablet;
import it.epicode.be.devicedistribution.models.User;
import it.epicode.be.devicedistribution.services.DeviceService;
import it.epicode.be.devicedistribution.services.RoleService;
import it.epicode.be.devicedistribution.services.UserService;

@SpringBootApplication
public class DeviceDistributionApplication implements CommandLineRunner {
	
	private ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
	
	@Autowired
	UserService uS;
	@Autowired
	RoleService rS;
	@Autowired
	DeviceService dS;
	@Autowired
	PasswordEncoder pE;

	public static void main(String[] args) {
		SpringApplication.run(DeviceDistributionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		if (rS.getAllRoles().size() == 0) {
			populateDb();
		}		
	}
	
	public void populateDb() {		
		
		Role r1 = save(RoleType.Administrator);
		Role r2 = save(RoleType.Intern);
		Role r3 = save(RoleType.Manager);
		Role r4 = save(RoleType.Assistant);
		Role r5 = save(RoleType.Promoter);
		Role r6 = save(RoleType.Technician);
		Role r7 = save(RoleType.Employee);
		User u1 = save("Marius", "mariorossi@gmail.com", "mario123", "Mario", "Rossi", r1);
		User u2 = save("Blackie", "lucaneri@hotmail.com", "luca123", "Luca", "Neri", r2);
		User u3 = save("GreenHorn", "luigiverdi@aol.com", "luigi123", "Luigi", "Verdi", r3);
		User u4 = save("DoctorM", "marcobianchi@yahoo.com", "marco123", "Marco", "Bianchi", r4);
		User u5 = save("Sarexx", "saragrandi@email.com", "sara123", "Sara", "Grandi", r5);
		User u6 = save("Lauretta", "lauraesposito@bcc.com", "laura123", "Laura", "Esposito", r6);
		User u7 = save("Yoko", "katyferrari@samsung.com", "katy123", "Katy", "Ferrari", r7);
		save("Samsung Galaxy TabS7+", "tablet", u1);
		save("Lenovo Tab P11", "Tablet", u2);
		save("Samsung Galaxy TabS6", "Tablet", Status.Maintained);
		save("Samsung Galaxy TabA8", "Tablet", u7);
		save("Lenovo Tab M10", "Tablet", Status.Available);
		save("Lenovo IdeaPad 3", "Notebook", u1);
		save("HP Pavillion 15-EG2013NL", "Notebook", u5);
		save("Asus F515EA", "Notebook", u6);
		save("Lenovo V V15", "Notebook", Status.Available);
		save("Samsung S22", "Smartphone", u3);
		save("Samsung S7", "Smartphone", Status.Outdated);
		save("OnePlus 2", "Smartphone", u4);
		save("Xiaomi Redmi Note 11", "Smartphone", Status.Available);
		save("Motorola Moto G22", "Smartphone", Status.Available);
		save("Nokia Lumia 950", "Smartphone", Status.Outdated);		
	
	}
	
	public User save(String u, String e, String p, String fn, String ln, Role r) {
		User x = (User)ctx.getBean("newUser", u, e, pE.encode(p), fn, ln);
		Set<Role> y = new HashSet<Role>();
		y.add(r);
		x.setRoles(y);
		uS.save(x);
		return x;
	}
	
	public Role save(RoleType r) {
		Role x = (Role)ctx.getBean("newRole", r);
		rS.save(x);
		return x;
	}
	
	public Device save(String n, String t) {
		Device x = create(n, t);
		dS.save(x);
		return x;
	}
	
	public Device save(String n, String t, User u) {
		Device x = create(n, t);
		x.setUser(u);
		x.setStatus(Status.Taken);
		dS.save(x);
		return x;		
	}
	
	public Device save(String n, String t, Status s) {
		Device x = create(n, t);
		x.setStatus(s);
		dS.save(x);
		return x;
	}
	
	public Device create(String n, String t) {
		Device x = null;					
		switch (t.toLowerCase()) {
			case "tablet" -> x = (Tablet)ctx.getBean("newTablet", n);
			case "notebook" -> x = (Notebook)ctx.getBean("newNotebook", n);
			case "smartphone" -> x = (Smartphone)ctx.getBean("newSmartphone", n);
		}
		return x;
	}	
	
}
