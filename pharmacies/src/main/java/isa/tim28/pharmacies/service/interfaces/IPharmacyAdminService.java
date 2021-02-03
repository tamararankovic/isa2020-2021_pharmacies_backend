package isa.tim28.pharmacies.service.interfaces;

import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.User;

public interface IPharmacyAdminService {

	PharmacyAdmin findByUser(User user) throws UserDoesNotExistException;
}
