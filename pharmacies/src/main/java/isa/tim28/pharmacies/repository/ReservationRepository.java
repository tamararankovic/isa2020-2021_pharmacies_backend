package isa.tim28.pharmacies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findByPatient_Id(long id);
	
	List<Reservation> findAllByPatient_Id(long id);
}
