package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.IUserService;

@Service
public class UserService implements IUserService {

private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	@Override
	public User save(User user) {
		User newUser = userRepository.save(user);
		return newUser;
	}
	@Override
	public boolean activate(Long id) {
		User user = userRepository.findOneById(id);
		if(user == null) {
			return false;
		} else {
		user.setActive(true);
		userRepository.save(user);
		return true;
		}		
	}

}
