package isa.tim28.pharmacies.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.dtos.PasswordChangeDTO;
import isa.tim28.pharmacies.dtos.PatientProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.PatientService;

@RestController
@RequestMapping(value = "patient")
public class PatientController {

	private PatientService patientService;

	@Autowired
	public PatientController(PatientService patientService) {
		super();
		this.patientService = patientService;
	}

	/*
	 * url: GET localhost:8081/patient/get HTTP request for patient profile returns
	 * ResponseEntity object
	 */
	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PatientProfileDTO> getPatient(HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if (loggedInUser.getRole() != Role.PATIENT) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can view his profile data.");
		}

		User user;
		try {
			user = patientService.getUserPart(loggedInUser.getId());
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with given id doesn't exist!");
		}

		Patient patient = new Patient();
		try {
			patient = patientService.getPatientById(loggedInUser.getId());
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<PatientProfileDTO>(new PatientProfileDTO(user.getName(), user.getSurname(),
				user.getEmail(), patient.getAddress(), patient.getCity(), patient.getCountry(), patient.getPhone(),
				patient.getPoints(), patient.getCategory().toString()), HttpStatus.OK);
	}

	@PostMapping(value = "edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PatientProfileDTO> editPatient(@RequestBody PatientProfileDTO newPatient,
			HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if (loggedInUser.getRole() != Role.PATIENT) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can change his profile data.");
		}

		Patient patient;
		try {
			patient = patientService.editPatient(newPatient, loggedInUser.getId());
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient with given id doesn't exist!");
		}
		/*
		 * } catch (BadNameException e1) { throw new
		 * ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad name!"); } catch
		 * (BadSurnameException e2) { throw new
		 * ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad surname!"); } catch
		 * (BadNewEmailException e3) { throw new
		 * ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad email!"); }
		 */

		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	/*
	 url: POST localhost:8081/derm/changePassword
	 HTTP request for changing dermatologist password
	 returns ResponseEntity object
	*/
	@PostMapping(value="changePassword", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO dto, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.PATIENT) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can change his password.");
		}
		if(!dto.isValid()) return new ResponseEntity<>("Passwords are not valid.", HttpStatus.BAD_REQUEST);
		
		try {
			if(patientService.checkOldPassword(loggedInUser.getId(), dto.getOldPassword()));
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (PasswordIncorrectException e1) {
			return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		try {
			patientService.changePassword(loggedInUser.getId(), dto.getNewPassword());
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
