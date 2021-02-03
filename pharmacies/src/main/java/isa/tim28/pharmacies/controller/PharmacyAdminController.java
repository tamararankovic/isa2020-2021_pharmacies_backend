package isa.tim28.pharmacies.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isa.tim28.pharmacies.dtos.PharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.UserPasswordChangeDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;

@RestController
@RequestMapping("pharmacy-admin")
public class PharmacyAdminController {

private IPharmacyAdminService pharmacyAdminService;
	
	@Autowired
	public PharmacyAdminController(IPharmacyAdminService pharmacyAdminService) {
		super();
		this.pharmacyAdminService = pharmacyAdminService;
	}
	
	@GetMapping
	public ResponseEntity<PharmacyAdminDTO> get(HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PHARMACY_ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
			return new ResponseEntity<>(new PharmacyAdminDTO(admin.getUser().getName(), admin.getUser().getSurname(), admin.getUser().getEmail()), HttpStatus.OK);
		} catch(UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		}
	}
	
	@PostMapping(value="update")
	public void update(@RequestBody PharmacyAdminDTO dto, HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PHARMACY_ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
			pharmacyAdminService.update(admin, dto);
		} catch(UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		} catch (BadNewEmailException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadNameException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadSurnameException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PostMapping(value="change-password")
	public void update(@RequestBody UserPasswordChangeDTO dto, HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PHARMACY_ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
			pharmacyAdminService.changePassword(admin, dto);
		} catch(UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		} catch (PasswordIncorrectException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
