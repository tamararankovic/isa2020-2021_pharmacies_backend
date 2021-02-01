package isa.tim28.pharmacies.service.interfaces;

import isa.tim28.pharmacies.dtos.PatientProfileDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.User;


public interface IPatientService {


	Patient getPatientById(long id) throws UserDoesNotExistException;
	
	User getUserPart(long id) throws UserDoesNotExistException;
	
	Patient editPatient(PatientProfileDTO newPatient);
	
	
	
}
