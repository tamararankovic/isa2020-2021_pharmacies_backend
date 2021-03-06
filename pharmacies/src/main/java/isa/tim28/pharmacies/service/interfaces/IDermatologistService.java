package isa.tim28.pharmacies.service.interfaces;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.ComplaintDTO;
import isa.tim28.pharmacies.dtos.DermPharmacyDTO;
import isa.tim28.pharmacies.dtos.DermatologistDTO;
import isa.tim28.pharmacies.dtos.DermatologistForComplaintDTO;
import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.dtos.DermatologistToEmployDTO;
import isa.tim28.pharmacies.dtos.DoctorRatingDTO;
import isa.tim28.pharmacies.dtos.NewDermatologistInPharmacyDTO;
import isa.tim28.pharmacies.exceptions.AddingDermatologistToPharmacyException;
import isa.tim28.pharmacies.dtos.PatientSearchDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.InvalidComplaintException;
import isa.tim28.pharmacies.exceptions.InvalidDeleteUserAttemptException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Rating;
import isa.tim28.pharmacies.model.User;

public interface IDermatologistService {

	Dermatologist getDermatologistById(long id) throws UserDoesNotExistException;
	
	User getUserPart(long id) throws UserDoesNotExistException;
	
	User updateDermatologist(DermatologistProfileDTO newUser, long id) throws BadNameException, BadSurnameException, BadNewEmailException, UserDoesNotExistException;
	
	boolean checkOldPassword(long id, String oldPassword) throws UserDoesNotExistException, PasswordIncorrectException;
	
	void changePassword(long id, String newPassword) throws UserDoesNotExistException;

	Set<Dermatologist> findAllByPharmacyId(long pharmacyId);
	
	Set<DermatologistDTO> findAllByPharmacyAdmin(PharmacyAdmin admin);
	
	Set<DermatologistDTO> findAll();
	
	void deleteByPharmacyAdmin(long dermatologistId, PharmacyAdmin admin) throws UserDoesNotExistException, InvalidDeleteUserAttemptException;
	
	Set<DermatologistDTO> search(String fullName);
	
	Set<DermatologistDTO> searchByPharmacyAdmin(String fullName, PharmacyAdmin admin);
	
	Set<DermatologistToEmployDTO> findUnemployedByPharmacyAdmin(Pharmacy pharmacy);
	
	void addToPharmacy(NewDermatologistInPharmacyDTO dto, Pharmacy pharmacy) throws AddingDermatologistToPharmacyException, UserDoesNotExistException;
	
	List<PatientSearchDTO> getAllPatientsByNameAndSurname(String name, String surname);
	
	Dermatologist save(Dermatologist dermatologist);
	
	List<DermPharmacyDTO> getAllPharmaciesByDermatologist(long userId);
	
	void createPredefinedAppointment(long dermatologistId, LocalDateTime startDateTime, int durationInMinutes, long price, Pharmacy pharmacy) throws UserDoesNotExistException, ForbiddenOperationException;

	List<DermatologistForComplaintDTO> getAllDermatologists();

	boolean createComplaint(Patient patient, ComplaintDTO dto) throws InvalidComplaintException, UserDoesNotExistException;
	List<DoctorRatingDTO> getAllDoctorsForRating(long id);

	List<Rating> getRatingsByDermatologist(long dermId, long patientId) throws UserDoesNotExistException;

	Rating saveDermatologistRating(DoctorRatingDTO dto, long id);

	List<DoctorRatingDTO> getPharmaciesFromReservations(long id) throws PharmacyNotFoundException;
}
