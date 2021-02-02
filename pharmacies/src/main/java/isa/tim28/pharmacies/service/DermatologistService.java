package isa.tim28.pharmacies.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DermatologistDTO;
import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.InvalidDeleteUserAttemptException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.mapper.DermatologistMapper;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.EngagementInPharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.DermatologistRepository;
import isa.tim28.pharmacies.repository.EngagementInPharmacyRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistAppointmentService;
import isa.tim28.pharmacies.service.interfaces.IDermatologistService;

@Service
public class DermatologistService implements IDermatologistService {

	private DermatologistRepository dermatologistRepository;
	private UserRepository userRepository;
	private DermatologistMapper dermatologistMapper;
	private IDermatologistAppointmentService appointmentService;
	private EngagementInPharmacyRepository engagementRepository;
	
	@Autowired
	public DermatologistService(DermatologistRepository dermatolgistRepository, UserRepository userRepository, DermatologistMapper dermatologistMapper, IDermatologistAppointmentService appointmentService, EngagementInPharmacyRepository engagementRepository) {
		super();
		this.dermatologistRepository = dermatolgistRepository;
		this.userRepository = userRepository;
		this.dermatologistMapper = dermatologistMapper;
		this.appointmentService = appointmentService;
		this.engagementRepository = engagementRepository;
		
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

	@Override
	public Set<DermatologistDTO> findAllByPharmacyAdmin(PharmacyAdmin admin) {
		Set<DermatologistDTO> dtos = new HashSet<DermatologistDTO>();
		List<Dermatologist> dermatologists = dermatologistRepository.findAll();
		for(Dermatologist dermatologist : dermatologists)
			if (dermatologist.hasEngagementInPharmacy(admin.getPharmacy()))
				dtos.add(dermatologistMapper.dermatologistToDermatologistDTO(dermatologist));
		return dtos;
	}

	@Override
	public Set<DermatologistDTO> findAll() {
		Set<DermatologistDTO> dtos = new HashSet<DermatologistDTO>();
		List<Dermatologist> dermatologists = dermatologistRepository.findAll();
		for(Dermatologist dermatologist : dermatologists)
			dtos.add(dermatologistMapper.dermatologistToDermatologistDTO(dermatologist));
		return dtos;
	}

	@Override
	public void deleteByPharmacyAdmin(long dermatologistId, PharmacyAdmin admin)
			throws UserDoesNotExistException, InvalidDeleteUserAttemptException {
		Optional<Dermatologist> dermatologistOptional = dermatologistRepository.findById(dermatologistId);
		if(dermatologistOptional.isEmpty())
			throw new UserDoesNotExistException("You attempted to delete a pharmacist that does not exist!");
		Dermatologist dermatologist = dermatologistOptional.get();
		if(!dermatologist.hasEngagementInPharmacy(admin.getPharmacy()))
			throw new InvalidDeleteUserAttemptException("It is not allowed to delete a dermatologist that does not work in your pharmacy!");
		if(appointmentService.dermatologistHasIncomingAppointmentsInPharmacy(dermatologist, admin.getPharmacy()))
			throw new InvalidDeleteUserAttemptException("Dermatologist has incoming appointments!");
		
		Set<Long> engagementIds = new HashSet<Long>();
		for(EngagementInPharmacy e : dermatologist.getEngegementInPharmacies())
			if(e.getPharmacy().getId() == admin.getPharmacy().getId())
				engagementIds.add(e.getId());
		dermatologist.getEngegementInPharmacies().removeIf(e -> e.getPharmacy().getId() == admin.getPharmacy().getId());
		dermatologistRepository.save(dermatologist);
		for (Long engid : engagementIds)
			engagementRepository.deleteById(engid);
		appointmentService.deleteUnscheduledAppointments(dermatologist);
	}

}
