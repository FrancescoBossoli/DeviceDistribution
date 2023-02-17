package it.epicode.be.devicedistribution.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.be.devicedistribution.models.Smartphone;
import it.epicode.be.devicedistribution.models.Status;
import it.epicode.be.devicedistribution.repositories.SmartphoneRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmartphoneService {

	@Autowired
	private SmartphoneRepository nR;
	
	public void save(Smartphone n) {
		nR.save(n);
		log.info("The Smartphone has been saved in the Database.");	
	}
	
	public Optional<Smartphone> getSmartphoneById(Long id) {
		return nR.findById(id);
	}
	
	public List<Smartphone> getSmartphonesByStatus(Status s) {
		return nR.findSmartphonesByStatus(s);
	}
	
	public List<Smartphone> getSmartphonesByUsername(String u) {
		return nR.findSmartphonesByUsername(u);
	}	
	
	public List<Smartphone> getAllSmartphones(){
		return nR.findAll(PageRequest.of(0 , 2000)).getContent();
	}
	
	public Page<Smartphone> getAllSmartphones(Pageable p) {
		return nR.findAll(p);
	}
	
	public void deleteSmartphoneById(Long id) {
		nR.deleteById(id);
	}
	
	public void printList(List<Smartphone> list) {
		for (Smartphone l : list)
			log.info(l.toString());
	}
}	
