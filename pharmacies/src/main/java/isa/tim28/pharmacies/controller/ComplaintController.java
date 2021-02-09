package isa.tim28.pharmacies.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isa.tim28.pharmacies.dtos.ComplaintDTO;
import isa.tim28.pharmacies.exceptions.InvalidComplaintException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IDermatologistService;
import isa.tim28.pharmacies.service.interfaces.IPatientService;
import isa.tim28.pharmacies.service.interfaces.IPharmacistService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;

@RestController
@RequestMapping(value = "complaint")
public class ComplaintController {
	
	private IDermatologistService dermatologistService;
	private IPatientService patientService;
	private IPharmacistService pharmacistService;
	private IPharmacyService pharmacyService;
	
	@Autowired
	public ComplaintController(IDermatologistService dermatologistService, IPatientService patientService,
			IPharmacistService pharmacistService, IPharmacyService pharmacyService) {
		super();
		this.dermatologistService = dermatologistService;
		this.patientService = patientService;
		this.pharmacistService = pharmacistService;
		this.pharmacyService = pharmacyService;
	}
	
	@PostMapping(value = "complaintDermatologist")
	public ResponseEntity<String> complainDermatologist(@RequestBody ComplaintDTO dto, HttpSession session) {
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return new ResponseEntity<>("No logged in user!", HttpStatus.FORBIDDEN);
		}
		if(loggedInUser.getRole() != Role.PATIENT) {
			return new ResponseEntity<>("Only patinet can create complaint.", HttpStatus.FORBIDDEN);
		}
		
		try {
			Patient patient = patientService.getPatientById(loggedInUser.getId());
			boolean canWrite = dermatologistService.createComplaint(patient, dto);
			if(canWrite == false) {
				return new ResponseEntity<>("You cannot write a complaint on dermatologist if you don't any finished appointments with him.", HttpStatus.BAD_REQUEST);
			}
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
		} catch (InvalidComplaintException e) {
			return new ResponseEntity<>("Complaint must have between 2 and 3000 characters.", HttpStatus.BAD_REQUEST);

		}
		
		return new ResponseEntity<>("", HttpStatus.CREATED);
		
	}
	@PostMapping(value = "complaintPharmacist")
	public ResponseEntity<String> complainPharmacist(@RequestBody ComplaintDTO dto, HttpSession session) {
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return new ResponseEntity<>("No logged in user!", HttpStatus.FORBIDDEN);
		}
		if(loggedInUser.getRole() != Role.PATIENT) {
			return new ResponseEntity<>("Only patinet can create complaint.", HttpStatus.FORBIDDEN);
		}
		
		try {
			Patient patient = patientService.getPatientById(loggedInUser.getId());
			boolean canWrite = pharmacistService.createComplaint(patient, dto);
			if(canWrite == false) {
				return new ResponseEntity<>("You cannot write a complaint on pharmacist if you don't any finished appointments with him.", HttpStatus.BAD_REQUEST);
			}
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
		} catch (InvalidComplaintException e) {
			return new ResponseEntity<>("Complaint must have between 2 and 3000 characters.", HttpStatus.BAD_REQUEST);

		}
		
		return new ResponseEntity<>("", HttpStatus.CREATED);
		
	}
	
	@PostMapping(value = "complaintPharmacy")
	public ResponseEntity<String> complainPharmacy(@RequestBody ComplaintDTO dto, HttpSession session) {
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return new ResponseEntity<>("No logged in user!", HttpStatus.FORBIDDEN);
		}
		if(loggedInUser.getRole() != Role.PATIENT) {
			return new ResponseEntity<>("Only patinet can create complaint.", HttpStatus.FORBIDDEN);
		}
		
		try {
			Patient patient = patientService.getPatientById(loggedInUser.getId());
			boolean canWrite = pharmacyService.createComplaint(patient, dto);
			if(canWrite == false) {
				return new ResponseEntity<>("You cannot write a complaint on a pharmacy until you had been in an any appointment in it, or reserve and receive medicine from it, or bought medicine via eRecepy from it.", HttpStatus.BAD_REQUEST);
			}
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
		} catch (InvalidComplaintException e) {
			return new ResponseEntity<>("Complaint must have between 2 and 3000 characters.", HttpStatus.BAD_REQUEST);

		}
		
		return new ResponseEntity<>("", HttpStatus.CREATED);
		
	}
}
