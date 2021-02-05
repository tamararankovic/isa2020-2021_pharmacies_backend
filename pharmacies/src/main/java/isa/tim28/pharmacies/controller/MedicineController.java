package isa.tim28.pharmacies.controller;

import java.util.ArrayList;
import java.util.List;

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

import isa.tim28.pharmacies.dtos.MedicineInfoDTO;
import isa.tim28.pharmacies.dtos.PharmacyInfoForPatientDTO;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.MedicineService;

@RestController
@RequestMapping(value = "medicine")
public class MedicineController {

	private MedicineService medicineService;

	@Autowired
	public MedicineController(MedicineService medicineService) {
		super();
		this.medicineService = medicineService;
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

}
