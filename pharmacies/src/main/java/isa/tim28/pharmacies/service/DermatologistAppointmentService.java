package isa.tim28.pharmacies.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.ExistingDermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.MedicineDTOM;
import isa.tim28.pharmacies.dtos.MedicineDetailsDTO;
import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.dtos.TherapyDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.DermatologistLeaveRequest;
import isa.tim28.pharmacies.model.DermatologistReport;
import isa.tim28.pharmacies.model.EngagementInPharmacy;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineMissingNotification;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.PharmacistAppointment;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.Therapy;
import isa.tim28.pharmacies.model.DailyEngagement;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.repository.DermatologistAppointmentRepository;
import isa.tim28.pharmacies.repository.DermatologistLeaveRequestRepository;
import isa.tim28.pharmacies.repository.DermatologistReportRepository;
import isa.tim28.pharmacies.repository.MedicineMissingNotificationRepository;
import isa.tim28.pharmacies.repository.MedicineQuantityRepository;
import isa.tim28.pharmacies.repository.MedicineRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.PharmacistAppointmentRepository;
import isa.tim28.pharmacies.repository.ReservationRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistAppointmentService;

@Service
public class DermatologistAppointmentService implements IDermatologistAppointmentService {

	private DermatologistAppointmentRepository appointmentRepository;
	private PatientRepository patientRepository;
	private MedicineRepository medicineRepository;
	private DermatologistReportRepository dermatologistReportRepository;
	private MedicineQuantityRepository medicineQuantityRepository;
	private ReservationRepository reservationRepository;
	private MedicineMissingNotificationRepository medicineMissingNotificationRepository;
	private DermatologistLeaveRequestRepository dermatologistLeaveRequestRepository;
	private PharmacistAppointmentRepository pharmacistAppointmentRepository;
	
	@Autowired
	public DermatologistAppointmentService(DermatologistAppointmentRepository appointmentRepository, MedicineRepository medicineRepository, 
			DermatologistReportRepository dermatologistReportRepository, PatientRepository patientRepository, MedicineQuantityRepository medicineQuantityRepository,
			ReservationRepository reservationRepository, MedicineMissingNotificationRepository medicineMissingNotificationRepository,
			DermatologistLeaveRequestRepository dermatologistLeaveRequestRepository, PharmacistAppointmentRepository pharmacistAppointmentRepository) {
		super();
		this.appointmentRepository = appointmentRepository;
		this.medicineRepository = medicineRepository;
		this.dermatologistReportRepository = dermatologistReportRepository;
		this.patientRepository = patientRepository;
		this.medicineQuantityRepository = medicineQuantityRepository;
		this.reservationRepository = reservationRepository;
		this.medicineMissingNotificationRepository = medicineMissingNotificationRepository;
		this.dermatologistLeaveRequestRepository = dermatologistLeaveRequestRepository;
		this.pharmacistAppointmentRepository = pharmacistAppointmentRepository;
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
	public List<MedicineDTOM> getMedicineList() {
		List<MedicineDTOM> dtos = new ArrayList<MedicineDTOM>();
		for (Medicine m : medicineRepository.findAll()) {
			dtos.add(new MedicineDTOM(m.getId(), m.getName(), m.getManufacturer()));
		}
		return dtos;
	}

	@Override
	public String fillReport(DermatologistReportDTO dto) {
		DermatologistReport report = new DermatologistReport();
		DermatologistAppointment app = appointmentRepository.findById(dto.getAppointmentId()).get();
		report.setAppointment(app);
		report.setDiagnosis(dto.getDiagnosis());
		Set<Therapy> therapies = new HashSet<Therapy>();
		String reservationCodes = "";
		
		for(TherapyDTO t : dto.getTherapies()) {
			Therapy therapy = new Therapy();
			Medicine medicine = medicineRepository.findById(t.getMedicineId()).get();
			therapy.setDurationInDays(t.getDurationInDays());
			therapy.setMedicine(medicine);
			therapies.add(therapy);
			reservationCodes = reservationCodes + medicine.getCode() + "; ";
			updateMedicineQuantity(medicine.getId(), app.getId());
			/*
			Reservation reservation = new Reservation();
			reservation.setMedicine(medicine);
			reservation.setPatient(app.getPatient());
			reservation.setPharmacy(app.getPharmacy());
			reservation.setReceived(false);
			reservation.setAppointment(app.getId());
			reservation.setDueDate(LocalDate.now().plusDays(1));
			reservationRepository.save(reservation);
			*/
		}
		report.setTherapies(therapies);
		dermatologistReportRepository.save(report);
		
		/*
		Set<Reservation> reservations = reservationRepository.findAllByAppointment(app.getId());
		String reservationCodes = "";
		for(Reservation res : reservations) {
			reservationCodes = reservationCodes + res.getId() + "; ";
		}*/
		return reservationCodes;
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

	@Override
	public MedicineQuantityCheckDTO checkIfMedicineIsAvailable(long medicineId, long appointmentId) {
		Pharmacy pharmacy = appointmentRepository.findById(appointmentId).get().getPharmacy();
		Set<MedicineQuantity> medicines = pharmacy.getMedicines();
		for (MedicineQuantity mq : medicines) {
			if (mq.getMedicine().getId() == medicineId) {
				MedicineQuantityCheckDTO dto = new MedicineQuantityCheckDTO(mq.getQuantity());
				if(!dto.isAvailable()) {
					MedicineMissingNotification notification = new MedicineMissingNotification();
					notification.setMedicine(mq.getMedicine());
					notification.setPharmacy(pharmacy);
					medicineMissingNotificationRepository.save(notification);
				}
				return dto;
			}
		}
		return new MedicineQuantityCheckDTO(0);
	}
	
	private void updateMedicineQuantity(long medicineId, long appointmentId) {
		Set<MedicineQuantity> medicines = appointmentRepository.findById(appointmentId).get().getPharmacy().getMedicines();
		for (MedicineQuantity mq : medicines) {
			if (mq.getMedicine().getId() == medicineId) {
				mq.setQuantity(mq.getQuantity() - 1);
				medicineQuantityRepository.save(mq);
				return;
			}
		}
	}

	@Override
	public List<MedicineDTOM> compatibleMedicine(long medicineId) {
		Medicine medicine = medicineRepository.findById(medicineId).get();
		List<MedicineDTOM> compatible = new ArrayList<MedicineDTOM>();
		for(String code : medicine.getCompatibleMedicineCodes()) {
			Medicine m = medicineRepository.findOneByCode(code);
			if(m != null) compatible.add(new MedicineDTOM(m.getId(), m.getName(), m.getManufacturer()));
		}
		return compatible;
	}

	@Override
	public MedicineDetailsDTO medicineDetails(long medicineId) {
		Medicine medicine = medicineRepository.findById(medicineId).get();
		if(medicine != null) return new MedicineDetailsDTO(medicine);
		else return new MedicineDetailsDTO();
	}
	
	public boolean dermatologistHasIncomingAppointmentsInPharmacy(Dermatologist dermatologist, Pharmacy pharmacy) {
		return appointmentRepository.findAll().stream().filter(a -> a.getDermatologist().getId() == dermatologist.getId()
				&& a.getPharmacy().getId() == pharmacy.getId()
				&& a.getStartDateTime().isAfter(LocalDateTime.now())
				&& a.isScheduled()).count() > 0;
	}

	@Override
	public void deleteUnscheduledAppointments(Dermatologist dermatologist) {
		Set<DermatologistAppointment> appointments = appointmentRepository.findAll().stream()
														.filter(a -> a.getDermatologist().getId() == dermatologist.getId()
														&& a.getStartDateTime().isAfter(LocalDateTime.now())
														&& !a.isScheduled())
														.collect(Collectors.toSet());
		for(DermatologistAppointment a : appointments)
			appointmentRepository.delete(a);
	}

	@Override
	public DermatologistAppointment saveDermatologistAppointment(long lastAppointmentId, long price, LocalDateTime startDateTime) {
		try{ 
			DermatologistAppointment lastAppointment = appointmentRepository.findById(lastAppointmentId).get();
			if(lastAppointment == null) return null;
			DermatologistAppointment newAppointment = new DermatologistAppointment();
			newAppointment.setPatient(lastAppointment.getPatient());
			newAppointment.setPatientWasPresent(false);
			newAppointment.setDermatologist(lastAppointment.getDermatologist());
			newAppointment.setStartDateTime(startDateTime);
			newAppointment.setPrice(price);
			newAppointment.setScheduled(true);
			newAppointment.setDurationInMinutes(30);
			newAppointment.setPharmacy(lastAppointment.getPharmacy());
			appointmentRepository.save(newAppointment);
			return newAppointment;
		} catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public DermatologistAppointment saveExistingDermatologistAppointment(long lastAppointmentId, long newAppointmentId) {
		try{ 
			DermatologistAppointment lastAppointment = appointmentRepository.findById(lastAppointmentId).get();
			DermatologistAppointment newAppointment = appointmentRepository.findById(newAppointmentId).get();
			if(lastAppointment == null || newAppointment == null) return null;
			newAppointment.setScheduled(true);
			newAppointment.setPatient(lastAppointment.getPatient());
			appointmentRepository.save(newAppointment);
			return newAppointment;
		} catch(Exception e) {
			return null;
		}
	}
	

	@Override
	public Set<ExistingDermatologistAppointmentDTO> getExistingDermatologistAppointments(long lastAppointmentId) {
		try{ 
			DermatologistAppointment lastAppointment = appointmentRepository.findById(lastAppointmentId).get();
			if(lastAppointment == null) return null;
			System.out.println("Pronasao je appointment...");
			Set<ExistingDermatologistAppointmentDTO> dtos = new HashSet<ExistingDermatologistAppointmentDTO>();
			Set<DermatologistAppointment> dermAppointments = appointmentRepository.findAllByDermatologist_Id(lastAppointment.getDermatologist().getId());
			for(DermatologistAppointment appointment : dermAppointments) {
				System.out.println("Prvi appointment je appointment id = " + appointment.getId());
				if(!appointment.isScheduled()) {
					String dateTime = appointment.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy.")).toString();
					ExistingDermatologistAppointmentDTO dto = new ExistingDermatologistAppointmentDTO(appointment.getId(), dateTime, appointment.getDefaultDurationInMinutes(), appointment.getPrice());
					dtos.add(dto);
				}
			}
			System.out.println("NEMA VISE...");
			return dtos;
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public boolean checkIfFreeAppointmentExists(long lastAppointmentId, LocalDateTime startDateTime) {
		try {
			DermatologistAppointment lastAppointment = appointmentRepository.findById(lastAppointmentId).get();
			if(lastAppointment == null) return false;
			if(!isDermatologistInPharmacy(lastAppointment.getDermatologist(), startDateTime, lastAppointment.getPharmacy())) return false;
			if(!isDermatologistAvailable(lastAppointment.getDermatologist(), startDateTime)) return false;
			if(!isPatientAvailable(lastAppointment.getPatient(), startDateTime)) return false;
			if(!startDateTime.isAfter(LocalDateTime.now().plusMinutes(1))) return false;
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	private boolean isDermatologistInPharmacy(Dermatologist dermatologist, LocalDateTime startDateTime, Pharmacy pharmacy) {
		DayOfWeek dayOfWeek = startDateTime.getDayOfWeek();
		boolean isWorkingThatDay = false;
		Set<EngagementInPharmacy> engagements = dermatologist.getEngegementInPharmacies();
		if(engagements.isEmpty()) return false;
		
		EngagementInPharmacy engagement = new EngagementInPharmacy();
		for(EngagementInPharmacy e : engagements) {
			if(e.getPharmacy().getId() == pharmacy.getId()) engagement = e;
		}
		if (engagement == null || engagement.getDailyEngagements() == null || engagement.getDailyEngagements().isEmpty()) return false;
		
		for (DailyEngagement dailyEngagement : engagement.getDailyEngagements()) {
			if (dailyEngagement.getDayOfWeek().equals(dayOfWeek)) {
				isWorkingThatDay = true;
				if (!isTimeInInterval(startDateTime.toLocalTime(), dailyEngagement.getStartTime(), dailyEngagement.getEndTime()))
					return false;
			}
		}
		if(!isWorkingThatDay) return false;
		for (DermatologistLeaveRequest request : dermatologistLeaveRequestRepository.findAllByDermatologist_Id(dermatologist.getId())) {
			if (isDateInInterval(startDateTime.toLocalDate(), request.getStartDate(), request.getEndDate()) && request.isConfirmed()) 
				return false;
		}
		return true;
	}
	
	private boolean isPatientAvailable(Patient patient, LocalDateTime startDateTime) {
		Set<PharmacistAppointment> pharmAppointments = pharmacistAppointmentRepository.findAllByPatient_Id(patient.getId());
		for(PharmacistAppointment appointment : pharmAppointments) {
			if(appointment.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if(isTimeInInterval(startDateTime.toLocalTime(), appointment.getStartDateTime().toLocalTime(), appointment.getStartDateTime().toLocalTime().plusMinutes(30)))
					return false;
			}
		}
		Set<DermatologistAppointment> dermAppointments = appointmentRepository.findAllByPatient_Id(patient.getId());
		for(DermatologistAppointment appointment : dermAppointments) {
			if(appointment.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if(isTimeInInterval(startDateTime.toLocalTime(), appointment.getStartDateTime().toLocalTime(), appointment.getStartDateTime().toLocalTime().plusMinutes(appointment.getDurationInMinutes())))
					return false;
			}
		}
		return true;
	}
	
	private boolean isDermatologistAvailable(Dermatologist dermatologist, LocalDateTime startDateTime) {
		Set<DermatologistAppointment> dermAppointments = appointmentRepository.findAllByDermatologist_Id(dermatologist.getId());
		for(DermatologistAppointment appointment : dermAppointments) {
			if(appointment.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if(isTimeInInterval(startDateTime.toLocalTime(), appointment.getStartDateTime().toLocalTime(), appointment.getStartDateTime().toLocalTime().plusMinutes(appointment.getDurationInMinutes())))
					return false;
			}
		}
		return true;
	}
	
	private boolean isTimeInInterval(LocalTime time, LocalTime startTime, LocalTime endTime) {
		if(!time.isBefore(startTime) && !time.plusMinutes(30).isAfter(endTime)) return true;
		if(time.isAfter(startTime) && time.isBefore(endTime)) return true;
		if(time.plusMinutes(30).isAfter(startTime) && time.plusMinutes(30).isBefore(endTime)) return true;
		return false;
	}
	
	private boolean isDateInInterval(LocalDate date, LocalDate startDate, LocalDate endDate) {
		if(!date.isBefore(startDate) && !date.isAfter(endDate)) return false;
		return true;
	}
}
