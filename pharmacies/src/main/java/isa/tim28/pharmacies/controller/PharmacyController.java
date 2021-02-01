package isa.tim28.pharmacies.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isa.tim28.pharmacies.dtos.PharmacyInfoForPatientDTO;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;

@RestController
@RequestMapping("pharmacy")
public class PharmacyController {
	
	private IPharmacyService pharmacyService;
	
	@Autowired
	public PharmacyController(IPharmacyService pharmacyService) {
		super();
		this.pharmacyService = pharmacyService;
	}
	
	@GetMapping(value="info/{id}")
	public ResponseEntity<PharmacyInfoForPatientDTO> getPharmacyInfoForPatient(@PathVariable long id, HttpSession session){
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PATIENT) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		try {
			return new ResponseEntity<>(pharmacyService.getPharmacyInfo(id), HttpStatus.OK);
		} catch (PharmacyNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pharmacy not found");
		}
	}
}
