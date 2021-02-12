package isa.tim28.pharmacies.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import isa.tim28.pharmacies.model.PharmacistComplaint;


public interface PharmacistComplaintRepository  extends JpaRepository<PharmacistComplaint, Long>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from PharmacistComplaint p where p.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
	public PharmacistComplaint findPharmacistComplaintById(long id);
}
