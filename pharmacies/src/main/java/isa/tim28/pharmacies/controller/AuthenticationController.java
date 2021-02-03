package isa.tim28.pharmacies.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import isa.tim28.pharmacies.dtos.PatientDTO;
import isa.tim28.pharmacies.dtos.UserLoginDTO;
import isa.tim28.pharmacies.dtos.UserPasswordChangeDTO;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.EmailService;
import isa.tim28.pharmacies.service.interfaces.IAuthenticationService;
import isa.tim28.pharmacies.service.interfaces.IPatientService;
import isa.tim28.pharmacies.service.interfaces.IUserService;


@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	private IAuthenticationService authenticationService;
	private IPatientService patientService;
	private IUserService userService;
	private EmailService emailService;
	
	@Autowired
	public AuthenticationController(IAuthenticationService authenticationService, IPatientService patientService, IUserService userService, EmailService emailService) {
		super();
		this.authenticationService = authenticationService;
		this.patientService = patientService;
		this.userService = userService;
		this.emailService = emailService;
	}
	
	@PostMapping(value = "changePassword")
	public ResponseEntity<String> changePasswordWhileLoggingForTheFirstTime(@RequestBody UserPasswordChangeDTO dto, HttpSession session)
	{
		if (session.getAttribute("loggedInUser") != null) {
			return new ResponseEntity<>("You are already logged in!", HttpStatus.FORBIDDEN);
		}
		try {
			if(authenticationService.checkOldPassword(dto.getEmail(), dto.getPassword()));
		} catch (PasswordIncorrectException e1) {
			return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
		}
		try {
			authenticationService.changePassword(dto.getEmail(), dto.getNewPassword());
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>("", HttpStatus.OK);
		
	}
	@PostMapping(value = "login")
	public ResponseEntity<String> logIn(@RequestBody UserLoginDTO dto, HttpSession session){
		if (session.getAttribute("loggedInUser") != null) {
			return new ResponseEntity<>("You are already logged in!", HttpStatus.FORBIDDEN);
		}
		User newUser = authenticationService.getUserByEmail(dto.getEmail());
		if(newUser == null) {
			return new ResponseEntity<>("User with specified email address does not exist.", HttpStatus.NOT_FOUND);
		}
		if(newUser.getActive() == false) {
			return new ResponseEntity<>("You haven't activated your account yet! Check mail!", HttpStatus.FORBIDDEN);
		}		
		try {
			User user = authenticationService.getUserByCredentials(dto.getEmail(), dto.getPassword());
			if(user.getLoged() == false) {
				return new ResponseEntity<>("You have to change your password while logging in for the first time.", HttpStatus.FORBIDDEN);
			}
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
	
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PatientDTO> registerPatient(@RequestBody PatientDTO patientDto, HttpSession session, HttpServletRequest request) throws Exception {
		
		if (session.getAttribute("loggedInUser") != null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot register while user is loged in!");
		}
		
		User user = authenticationService.getUserByEmail(patientDto.getEmail());
		if(user != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
		User newUser = new User();
		newUser.setName(patientDto.getName());
		newUser.setSurname(patientDto.getSurname());
		newUser.setPassword(patientDto.getPassword());
		newUser.setEmail(patientDto.getEmail());
		newUser.setRole(Role.PATIENT);
		newUser.setLoged(true);
		newUser.setActive(false);
		
		Patient newPatient = new Patient();
		newPatient.setAddress(patientDto.getAddress());
		newPatient.setCity(patientDto.getCity());
		newPatient.setCountry(patientDto.getCountry());
		newPatient.setPhone(patientDto.getPhone());
		newPatient.setUser(newUser);
		
		try {
			userService.save(newUser);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
			
		try {
			patientService.save(newPatient);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
		
		emailService.sendMailAsync(newUser, getSiteURL(request));
		
		return new ResponseEntity<>(patientDto, HttpStatus.CREATED);

	}
	
	@GetMapping("/verify")
	public String activateUser(@Param("id") Long id) {
		if(userService.activate(id)) {
			return "Uspesno ste aktivirali Vas nalog.";
		}else {
			return "Neuspesno ste aktivirali Vas nalog.";
		}
	}
	
	private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  
}
