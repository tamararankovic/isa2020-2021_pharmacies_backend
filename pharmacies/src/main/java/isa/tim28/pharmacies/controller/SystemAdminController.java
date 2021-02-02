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

import isa.tim28.pharmacies.dtos.SupplierDTO;

import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.Supplier;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IAuthenticationService;
import isa.tim28.pharmacies.service.interfaces.ISupplierService;
import isa.tim28.pharmacies.service.interfaces.IUserService;

@RestController
@RequestMapping("admin")
public class SystemAdminController {

	private IUserService userService;
	private ISupplierService supplierService;
	private IAuthenticationService authenticationService;
	
	@Autowired
	public SystemAdminController(IUserService userService, ISupplierService supplierService, IAuthenticationService authenticationService) {
		super();
		this.userService = userService;
		this.supplierService = supplierService;
		this.authenticationService = authenticationService;
	}
	
	@PostMapping(value = "registerSupplier")
	public ResponseEntity<String> registerSupplier(@RequestBody SupplierDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return new ResponseEntity<>("No logged in user!", HttpStatus.FORBIDDEN);
		}
		if(loggedInUser.getRole() != Role.SYSTEM_ADMIN) {
			return new ResponseEntity<>("Only system admin can register new supplier.", HttpStatus.FORBIDDEN);
		}
		
		User supplier = authenticationService.getUserByEmail(dto.getEmail());
		if(supplier != null) {
			return new ResponseEntity<>("User with specified email address already exists!", HttpStatus.BAD_REQUEST);
		}
		User newUser = new User();
		newUser.setName(dto.getName());
		newUser.setSurname(dto.getSurname());
		newUser.setPassword(dto.getPassword());
		newUser.setEmail(dto.getEmail());
		newUser.setRole(Role.SUPPLIER);
		newUser.setLoged(false);
		newUser.setActive(true);
		
		Supplier newSupplier = new Supplier();
		newSupplier.setUser(newUser);
		
		try {
			userService.save(newUser);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
		try {
			supplierService.save(newSupplier);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
		
		return new ResponseEntity<>("", HttpStatus.CREATED);
		
	}
	
	
	
	
}
