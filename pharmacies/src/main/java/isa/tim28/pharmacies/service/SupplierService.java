package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import isa.tim28.pharmacies.dtos.SupplierProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Supplier;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.SupplierRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.ISupplierService;


@Service
public class SupplierService implements ISupplierService {
	
	private SupplierRepository supplierRepository;
	private UserRepository userRepository;
	
	@Autowired
	public SupplierService(SupplierRepository supplierRepository, UserRepository userRepository) {
		super();
		this.supplierRepository = supplierRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public Supplier getSupplierById(long id) throws UserDoesNotExistException {
		if (supplierRepository.findOneByUser_Id(id) == null)
			throw new UserDoesNotExistException("Supplier does not exist!");
		else
			return supplierRepository.findOneByUser_Id(id);
	}
	
	@Override
	public Supplier save(Supplier supplier) {
		Supplier newSupplier = supplierRepository.save(supplier);
		return newSupplier;
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
	public User updateSupplier(SupplierProfileDTO newUser, long id) throws BadNameException, BadSurnameException, BadNewEmailException, UserDoesNotExistException {
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
