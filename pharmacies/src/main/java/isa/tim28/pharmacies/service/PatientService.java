package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.PatientProfileDTO;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.IPatientService;

@Service
public class PatientService implements IPatientService{


	private PatientRepository patientRepository;
	private UserRepository userRepository;
	
	@Autowired
	public PatientService(PatientRepository patientRepository, UserRepository  userRepository) {
		super();
		this.patientRepository = patientRepository;
		this.userRepository = userRepository;
	}
	
	
	@Override
	public Patient getPatientById(long id) throws UserDoesNotExistException {
		if (patientRepository.findOneByUser_Id(id) == null)
			throw new UserDoesNotExistException("Patient does not exist!");
		else 
			return patientRepository.findOneByUser_Id(id);
	}

	@Override
	public User getUserPart(long id) throws UserDoesNotExistException {
		User user = userRepository.findOneById(id);
		if (user == null)
			throw new UserDoesNotExistException("Patient does not exist!");
		else 
			return user;
	}

	@Override
	public Patient editPatient(PatientProfileDTO newPatient, long id) throws UserDoesNotExistException {
		User user;
		user = getUserPart(id);
		user.setName(newPatient.getName());
		user.setSurname(newPatient.getSurname());
		
		Patient patient = getPatientById(id);
		patient.setAddress(newPatient.getAddress());
		patient.setCity(newPatient.getCity());
		patient.setCountry(newPatient.getCountry());
		patient.setPhone(newPatient.getPhone());
		
		/*if(!user.isNameValid()) throw new BadNameException("Bad name. Try again.");
		if(!user.isSurnameValid()) throw new BadSurnameException("Bad surname. Try again.");
		if(!user.isEmailValid()) throw new BadNewEmailException("Bad email. Try again.");*/

		userRepository.save(user);
		patientRepository.save(patient);
		return patient;
	}
	
	@Override
	public void changePassword(long id, String newPassword) throws UserDoesNotExistException {
		User user = getUserPart(id);
		user.setPassword(newPassword);
		
		userRepository.save(user);
	}


	@Override
	public boolean checkOldPassword(long id, String oldPassword)
			throws UserDoesNotExistException, PasswordIncorrectException {
		// TODO Auto-generated method stub
		return false;
	}


}
