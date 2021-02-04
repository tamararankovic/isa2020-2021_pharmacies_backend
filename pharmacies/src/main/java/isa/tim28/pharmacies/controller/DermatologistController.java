package isa.tim28.pharmacies.controller;

import java.util.Set;
import java.util.List;

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
import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.IsAllergicDTO;
import isa.tim28.pharmacies.dtos.MedicineDTOM;
import isa.tim28.pharmacies.dtos.MedicineDetailsDTO;
import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.dtos.PasswordChangeDTO;
import isa.tim28.pharmacies.dtos.PatientReportAllergyDTO;
import isa.tim28.pharmacies.dtos.DermatologistDTO;
import isa.tim28.pharmacies.dtos.DermatologistToEmployDTO;
import isa.tim28.pharmacies.dtos.NewDermatologistInPharmacyDTO;
import isa.tim28.pharmacies.exceptions.AddingDermatologistToPharmacyException;
import isa.tim28.pharmacies.dtos.PatientSearchDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.InvalidDeleteUserAttemptException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.DermatologistAppointmentService;
import isa.tim28.pharmacies.service.DermatologistService;
import isa.tim28.pharmacies.service.EmailService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;

@RestController
@RequestMapping(value = "derm")
public class DermatologistController {
	
	private DermatologistService dermatologistService;
	private IPharmacyAdminService pharmacyAdminService;
	private DermatologistAppointmentService dermatologistAppointmentService;
	private EmailService emailService;
	
	@Autowired
	public DermatologistController(DermatologistService dermatologistService, DermatologistAppointmentService dermatologistAppointmentService, EmailService emailService, IPharmacyAdminService pharmacyAdminService) {
		super();
		this.dermatologistService = dermatologistService;
		this.dermatologistAppointmentService = dermatologistAppointmentService;
		this.emailService = emailService;
		this.pharmacyAdminService = pharmacyAdminService;
	}
	
	/*
	 url: GET localhost:8081/derm/get
	 HTTP request for dermatologist profile
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DermatologistProfileDTO> getDermatologist(HttpSession session) {
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist can view his profile data.");
		}
		
		User dermatologist;
		try {
			dermatologist = dermatologistService.getUserPart(loggedInUser.getId());
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dermatologist with given id doesn't exist!");
		}
		
		return new ResponseEntity<>(new DermatologistProfileDTO(dermatologist.getName(), dermatologist.getSurname(), dermatologist.getEmail()), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/derm/update
	 HTTP request for changing dermatologist personal info
	 returns ResponseEntity object
	*/
	
	@PostMapping(value="update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DermatologistProfileDTO> updateDermatologist(@RequestBody DermatologistProfileDTO newDermatologist, HttpSession session) {
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist can change his profile data.");
		}
		
		User dermatologist;
		try {
			dermatologist = dermatologistService.updateDermatologist(newDermatologist, loggedInUser.getId());
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dermatologist with given id doesn't exist!");
		} catch (BadNameException e1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad name!");
		} catch (BadSurnameException e2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad surname!");
		} catch (BadNewEmailException e3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad email!");
		}
		return new ResponseEntity<>(new DermatologistProfileDTO(dermatologist.getName(), dermatologist.getSurname(), dermatologist.getEmail()), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/derm/changePassword
	 HTTP request for changing dermatologist password
	 returns ResponseEntity object
	*/
	@PostMapping(value="changePassword", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO dto, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist can change his password.");
		}
		if(!dto.isValid()) return new ResponseEntity<>("Passwords are not valid.", HttpStatus.BAD_REQUEST);
		
		try {
			if(dermatologistService.checkOldPassword(loggedInUser.getId(), dto.getOldPassword()));
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (PasswordIncorrectException e1) {
			return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		try {
			dermatologistService.changePassword(loggedInUser.getId(), dto.getNewPassword());
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<DermatologistDTO>> getAll(HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if(loggedInUser.getRole() == Role.PATIENT)
			return new ResponseEntity<>(dermatologistService.findAll(), HttpStatus.OK);
		if(loggedInUser.getRole() == Role.PHARMACY_ADMIN) {
			try {
				return new ResponseEntity<>(dermatologistService.findAllByPharmacyAdmin(pharmacyAdminService.findByUser(loggedInUser)), HttpStatus.OK);
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
				dermatologistService.deleteByPharmacyAdmin(id, admin);
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
	public ResponseEntity<Set<DermatologistDTO>> search(@PathVariable String fullName, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if(loggedInUser.getRole() == Role.PATIENT)
			return new ResponseEntity<>(dermatologistService.search(fullName), HttpStatus.OK);
		if(loggedInUser.getRole() == Role.PHARMACY_ADMIN) {
			try {
				PharmacyAdmin admin = pharmacyAdminService.findByUser(loggedInUser);
				return new ResponseEntity<>(dermatologistService.searchByPharmacyAdmin(fullName, admin), HttpStatus.OK);
			} catch(UserDoesNotExistException e1) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
	}
	
	@GetMapping(value="/get-unemployed-in-pharmacy")
	public ResponseEntity<Set<DermatologistToEmployDTO>> getUnemployedInPharmacy(HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if(loggedInUser.getRole() == Role.PHARMACY_ADMIN) {
			try {
				PharmacyAdmin admin = pharmacyAdminService.findByUser(loggedInUser);
				return new ResponseEntity<>(dermatologistService.findUnemployedByPharmacyAdmin(admin.getPharmacy()), HttpStatus.OK);
			} catch(UserDoesNotExistException e1) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
	}
	
	@PostMapping(value="/new-pharmacy")
	public void addToPharmacy(@RequestBody NewDermatologistInPharmacyDTO dto, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if(loggedInUser.getRole() == Role.PHARMACY_ADMIN) {
			try {
				PharmacyAdmin admin = pharmacyAdminService.findByUser(loggedInUser);
				dermatologistService.addToPharmacy(dto, admin.getPharmacy());
				return;
			} catch(UserDoesNotExistException e1) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
			} catch (AddingDermatologistToPharmacyException e2) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e2.getMessage());
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
	}
	/*
	 url: POST localhost:8081/derm/patients
	 HTTP request for searching patients
	 returns ResponseEntity object
	*/
	@PostMapping(value = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PatientSearchDTO>> getPatients(@RequestBody PatientSearchDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist can view patients.");
		}
		
		return new ResponseEntity<>(dermatologistService.getAllPatientsByNameAndSurname(dto.getName(), dto.getSurname()), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/derm/appointment/{appointmentId}
	 HTTP request for appointment
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/appointment/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DermatologistAppointmentDTO> getAppointment(@PathVariable Long appointmentId, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist can get appointment.");
		}
		
		return new ResponseEntity<>(dermatologistAppointmentService.getAppointmentDTOById(appointmentId), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/derm/medicine
	 HTTP request for medicine list
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/medicine", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicineDTOM>> getMedicine(HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist can get medicine list.");
		}
		
		return new ResponseEntity<>(dermatologistAppointmentService.getMedicineList(), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/derm/report
	 HTTP request for saving report
	 returns ResponseEntity object
	*/
	@PostMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveReport(@RequestBody DermatologistReportDTO dto, HttpSession session) throws Exception{
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist can fill report.");
		}
		String medicineCodes = dermatologistAppointmentService.fillReport(dto);
		User u = dermatologistAppointmentService.getAppointmentById(dto.getAppointmentId()).getPatient().getUser();
		emailService.sendMedicineEmailAsync(u.getName(), u.getEmail(), medicineCodes);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/derm/allergies
	 HTTP request for checking if patient is allergic
	 returns ResponseEntity object
	*/
	@PostMapping(value = "/allergies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IsAllergicDTO> checkAllergies(@RequestBody PatientReportAllergyDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist can check if patient is allergic.");
		}
		try {
			boolean isAllergic = dermatologistAppointmentService.checkAllergies(dto.getPatientId(), dto.getMedicineId());
			return new ResponseEntity<>(new IsAllergicDTO(isAllergic), HttpStatus.OK);
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient with given id does not exist.");
		}
	}
	
	/*
	 url: GET localhost:8081/derm/medicine/isAvailable/{medicineId}/{appointmentId}
	 HTTP request for checking if medicine is available
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/medicine/isAvailable/{medicineId}/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicineQuantityCheckDTO> checkMedicineQuantity(@PathVariable Long medicineId, @PathVariable Long appointmentId, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist can check medicine quantity.");
		}
		return new ResponseEntity<>(dermatologistAppointmentService.checkIfMedicineIsAvailable(medicineId, appointmentId), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/derm/medicine/compatible/{medicineId}
	 HTTP request for compatible medicine
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/medicine/compatible/{medicineId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicineDTOM>> compatibleMedicine(@PathVariable Long medicineId, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist can get compatible medicine.");
		}
		return new ResponseEntity<>(dermatologistAppointmentService.compatibleMedicine(medicineId), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/derm/medicineDetails/{medicineId}
	 HTTP request for compatible medicine
	 returns ResponseEntity object
	*/
	@GetMapping(value = "/medicineDetails/{medicineId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicineDetailsDTO> medicineDetails(@PathVariable Long medicineId, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.DERMATOLOGIST && loggedInUser.getRole() != Role.PHARMACIST) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only dermatologist and pharmacist can see medicine details.");
		}
		return new ResponseEntity<>(dermatologistAppointmentService.medicineDetails(medicineId), HttpStatus.OK);
	}
	
	
	
}
