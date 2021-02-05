package isa.tim28.pharmacies.service.interfaces;

import java.util.List;

import isa.tim28.pharmacies.dtos.ReservationDTO;
import isa.tim28.pharmacies.model.CancelledReservation;

public interface IReservationService {

	List<ReservationDTO> getReservationByPatient(long id);

	CancelledReservation cancelReservation(ReservationDTO dto, long id);

}
