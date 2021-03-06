package isa.tim28.pharmacies.controller;

import java.util.List;
import java.util.Set;

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

import isa.tim28.pharmacies.dtos.MedicineForPharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.MedicineInPharmacyDTO;
import isa.tim28.pharmacies.dtos.MedicineInfoDTO;
import isa.tim28.pharmacies.dtos.MedicineSearchDTO;
import isa.tim28.pharmacies.dtos.SearchMedicineByNameDTO;


import isa.tim28.pharmacies.dtos.SearchMedicineDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.PharmacyAdmin;

import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IMedicineService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;

@RestController
@RequestMapping(value = "medicine")
public class MedicineController {
	
	private IMedicineService medicineService;
	private IPharmacyAdminService pharmacyAdminService;
	private IPharmacyService pharmacyService;
	
	@Autowired
	public MedicineController(IMedicineService medicineService, IPharmacyAdminService pharmacyAdminService, IPharmacyService pharmacyService) {
		super();
		this.medicineService = medicineService;
		this.pharmacyAdminService = pharmacyAdminService;
		this.pharmacyService = pharmacyService; 
	}
	
	
	@GetMapping(value = "by-pharmacy")
	public ResponseEntity<Set<MedicineForPharmacyAdminDTO>> getAll(HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PHARMACY_ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
			return new ResponseEntity<>(medicineService.getAll(admin.getPharmacy()), HttpStatus.OK);
		} catch(UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		}
	}
	
	@GetMapping(value = "offered-by-pharmacy")
	public ResponseEntity<Set<MedicineInPharmacyDTO>> getAllOffered(HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PHARMACY_ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
			return new ResponseEntity<>(medicineService.getAllOffered(admin.getPharmacy()), HttpStatus.OK);
		} catch(UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		}
	}
	
	@GetMapping(value = "not-offered-by-pharmacy")
	public ResponseEntity<Set<MedicineForPharmacyAdminDTO>> getAllNotOffered(HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PHARMACY_ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
			return new ResponseEntity<>(medicineService.getAllNotOffered(admin.getPharmacy()), HttpStatus.OK);
		} catch(UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		}
	}

	@PostMapping(value = "search")
	public ResponseEntity<Set<MedicineInPharmacyDTO>> search(@RequestBody SearchMedicineDTO dto, HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PHARMACY_ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
			return new ResponseEntity<>(medicineService.search(dto, admin.getPharmacy()), HttpStatus.OK);
		} catch(UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		}
	}

	@PostMapping(value = "/all")
	public ResponseEntity<List<MedicineInfoDTO>> getAllMedicine(@RequestBody MedicineInfoDTO dto, HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");
		if (user == null) {
				return new ResponseEntity<>(medicineService.getAllMedicineInfo(dto.getName(), dto.getForm(),
						dto.getType(), dto.getManufacturer()), HttpStatus.OK);

			
		} else if (user.getRole() == Role.PATIENT) {
			
				return new ResponseEntity<>(medicineService.getAllMedicineInfo(dto.getName(), dto.getForm(),
						dto.getType(), dto.getManufacturer()), HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");

		}
	}
	@PostMapping(value = "/searchMedicine")
	public ResponseEntity<List<MedicineSearchDTO>> searchMedicine(@RequestBody SearchMedicineByNameDTO dto, HttpSession session) {
		
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			return new ResponseEntity<>(pharmacyService.searchMedicineByName(dto.getName()), HttpStatus.OK);
		}
		else if (user.getRole() == Role.SUPPLIER) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}else {
		
			return new ResponseEntity<>(pharmacyService.searchMedicineByName(dto.getName()), HttpStatus.OK);
		}
	}
}
