package isa.tim28.pharmacies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.CancelledReservation;

public interface CancelledReservationRepository extends JpaRepository<CancelledReservation, Long> {

	List<CancelledReservation> findByPatient_Id(long id);
}
