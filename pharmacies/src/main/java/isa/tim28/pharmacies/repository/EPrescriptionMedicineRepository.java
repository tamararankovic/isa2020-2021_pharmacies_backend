package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.EPrescriptionMedicine;

public interface EPrescriptionMedicineRepository extends JpaRepository<EPrescriptionMedicine, Long> {

}
