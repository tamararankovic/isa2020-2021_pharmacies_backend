package isa.tim28.pharmacies.service.interfaces;

import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.DoctorRatingDTO;
import isa.tim28.pharmacies.dtos.ReservationDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.MedicineDoesNotExistException;
import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.Rating;

public interface IReservationService {

	List<ReservationDTO> getReservationByPatient(long id);
	
	Set<Reservation> getAllByPharamcy(Pharmacy pharmacy);

	List<ReservationDTO> cancelReservation(ReservationDTO dto, long id);

	Reservation makeReservation(ReservationDTO dto, User user);

	boolean pharmacyHasActiveReservationsForMedicine(Pharmacy pharmacy, Medicine med);

	void deleteMedicine(Pharmacy pharmacy, long medicineId)
			throws MedicineDoesNotExistException, ForbiddenOperationException;

	List<DoctorRatingDTO> getMedicineForRating(long userId);

	Rating saveMedicineRating(DoctorRatingDTO dto, long id);

}
