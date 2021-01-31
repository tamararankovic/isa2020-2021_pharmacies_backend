package isa.tim28.pharmacies.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.DermatologistService;

@RestController
@RequestMapping(value = "derm")
public class DermatologistController {
	
	private DermatologistService dermatologistService;
	
	@Autowired
	public DermatologistController(DermatologistService dermatologistService) {
		super();
		this.dermatologistService = dermatologistService;
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

}
