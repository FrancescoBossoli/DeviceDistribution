package it.epicode.be.devicedistribution.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.be.devicedistribution.models.Tablet;
import it.epicode.be.devicedistribution.models.Status;
import it.epicode.be.devicedistribution.repositories.TabletRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TabletService {

	@Autowired
	private TabletRepository nR;
	
	public void save(Tablet n) {
		nR.save(n);
		log.info("The Tablet has been saved in the Database.");	
	}
	
	public Optional<Tablet> getTabletById(Long id) {
		return nR.findById(id);
	}
	
	public List<Tablet> getTabletsByStatus(Status s) {
		return nR.findTabletsByStatus(s);
	}
	
	public List<Tablet> getTabletsByUsername(String u) {
		return nR.findTabletsByUsername(u);
	}	
	
	public List<Tablet> getAllTablets(){
		return nR.findAll(PageRequest.of(0 , 2000)).getContent();
	}
	
	public Page<Tablet> getAllTablets(Pageable p) {
		return nR.findAll(p);
	}
	
	public void deleteTabletById(Long id) {
		nR.deleteById(id);
	}
	
	public void printList(List<Tablet> list) {
		for (Tablet l : list)
			log.info(l.toString());
	}
}