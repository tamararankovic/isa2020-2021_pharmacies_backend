package isa.tim28.pharmacies.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.PharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.UserPasswordChangeDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.PharmacyAdminRepository;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;
import isa.tim28.pharmacies.service.interfaces.IUserService;

@Service
public class PharmacyAdminService implements IPharmacyAdminService {

	private PharmacyAdminRepository pharmacyAdminRepository;
	private IUserService userService;
	
	@Autowired
	public PharmacyAdminService(PharmacyAdminRepository pharmacyAdminRepository, IUserService userService) {
		super();
		this.pharmacyAdminRepository = pharmacyAdminRepository;
		this.userService = userService;
	}

	@Override
	public PharmacyAdmin findByUser(User user) throws UserDoesNotExistException {
		Optional<PharmacyAdmin> admin = pharmacyAdminRepository.findAll().stream().filter(a -> a.getUser().getId() == user.getId()).findFirst();
		if (admin.isEmpty())
			throw new UserDoesNotExistException("Pharmacy admin not found");
		return admin.get();
	}

	@Override
	public void update(PharmacyAdmin admin, PharmacyAdminDTO dto) throws BadNewEmailException, BadNameException, BadSurnameException {
		if (userService.isEmailTaken(dto.getEmail()))
			throw new BadNewEmailException("Email is taken! Try another one");
		admin.getUser().setName(dto.getName());
		admin.getUser().setSurname(dto.getSurname());
		admin.getUser().setEmail(dto.getEmail());
		if (!admin.getUser().isNameValid())
			throw new BadNameException("Name must have between 2 and 30 characters!");
		if (!admin.getUser().isSurnameValid())
			throw new BadSurnameException("Surname must have between 2 and 30 characters!");
		if (!admin.getUser().isEmailValid())
			throw new BadNewEmailException("Email must have between 2 and 30 characters and contain @!");
		pharmacyAdminRepository.save(admin);
	}

	@Override
	public void changePassword(PharmacyAdmin admin, UserPasswordChangeDTO dto) throws PasswordIncorrectException {
		if(!dto.getPassword().equals(admin.getUser().getPassword()))
			throw new PasswordIncorrectException("Password incorrect. Try again!");
		admin.getUser().setPassword(dto.getNewPassword());
		if(!admin.getUser().isPasswordValid())
			throw new PasswordIncorrectException("New password must have between 4 and 30 characters!");
		pharmacyAdminRepository.save(admin);
	}

	@Override
	public PharmacyAdmin save(PharmacyAdmin pharmacyAdmin) {
		PharmacyAdmin newPharmacyAdmin = pharmacyAdminRepository.save(pharmacyAdmin);
		return newPharmacyAdmin;
	}

}
