package isa.tim28.pharmacies.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.EngagementInPharmacy;
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
		if (dermatologistRepository.findById(id).isEmpty())
			throw new UserDoesNotExistException("Dermatologist does not exist!");
		else 
			return dermatologistRepository.findById(id).get();
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

	@Override
	public Set<Dermatologist> findAllByPharmacyId(long pharmacyId) {
		Set<Dermatologist> ret = new HashSet<Dermatologist>();
		for(Dermatologist d : dermatologistRepository.findAll())
			for (EngagementInPharmacy ep : d.getEngegementInPharmacies())
				if (ep.getPharmacy().getId() == pharmacyId)
					ret.add(d);
		return ret;
	}

}
