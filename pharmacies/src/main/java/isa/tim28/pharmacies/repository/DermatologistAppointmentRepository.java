package isa.tim28.pharmacies.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import isa.tim28.pharmacies.model.DermatologistAppointment;

public interface DermatologistAppointmentRepository extends JpaRepository<DermatologistAppointment, Long>{

	Set<DermatologistAppointment> findAllByPatient_Id(long patient_Id);

	Set<DermatologistAppointment> findAllByDermatologist_Id(long dermatologist_Id);
	
	DermatologistAppointment save(DermatologistAppointment da);
	
	
}
