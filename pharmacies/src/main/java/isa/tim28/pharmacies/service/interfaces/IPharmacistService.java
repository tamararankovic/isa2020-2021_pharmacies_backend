package isa.tim28.pharmacies.service.interfaces;

import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.ComplaintDTO;
import isa.tim28.pharmacies.dtos.DermatologistForComplaintDTO;
import isa.tim28.pharmacies.dtos.DoctorRatingDTO;
import isa.tim28.pharmacies.dtos.NewPharmacistDTO;
import isa.tim28.pharmacies.dtos.PharmacistAppointmentDTO;
import isa.tim28.pharmacies.dtos.PharmacistDTO;
import isa.tim28.pharmacies.dtos.PharmacistProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.CreatePharmacistException;
import isa.tim28.pharmacies.exceptions.InvalidComplaintException;
import isa.tim28.pharmacies.exceptions.InvalidDeleteUserAttemptException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Rating;
import isa.tim28.pharmacies.model.User;

public interface IPharmacistService {

	Pharmacist getPharmacistById(long id) throws UserDoesNotExistException;
	
	User getUserPart(long id) throws UserDoesNotExistException;
	
	Pharmacist getPharmacistFromUser(long userId);
	
	User updatePharmacist(PharmacistProfileDTO newUser, long id) throws BadNameException, BadSurnameException, BadNewEmailException, UserDoesNotExistException;
	
	boolean checkOldPassword(long id, String oldPassword) throws UserDoesNotExistException, PasswordIncorrectException;
	
	void changePassword(long id, String newPassword) throws UserDoesNotExistException;

	Set<Pharmacist> findAllByPharmacyId(long pharmacyId);
	
	Set<PharmacistDTO> findAllByPharmacyAdmin(PharmacyAdmin admin);
	
	Set<PharmacistDTO> findAll();
	
	void deleteByPharmacyAdmin(long pharmacistId, PharmacyAdmin admin) throws UserDoesNotExistException, InvalidDeleteUserAttemptException;

	Set<PharmacistDTO> search(String fullName);
	
	Set<PharmacistDTO> searchByPharmacyAdmin(String fullName, PharmacyAdmin admin);
	
	void create(NewPharmacistDTO dto, Pharmacy pharmacy) throws CreatePharmacistException;

	List<DermatologistForComplaintDTO> getAllPharmacists();

	boolean createComplaint(Patient patient, ComplaintDTO dto) throws InvalidComplaintException, UserDoesNotExistException;
	
	List<PharmacistDTO> getAvailablePharmacistsByPharmacy(PharmacistAppointmentDTO dto);
	
	List<Rating> getRatingsByPharmacist(long pharmId, long patientId) throws UserDoesNotExistException;
	
	List<DoctorRatingDTO> getAllDoctorsForRating(long id);

	Rating savePharmacistRating(DoctorRatingDTO dto, long id);

	List<DoctorRatingDTO> getPharmaciesFromReservations(long id) throws PharmacyNotFoundException;
}
