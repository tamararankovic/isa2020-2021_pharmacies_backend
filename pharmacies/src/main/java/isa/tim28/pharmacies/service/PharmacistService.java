package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.PharmacistRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.IPharmacistService;

@Service
public class PharmacistService implements IPharmacistService {

	private PharmacistRepository pharmacistRepository;
	private UserRepository userRepository;
	
	@Autowired
	public PharmacistService(PharmacistRepository pharmacistRepository, UserRepository userRepository) {
		super();
		this.pharmacistRepository = pharmacistRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public Pharmacist getPharmacistById(long id) throws UserDoesNotExistException {
		Pharmacist pharmacist = pharmacistRepository.findOneById(id);
		if (pharmacist == null)
			throw new UserDoesNotExistException("Pharmacist does not exist!");
		else 
			return pharmacist;
	}

	@Override
	public User getUserPart(long id) throws UserDoesNotExistException {
		User user = userRepository.findOneById(id);
		if (user == null)
			throw new UserDoesNotExistException("Pharmacist does not exist!");
		else 
			return user;
	}
}
