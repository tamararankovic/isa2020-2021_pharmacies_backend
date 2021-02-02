package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
}
