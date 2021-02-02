package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.PharmacistAppointment;

public interface PharmacistAppointmentRepository extends JpaRepository<PharmacistAppointment, Long> {

}
