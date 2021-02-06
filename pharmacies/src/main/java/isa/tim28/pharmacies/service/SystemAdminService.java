package isa.tim28.pharmacies.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.SystemAdmin;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.SystemAdminRepository;
import isa.tim28.pharmacies.service.interfaces.ISystemAdminService;

@Service
public class SystemAdminService implements ISystemAdminService{

private SystemAdminRepository systemAdminRepository;
	
	@Autowired
	public SystemAdminService(SystemAdminRepository systemAdminRepository) {
		super();
		this.systemAdminRepository = systemAdminRepository;
	}
	
	@Override
	public SystemAdmin save(SystemAdmin systemAdmin) {
		SystemAdmin newSystemAdmin = systemAdminRepository.save(systemAdmin);
		return newSystemAdmin;
	}

	@Override
	public SystemAdmin findByUser(User user) throws UserDoesNotExistException {
		Optional<SystemAdmin> adminOpt = systemAdminRepository.findAll().stream().filter(a -> a.getUser().getId() == user.getId()).findFirst();
		if(adminOpt.isEmpty())
			throw new UserDoesNotExistException("System administrator couldn't be found!");
		return adminOpt.get();
	}
}
