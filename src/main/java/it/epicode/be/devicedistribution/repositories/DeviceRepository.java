package it.epicode.be.devicedistribution.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.be.devicedistribution.models.Device;
import it.epicode.be.devicedistribution.models.Status;

@Repository
public interface DeviceRepository extends PagingAndSortingRepository<Device, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM devices WHERE name = :n")
	List<Device> findDeviceByName(@Param("n") String n);
	
	@Query(nativeQuery = true, value = "SELECT * FROM devices WHERE type = :t")
	List<Device> findDevicesByType(@Param("t") String t);
	
	@Query(nativeQuery = true, value = "SELECT * FROM devices WHERE status = :s")
	List<Device> findDevicesByStatus(@Param("s") Status s);
	
	@Query(nativeQuery = true, value = "SELECT * FROM devices d INNER JOIN users u ON d.user_id = u.id WHERE u.username = :u")
	List<Device> findDevicesByUsername(@Param("u") String u);
}
