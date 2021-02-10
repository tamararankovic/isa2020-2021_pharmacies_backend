package isa.tim28.pharmacies.service.interfaces;

import isa.tim28.pharmacies.dtos.LoyaltyDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.SystemAdmin;
import isa.tim28.pharmacies.model.User;

public interface ISystemAdminService {
	SystemAdmin save(SystemAdmin systemAdmin);
	
	SystemAdmin findByUser(User user) throws UserDoesNotExistException;

	void createLoyalty(LoyaltyDTO dto);
}
