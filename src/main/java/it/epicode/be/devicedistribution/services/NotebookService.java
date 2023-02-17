package it.epicode.be.devicedistribution.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.devicedistribution.models.Notebook;
import it.epicode.be.devicedistribution.models.Status;
import it.epicode.be.devicedistribution.repositories.NotebookRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotebookService {

	@Autowired
	private NotebookRepository nR;
	
	public void save(Notebook n) {
		nR.save(n);
		log.info("The Notebook has been saved in the Database.");	
	}
	
	public Optional<Notebook> getNotebookById(Long id) {
		return nR.findById(id);
	}
	
	public List<Notebook> getNotebooksByStatus(Status s) {
		return nR.findNotebooksByStatus(s);
	}
	
	public List<Notebook> getNotebooksByUsername(String u) {
		return nR.findNotebooksByUsername(u);
	}	
	
	public List<Notebook> getAllNotebooks(){
		return nR.findAll(PageRequest.of(0 , 2000)).getContent();
	}
	
	public Page<Notebook> getAllNotebooks(Pageable p) {
		return nR.findAll(p);
	}
	
	public void deleteNotebookById(Long id) {
		nR.deleteById(id);
	}
	
	public void printList(List<Notebook> list) {
		for (Notebook l : list)
			log.info(l.toString());
	}
}	
