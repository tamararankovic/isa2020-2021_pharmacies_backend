package isa.tim28.pharmacies.controller;

import java.util.Set;
import java.time.LocalDateTime;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import isa.tim28.pharmacies.dtos.DermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.IsAllergicDTO;
import isa.tim28.pharmacies.dtos.IsAppointmentAvailableDTO;
import isa.tim28.pharmacies.dtos.MedicineDTOM;
import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.dtos.PasswordChangeDTO;
import isa.tim28.pharmacies.dtos.PatientReportAllergyDTO;
import isa.tim28.pharmacies.dtos.NewPharmacistDTO;
import isa.tim28.pharmacies.dtos.PharmacistDTO;
import isa.tim28.pharmacies.dtos.PatientSearchDTO;
import isa.tim28.pharmacies.dtos.PharmacistProfileDTO;
import isa.tim28.pharmacies.dtos.PharmacistSaveAppointmentDTO;
import isa.tim28.pharmacies.dtos.ReservationValidDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.CreatePharmacistException;
import isa.tim28.pharmacies.exceptions.InvalidDeleteUserAttemptException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.PharmacistAppointment;
import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.DermatologistService;
import isa.tim28.pharmacies.service.EmailService;
import isa.tim28.pharmacies.service.PharmacistAppointmentService;
import isa.tim28.pharmacies.service.PharmacistService;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;

@RestController
@RequestMapping(value = "pharm")
public class PharmacistController {

	private PharmacistService pharmacistService;
	private DermatologistService dermatologistService;
	private IPharmacyAdminService pharmacyAdminService;
	private PharmacistAppointmentService pharmacistAppointmentService;
	private EmailService emailService;
	
	@Autowired
	public PharmacistController(PharmacistService pharmacistService, DermatologistService dermatologistService, PharmacistAppointmentService pharmacistAppointmentService, EmailService emailService, IPharmacyAdminService pharmacyAdminService) {
		super();
		this.pharmacistService = pharmacistService;
		this.pharmacyAdminService = pharmacyAdminService;
		this.dermatologistService = dermatologistService;
		this.emailService = emailService;
		this.pharmacistAppointmentService = pharmacistAppointmentService;
	}
	
	/*
	 url: GET localhost:8081/pharm/get
	 HTTP request for pharmacist profile
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacistProfileDTO> getPharmacist(HttpSession session) {
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can view his profile data.");
		}
		
		User pharmacist;
		try {
			pharmacist = pharmacistService.getUserPart(loggedInUser.getId());
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pharmacist with given id doesn't exist!");
		}
		
		return new ResponseEntity<>(new PharmacistProfileDTO(pharmacist.getName(), pharmacist.getSurname(), pharmacist.getEmail()), HttpStatus.OK);
	}
	
	
	/*
	 url: GET localhost:8081/pharm/update
	 HTTP request for changing pharmacist personal info
	 returns ResponseEntity object
	*/
	@PostMapping(value="update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PharmacistProfileDTO> updatePharmacist(@RequestBody PharmacistProfileDTO newPharmacist, HttpSession session) {
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can change his profile data.");
		}
		
		User pharmacist;
		try {
			pharmacist = pharmacistService.updatePharmacist(newPharmacist, loggedInUser.getId());
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pharmacist with given id doesn't exist!");
		} catch (BadNameException e1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad name!");
		} catch (BadSurnameException e2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad surname!");
		} catch (BadNewEmailException e3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad email!");
		}
		return new ResponseEntity<>(new PharmacistProfileDTO(pharmacist.getName(), pharmacist.getSurname(), pharmacist.getEmail()), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/pharm/changePassword
	 HTTP request for changing pharmacist password
	 returns ResponseEntity object
	*/
	@PostMapping(value="changePassword", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO dto, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can change his password.");
		}
		if(!dto.isValid()) return new ResponseEntity<>("Passwords are not valid.", HttpStatus.BAD_REQUEST);
		
		try {
			if(pharmacistService.checkOldPassword(loggedInUser.getId(), dto.getOldPassword()));
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (PasswordIncorrectException e1) {
			return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		try {
			pharmacistService.changePassword(loggedInUser.getId(), dto.getNewPassword());
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<PharmacistDTO>> getAll(HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if(loggedInUser.getRole() == Role.PATIENT)
			return new ResponseEntity<>(pharmacistService.findAll(), HttpStatus.OK);
		if(loggedInUser.getRole() == Role.PHARMACY_ADMIN) {
			try {
				return new ResponseEntity<>(pharmacistService.findAllByPharmacyAdmin(pharmacyAdminService.findByUser(loggedInUser)), HttpStatus.OK);
			} catch(UserDoesNotExistException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
	}
	
	@PostMapping(value="/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if(loggedInUser.getRole() == Role.PHARMACY_ADMIN) {
			try {
				PharmacyAdmin admin = pharmacyAdminService.findByUser(loggedInUser);
				pharmacistService.deleteByPharmacyAdmin(id, admin);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch(UserDoesNotExistException e1) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
			} catch(InvalidDeleteUserAttemptException e2 ) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e2.getMessage());
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
	}
	
	@GetMapping(value="/search/{fullName}")
	public ResponseEntity<Set<PharmacistDTO>> search(@PathVariable String fullName, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if(loggedInUser.getRole() == Role.PATIENT)
			return new ResponseEntity<>(pharmacistService.search(fullName), HttpStatus.OK);
		if(loggedInUser.getRole() == Role.PHARMACY_ADMIN) {
			try {
				PharmacyAdmin admin = pharmacyAdminService.findByUser(loggedInUser);
				return new ResponseEntity<>(pharmacistService.searchByPharmacyAdmin(fullName, admin), HttpStatus.OK);
			} catch(UserDoesNotExistException e1) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
	}
	
	@PostMapping(value="/create")
	public ResponseEntity<String> create(@RequestBody NewPharmacistDTO dto, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if(loggedInUser.getRole() == Role.PHARMACY_ADMIN) {
			try {
				PharmacyAdmin admin = pharmacyAdminService.findByUser(loggedInUser);
				pharmacistService.create(dto, admin.getPharmacy());
				return new ResponseEntity<>(HttpStatus.OK);
			} catch(UserDoesNotExistException e1) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
			} catch(CreatePharmacistException e2 ) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e2.getMessage());
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
	}
	/*
	 url: POST localhost:8081/pharm/patients
	 HTTP request for searching patients
	 returns ResponseEntity object
	*/
	@PostMapping(value = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PatientSearchDTO>> getPatients(@RequestBody PatientSearchDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can view patients.");
		}
		
		return new ResponseEntity<>(dermatologistService.getAllPatientsByNameAndSurname(dto.getName(), dto.getSurname()), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/pharm/appointment/{appointmentId}
	 HTTP request for appointment
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/appointment/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DermatologistAppointmentDTO> getAppointment(@PathVariable Long appointmentId, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can get appointment.");
		}
		
		return new ResponseEntity<>(pharmacistAppointmentService.getAppointmentDTOById(appointmentId), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/pharm/medicine
	 HTTP request for medicine list
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/medicine", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicineDTOM>> getMedicine(HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can get medicine list.");
		}
		
		return new ResponseEntity<>(pharmacistAppointmentService.getMedicineList(), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/pharm/report
	 HTTP request for saving report
	 returns ResponseEntity object
	*/
	@PostMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveReport(@RequestBody DermatologistReportDTO dto, HttpSession session) throws Exception{
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can fill report.");
		}
		String medicineCodes = pharmacistAppointmentService.fillReport(dto);
		User u = pharmacistAppointmentService.getAppointmentById(dto.getAppointmentId()).getPatient().getUser();
		emailService.sendMedicineEmailAsync(u.getName(), u.getEmail(), medicineCodes);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/pharm/allergies
	 HTTP request for checking if patient is allergic
	 returns ResponseEntity object
	*/
	@PostMapping(value = "/allergies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IsAllergicDTO> checkAllergies(@RequestBody PatientReportAllergyDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can check if patient is allergic.");
		}
		try {
			boolean isAllergic = pharmacistAppointmentService.checkAllergies(dto.getPatientId(), dto.getMedicineId());
			return new ResponseEntity<>(new IsAllergicDTO(isAllergic), HttpStatus.OK);
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient with given id does not exist.");
		}
	}
	
	/*
	 url: GET localhost:8081/pharm/medicine/isAvailable/{medicineId}/{appointmentId}
	 HTTP request for checking if medicine is available
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/medicine/isAvailable/{medicineId}/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicineQuantityCheckDTO> checkMedicineQuantity(@PathVariable Long medicineId, @PathVariable Long appointmentId, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can check medicine quantity.");
		}
		return new ResponseEntity<>(pharmacistAppointmentService.checkIfMedicineIsAvailable(medicineId, appointmentId), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/pharm/medicine/compatible/{medicineId}
	 HTTP request for compatible medicine
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/medicine/compatible/{medicineId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicineDTOM>> compatibleMedicine(@PathVariable Long medicineId, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can get compatible medicine.");
		}
		return new ResponseEntity<>(pharmacistAppointmentService.compatibleMedicine(medicineId), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/pharm/reservationValid/{reservationId}
	 HTTP request for searching reservations
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/reservationValid/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReservationValidDTO> reservationValid(@PathVariable Long reservationId, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can see reservations.");
		}
		Pharmacist pharmacist = pharmacistService.getPharmacistFromUser(loggedInUser.getId());
		if(pharmacist != null) {
			return new ResponseEntity<>(pharmacistAppointmentService.isReservationValid(reservationId, pharmacist), HttpStatus.OK);
		} else return new ResponseEntity<>(new ReservationValidDTO(false), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/pharm/reservation/{reservationId}
	 HTTP request for reservation medicine received
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/reservation/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reservationReceived(@PathVariable Long reservationId, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can see reservations.");
		}
		
		Reservation reservation = pharmacistAppointmentService.reservationTaken(reservationId);
		if(reservation != null) {
			String patientName = reservation.getPatient().getUser().getName();
			String patientEmail = reservation.getPatient().getUser().getEmail();
			try {
				emailService.sendReservationReceivedEmailAsync(patientName, patientEmail, reservationId);
				return new ResponseEntity<>("", HttpStatus.OK);
			} catch (MessagingException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email not sent.");
			}
		}
		else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No reservation.");	
	}
	
	/*
	 url: POST localhost:8081/pharm/saveAppointment
	 HTTP request for saving new appointment
	 returns ResponseEntity object
	*/
	@PostMapping(value = "/saveAppointment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveAppointment(@RequestBody PharmacistSaveAppointmentDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can schedule appointments.");
		}
		
		PharmacistAppointment appointment = pharmacistAppointmentService.savePharmacistAppointment(dto.getLastAppointmentId(), dto.getStartDateTime());
		if(appointment != null) {
			String patientName = appointment.getPatient().getUser().getName();
			String patientEmail = appointment.getPatient().getUser().getEmail();
			LocalDateTime startDateTime = appointment.getStartDateTime();
			try {
				emailService.sendAppointmnetConfirmationAsync(patientName, patientEmail, startDateTime);
				return new ResponseEntity<>("", HttpStatus.OK);
			} catch (MessagingException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email not sent.");
			}
		}
		else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No appointment.");
	}
	
	/*
	 url: POST localhost:8081/pharm/appointment
	 HTTP request for saving new appointment
	 returns ResponseEntity object
	*/
	@PostMapping(value = "/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IsAppointmentAvailableDTO> isAppointmentAvailable(@RequestBody PharmacistSaveAppointmentDTO dto, HttpSession session){
		
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacist can schedule appointments.");
		}
		
		IsAppointmentAvailableDTO available = new IsAppointmentAvailableDTO(pharmacistAppointmentService.checkIfFreeAppointmentExists(dto.getLastAppointmentId(), dto.getStartDateTime()));
		return new ResponseEntity<>(available, HttpStatus.OK);
	}
}
