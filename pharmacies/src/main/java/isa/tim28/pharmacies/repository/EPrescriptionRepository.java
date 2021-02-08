package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.EPrescription;


public interface EPrescriptionRepository   extends JpaRepository<EPrescription, Long> {

}
