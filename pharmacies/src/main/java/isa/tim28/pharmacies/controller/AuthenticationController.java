package isa.tim28.pharmacies.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isa.tim28.pharmacies.dtos.UserLoginDTO;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IAuthenticationService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	private IAuthenticationService authenticationService;
	
	@Autowired
	public AuthenticationController(IAuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}

	@PostMapping(value = "login")
	public ResponseEntity<String> logIn(@RequestBody UserLoginDTO dto, HttpSession session){
		if (session.getAttribute("loggedInUser") != null) {
			return new ResponseEntity<>("You are already logged in!", HttpStatus.FORBIDDEN);
		}
		try {
			User user = authenticationService.getUserByCredentials(dto.getEmail(), dto.getPassword());
			session.setAttribute("loggedInUser", user);
			return new ResponseEntity<>(user.getRole().toString(), HttpStatus.OK);
		} catch (UserDoesNotExistException e1) {
			return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
		} catch (PasswordIncorrectException e2) {
			return new ResponseEntity<>(e2.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "logout")
	public void logOut(HttpSession session){
		if (session.getAttribute("loggedInUser") == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user logged in!");
		}
		session.invalidate();
	}
}
