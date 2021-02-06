package isa.tim28.pharmacies.service.interfaces;


import isa.tim28.pharmacies.dtos.SupplierProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Supplier;
import isa.tim28.pharmacies.model.User;

public interface ISupplierService {
	Supplier save(Supplier supplier);
	
	User getUserPart(long id) throws UserDoesNotExistException;
	
	User updateSupplier(SupplierProfileDTO newUser, long id) throws BadNameException, BadSurnameException, BadNewEmailException, UserDoesNotExistException;
	
	boolean checkOldPassword(long id, String oldPassword) throws UserDoesNotExistException, PasswordIncorrectException;
	
	void changePassword(long id, String newPassword) throws UserDoesNotExistException;
	
	Supplier getSupplierById(long id) throws UserDoesNotExistException;
}
