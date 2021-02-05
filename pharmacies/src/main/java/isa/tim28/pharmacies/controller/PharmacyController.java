package isa.tim28.pharmacies.controller;

import java.util.ArrayList;
import java.util.List;

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

import isa.tim28.pharmacies.dtos.MedicineInfoDTO;


import isa.tim28.pharmacies.dtos.PharmacyBasicInfoDTO;

import isa.tim28.pharmacies.dtos.PharmacyInfoForPatientDTO;
import isa.tim28.pharmacies.exceptions.PharmacyDataInvalidException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;

@RestController
@RequestMapping("pharmacy")
public class PharmacyController {

	private IPharmacyService pharmacyService;
	private IPharmacyAdminService pharmacyAdminService;

	@Autowired
	public PharmacyController(IPharmacyService pharmacyService, IPharmacyAdminService pharmacyAdminService) {
		super();
		this.pharmacyService = pharmacyService;
		this.pharmacyAdminService = pharmacyAdminService;
	}

	@GetMapping(value = "info/{id}")
	public ResponseEntity<PharmacyInfoForPatientDTO> getPharmacyInfoForPatient(@PathVariable long id,
			HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PATIENT) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		try {
			return new ResponseEntity<>(pharmacyService.getPharmacyInfo(id), HttpStatus.OK);
		} catch (PharmacyNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pharmacy not found");
		}
	}

	@PostMapping(value = "/all")
	public ResponseEntity<ArrayList<PharmacyInfoForPatientDTO>> getPharmacyInfoForPatient(
			@RequestBody PharmacyInfoForPatientDTO dto, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null) {
			try {
				return new ResponseEntity<>(pharmacyService.getAllPharmacies(dto.getName(), dto.getAddress()),
						HttpStatus.OK);
			} catch (PharmacyNotFoundException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pharmacy not found");
			}
		} else if (user.getRole() == Role.PATIENT) {
			try {
				return new ResponseEntity<>(pharmacyService.getAllPharmacies(dto.getName(), dto.getAddress()),
						HttpStatus.OK);
			} catch (PharmacyNotFoundException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pharmacy not found");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
	}

	@GetMapping(value = "basic-info")
	public ResponseEntity<PharmacyBasicInfoDTO> getBasicInfo(HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PHARMACY_ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
			return new ResponseEntity<>(pharmacyService.getBasicInfo(admin), HttpStatus.OK);
		} catch (UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		} catch (PharmacyNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping(value = "update")
	public void update(@RequestBody PharmacyBasicInfoDTO dto, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PHARMACY_ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
			pharmacyService.update(admin, dto);
		} catch (UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		} catch (PharmacyNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (PharmacyDataInvalidException e2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e2.getMessage());
		}
	}

	@PostMapping(value = "/getByMedicine",  produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacyInfoForPatientDTO>> getPharmacyByMedicine(@RequestBody MedicineInfoDTO dto, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No logged in user!");
		}
		if (loggedInUser.getRole() != Role.PATIENT) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can view his profile data.");
		}
		
		List<PharmacyInfoForPatientDTO> res = new ArrayList<PharmacyInfoForPatientDTO>();
		try {
			res = pharmacyService.getPharmacyByMedicineId(dto.getId());
		} catch (PharmacyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

}
