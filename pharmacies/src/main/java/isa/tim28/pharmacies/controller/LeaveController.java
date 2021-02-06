package isa.tim28.pharmacies.controller;

import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isa.tim28.pharmacies.dtos.LeaveRequestDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.LeaveRequestnotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.ILeaveService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;
import isa.tim28.pharmacies.service.interfaces.ISystemAdminService;

@RestController
@RequestMapping(value = "leave")
public class LeaveController {

	private ILeaveService leaveService;
	private IPharmacyAdminService pharmacyAdminService;
	private ISystemAdminService systemAdminService;
	
	@Autowired
	public LeaveController(ILeaveService leaveService, IPharmacyAdminService pharmacyAdminService,
			ISystemAdminService systemAdminService) {
		super();
		this.leaveService = leaveService;
		this.pharmacyAdminService = pharmacyAdminService;
		this.systemAdminService = systemAdminService;
	}
	
	@GetMapping
	public ResponseEntity<Set<LeaveRequestDTO>> getAll(HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() == Role.PHARMACY_ADMIN) {
			try {
				PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
				return new ResponseEntity<>(leaveService.getAllWaitingReviewForPharmacists(admin.getPharmacy()), HttpStatus.OK);
			} catch(UserDoesNotExistException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			}
		}
		else if (user.getRole() == Role.SYSTEM_ADMIN) {
			try {
				systemAdminService.findByUser(user);
				return new ResponseEntity<>(leaveService.getAllWaitingReviewForDermatologists(), HttpStatus.OK);
			} catch(UserDoesNotExistException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			}
		}
		else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
	}
	
	@PostMapping(value = "accept/{requestId}")
	public void accept(@PathVariable long requestId, HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() == Role.PHARMACY_ADMIN) {
			try {
				PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
				leaveService.acceptPharmacistLeaveRequest(requestId, admin);
			} catch(UserDoesNotExistException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			} catch (LeaveRequestnotFoundException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			} catch (ForbiddenOperationException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			} catch (MessagingException e) {
				return;
			}
		}
		else if (user.getRole() == Role.SYSTEM_ADMIN) {
			try {
				systemAdminService.findByUser(user);
				leaveService.acceptDermatologistLeaveRequest(requestId);
			} catch(UserDoesNotExistException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			} catch (ForbiddenOperationException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			} catch (LeaveRequestnotFoundException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			} catch (MessagingException e) {
				return;
			}
		}
		else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
	}
	
	@PostMapping(value = "decline/{requestId}")
	public void accept(@PathVariable long requestId, @RequestBody String reasonDeclined, HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() == Role.PHARMACY_ADMIN) {
			try {
				PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
				leaveService.declinePharmacistLeaveRequest(requestId, admin, reasonDeclined);
			} catch(UserDoesNotExistException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			} catch (LeaveRequestnotFoundException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			} catch (ForbiddenOperationException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			} catch (MessagingException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			}
		}
		else if (user.getRole() == Role.SYSTEM_ADMIN) {
			try {
				systemAdminService.findByUser(user);
				leaveService.declineDermatologistLeaveRequest(requestId, reasonDeclined);
			} catch(UserDoesNotExistException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			} catch (ForbiddenOperationException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			} catch (LeaveRequestnotFoundException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			} catch (MessagingException e) {
				return;
			}
		}
		else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
	}
}
