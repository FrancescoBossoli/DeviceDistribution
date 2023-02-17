package it.epicode.be.devicedistribution.models;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "devices")
@Setter
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) 
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	String name;
	@Enumerated(EnumType.STRING)
	Status status;
	@ManyToOne
	@JsonFormat
	private User user;
	
	public Device(String n) {
		setName(n);
		setStatus(Status.Available);
	}
	
	public Device(String n, Status s) {
		setName(n);
		setStatus(s);
	}
}
