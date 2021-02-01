package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.PatientProfileDTO;
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
		if (patientRepository.findOneByUserId(id) == null)
			throw new UserDoesNotExistException("Patient does not exist!");
		else 
			return patientRepository.findById(id).get();
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
	public Patient editPatient(PatientProfileDTO newPatient) {
		// TODO Auto-generated method stub
		return null;
	}

}
