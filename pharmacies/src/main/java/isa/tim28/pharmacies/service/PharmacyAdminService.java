package isa.tim28.pharmacies.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.PharmacyAdminRepository;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;

@Service
public class PharmacyAdminService implements IPharmacyAdminService {

	private PharmacyAdminRepository pharmacyAdminRepository;
	
	@Autowired
	public PharmacyAdminService(PharmacyAdminRepository pharmacyAdminRepository) {
		super();
		this.pharmacyAdminRepository = pharmacyAdminRepository;
	}

	@Override
	public PharmacyAdmin findByUser(User user) throws UserDoesNotExistException {
		Optional<PharmacyAdmin> admin = pharmacyAdminRepository.findAll().stream().filter(a -> a.getUser().getId() == user.getId()).findFirst();
		if (admin.isEmpty())
			throw new UserDoesNotExistException("Pharmacy admin not found");
		return admin.get();
	}

}
