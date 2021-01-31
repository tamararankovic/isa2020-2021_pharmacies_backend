package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Pharmacist;

//Interface for pharmacist database access
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

	Pharmacist findOneById(long id);
}
