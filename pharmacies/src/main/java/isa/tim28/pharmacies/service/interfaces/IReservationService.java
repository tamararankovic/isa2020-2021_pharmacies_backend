package isa.tim28.pharmacies.service.interfaces;

import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import isa.tim28.pharmacies.dtos.ReservationDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.MedicineDoesNotExistException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Pharmacy;

public interface IReservationService {

	List<ReservationDTO> getReservationByPatient(long id) throws UserDoesNotExistException;
	
	Set<Reservation> getAllByPharamcy(Pharmacy pharmacy);

	List<ReservationDTO> cancelReservation(ReservationDTO dto, long id) throws UserDoesNotExistException;

	Reservation makeReservation(ReservationDTO dto, User user) throws UserDoesNotExistException, MessagingException;

	boolean pharmacyHasActiveReservationsForMedicine(Pharmacy pharmacy, Medicine med);

	void deleteMedicine(Pharmacy pharmacy, long medicineId)
			throws MedicineDoesNotExistException, ForbiddenOperationException;

}
