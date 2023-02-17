package it.epicode.be.devicedistribution.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "smartphones")
@DiscriminatorValue("smartphone")
@Getter
@Setter
@NoArgsConstructor
public class Smartphone extends Device {

	public Smartphone(String n) {
		super(n);
	}
	
	public Smartphone(String n, Status s) {
		super(n, s);
	}
}
