package isa.tim28.pharmacies.service.interfaces;

import java.util.List;

import isa.tim28.pharmacies.dtos.ReservationDTO;
import isa.tim28.pharmacies.model.Reservation;


public interface IReservationService {

	List<ReservationDTO> getReservationByPatient(long id);

	List<ReservationDTO> cancelReservation(ReservationDTO dto, long id);

	Reservation makeReservation(ReservationDTO dto, long id);

}
