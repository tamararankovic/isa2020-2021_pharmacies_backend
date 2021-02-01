package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

}
