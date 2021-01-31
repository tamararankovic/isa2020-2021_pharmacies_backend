package isa.tim28.pharmacies.service.interfaces;

import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.User;

public interface IPharmacistService {

	Pharmacist getPharmacistById(long id) throws UserDoesNotExistException;
	
	User getUserPart(long id) throws UserDoesNotExistException;
}
