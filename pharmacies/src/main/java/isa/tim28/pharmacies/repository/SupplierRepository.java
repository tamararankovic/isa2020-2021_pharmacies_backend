package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long>{
	
	Supplier findOneByUser_Id(long id);
}
