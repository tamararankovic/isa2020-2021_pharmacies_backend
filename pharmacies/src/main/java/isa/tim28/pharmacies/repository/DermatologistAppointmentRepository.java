package isa.tim28.pharmacies.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.DermatologistAppointment;

public interface DermatologistAppointmentRepository extends JpaRepository<DermatologistAppointment, Long>{

	Set<DermatologistAppointment> findAllByPatient_Id(long patient_Id);
	
}
