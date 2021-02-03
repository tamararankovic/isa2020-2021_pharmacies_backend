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

	@Override
	public User getUserByEmail(String email) {
		User user = userRepository.findOneByEmail(email);
		if (user == null)
			return null;
		else 
			return user;
	}

	@Override
	public boolean checkOldPassword(String email, String oldPassword) throws PasswordIncorrectException {
		User user = getUserByEmail(email);
		if(!user.getPassword().equals(oldPassword)) {
			throw new PasswordIncorrectException("Old password is incorrect.");
		}
		return true;
	}

	@Override
	public void changePassword(String email, String newPassword) throws UserDoesNotExistException {
		User user = getUserByEmail(email);
		user.setPassword(newPassword);
		user.setLoged(true);
		userRepository.save(user);
	}
	
	
}
