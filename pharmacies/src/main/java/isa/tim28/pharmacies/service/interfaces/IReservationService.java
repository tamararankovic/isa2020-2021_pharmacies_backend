package isa.tim28.pharmacies.service.interfaces;

import java.util.List;

import isa.tim28.pharmacies.dtos.ReservationDTO;

public interface IReservationService {

	List<ReservationDTO> getReservationByPatient(long id);

}
