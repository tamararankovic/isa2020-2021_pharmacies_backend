package isa.tim28.pharmacies.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.DermatologistLeaveRequest;

public interface DermatologistLeaveRequestRepository extends JpaRepository<DermatologistLeaveRequest, Long>  {

	Set<DermatologistLeaveRequest> findAllByDermatologist_Id(long dermatologist_Id);
	
}
