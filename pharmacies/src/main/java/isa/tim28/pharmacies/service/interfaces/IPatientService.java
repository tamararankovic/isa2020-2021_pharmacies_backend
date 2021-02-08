package isa.tim28.pharmacies.service.interfaces;

import java.util.ArrayList;
import java.util.List;

import isa.tim28.pharmacies.dtos.ERecepyDTO;
import isa.tim28.pharmacies.dtos.PatientProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.User;


public interface IPatientService {


	Patient getPatientById(long id) throws UserDoesNotExistException;
	
	User getUserPart(long id) throws UserDoesNotExistException;

	Patient editPatient(PatientProfileDTO newPatient, long id) throws UserDoesNotExistException, BadNameException, BadSurnameException;

	void changePassword(long id, String newPassword) throws UserDoesNotExistException;

	boolean checkOldPassword(long id, String oldPassword) throws UserDoesNotExistException, PasswordIncorrectException;

	ArrayList<String> getAllAllergies(Patient patient);

	ArrayList<String> getAllMedicine(Patient patient);
	
	Patient save(Patient patient);

	List<ERecepyDTO> getPharmaciesWithMedicines(String decodedQr);

	void choosePharmacy(Patient patient, ERecepyDTO dto) throws PharmacyNotFoundException;

	List<ERecepyDTO> sortByPrice(List<ERecepyDTO> dtos);

	List<ERecepyDTO> sortByRating(List<ERecepyDTO> dtos);

	List<ERecepyDTO> sortByPharmacyName(List<ERecepyDTO> dtos);

	List<ERecepyDTO> sortByPharmacyAddress(List<ERecepyDTO> dtos);

}
	
	
