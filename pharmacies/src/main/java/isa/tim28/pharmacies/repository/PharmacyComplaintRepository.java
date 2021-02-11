package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.PharmacyComplaint;

public interface PharmacyComplaintRepository  extends JpaRepository<PharmacyComplaint, Long> {

}
