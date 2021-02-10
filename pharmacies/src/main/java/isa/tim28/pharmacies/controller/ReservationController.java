package isa.tim28.pharmacies.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
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


import isa.tim28.pharmacies.dtos.ReservationDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IReservationService;

@RestController
@RequestMapping(value = "reserv")
public class ReservationController {
	
	private IReservationService reservationService;
	
	@Autowired
	public ReservationController(IReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}
	
	@GetMapping(value = "/getByPatientId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReservationDTO>> getReservationByPatient(HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if (loggedInUser.getRole() != Role.PATIENT) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can view his profile data.");
		}
		
		List<ReservationDTO> reservations;
		try {
			reservations = reservationService.getReservationByPatient(loggedInUser.getId());
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

		}

		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}
	
	@PostMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReservationDTO>> cancelReservation(@RequestBody ReservationDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if (loggedInUser.getRole() != Role.PATIENT) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can view his profile data.");
		}
		List<ReservationDTO> res;
		try {
			res = reservationService.cancelReservation(dto, loggedInUser.getId());
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		
		return new ResponseEntity<>(res , HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/make", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReservationDTO> makeReservation(@RequestBody ReservationDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if (loggedInUser.getRole() != Role.PATIENT) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can view his profile data.");
		}
		try {
			Reservation res = reservationService.makeReservation(dto, loggedInUser);
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

		} catch (MessagingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail not sent");
			

		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
