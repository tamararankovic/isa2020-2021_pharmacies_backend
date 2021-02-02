package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;

import isa.tim28.pharmacies.dtos.PharmacistDTO;
import isa.tim28.pharmacies.dtos.PharmacistProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.InvalidDeleteUserAttemptException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.User;

public interface IPharmacistService {

	Pharmacist getPharmacistById(long id) throws UserDoesNotExistException;
	
	User getUserPart(long id) throws UserDoesNotExistException;
	
	User updatePharmacist(PharmacistProfileDTO newUser, long id) throws BadNameException, BadSurnameException, BadNewEmailException, UserDoesNotExistException;
	
	boolean checkOldPassword(long id, String oldPassword) throws UserDoesNotExistException, PasswordIncorrectException;
	
	void changePassword(long id, String newPassword) throws UserDoesNotExistException;

	Set<Pharmacist> findAllByPharmacyId(long pharmacyId);
	
	Set<PharmacistDTO> findAllByPharmacyAdmin(PharmacyAdmin admin);
	
	Set<PharmacistDTO> findAll();
	
	void deleteByPharmacyAdmin(long pharmacistId, PharmacyAdmin admin) throws UserDoesNotExistException, InvalidDeleteUserAttemptException;
}
