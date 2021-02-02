package isa.tim28.pharmacies.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.PatientProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.MedicineRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.IPatientService;

@Service
public class PatientService implements IPatientService {

	private PatientRepository patientRepository;
	private UserRepository userRepository;
	private MedicineRepository medicineRepository;

	@Autowired
	public PatientService(PatientRepository patientRepository, UserRepository userRepository,
			MedicineRepository medicineRepository) {
		super();
		this.patientRepository = patientRepository;
		this.userRepository = userRepository;
		this.medicineRepository = medicineRepository;
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
	public Patient editPatient(PatientProfileDTO newPatient, long id) throws UserDoesNotExistException, BadNameException, BadSurnameException{
		User user;
		user = getUserPart(id);
		user.setName(newPatient.getName());
		user.setSurname(newPatient.getSurname());

		Patient patient = getPatientById(id);
		patient.setAddress(newPatient.getAddress());
		patient.setCity(newPatient.getCity());
		patient.setCountry(newPatient.getCountry());
		patient.setPhone(newPatient.getPhone());

		ArrayList<String> allergies = newPatient.getAllergies();
		Set<Medicine> medicine = new HashSet<Medicine>();
		for (String s : allergies) {
			medicine.add(medicineRepository.findByName(s));
		}
		patient.setAllergies(medicine);

		if (!user.isNameValid())
			throw new BadNameException("Bad name. Try again.");
		if (!user.isSurnameValid())
			throw new BadSurnameException("Bad surname. Try again.");
		
			
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

	@Override
	public ArrayList<String> getAllMedicine(Patient patient) {
		List<Medicine> medicine = medicineRepository.findAll();
		ArrayList<String> allergies = getAllAllergies(patient);

		boolean medExistsInAllergies = false;
		ArrayList<String> res = new ArrayList<String>();
		if (allergies.isEmpty()) {
			for (Medicine me : medicine) {
				res.add(me.getName());
			}
		} else {
			for (Medicine m : medicine) {
				medExistsInAllergies = false;
				for (String s : allergies) {
					if (m.getName().equals(s)) {
						medExistsInAllergies = true;
					}
				}
				if (!medExistsInAllergies) {
					res.add(m.getName());
				}

			}
		}
		return res;
	}

	@Override
	public ArrayList<String> getAllAllergies(Patient patient) {
		ArrayList<String> allergy = new ArrayList<String>();

		Set<Medicine> allergies = patient.getAllergies();
		for (Medicine m : allergies) {
			allergy.add(m.getName());
		}
		return allergy;
	}

}
