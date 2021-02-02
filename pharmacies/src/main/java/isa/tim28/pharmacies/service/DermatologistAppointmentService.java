package isa.tim28.pharmacies.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.MedicineDTO;
import isa.tim28.pharmacies.dtos.TherapyDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.DermatologistReport;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Therapy;
import isa.tim28.pharmacies.repository.DermatologistAppointmentRepository;
import isa.tim28.pharmacies.repository.DermatologistReportRepository;
import isa.tim28.pharmacies.repository.MedicineRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistAppointmentService;

@Service
public class DermatologistAppointmentService implements IDermatologistAppointmentService {

	private DermatologistAppointmentRepository appointmentRepository;
	private PatientRepository patientRepository;
	private MedicineRepository medicineRepository;
	private DermatologistReportRepository dermatologistReportRepository;
	
	@Autowired
	public DermatologistAppointmentService(DermatologistAppointmentRepository appointmentRepository, MedicineRepository medicineRepository, 
			DermatologistReportRepository dermatologistReportRepository, PatientRepository patientRepository) {
		super();
		this.appointmentRepository = appointmentRepository;
		this.medicineRepository = medicineRepository;
		this.dermatologistReportRepository = dermatologistReportRepository;
		this.patientRepository = patientRepository;
	}

	@Override
	public Set<DermatologistAppointment> findAllAvailableByPharmacyId(long pharmacyId) {
		return appointmentRepository.findAll().stream()
				.filter(a -> a.getPharmacy().getId() == pharmacyId)
				.filter(a -> a.getStartDateTime().isAfter(LocalDateTime.now()))
				.filter(a -> !a.isScheduled()).collect(Collectors.toSet());
	}

	@Override
	public DermatologistAppointmentDTO getAppointmentDTOById(long appointmentId) {
		DermatologistAppointment appointment = appointmentRepository.findById(appointmentId).get();
		if(appointment == null) return new DermatologistAppointmentDTO();
		else return new DermatologistAppointmentDTO(appointment.getId(), appointment.getPatient().getId(), appointment.getPatient().getUser().getFullName());
	}

	@Override
	public List<MedicineDTO> getMedicineList() {
		List<MedicineDTO> dtos = new ArrayList<MedicineDTO>();
		for (Medicine m : medicineRepository.findAll()) {
			dtos.add(new MedicineDTO(m.getId(), m.getName(), m.getManufacturer()));
		}
		return dtos;
	}

	@Override
	public void fillReport(DermatologistReportDTO dto) {
		DermatologistReport report = new DermatologistReport();
		report.setAppointment(appointmentRepository.findById(dto.getAppointmentId()).get());
		report.setDiagnosis(dto.getDiagnosis());
		Set<Therapy> therapies = new HashSet<Therapy>();
		for(TherapyDTO t : dto.getTherapies()) {
			Therapy therapy = new Therapy();
			therapy.setDurationInDays(t.getDurationInDays());
			therapy.setMedicine(medicineRepository.findById(t.getMedicineId()).get());
			therapies.add(therapy);
		}
		report.setTherapies(therapies);
		dermatologistReportRepository.save(report);
	}

	@Override
	public boolean checkAllergies(long patientId, long medicineId) throws UserDoesNotExistException {
		Patient patient = patientRepository.findById(patientId).get();
		if(patient == null) throw new UserDoesNotExistException("Patient with given id doesn't exist.");
		Set<Medicine> allergies = patient.getAllergies();
		for (Medicine allergy : allergies) 
			if (allergy.getId() == medicineId) return false;
		return true;
	}

	@Override
	public DermatologistAppointment getAppointmentById(long appointmentId) {
		return appointmentRepository.findById(appointmentId).get();
	}

	@Override
	public String getMedicineCodeById(long medicineId) {
		return medicineRepository.findById(medicineId).get().getCode();
	}
	
}
