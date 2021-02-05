package isa.tim28.pharmacies.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.PharmacistLeaveRequest;

public interface PharmacistLeaveRequestRepository extends JpaRepository<PharmacistLeaveRequest, Long> {
	
	Set<PharmacistLeaveRequest> findAllByPharmacist_Id(long pharmacist_Id);

}
