package isa.tim28.pharmacies.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import isa.tim28.pharmacies.model.Medicine;


public interface MedicineRepository extends JpaRepository<Medicine, Long> {

	Medicine findOneByCode(String code);
	Medicine findByName(String name);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select m from Medicine m where m.name = :name")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
	public Medicine findMedicineByName(String name);
}
