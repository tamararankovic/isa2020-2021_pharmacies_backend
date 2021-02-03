package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.MedicineQuantity;

public interface MedicineQuantityRepository extends JpaRepository<MedicineQuantity, Long> {
	
	MedicineQuantity findOneByMedicine_Id(long medicineId);

}
