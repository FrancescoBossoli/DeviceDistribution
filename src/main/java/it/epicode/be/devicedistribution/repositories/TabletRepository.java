package it.epicode.be.devicedistribution.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.be.devicedistribution.models.Tablet;
import it.epicode.be.devicedistribution.models.Status;

@Repository
public interface TabletRepository extends PagingAndSortingRepository<Tablet, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM notebooks WHERE status = :s")
	List<Tablet> findTabletsByStatus(@Param("s") Status s);
	
	@Query(nativeQuery = true, value = "SELECT * FROM notebooks d INNER JOIN users u ON d.user_id = u.id WHERE u.username = :u")
	List<Tablet> findTabletsByUsername(@Param("u") String u);
}
