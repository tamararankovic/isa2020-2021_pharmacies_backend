package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.PharmacistProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
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

	@Override
	public User updatePharmacist(PharmacistProfileDTO newUser, long id) throws BadNameException, BadSurnameException, BadNewEmailException, UserDoesNotExistException {
		User user;
		user = getUserPart(id);
		user.setName(newUser.getName());
		user.setSurname(newUser.getSurname());
		user.setEmail(newUser.getEmail());
		
		if(!user.isNameValid()) throw new BadNameException("Bad name. Try again.");
		if(!user.isSurnameValid()) throw new BadSurnameException("Bad surname. Try again.");
		if(!user.isEmailValid()) throw new BadNewEmailException("Bad email. Try again.");
		
		userRepository.save(user);
		return user;
	}
	
	@Override
	public boolean checkOldPassword(long id, String oldPassword) throws UserDoesNotExistException, PasswordIncorrectException {
		User user = getUserPart(id);
		
		if(!user.getPassword().equals(oldPassword)) throw new PasswordIncorrectException("Old password is incorrect.");
		
		return true;
	}

	@Override
	public void changePassword(long id, String newPassword) throws UserDoesNotExistException {
		User user = getUserPart(id);
		user.setPassword(newPassword);
		
		userRepository.save(user);
	}
}
