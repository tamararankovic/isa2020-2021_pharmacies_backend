package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import isa.tim28.pharmacies.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{


	Patient findOneByUser_Id(long id);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from Patient p where p.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
	public Patient findPatientById(long id);
	
}
