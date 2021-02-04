package isa.tim28.pharmacies.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.PharmacistAppointment;

public interface PharmacistAppointmentRepository extends JpaRepository<PharmacistAppointment, Long> {

	Set<PharmacistAppointment> findAllByPatient_Id(long patient_Id);
	
	Set<PharmacistAppointment> findAllByPharmacist_Id(long pharmacist_Id);
	
}
