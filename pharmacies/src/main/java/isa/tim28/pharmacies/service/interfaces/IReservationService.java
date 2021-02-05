package isa.tim28.pharmacies.service.interfaces;

import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.ReservationDTO;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.Reservation;

public interface IReservationService {

	List<ReservationDTO> getReservationByPatient(long id);
	
	Set<Reservation> getAllByPharamcy(Pharmacy pharmacy);

}
