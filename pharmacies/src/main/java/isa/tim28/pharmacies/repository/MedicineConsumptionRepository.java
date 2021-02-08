package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.MedicineConsumption;

public interface MedicineConsumptionRepository extends JpaRepository<MedicineConsumption, Long> {

}
