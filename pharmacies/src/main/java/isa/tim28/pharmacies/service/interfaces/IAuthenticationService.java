package isa.tim28.pharmacies.service.interfaces;

import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.User;

public interface IAuthenticationService {

	User getUserByCredentials(String email, String password) throws UserDoesNotExistException, PasswordIncorrectException;
	User getUserByEmail(String email);
	boolean checkOldPassword(String email, String oldPassword) throws PasswordIncorrectException;
	void changePassword(String email, String newPassword) throws UserDoesNotExistException;
}
