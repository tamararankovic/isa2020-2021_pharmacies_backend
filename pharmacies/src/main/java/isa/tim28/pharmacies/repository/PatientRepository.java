package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import isa.tim28.pharmacies.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{


	Patient findOneByUser_Id(long id);
}
