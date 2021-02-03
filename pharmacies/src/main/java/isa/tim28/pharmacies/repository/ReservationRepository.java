package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
