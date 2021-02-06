package isa.tim28.pharmacies.controller;

import java.util.Set;

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
import isa.tim28.pharmacies.dtos.OfferSupplierDTO;
import isa.tim28.pharmacies.dtos.OfferSupplierProfileDTO;
import isa.tim28.pharmacies.dtos.OfferUpdateDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.OfferDoesNotExistException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.Supplier;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IOfferService;
import isa.tim28.pharmacies.service.interfaces.ISupplierService;


@RestController
@RequestMapping(value = "offer")
public class OfferController {
	
	private IOfferService offerService;
	private ISupplierService supplierService;
	
	@Autowired
	public OfferController(ISupplierService supplierService, IOfferService offerService)
	 {
		super();
		this.supplierService = supplierService;
		this.offerService = offerService;
	}
	

	@PostMapping(value = "createOffer")
	public ResponseEntity<String> create(@RequestBody OfferSupplierDTO offer, HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.SUPPLIER) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			Supplier supplier = supplierService.getSupplierById(user.getId());
			boolean alreadySent  = offerService.alreadySentOffer(offer, supplier);
			if(alreadySent == false) {
				return new ResponseEntity<>("You have already gave an offer to this order!", HttpStatus.BAD_REQUEST);

			}
			boolean exists= offerService.createOffer(offer, supplier);
			if(exists == false) {
				return new ResponseEntity<>("Deadline for giving an offer to this order has been passed! ", HttpStatus.BAD_REQUEST);
			}
			} catch(UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
			}
		
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	@GetMapping(value = "/getOffers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<OfferSupplierProfileDTO>> getAllOffers(HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.SUPPLIER) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		Supplier supplier;
		try {
			supplier = supplierService.getSupplierById(user.getId());
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

		}
		return new ResponseEntity<>(offerService.allOffersForSupplier(supplier), HttpStatus.OK);
		
	}
	
	@PostMapping(value = "supplierUpdateOfferUrl")
	public ResponseEntity<String> update(@RequestBody OfferUpdateDTO offer, HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.SUPPLIER) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
	
		Supplier supplier;
		try {
			supplier = supplierService.getSupplierById(user.getId());
		} catch (UserDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

		}
		try {
			offerService.updateOffer(supplier, offer);
		} catch (OfferDoesNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ForbiddenOperationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("", HttpStatus.OK);
	}
}
