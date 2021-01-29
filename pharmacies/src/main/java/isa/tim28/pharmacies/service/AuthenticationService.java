package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService {

	private UserRepository userRepository;
	
	@Autowired
	public AuthenticationService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	@Override
	public User getUserByCredentials(String email, String password) throws UserDoesNotExistException, PasswordIncorrectException {
		User user = userRepository.findOneByEmail(email);
		if (user == null)
			throw new UserDoesNotExistException("User with provided email does not exist!");
		if (!user.getPassword().equals(password))
			throw new PasswordIncorrectException("Password is incorrect!");
		else
			return user;
	}
}
