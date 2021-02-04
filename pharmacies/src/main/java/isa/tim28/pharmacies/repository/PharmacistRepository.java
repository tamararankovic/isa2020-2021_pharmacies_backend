package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Pharmacist;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
	
	Pharmacist findOneByUser_Id(long user_id);

}
