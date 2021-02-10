package isa.tim28.pharmacies.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.LeaveDTO;
import isa.tim28.pharmacies.dtos.LeaveViewDTO;
import isa.tim28.pharmacies.dtos.MedicineDTOM;
import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.dtos.MyPatientDTO;
import isa.tim28.pharmacies.dtos.PharmAppByMonthDTO;
import isa.tim28.pharmacies.dtos.PharmAppByWeekDTO;
import isa.tim28.pharmacies.dtos.PharmAppByYearDTO;
import isa.tim28.pharmacies.dtos.PharmAppDTO;
import isa.tim28.pharmacies.dtos.PharmacistAppointmentDTO;
import isa.tim28.pharmacies.dtos.ReservationValidDTO;
import isa.tim28.pharmacies.dtos.ShowCounselingDTO;
import isa.tim28.pharmacies.dtos.TherapyDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.DailyEngagement;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.LeaveType;
import isa.tim28.pharmacies.model.Loyalty;
import isa.tim28.pharmacies.model.LoyaltyPoints;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineConsumption;
import isa.tim28.pharmacies.model.MedicineMissingNotification;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.PharmacistAppointment;
import isa.tim28.pharmacies.model.PharmacistLeaveRequest;
import isa.tim28.pharmacies.model.PharmacistReport;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.model.Therapy;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.DermatologistAppointmentRepository;
import isa.tim28.pharmacies.repository.LoyaltyPointsRepository;
import isa.tim28.pharmacies.repository.MedicineConsumptionRepository;
import isa.tim28.pharmacies.repository.MedicineMissingNotificationRepository;
import isa.tim28.pharmacies.repository.MedicineQuantityRepository;
import isa.tim28.pharmacies.repository.MedicineRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.PharmacistAppointmentRepository;
import isa.tim28.pharmacies.repository.PharmacistLeaveRequestRepository;
import isa.tim28.pharmacies.repository.PharmacistReportRepository;
import isa.tim28.pharmacies.repository.PharmacistRepository;
import isa.tim28.pharmacies.repository.ReservationRepository;
import isa.tim28.pharmacies.service.interfaces.IPharmacistAppointmentService;

@Service
public class PharmacistAppointmentService implements IPharmacistAppointmentService {

	private PharmacistAppointmentRepository appointmentRepository;
	private DermatologistAppointmentRepository dermatologistAppointmentRepository;
	private PatientRepository patientRepository;
	private MedicineRepository medicineRepository;
	private PharmacistReportRepository pharmacistReportRepository;
	private MedicineQuantityRepository medicineQuantityRepository;
	private ReservationRepository reservationRepository;
	private MedicineMissingNotificationRepository medicineMissingNotificationRepository;
	private PharmacistLeaveRequestRepository pharmacistLeaveRequestRepository;
	private PharmacistRepository pharmacistRepository;
	private MedicineConsumptionRepository medicineConsumptionRepository;
	private EmailService emailService;
	private LoyaltyPointsRepository loyaltyPointsRepository;
	private SystemAdminService systemAdminService;

	@Autowired
	public PharmacistAppointmentService(PharmacistAppointmentRepository appointmentRepository,
			MedicineRepository medicineRepository, PharmacistReportRepository pharmacistReportRepository,
			PatientRepository patientRepository, MedicineQuantityRepository medicineQuantityRepository,
			ReservationRepository reservationRepository,
			MedicineMissingNotificationRepository medicineMissingNotificationRepository,
			PharmacistLeaveRequestRepository pharmacistLeaveRequestRepository,
			DermatologistAppointmentRepository dermatologistAppointmentRepository,
			PharmacistRepository pharmacistRepository, EmailService emailService,
			MedicineConsumptionRepository medicineConsumptionRepository, LoyaltyPointsRepository loyaltyPointsRepository,
			SystemAdminService systemAdminService) {
		
		super();
		this.appointmentRepository = appointmentRepository;
		this.medicineRepository = medicineRepository;
		this.pharmacistReportRepository = pharmacistReportRepository;
		this.patientRepository = patientRepository;
		this.medicineQuantityRepository = medicineQuantityRepository;
		this.reservationRepository = reservationRepository;
		this.medicineMissingNotificationRepository = medicineMissingNotificationRepository;
		this.pharmacistLeaveRequestRepository = pharmacistLeaveRequestRepository;
		this.dermatologistAppointmentRepository = dermatologistAppointmentRepository;
		this.pharmacistRepository = pharmacistRepository;
		this.emailService = emailService;
		this.medicineConsumptionRepository = medicineConsumptionRepository;
		this.systemAdminService = systemAdminService;
	}

	@Override
	public DermatologistAppointmentDTO getAppointmentDTOById(long appointmentId) {
		PharmacistAppointment appointment = appointmentRepository.findById(appointmentId).get();
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
	public String fillReport(DermatologistReportDTO dto) {
		PharmacistReport report = new PharmacistReport();
		PharmacistAppointment app = appointmentRepository.findById(dto.getAppointmentId()).get();
		report.setAppointment(app);
		report.setDiagnosis(dto.getDiagnosis());
		Set<Therapy> therapies = new HashSet<Therapy>();
		String reservationCodes = "";

		for (TherapyDTO t : dto.getTherapies()) {
			Therapy therapy = new Therapy();
			Medicine medicine = medicineRepository.findById(t.getMedicineId()).get();
			therapy.setDurationInDays(t.getDurationInDays());
			therapy.setMedicine(medicine);
			therapies.add(therapy);
			reservationCodes = reservationCodes + medicine.getCode() + "; ";
			updateMedicineQuantity(medicine.getId(), app.getId());
			/*
			 * Reservation reservation = new Reservation();
			 * reservation.setMedicine(medicine); reservation.setPatient(app.getPatient());
			 * reservation.setPharmacy(app.getPharmacy()); reservation.setReceived(false);
			 * reservation.setAppointment(app.getId());
			 * reservation.setDueDate(LocalDate.now().plusDays(1));
			 * reservationRepository.save(reservation);
			 */
		}
		report.setTherapies(therapies);
		pharmacistReportRepository.save(report);
		app.setDone(true);
		
		//loyalty
		Patient patient = app.getPatient();
		patient.addPoints(app.getPointsAfterAdvising());
		systemAdminService.updateCathegoryOfPatient(patient);
		patientRepository.save(patient);
		
		appointmentRepository.save(app);

		/*
		 * Set<Reservation> reservations =
		 * reservationRepository.findAllByAppointment(app.getId()); String
		 * reservationCodes = ""; for(Reservation res : reservations) { reservationCodes
		 * = reservationCodes + res.getId() + "; "; }
		 */
		return reservationCodes;
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
	public PharmacistAppointment getAppointmentById(long appointmentId) {
		return appointmentRepository.findById(appointmentId).get();
	}

	@Override
	public String getMedicineCodeById(long medicineId) {
		return medicineRepository.findById(medicineId).get().getCode();
	}

	@Override
	public MedicineQuantityCheckDTO checkIfMedicineIsAvailable(long medicineId, long appointmentId) {
		Pharmacy pharmacy = appointmentRepository.findById(appointmentId).get().getPharmacist()
				.getEngegementInPharmacy().getPharmacy();
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

	private void updateMedicineQuantity(long medicineId, long appointmentId) {
		Set<MedicineQuantity> medicines = appointmentRepository.findById(appointmentId).get().getPharmacist()
				.getEngegementInPharmacy().getPharmacy().getMedicines();
		for (MedicineQuantity mq : medicines) {
			if (mq.getMedicine().getId() == medicineId) {
				mq.setQuantity(mq.getQuantity() - 1);
				medicineQuantityRepository.save(mq);
				return;
			}
		}
	}

	@Override
	public ReservationValidDTO isReservationValid(long reservationId, Pharmacist pharmacist) {
		try {
			Reservation reservation = reservationRepository.findById(reservationId).get();
			if (reservation == null)
				return new ReservationValidDTO(false);
			else if (reservation.getPharmacy().getId() == pharmacist.getEngegementInPharmacy().getId()
					&& !reservation.isReceived()) {
				return new ReservationValidDTO(reservation.getDueDate());
			} else
				return new ReservationValidDTO(false);
		} catch (Exception e) {
			return new ReservationValidDTO(false);
		}
	}

	@Override
	public Reservation reservationTaken(long reservationId) {
		try {
			Reservation reservation = reservationRepository.findById(reservationId).get();
			if (reservation == null)
				return null;
			else {
				reservation.setReceived(true);
				//loyalty program
				Patient patient = reservation.getPatient();
				patient.addPoints(reservation.getMedicine().getPoints());
				systemAdminService.updateCathegoryOfPatient(patient);
				patientRepository.save(patient);
				
				reservationRepository.save(reservation);
				medicineConsumptionRepository.save(new MedicineConsumption(reservation.getMedicine(), reservation.getPharmacy(), 1));
				return reservation;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean pharmacistHasIncomingAppointments(Pharmacist pharmacist) {
		return appointmentRepository.findAll().stream().filter(a -> a.getPharmacist().getId() == pharmacist.getId()
				&& a.getStartDateTime().isAfter(LocalDateTime.now())).count() > 0;
	}

	@Override
	public PharmacistAppointment savePharmacistAppointment(long lastAppointmentId, LocalDateTime startDateTime) {
		try {
			PharmacistAppointment lastAppointment = appointmentRepository.findById(lastAppointmentId).get();
			if (lastAppointment == null)
				return null;
			PharmacistAppointment newAppointment = new PharmacistAppointment();
			newAppointment.setPatient(lastAppointment.getPatient());
			newAppointment.setPatientWasPresent(false);
			newAppointment.setPharmacist(lastAppointment.getPharmacist());
			newAppointment.setStartDateTime(startDateTime);
			
			//loyalty program
			if(loyaltyPointsRepository.findAll() == null) {
				newAppointment.setPointsAfterAdvising(0);
				newAppointment.setPrice(lastAppointment.getPharmacist().getEngegementInPharmacy().getPharmacy().getPharmacistAppointmentCurrentPrice());
			}else 
			{
				List<LoyaltyPoints> points = loyaltyPointsRepository.findAll();
				if(!points.isEmpty()) {
					LoyaltyPoints lp = points.get(points.size() - 1);
					newAppointment.setPointsAfterAdvising(lp.getPointsAfterAdvising());
					
					double price = lastAppointment.getPharmacist().getEngegementInPharmacy().getPharmacy().getPharmacistAppointmentCurrentPrice();
					if(lastAppointment.getPatient().getCategory().equals(Loyalty.REGULAR)) {
						newAppointment.setPrice(price);
					}else if(lastAppointment.getPatient().getCategory().equals(Loyalty.SILVER)) {
						double procentage = price*(lp.getDiscountForSilver()/100);
						newAppointment.setPrice(price-procentage);
					}else if(lastAppointment.getPatient().getCategory().equals(Loyalty.GOLD)) {
						double procentage = price*(lp.getDiscountForGold()/100);
						newAppointment.setPrice(price-procentage);
					}
				}else { 
					newAppointment.setPointsAfterAdvising(0);
					newAppointment.setPrice(lastAppointment.getPharmacist().getEngegementInPharmacy().getPharmacy().getPharmacistAppointmentCurrentPrice());

				}
			}
			
			appointmentRepository.save(newAppointment);
			return newAppointment;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean checkIfFreeAppointmentExists(long lastAppointmentId, LocalDateTime startDateTime) {
		try {
			PharmacistAppointment lastAppointment = appointmentRepository.findById(lastAppointmentId).get();
			if (lastAppointment == null)
				return false;
			if (!isPharmacistInPharmacy(lastAppointment.getPharmacist(), startDateTime))
				return false;
			if (!isPharmacistAvailable(lastAppointment.getPharmacist(), startDateTime))
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

	private boolean isPharmacistInPharmacy(Pharmacist pharmacist, LocalDateTime startDateTime) {
		DayOfWeek dayOfWeek = startDateTime.getDayOfWeek();
		boolean isWorkingThatDay = false;
		if (pharmacist.getEngegementInPharmacy().getDailyEngagements().isEmpty())
			return false;
		for (DailyEngagement dailyEngagement : pharmacist.getEngegementInPharmacy().getDailyEngagements()) {
			if (dailyEngagement.getDayOfWeek().equals(dayOfWeek)) {
				isWorkingThatDay = true;
				if (!isTimeInInterval(startDateTime.toLocalTime(), dailyEngagement.getStartTime(),
						dailyEngagement.getEndTime()))
					return false;
			}
		}
		if (!isWorkingThatDay)
			return false;
		for (PharmacistLeaveRequest request : pharmacistLeaveRequestRepository
				.findAllByPharmacist_Id(pharmacist.getId())) {
			if (isDateInInterval(startDateTime.toLocalDate(), request.getStartDate(), request.getEndDate())
					&& request.isConfirmed())
				return false;
		}
		return true;
	}

	private boolean isPatientAvailable(Patient patient, LocalDateTime startDateTime) {
		Set<PharmacistAppointment> pharmAppointments = appointmentRepository.findAllByPatient_Id(patient.getId());
		for (PharmacistAppointment appointment : pharmAppointments) {
			if (appointment.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeInInterval(startDateTime.toLocalTime(), appointment.getStartDateTime().toLocalTime(),
						appointment.getStartDateTime().toLocalTime().plusMinutes(30)))
					return false;
			}
		}
		Set<DermatologistAppointment> dermAppointments = dermatologistAppointmentRepository
				.findAllByPatient_Id(patient.getId());
		for (DermatologistAppointment appointment : dermAppointments) {
			if (appointment.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeInInterval(startDateTime.toLocalTime(), appointment.getStartDateTime().toLocalTime(),
						appointment.getStartDateTime().toLocalTime().plusMinutes(appointment.getDurationInMinutes())))
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean isPharmacistAvailable(Pharmacist pharmacist, LocalDateTime startDateTime) {
		Set<PharmacistAppointment> pharmAppointments = appointmentRepository.findAllByPharmacist_Id(pharmacist.getId());
		for (PharmacistAppointment appointment : pharmAppointments) {
			if (appointment.getStartDateTime().toLocalDate().equals(startDateTime.toLocalDate())) {
				if (isTimeInInterval(startDateTime.toLocalTime(), appointment.getStartDateTime().toLocalTime(),
						appointment.getStartDateTime().toLocalTime().plusMinutes(30)))
					return false;
			}
		}
		return true;
	}

	private boolean isTimeInInterval(LocalTime time, LocalTime startTime, LocalTime endTime) {
		if (!time.isBefore(startTime) && !time.plusMinutes(30).isAfter(endTime))
			return true;
		if (time.isAfter(startTime) && time.isBefore(endTime))
			return true;
		if (time.plusMinutes(30).isAfter(startTime) && time.plusMinutes(30).isBefore(endTime))
			return true;
		return false;
	}

	private boolean isDateInInterval(LocalDate date, LocalDate startDate, LocalDate endDate) {
		if (!date.isBefore(startDate) && !date.isAfter(endDate))
			return true;
		return false;
	}

	@Override
	public boolean pharmacisttHasAppointmentsInTimInterval(Pharmacist pharmacist, LocalDate startDate,
			LocalDate endDate) {
		Set<PharmacistAppointment> appointments = appointmentRepository.findAll().stream()
				.filter(a -> a.getPharmacist().getId() == pharmacist.getId()).collect(Collectors.toSet());
		for (PharmacistAppointment a : appointments) {
			if (isDateInInterval(a.getStartDateTime().toLocalDate(), startDate, endDate))
				return true;
		}
		return false;
	}

	@Override
	public List<PharmAppDTO> getAppointmentsByWeek(PharmAppByWeekDTO dto, long userId) {
		try {
			Pharmacist pharmacist = pharmacistRepository.findOneByUser_Id(userId);
			Set<PharmacistAppointment> pharmAppointments = appointmentRepository.findAllByPharmacist_Id(pharmacist.getId());
			List<PharmAppDTO> dtos = new ArrayList<PharmAppDTO>();
			for (PharmacistAppointment app : pharmAppointments) {
				if (isDateInInterval(app.getStartDateTime().toLocalDate(), dto.getStartDate(), dto.getEndDate()) && !app.isDone()) {
					String startTime = app.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy."));
					dtos.add(new PharmAppDTO(app.getId(), startTime, app.getDefaultDurationInMinutes(), app.getPatient().getUser().getFullName()));
				}
			}
			return dtos;
		} catch (Exception e) {
			return new ArrayList<PharmAppDTO>();
		}
	}

	@Override
	public List<PharmAppDTO> getAppointmentsByMonth(PharmAppByMonthDTO dto, long userId) {
		try {
			Pharmacist pharmacist = pharmacistRepository.findOneByUser_Id(userId);
			Set<PharmacistAppointment> pharmAppointments = appointmentRepository.findAllByPharmacist_Id(pharmacist.getId());
			List<PharmAppDTO> dtos = new ArrayList<PharmAppDTO>();
			for (PharmacistAppointment app : pharmAppointments) {
				if (app.getStartDateTime().getYear() == dto.getYear()
						&& app.getStartDateTime().getMonthValue() == dto.getMonth() && !app.isDone()) {
					String startTime = app.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy."));
					dtos.add(new PharmAppDTO(app.getId(), startTime, app.getDefaultDurationInMinutes(),
							app.getPatient().getUser().getFullName()));
				}
			}
			return dtos;
		} catch (Exception e) {
			return new ArrayList<PharmAppDTO>();
		}
	}

	@Override
	public List<PharmAppDTO> getAppointmentsByYear(PharmAppByYearDTO dto, long userId) {
		try {
			Pharmacist pharmacist = pharmacistRepository.findOneByUser_Id(userId);
			Set<PharmacistAppointment> pharmAppointments = appointmentRepository.findAllByPharmacist_Id(pharmacist.getId());
			List<PharmAppDTO> dtos = new ArrayList<PharmAppDTO>();
			for (PharmacistAppointment app : pharmAppointments) {
				if (app.getStartDateTime().getYear() == dto.getYear() && !app.isDone()) {
					String startTime = app.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy."));
					dtos.add(new PharmAppDTO(app.getId(), startTime, app.getDefaultDurationInMinutes(),
							app.getPatient().getUser().getFullName()));
				}
			}
			return dtos;
		} catch (Exception e) {
			return new ArrayList<PharmAppDTO>();
		}
	}

	@Override
	public void patientWasNotPresent(long appointmentId) {
		try {
			PharmacistAppointment app = appointmentRepository.findById(appointmentId).get();
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

	public PharmacistAppointment patientSaveApp(PharmacistAppointmentDTO dto, User loggedInUser)
			throws UserDoesNotExistException, MessagingException {
		PharmacistAppointment app = new PharmacistAppointment();
		app.setPatient(patientRepository.findOneByUser_Id(loggedInUser.getId()));
		app.setDone(false);
		Pharmacist pharm = new Pharmacist();

		if (pharmacistRepository.findById(dto.getPharmacistId()).isEmpty())
			throw new UserDoesNotExistException("Pharmacist does not exist!");
		else
			pharm = pharmacistRepository.findById(dto.getPharmacistId()).get();

		app.setPharmacist(pharm);
		app.setPatientWasPresent(false);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime date = LocalDateTime.parse(dto.getDate(), formatter);
		app.setStartDateTime(date);

		PharmacistAppointment savedApp = appointmentRepository.save(app);

			emailService.sendCounselingScheduled(loggedInUser.getFullName(), "pajapataktevoli@gmail.com",
					savedApp.getPharmacist().getUser().getFullName(), dto.getDate());
		
		return app;

	}
	
	@Override
	public List<ShowCounselingDTO> getAllIncomingCounsellings(long id, boolean past){
		
		List<ShowCounselingDTO> resIncoming = new ArrayList<ShowCounselingDTO>();
		List<ShowCounselingDTO> resPast = new ArrayList<ShowCounselingDTO>();
		long patientId = patientRepository.findOneByUser_Id(id).getId();
		boolean cancellable = false;
		
		LocalDateTime today = LocalDateTime.now();
		
		Set<PharmacistAppointment> all = appointmentRepository.findAllByPatient_Id(patientId);
		for(PharmacistAppointment pa : all) {
			
			LocalDateTime checkDate = pa.getStartDateTime();
			
			if(!(pa.isDone() || today.isAfter(checkDate))) {
				cancellable = false;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
				String date = pa.getStartDateTime().format(formatter);
				
				if(today.isBefore(checkDate.minus(Period.ofDays(1)))) {
					cancellable =  true;
				}
				
				ShowCounselingDTO dto =  new ShowCounselingDTO(pa.getId(),date, pa.getPharmacist().getUser().getFullName(),cancellable,"PHARMACIST");
				resIncoming.add(dto);
			}else {
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
				String date = pa.getStartDateTime().format(formatter);
				ShowCounselingDTO dto =  new ShowCounselingDTO(pa.getId(),date, pa.getPharmacist().getUser().getFullName(),false,"PHARMACIST");
				resPast.add(dto);
			}
		}
		
		if(past) {
			return resPast;
		}else {
			return resIncoming;
		}
		
	}
	
	public void cancelApp(long id) {
		appointmentRepository.deleteById(id);
	}
	
	@Override
	public void saveLeaveRequest(LeaveDTO dto, long userId) {
		try {
			Pharmacist pharmacist = pharmacistRepository.findOneByUser_Id(userId);
			PharmacistLeaveRequest request = new PharmacistLeaveRequest();
			request.setStartDate(dto.getStartDate());
			request.setEndDate(dto.getEndDate());
			request.setPharmacist(pharmacist);
			if(dto.getType().equals("SICK_LEAVE")) request.setType(LeaveType.SICK_LEAVE);
			else request.setType(LeaveType.ANNUAL_LEAVE);
			pharmacistLeaveRequestRepository.save(request);
		} catch(Exception e) {
			return;
		}
		
	}

	@Override
	public List<LeaveViewDTO> allLeaveRequests(long userId) {
		try {
			Pharmacist pharmacist = pharmacistRepository.findOneByUser_Id(userId);
			List<LeaveViewDTO> dtos = new ArrayList<LeaveViewDTO>();
			Set<PharmacistLeaveRequest> requests = pharmacistLeaveRequestRepository.findAllByPharmacist_Id(pharmacist.getId());
			for(PharmacistLeaveRequest request : requests) {;
				LeaveViewDTO dto = new LeaveViewDTO();
				dto.setConfirmed(request.getState().toString());
				dto.setStartDate(request.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
				dto.setEndDate(request.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
				dto.setType(request.getType().toString());
				dtos.add(dto);
			}
			return dtos;
		} catch(Exception e) {
			return new ArrayList<LeaveViewDTO>();
		}
	}

	@Override
	public List<MyPatientDTO> myPatients(long userId) {
		try {
			Pharmacist pharmacist = pharmacistRepository.findOneByUser_Id(userId);
			List<MyPatientDTO> dtos = new ArrayList<MyPatientDTO>();
			Set<PharmacistAppointment> appointments = appointmentRepository.findAllByPharmacist_Id(pharmacist.getId());
			for(PharmacistAppointment app : appointments) {
				if(app.isPatientWasPresent() && app.isDone()) {
					MyPatientDTO dto = new MyPatientDTO();
					dto.setPatientId(app.getPatient().getId());
					dto.setName(app.getPatient().getUser().getName());
					dto.setSurname(app.getPatient().getUser().getSurname());
					dto.setAppointmentDate(app.getStartDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
					dto.setTime(app.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
					dtos.add(dto);
				}
			}
			return dtos;
		} catch(Exception e) {
			return new ArrayList<MyPatientDTO>();
		}
	}

	@Override
	public PharmAppDTO hasAppointmentWithPatient(long userId, long patientId) {
		try {
			Pharmacist pharmacist = pharmacistRepository.findOneByUser_Id(userId);
			Set<PharmacistAppointment> appointments = appointmentRepository.findAllByPharmacist_Id(pharmacist.getId());
			PharmAppDTO dto = new PharmAppDTO(0, "", 0, "");
			for(PharmacistAppointment app : appointments) {
				if(!app.isDone() && app.getPatient().getId() == patientId && isAppointmentNow(app.getStartDateTime())) {
					dto.setAppointmentId(app.getId());
					dto.setDurationInMinutes(app.getDefaultDurationInMinutes());
					dto.setPatientName(app.getPatient().getUser().getFullName());
					dto.setStartTime(app.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy.")));
				}
			}
			return dto;
		} catch(Exception e) {
			return new PharmAppDTO(0, "", 0, "");
		}
	}
	
	private boolean isAppointmentNow(LocalDateTime startTime) {
		//moze 10 minuta ranije i moze da kasni do 29 minuta
		if(!startTime.isBefore(LocalDateTime.now().minusMinutes(29)) && !startTime.isAfter(LocalDateTime.now().plusMinutes(10))) return true;
		return false;
	}
}
