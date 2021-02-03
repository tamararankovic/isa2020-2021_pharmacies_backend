package isa.tim28.pharmacies.service.interfaces;

import isa.tim28.pharmacies.model.User;

public interface IUserService {
	User save(User user);
	boolean activate(Long id);
}
