package isa.tim28.pharmacies.service.interfaces;

import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.dtos.PatientSearchDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.User;

public interface IDermatologistService {

	Dermatologist getDermatologistById(long id) throws UserDoesNotExistException;
	
	User getUserPart(long id) throws UserDoesNotExistException;
	
	User updateDermatologist(DermatologistProfileDTO newUser, long id) throws BadNameException, BadSurnameException, BadNewEmailException, UserDoesNotExistException;
	
	boolean checkOldPassword(long id, String oldPassword) throws UserDoesNotExistException, PasswordIncorrectException;
	
	void changePassword(long id, String newPassword) throws UserDoesNotExistException;

	Set<Dermatologist> findAllByPharmacyId(long pharmacyId);
	
	List<PatientSearchDTO> getAllPatientsByNameAndSurname(String name, String surname);
}