package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Dermatologist;

//Interface for dermatologist database access
public interface DermatologistRepository extends JpaRepository<Dermatologist, Long> {

	Dermatologist findOneById(long id);
	
}
