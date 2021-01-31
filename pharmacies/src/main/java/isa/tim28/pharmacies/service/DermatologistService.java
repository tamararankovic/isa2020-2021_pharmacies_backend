package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.DermatologistRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistService;

@Service
public class DermatologistService implements IDermatologistService {

	private DermatologistRepository dermatologistRepository;
	private UserRepository userRepository;
	
	@Autowired
	public DermatologistService(DermatologistRepository dermatolgistRepository, UserRepository userRepository) {
		super();
		this.dermatologistRepository = dermatolgistRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public Dermatologist getDermatologistById(long id) throws UserDoesNotExistException {
		Dermatologist dermatologist = dermatologistRepository.findOneById(id);
		if (dermatologist == null)
			throw new UserDoesNotExistException("Dermatologist does not exist!");
		else 
			return dermatologist;
	}

	@Override
	public User getUserPart(long id) throws UserDoesNotExistException {
		User user = userRepository.findOneById(id);
		if (user == null)
			throw new UserDoesNotExistException("Dermatologist does not exist!");
		else 
			return user;
	}

	@Override
	public User updateDermatologist(DermatologistProfileDTO newUser, long id) throws BadNameException, BadSurnameException, BadNewEmailException, UserDoesNotExistException {
		User user;
		user = getUserPart(id);
		user.setName(newUser.getName());
		user.setSurname(newUser.getSurname());
		user.setEmail(newUser.getEmail());
		System.out.println(newUser.getName());
		
		if(!user.isNameValid()) throw new BadNameException("Bad name. Try again.");
		if(!user.isSurnameValid()) throw new BadSurnameException("Bad surname. Try again.");
		if(!user.isEmailValid()) throw new BadNewEmailException("Bad email. Try again.");

		userRepository.save(user);
		return user;
	}

}
