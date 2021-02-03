package isa.tim28.pharmacies.controller;

import java.util.List;

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
import isa.tim28.pharmacies.dtos.DermatologistRegisterDTO;
import isa.tim28.pharmacies.dtos.PharmacyAddAdminDTO;
import isa.tim28.pharmacies.dtos.PharmacyAdminRegisterDTO;
import isa.tim28.pharmacies.dtos.PharmacyRegisterDTO;
import isa.tim28.pharmacies.dtos.SupplierDTO;
import isa.tim28.pharmacies.dtos.SystemAdminDTO;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.Supplier;
import isa.tim28.pharmacies.model.SystemAdmin;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IAuthenticationService;
import isa.tim28.pharmacies.service.interfaces.IDermatologistService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;
import isa.tim28.pharmacies.service.interfaces.ISupplierService;
import isa.tim28.pharmacies.service.interfaces.ISystemAdminService;
import isa.tim28.pharmacies.service.interfaces.IUserService;

@RestController
@RequestMapping("admin")
public class SystemAdminController {

	private IUserService userService;
	private ISupplierService supplierService;
	private IAuthenticationService authenticationService;
	private ISystemAdminService systemAdminService;
	private IPharmacyService pharmacyService;
	private IDermatologistService dermatologistService;
	private IPharmacyAdminService pharmacyAdminService;
	
	@Autowired
	public SystemAdminController(IUserService userService, ISupplierService supplierService, IAuthenticationService authenticationService, ISystemAdminService systemAdminService, IPharmacyService pharmacyService, IDermatologistService dermatologistService, IPharmacyAdminService pharmacyAdminService) {
		super();
		this.userService = userService;
		this.supplierService = supplierService;
		this.authenticationService = authenticationService;
		this.systemAdminService = systemAdminService;
		this.pharmacyService = pharmacyService;
		this.dermatologistService = dermatologistService;
		this.pharmacyAdminService = pharmacyAdminService;
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
	
	@PostMapping(value = "registerSystemAdmin")
	public ResponseEntity<String> registerSystemAdmin(@RequestBody SystemAdminDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return new ResponseEntity<>("No logged in user!", HttpStatus.FORBIDDEN);
		}
		if(loggedInUser.getRole() != Role.SYSTEM_ADMIN) {
			return new ResponseEntity<>("Only system admin can register new system admin.", HttpStatus.FORBIDDEN);
		}
		
		User systemAdmin = authenticationService.getUserByEmail(dto.getEmail());
		if(systemAdmin != null) {
			return new ResponseEntity<>("User with specified email address already exists!", HttpStatus.BAD_REQUEST);
		}
		User newUser = new User();
		newUser.setName(dto.getName());
		newUser.setSurname(dto.getSurname());
		newUser.setPassword(dto.getPassword());
		newUser.setEmail(dto.getEmail());
		newUser.setRole(Role.SYSTEM_ADMIN);
		newUser.setLoged(false);
		newUser.setActive(true);
		
		SystemAdmin newSystemAdmin = new SystemAdmin();
		newSystemAdmin.setUser(newUser);
		
		try {
			userService.save(newUser);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
		try {
			systemAdminService.save(newSystemAdmin);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
		
		return new ResponseEntity<>("", HttpStatus.CREATED);
		
	}
	
	@PostMapping(value = "registerDermatologist")
	public ResponseEntity<String> registerDermatologist(@RequestBody DermatologistRegisterDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return new ResponseEntity<>("No logged in user!", HttpStatus.FORBIDDEN);
		}
		if(loggedInUser.getRole() != Role.SYSTEM_ADMIN) {
			return new ResponseEntity<>("Only system admin can register new dermatologist.", HttpStatus.FORBIDDEN);
		}
		
		User dermatologist = authenticationService.getUserByEmail(dto.getEmail());
		if(dermatologist != null) {
			return new ResponseEntity<>("User with specified email address already exists!", HttpStatus.BAD_REQUEST);
		}
		User newUser = new User();
		newUser.setName(dto.getName());
		newUser.setSurname(dto.getSurname());
		newUser.setPassword(dto.getPassword());
		newUser.setEmail(dto.getEmail());
		newUser.setRole(Role.DERMATOLOGIST);
		newUser.setLoged(false);
		newUser.setActive(true);
		
		Dermatologist newDermatologist = new Dermatologist();
		newDermatologist.setUser(newUser);
		
		try {
			userService.save(newUser);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
		try {
			dermatologistService.save(newDermatologist);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
		
		return new ResponseEntity<>("", HttpStatus.CREATED);
		
	}
	
	
	@PostMapping(value = "registerPharmacy")
	public ResponseEntity<String> registerPharmacy(@RequestBody PharmacyRegisterDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return new ResponseEntity<>("No logged in user!", HttpStatus.FORBIDDEN);
		}
		if(loggedInUser.getRole() != Role.SYSTEM_ADMIN) {
			return new ResponseEntity<>("Only system admin can register new system admin.", HttpStatus.FORBIDDEN);
		}
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setName(dto.getName());
		pharmacy.setAddress(dto.getAddress());
		pharmacy.setDescription(dto.getDescription());
		
		try {
			pharmacyService.save(pharmacy);
		} catch (Exception e) {
			return new ResponseEntity<>("Pharmacy cannot be saved!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("", HttpStatus.CREATED);
	}
		
	@GetMapping(value = "/getPharmacies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacyAddAdminDTO>> getAllPharmacies(HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if(loggedInUser.getRole() != Role.SYSTEM_ADMIN) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only system admin can see all pharmacies.");
		}
		
		return new ResponseEntity<>(pharmacyService.getAllPharmacies(), HttpStatus.OK);
	}
	
	@PostMapping(value = "registerPharmacyAdmin")
	public ResponseEntity<String> registerPharmacyAdmin(@RequestBody PharmacyAdminRegisterDTO dto, HttpSession session){
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return new ResponseEntity<>("No logged in user!", HttpStatus.FORBIDDEN);
		}
		if(loggedInUser.getRole() != Role.SYSTEM_ADMIN) {
			return new ResponseEntity<>("Only system admin can register new pharmacy admin.", HttpStatus.FORBIDDEN);
		}
		
		User systemAdmin = authenticationService.getUserByEmail(dto.getEmail());
		if(systemAdmin != null) {
			return new ResponseEntity<>("User with specified email address already exists!", HttpStatus.BAD_REQUEST);
		}
		User newUser = new User();
		newUser.setName(dto.getName());
		newUser.setSurname(dto.getSurname());
		newUser.setPassword(dto.getPassword());
		newUser.setEmail(dto.getEmail());
		newUser.setRole(Role.PHARMACY_ADMIN);
		newUser.setLoged(false);
		newUser.setActive(true);
		
		PharmacyAdmin newPharmacyAdmin = new PharmacyAdmin();
		newPharmacyAdmin.setUser(newUser);
		Pharmacy pharmacy;
		
		try {
			pharmacy = pharmacyService.getPharmacyById(dto.getIdOfPharmacy());
			newPharmacyAdmin.setPharmacy(pharmacy);		
		} catch (PharmacyNotFoundException e1) {
			return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		try {
			userService.save(newUser);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
		try {
			pharmacyAdminService.save(newPharmacyAdmin);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email address already exists!");
		}
		
		return new ResponseEntity<>("", HttpStatus.CREATED);
		
	}
	
}
