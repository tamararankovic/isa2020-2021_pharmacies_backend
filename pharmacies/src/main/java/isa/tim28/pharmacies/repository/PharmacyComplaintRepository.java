package isa.tim28.pharmacies.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import isa.tim28.pharmacies.model.PharmacyComplaint;

public interface PharmacyComplaintRepository  extends JpaRepository<PharmacyComplaint, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from PharmacyComplaint p where p.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
	public PharmacyComplaint findPharmacyComplaintById(long id);
}
