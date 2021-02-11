package isa.tim28.pharmacies.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import isa.tim28.pharmacies.dtos.CurrentlyHasAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.ExistingDermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.LeaveDTO;
import isa.tim28.pharmacies.dtos.LeaveViewDTO;
import isa.tim28.pharmacies.dtos.MedicineDTOM;
import isa.tim28.pharmacies.dtos.MedicineDetailsDTO;
import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.dtos.MyPatientDTO;
import isa.tim28.pharmacies.dtos.PharmAppByMonthDTO;
import isa.tim28.pharmacies.dtos.PharmAppByWeekDTO;
import isa.tim28.pharmacies.dtos.PharmAppByYearDTO;
import isa.tim28.pharmacies.dtos.PharmAppDTO;
import isa.tim28.pharmacies.dtos.ShowCounselingDTO;
import isa.tim28.pharmacies.dtos.TherapyDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.DailyEngagement;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.DermatologistLeaveRequest;
import isa.tim28.pharmacies.model.DermatologistReport;
import isa.tim28.pharmacies.model.EngagementInPharmacy;
import isa.tim28.pharmacies.model.LeaveType;
import isa.tim28.pharmacies.model.Loyalty;
import isa.tim28.pharmacies.model.LoyaltyPoints;
import isa.tim28.pharmacies.model.LeaveRequestState;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineMissingNotification;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.PharmacistAppointment;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.Therapy;
import isa.tim28.pharmacies.repository.LoyaltyPointsRepository;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.DermatologistAppointmentRepository;
import isa.tim28.pharmacies.repository.DermatologistLeaveRequestRepository;
import isa.tim28.pharmacies.repository.DermatologistReportRepository;
import isa.tim28.pharmacies.repository.DermatologistRepository;
import isa.tim28.pharmacies.repository.MedicineMissingNotificationRepository;
import isa.tim28.pharmacies.repository.MedicineQuantityRepository;
import isa.tim28.pharmacies.repository.MedicineRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.PharmacistAppointmentRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistAppointmentService;

@Service
@Transactional(readOnly = true)
public class DermatologistAppointmentService implements IDermatologistAppointmentService {

	private DermatologistAppointmentRepository appointmentRepository;
	private PatientRepository patientRepository;
	private MedicineRepository medicineRepository;
	private DermatologistReportRepository dermatologistReportRepository;
	private MedicineQuantityRepository medicineQuantityRepository;
	private MedicineMissingNotificationRepository medicineMissingNotificationRepository;
	private DermatologistLeaveRequestRepository dermatologistLeaveRequestRepository;
	private PharmacistAppointmentRepository pharmacistAppointmentRepository;
	private DermatologistRepository dermatologistRepository;
	private LoyaltyPointsRepository loyaltyPointsRepository;
	private SystemAdminService systemAdminService;
	private EmailService emailService;
	
	@Autowired
	public DermatologistAppointmentService(DermatologistAppointmentRepository appointmentRepository, MedicineRepository medicineRepository, 
			DermatologistReportRepository dermatologistReportRepository, PatientRepository patientRepository, MedicineQuantityRepository medicineQuantityRepository,
			MedicineMissingNotificationRepository medicineMissingNotificationRepository, DermatologistRepository dermatologistRepository,
			DermatologistLeaveRequestRepository dermatologistLeaveRequestRepository, PharmacistAppointmentRepository pharmacistAppointmentRepository,
			LoyaltyPointsRepository loyaltyPointsRepository,  SystemAdminService systemAdminService, EmailService emailService) {
	
		super();
		this.appointmentRepository = appointmentRepository;
		this.medicineRepository = medicineRepository;
		this.dermatologistReportRepository = dermatologistReportRepository;
		this.patientRepository = patientRepository;
		this.medicineQuantityRepository = medicineQuantityRepository;
		this.medicineMissingNotificationRepository = medicineMissingNotificationRepository;
		this.dermatologistLeaveRequestRepository = dermatologistLeaveRequestRepository;
		this.pharmacistAppointmentRepository = pharmacistAppointmentRepository;
		this.dermatologistRepository = dermatologistRepository;
		this.loyaltyPointsRepository = loyaltyPointsRepository;
		this.systemAdminService = systemAdminService;
		this.emailService = emailService;
	}

	@Override
	public Set<DermatologistAppointment> findAllAvailableByPharmacyId(long pharmacyId) {
		return appointmentRepository.findAll().stream().filter(a -> a.getPharmacy().getId() == pharmacyId)
				.filter(a -> a.getStartDateTime().isAfter(LocalDateTime.now())).filter(a -> !a.isScheduled())
				.collect(Collectors.toSet());
	}

	@Override
	public DermatologistAppointmentDTO getAppointmentDTOById(long appointmentId) {
		DermatologistAppointment appointment = appointmentRepository.findById(appointmentId).get();
		if (appointment == null)
			return new DermatologistAppointmentDTO();
		else
			return new DermatologistAppointmentDTO(appointment.getId(), appointment.getPatient().getId(),
					appointment.getPatient().getUser().getFullName());
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
	@Transactional(readOnly = false)
	public String fillReport(DermatologistReportDTO dto) {
		try {
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
			}
			report.setTherapies(therapies);
			dermatologistReportRepository.save(report);
			app.setDone(true);
			appointmentRepository.save(app);

			//loyalty
			Patient patient = app.getPatient();
			patient.addPoints(app.getPointsAfterAppointment());
			systemAdminService.updateCathegoryOfPatient(patient);
			patientRepository.save(patient);

			Dermatologist dermatologist = app.getDermatologist();
			dermatologist.setCurrentlyHasAppointment(false);
			dermatologistRepository.save(dermatologist);
			
			return reservationCodes;
			
		} catch(OptimisticLockException e1) {
			return "";
		} catch(Exception e) {
			return "";
		}
	}

	@Override
	public boolean checkAllergies(long patientId, long medicineId) throws UserDoesNotExistException {
		Patient patient = patientRepository.findById(patientId).get();
		if (patient == null)
			throw new UserDoesNotExistException("Patient with given id doesn't exist.");
		Set<Medicine> allergies = patient.getAllergies();
		for (Medicine allergy : allergies)
			if (allergy.getId() == medicineId)
				return false;
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
	@Transactional(readOnly = false)
	public MedicineQuantityCheckDTO checkIfMedicineIsAvailable(long medicineId, long appointmentId) {
		Pharmacy pharmacy = appointmentRepository.findById(appointmentId).get().getPharmacy();
		Set<MedicineQuantity> medicines = pharmacy.getMedicines();
		for (MedicineQuantity mq : medicines) {
			if (mq.getMedicine().getId() == medicineId) {
				MedicineQuantityCheckDTO dto = new MedicineQuantityCheckDTO(mq.getQuantity());
				if (!dto.isAvailable()) {
					MedicineMissingNotification notification = new MedicineMissingNotification();
					notification.setMedicine(mq.getMedicine());
					notification.setPharmacy(pharmacy);
					notification.setTimestamp(LocalDateTime.now());
					medicineMissingNotificationRepository.save(notification);
				}
				return dto;
			}
		}
		return new MedicineQuantityCheckDTO(0);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateMedicineQuantity(long medicineId, long appointmentId) {
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
		for (String code : medicine.getCompatibleMedicineCodes()) {
			Medicine m = medicineRepository.findOneByCode(code);
			if (m != null)
				compatible.add(new MedicineDTOM(m.getId(), m.getName(), m.getManufacturer()));
		}
		return compatible;
	}

	@Override
	public MedicineDetailsDTO medicineDetails(long medicineId) {
		Medicine medicine = medicineRepository.findById(medicineId).get();
		if (medicine != null)
			return new MedicineDetailsDTO(medicine);
		else
			return new MedicineDetailsDTO();
	}

	@Override
	public boolean dermatologistHasIncomingAppointmentsInPharmacy(Dermatologist dermatologist, Pharmacy pharmacy) {
		return appointmentRepository.findAll().stream()
				.filter(a -> a.getDermatologist().getId() == dermatologist.getId()
						&& a.getPharmacy().getId() == pharmacy.getId()
						&& a.getStartDateTime().isAfter(LocalDateTime.now()) && a.isScheduled())
				.count() > 0;
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUnscheduledAppointments(Dermatologist dermatologist) {
		Set<DermatologistAppointment> appointments = appointmentRepository.findAll().stream()
				.filter(a -> a.getDermatologist().getId() == dermatologist.getId()
						&& a.getStartDateTime().isAfter(LocalDateTime.now()) && !a.isScheduled())
				.collect(Collectors.toSet());
		for (DermatologistAppointment a : appointments)
			appointmentRepository.delete(a);
	}

	@Override
	@Transactional(readOnly = false)
	public DermatologistAppointment saveDermatologistAppointment(long lastAppointmentId, long price, LocalDateTime startDateTime) {
		boolean canSchedule = checkIfFreeAppointmentExists(lastAppointmentId, startDateTime);
		if(canSchedule) { 
			DermatologistAppointment lastAppointment = appointmentRepository.findById(lastAppointmentId).get();
			if (lastAppointment == null)
				return null;
			DermatologistAppointment newAppointment = new DermatologistAppointment();
			newAppointment.setPatient(lastAppointment.getPatient());
			newAppointment.setPatientWasPresent(false);
			newAppointment.setDermatologist(lastAppointment.getDermatologist());
			newAppointment.setStartDateTime(startDateTime);
			
			
			newAppointment.setScheduled(true);
			newAppointment.setDurationInMinutes(30);
			newAppointment.setPharmacy(lastAppointment.getPharmacy());
			
			//loyalty program
			if(loyaltyPointsRepository.findAll() == null) {
				newAppointment.setPointsAfterAppointment(0);
				newAppointment.setPrice(price);
			}else 
			{
				List<LoyaltyPoints> points = loyaltyPointsRepository.findAll();
				if(!points.isEmpty()) {
					LoyaltyPoints lp = points.get(points.size() - 1);
					newAppointment.setPointsAfterAppointment(lp.getPointsAfterAppointment());
					
					if(lastAppointment.getPatient().getCategory().equals(Loyalty.REGULAR)) {
						newAppointment.setPrice(price);
					}else if(lastAppointment.getPatient().getCategory().equals(Loyalty.SILVER)) {
						double procentage = price*(lp.getDiscountForSilver()/100);
						long result = (long) procentage;
						newAppointment.setPrice(price-result);
					}else if(lastAppointment.getPatient().getCategory().equals(Loyalty.GOLD)) {
						double procentage = price*(lp.getDiscountForGold()/100);
						long result = (long) procentage;
						newAppointment.setPrice(price-result);
					}
				}else { 
					newAppointment.setPointsAfterAppointment(0);
					newAppointment.setPrice(price);
				}
			}
			
			appointmentRepository.save(newAppointment);
			return newAppointment;
		}
		else return null;
	}

	@Override
	@Transactional(readOnly = false)
	public DermatologistAppointment saveExistingDermatologistAppointment(long lastAppointmentId, long newAppointmentId) {
		DermatologistAppointment lastAppointment = appointmentRepository.findById(lastAppointmentId).get();
		DermatologistAppointment newAppointment = appointmentRepository.findById(newAppointmentId).get();
		if(lastAppointment == null || newAppointment == null) return null;
		newAppointment.setScheduled(true);
		newAppointment.setPatient(lastAppointment.getPatient());
		appointmentRepository.save(newAppointment);
		return newAppointment;
	}

	@Override
	public Set<ExistingDermatologistAppointmentDTO> getExistingDermatologistAppointments(long lastAppointmentId) {
		try {
			DermatologistAppointment lastAppointment = appointmentRepository.findById(lastAppointmentId).get();
			if(lastAppointment == null) return null;
			Set<ExistingDermatologistAppointmentDTO> dtos = new HashSet<ExistingDermatologistAppointmentDTO>();
			Set<DermatologistAppointment> dermAppointments = appointmentRepository.findAllByDermatologist_Id(lastAppointment.getDermatologist().getId());
			for(DermatologistAppointment appointment : dermAppointments) {
				if(!appointment.isScheduled()) {
					String dateTime = appointment.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy.")).toString();
					ExistingDermatologistAppointmentDTO dto = new ExistingDermatologistAppointmentDTO(appointment.getId(), dateTime, appointment.getDefaultDurationInMinutes(), appointment.getPrice());
					dtos.add(dto);
				}
			}
			return dtos;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean checkIfFreeAppointmentExists(long lastAppointmentId, LocalDateTime startDateTime) {
		try {
			DermatologistAppointment lastAppointment = appointmentRepository.findById(lastAppointmentId).get();
			if (lastAppointment == null)
				return false;
			if (!isDermatologistInPharmacy(lastAppointment.getDermatologist(), startDateTime,
					lastAppointment.getPharmacy()))
				return false;
			if (!isDermatologistAvailable(lastAppointment.getDermatologist(), startDateTime))
				return false;
			if (!isPatientAvailable(lastAppointment.getPatient(), startDateTime))
				return false;
			if (!startDateTime.isAfter(LocalDateTime.now().plusMinutes(1)))
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isDermatologistInPharmacy(Dermatologist dermatologist, LocalDateTime startDateTime,
			Pharmacy pharmacy) {
		DayOfWeek dayOfWeek = startDateTime.getDayOfWeek();
		boolean isWorkingThatDay = false;
		Set<EngagementInPharmacy> engagements = dermatologist.getEngegementInPharmacies();
		if (engagements.isEmpty())
			return false;

		EngagementInPharmacy engagement = new EngagementInPharmacy();
		for (EngagementInPharmacy e : engagements) {
			if (e.getPharmacy().getId() == pharmacy.getId())
				engagement = e;
		}
		if (engagement == null || engagement.getDailyEngagements() == null
				|| engagement.getDailyEngagements().isEmpty())
			return false;

		for (DailyEngagement dailyEngagement : engagement.getDailyEngagements()) {
			if (dailyEngagement.getDayOfWeek().equals(dayOfWeek)) {
				isWorkingThatDay = true;
				if (!isTimeInInterval(startDateTime.toLocalTime(), 30, dailyEngagement.getStartTime(),
						dailyEngagement.getEndTime()))
					return false;
			}
		}
		if (!isWorkingThatDay)
			return false;
		for (DermatologistLeaveRequest request : dermatologistLeaveRequestRepository
				.findAllByDermatologist_Id(dermatologist.getId())) {
			if (isDateInInterval(startDateTime.toLocalDate(), request.getStartDate(), request.getEndDate())
					&& request.getState() == LeaveRequestState.ACCEPTED)
				return false;
		}
		return true;
	}

	private boolean isDermatologistInPharmacy(Dermatologist dermatologist, LocalDateTime startDateTime,
			LocalDateTime endDateTime, Pharmacy pharmacy) {
		return isDermatologistInPharmacy(dermatologist, startDateTime, pharmacy)
				&& isDermatologistInPharmacy(dermatologist, endDateTime, pharmacy);
	}

	private boolean isPatientAvailable(Patient patient, LocalDateTime startDateTime) {
		Set<PharmacistAppointment> pharmAppointments = pharmacistAppointmentRepository
				.findAllByPatient_Id(patient.getId());
		for (PharmacistAppointment appointment : pharmAppointments) {
			if (appointment.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeInInterval(startDateTime.toLocalTime(), 30, appointment.getStartDateTime().toLocalTime(),
						appointment.getStartDateTime().toLocalTime().plusMinutes(30)))
					return false;
			}
		}
		Set<DermatologistAppointment> dermAppointments = appointmentRepository.findAllByPatient_Id(patient.getId());
		for (DermatologistAppointment appointment : dermAppointments) {
			if (appointment.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeInInterval(startDateTime.toLocalTime(), 30, appointment.getStartDateTime().toLocalTime(),
						appointment.getStartDateTime().toLocalTime().plusMinutes(appointment.getDurationInMinutes())))
					return false;
			}
		}
		return true;
	}

	private boolean isDermatologistAvailable(Dermatologist dermatologist, LocalDateTime startDateTime) {
		Set<DermatologistAppointment> dermAppointments = appointmentRepository
				.findAllByDermatologist_Id(dermatologist.getId());
		for (DermatologistAppointment appointment : dermAppointments) {
			if (appointment.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeInInterval(startDateTime.toLocalTime(), 30, appointment.getStartDateTime().toLocalTime(),
						appointment.getStartDateTime().toLocalTime().plusMinutes(appointment.getDurationInMinutes())))
					return false;
			}
		}
		return true;
	}

	private boolean isTimeInInterval(LocalTime time, int duration, LocalTime startTime, LocalTime endTime) {
		if (!time.isBefore(startTime) && !time.plusMinutes(duration).isAfter(endTime))
			return true;
		if (time.isAfter(startTime) && time.isBefore(endTime))
			return true;
		if (time.plusMinutes(duration).isAfter(startTime) && time.plusMinutes(duration).isBefore(endTime))
			return true;
		if (time.equals(startTime))
			return true;
		return false;
	}

	private boolean isDateInInterval(LocalDate date, LocalDate startDate, LocalDate endDate) {
		if (!date.isBefore(startDate) && !date.isAfter(endDate))
			return true;
		return false;
	}

	@Override
	public boolean dermatologistHasAppointmentsInTimInterval(Dermatologist dermatologist, LocalDate startDate,
			LocalDate endDate) {
		Set<DermatologistAppointment> appointments = appointmentRepository.findAll().stream()
				.filter(a -> a.getDermatologist().getId() == dermatologist.getId()).collect(Collectors.toSet());
		for (DermatologistAppointment a : appointments) {
			if (isDateInInterval(a.getStartDateTime().toLocalDate(), startDate, endDate))
				return true;
		}
		return false;
	}

	@Override
	public List<PharmAppDTO> getAppointmentsByWeek(PharmAppByWeekDTO dto, long pharmacyId, long userId) {
		try {
			Dermatologist dermatologist = dermatologistRepository.findOneByUser_Id(userId);
			Set<DermatologistAppointment> dermAppointments = appointmentRepository
					.findAllByDermatologist_Id(dermatologist.getId());
			List<PharmAppDTO> dtos = new ArrayList<PharmAppDTO>();
			for (DermatologistAppointment app : dermAppointments) {
				
				if(isDateInInterval(app.getStartDateTime().toLocalDate(), dto.getStartDate(), dto.getEndDate()) && !app.isDone() && app.getPharmacy().getId() == pharmacyId) {

					String startTime = app.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy."));
					String patientName = "";
					if (app.isScheduled())
						patientName = app.getPatient().getUser().getFullName();
					dtos.add(new PharmAppDTO(app.getId(), startTime, app.getDefaultDurationInMinutes(), patientName));
				}
			}
			return dtos;
		} catch (Exception e) {
			return new ArrayList<PharmAppDTO>();
		}
	}

	@Override
	public List<PharmAppDTO> getAppointmentsByMonth(PharmAppByMonthDTO dto, long pharmacyId, long userId) {
		try {
			Dermatologist dermatologist = dermatologistRepository.findOneByUser_Id(userId);
			Set<DermatologistAppointment> dermAppointments = appointmentRepository
					.findAllByDermatologist_Id(dermatologist.getId());
			List<PharmAppDTO> dtos = new ArrayList<PharmAppDTO>();
			for (DermatologistAppointment app : dermAppointments) {
				if (app.getStartDateTime().getYear() == dto.getYear()
						&& app.getStartDateTime().getMonthValue() == dto.getMonth() && !app.isDone()
						&& app.getPharmacy().getId() == pharmacyId) {
					String startTime = app.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy."));
					String patientName = "";
					if (app.isScheduled())
						patientName = app.getPatient().getUser().getFullName();
					dtos.add(new PharmAppDTO(app.getId(), startTime, app.getDefaultDurationInMinutes(), patientName));
				}
			}
			return dtos;
		} catch (Exception e) {
			return new ArrayList<PharmAppDTO>();
		}
	}

	@Override
	public List<PharmAppDTO> getAppointmentsByYear(PharmAppByYearDTO dto, long pharmacyId, long userId) {
		try {
			Dermatologist dermatologist = dermatologistRepository.findOneByUser_Id(userId);
			Set<DermatologistAppointment> dermAppointments = appointmentRepository
					.findAllByDermatologist_Id(dermatologist.getId());
			List<PharmAppDTO> dtos = new ArrayList<PharmAppDTO>();
			for (DermatologistAppointment app : dermAppointments) {
				if (app.getStartDateTime().getYear() == dto.getYear() && !app.isDone()
						&& app.getPharmacy().getId() == pharmacyId) {
					String startTime = app.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy."));
					String patientName = "";
					if (app.isScheduled())
						patientName = app.getPatient().getUser().getFullName();
					dtos.add(new PharmAppDTO(app.getId(), startTime, app.getDefaultDurationInMinutes(), patientName));
				}
			}
			return dtos;
		} catch (Exception e) {
			return new ArrayList<PharmAppDTO>();
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void patientWasNotPresent(long appointmentId) {
		try {
			DermatologistAppointment app = appointmentRepository.findById(appointmentId).get();
			app.setDone(true);
			app.setPatientWasPresent(false);
			appointmentRepository.save(app);

			Patient p = app.getPatient();
			p.setPenalties(p.getPenalties() + 1);
			patientRepository.save(p);
		} catch (Exception e) {
			return;
		}

	}

	@Override
	@Transactional(readOnly = false)
	public void saveLeaveRequest(LeaveDTO dto, long userId) {
		try {
			Dermatologist dermatologist = dermatologistRepository.findOneByUser_Id(userId);
			DermatologistLeaveRequest request = new DermatologistLeaveRequest();
			request.setStartDate(dto.getStartDate());
			request.setEndDate(dto.getEndDate());
			request.setDermatologist(dermatologist);
			if (dto.getType().equals("SICK_LEAVE"))
				request.setType(LeaveType.SICK_LEAVE);
			else
				request.setType(LeaveType.ANNUAL_LEAVE);
			dermatologistLeaveRequestRepository.save(request);
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public List<LeaveViewDTO> allLeaveRequests(long userId) {
		try {
			Dermatologist dermatologist = dermatologistRepository.findOneByUser_Id(userId);
			List<LeaveViewDTO> dtos = new ArrayList<LeaveViewDTO>();
			Set<DermatologistLeaveRequest> requests = dermatologistLeaveRequestRepository
					.findAllByDermatologist_Id(dermatologist.getId());
			for (DermatologistLeaveRequest request : requests) {
				LeaveViewDTO dto = new LeaveViewDTO();
				dto.setConfirmed(request.getState().toString());
				dto.setStartDate(request.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
				dto.setEndDate(request.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
				dto.setType(request.getType().toString());
				dtos.add(dto);
			}
			return dtos;
		} catch (Exception e) {
			return new ArrayList<LeaveViewDTO>();
		}
	}

	@Override
	public List<MyPatientDTO> myPatients(long userId) {
		try {
			Dermatologist dermatologist = dermatologistRepository.findOneByUser_Id(userId);
			List<MyPatientDTO> dtos = new ArrayList<MyPatientDTO>();
			Set<DermatologistAppointment> appointments = appointmentRepository
					.findAllByDermatologist_Id(dermatologist.getId());
			for (DermatologistAppointment app : appointments) {
				if (app.isPatientWasPresent() && app.getPatient() != null && app.isDone()) {
					MyPatientDTO dto = new MyPatientDTO();
					dto.setPatientId(app.getPatient().getId());
					dto.setName(app.getPatient().getUser().getName());
					dto.setSurname(app.getPatient().getUser().getSurname());
					dto.setAppointmentDate(
							app.getStartDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
					dto.setTime(app.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
					dtos.add(dto);
				}
			}
			return dtos;
		} catch (Exception e) {
			return new ArrayList<MyPatientDTO>();
		}
	}

	@Override
	public PharmAppDTO hasAppointmentWithPatient(long userId, long patientId) {
		try {
			Dermatologist dermatologist = dermatologistRepository.findOneByUser_Id(userId);
			Set<DermatologistAppointment> appointments = appointmentRepository
					.findAllByDermatologist_Id(dermatologist.getId());
			PharmAppDTO dto = new PharmAppDTO(0, "", 0, "");
			for (DermatologistAppointment app : appointments) {
				if (app.isScheduled()) {
					if (app.getPatient().getId() == patientId
							&& isAppointmentNow(app.getStartDateTime(), app.getDurationInMinutes()) && !app.isDone()) {
						dto.setAppointmentId(app.getId());
						dto.setDurationInMinutes(app.getDurationInMinutes());
						dto.setPatientName(app.getPatient().getUser().getFullName());
						dto.setStartTime(
								app.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy.")));
					}
				}
			}
			return dto;
		} catch (Exception e) {
			return new PharmAppDTO(0, "", 0, "");
		}
	}

	private boolean isAppointmentNow(LocalDateTime startTime, int duration) {
		// moze 10 minuta ranije i moze da kasni do duration-1 minuta
		if (!startTime.isBefore(LocalDateTime.now().minusMinutes(duration - 1))
				&& !startTime.isAfter(LocalDateTime.now().plusMinutes(10)))
			return true;
		return false;
	}
	
	@Transactional(readOnly = false)
	public void createPredefinedAppointment(Dermatologist dermatologist, LocalDateTime startDateTime, int durationInMinutes,
			long price, Pharmacy pharmacy) throws ForbiddenOperationException {
		if(!isDermatologistInPharmacy(dermatologist, startDateTime, startDateTime.plusMinutes(durationInMinutes), pharmacy))
			throw new ForbiddenOperationException("Dermatologist is not present at the pharmacy at the selected date and time. He is either on leave or doesn't have working hours set at the selected time");
		if(!isDermatologistAvailable(dermatologist, startDateTime) || !isDermatologistAvailable(dermatologist, startDateTime.plusMinutes(durationInMinutes)))
			throw new ForbiddenOperationException("Dermatologist has an examination in the pharmacy that is overlapping with the one you want to create!");
		DermatologistAppointment predefined = new DermatologistAppointment();
		predefined.setDermatologist(dermatologist);
		predefined.setDone(false);
		predefined.setDurationInMinutes(durationInMinutes);
		predefined.setPharmacy(pharmacy);
		predefined.setPrice(price);
		predefined.setScheduled(false);
		predefined.setStartDateTime(startDateTime);
		appointmentRepository.save(predefined);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public CurrentlyHasAppointmentDTO isDermatologistInAppointment(long userId) {
		try {
			Dermatologist dermatologist = dermatologistRepository.findOneByUser_Id(userId);
			if(!dermatologist.isCurrentlyHasAppointment()) {
				dermatologist.setCurrentlyHasAppointment(true);
				dermatologistRepository.save(dermatologist);
				return new CurrentlyHasAppointmentDTO(false);
			}
			else return new CurrentlyHasAppointmentDTO(true);
		} catch(OptimisticLockException e1) {
			return new CurrentlyHasAppointmentDTO(true);
		} catch(Exception e) {
			return new CurrentlyHasAppointmentDTO(true);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void endCurrentAppointment(long userId) {
		try {
			Dermatologist dermatologist = dermatologistRepository.findOneByUser_Id(userId);
			if(dermatologist.isCurrentlyHasAppointment()) {
				dermatologist.setCurrentlyHasAppointment(false);
				dermatologistRepository.save(dermatologist);
			}
			else return;
		} catch(OptimisticLockException e1) {
			return;
		} catch(Exception e) {
			return;
		}
	}
		
	public void scheduleApp(long appId, User loggedInUser) {

		DermatologistAppointment da = appointmentRepository.findById(appId).get();
		da.setScheduled(true);
		da.setPatient(patientRepository.findOneByUser_Id(loggedInUser.getId()));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		String date = da.getStartDateTime().format(formatter);
		appointmentRepository.save(da);
		
		try {
			emailService.sendAppointmentScheduled(loggedInUser.getFullName(), loggedInUser.getEmail(),
					da.getDermatologist().getUser().getFullName(), date);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public List<ShowCounselingDTO> getAllIncomingAppointments(long id, boolean past) {

		List<ShowCounselingDTO> resIncoming = new ArrayList<ShowCounselingDTO>();
		List<ShowCounselingDTO> resPast = new ArrayList<ShowCounselingDTO>();
		long patientId = patientRepository.findOneByUser_Id(id).getId();
		boolean cancellable = false;

		LocalDateTime today = LocalDateTime.now();

		Set<DermatologistAppointment> all = appointmentRepository.findAllByPatient_Id(patientId);
		for (DermatologistAppointment pa : all) {

			LocalDateTime checkDate = pa.getStartDateTime();

			if (pa.isScheduled() && today.isBefore(checkDate)) {
				cancellable = false;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
				String date = pa.getStartDateTime().format(formatter);

				if (today.isBefore(checkDate.minus(Period.ofDays(1)))) {
					cancellable = true;
				}

				ShowCounselingDTO dto = new ShowCounselingDTO(pa.getId(),pa.getPharmacy().getId(), date,
						pa.getDermatologist().getId(),pa.getDermatologist().getUser().getFullName(), cancellable, pa.getDurationInMinutes(),
						pa.getPrice(), "DERMATOLOGIST APPOINTMENT");
				resIncoming.add(dto);
			} else if(pa.isPatientWasPresent() && pa.isDone()) {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
				String date = pa.getStartDateTime().format(formatter);
				ShowCounselingDTO dto = new ShowCounselingDTO(pa.getId(),pa.getPharmacy().getId(), date,
						pa.getDermatologist().getId(),pa.getDermatologist().getUser().getFullName(), false,pa.getDurationInMinutes(),pa.getPrice(), "DERMATOLOGIST APPOINTMENT");
				resPast.add(dto);
			}
		}

		if (past) {
			return resPast;
		} else {
			return resIncoming;
		}

	}
	

	public void cancelDermApp(long id) {
		DermatologistAppointment da = appointmentRepository.findById(id).get();
		da.setScheduled(false);
		appointmentRepository.save(da);
	}
}
