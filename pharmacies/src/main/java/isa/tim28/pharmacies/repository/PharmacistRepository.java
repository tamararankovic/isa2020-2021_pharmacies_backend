package isa.tim28.pharmacies.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import isa.tim28.pharmacies.model.Pharmacist;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
	
	Pharmacist findOneByUser_Id(long user_id);

	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select p from Pharmacist p where p.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="5000")})
	Pharmacist findOneById(@Param("id")Long id);
}
