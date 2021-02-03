package isa.tim28.pharmacies.service.interfaces;

import isa.tim28.pharmacies.dtos.PharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.UserPasswordChangeDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.User;

public interface IPharmacyAdminService {

	PharmacyAdmin findByUser(User user) throws UserDoesNotExistException;
	
	void update(PharmacyAdmin admin, PharmacyAdminDTO dto) throws BadNewEmailException, BadNameException, BadSurnameException;
	
	void changePassword(PharmacyAdmin admin, UserPasswordChangeDTO dto) throws PasswordIncorrectException;
}
