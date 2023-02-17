package it.epicode.be.devicedistribution.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notebooks")
@DiscriminatorValue("notebook")
@Getter
@Setter
@NoArgsConstructor
public class Notebook extends Device {

	public Notebook(String n) {
		super(n);
	}
	
	public Notebook(String n, Status s) {
		super(n, s);
	}
}
