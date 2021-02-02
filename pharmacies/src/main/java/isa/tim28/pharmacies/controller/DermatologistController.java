package isa.tim28.pharmacies.controller;

import java.util.Set;

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

import isa.tim28.pharmacies.dtos.DermatologistDTO;
import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.dtos.DermatologistToEmployDTO;
import isa.tim28.pharmacies.dtos.PasswordChangeDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.InvalidDeleteUserAttemptException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IDermatologistService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;

@RestController
@RequestMapping(value = "derm")
public class DermatologistController {
	
	private IDermatologistService dermatologistService;
	private IPharmacyAdminService pharmacyAdminService;
	
	@Autowired
	public DermatologistController(IDermatologistService dermatologistService, IPharmacyAdminService pharmacyAdminService) {
		super();
		this.dermatologistService = dermatologistService;
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
	 url: GET localhost:8081/derm/update
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
}
