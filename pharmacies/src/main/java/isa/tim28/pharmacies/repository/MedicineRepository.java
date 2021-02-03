package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

	Medicine findOneByCode(String code);
}
