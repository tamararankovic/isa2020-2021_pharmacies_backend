package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.service.interfaces.IPatientService;

@Service
public class PatientService implements IPatientService{

	private PatientRepository patientRepository;
	
	@Autowired
	public PatientService(PatientRepository patientRepository) {
		super();
		this.patientRepository = patientRepository;
	}
	
	@Override
	public Patient save(Patient patient) {
		Patient newPatient = patientRepository.save(patient);
		return newPatient;
	}

}
