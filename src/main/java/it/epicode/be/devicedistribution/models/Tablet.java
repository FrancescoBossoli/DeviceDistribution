package it.epicode.be.devicedistribution.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tablets")
@DiscriminatorValue("tablet")
@Getter
@Setter
@NoArgsConstructor
public class Tablet extends Device {

	public Tablet(String n) {
		super(n);
	}
	
	public Tablet(String n, Status s) {
		super(n, s);
	}
}
