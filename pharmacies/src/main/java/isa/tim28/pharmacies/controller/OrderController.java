package isa.tim28.pharmacies.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isa.tim28.pharmacies.dtos.NewOrderDTO;
import isa.tim28.pharmacies.exceptions.NewOrderInvalidException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.service.interfaces.IOrderService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;

@RestController
@RequestMapping(value = "order")
public class OrderController {
	
	private IOrderService orderService;
	private IPharmacyAdminService pharmacyAdminService;
	
	@Autowired
	public OrderController(IOrderService orderService, IPharmacyAdminService pharmacyAdminService) {
		super();
		this.orderService = orderService;
		this.pharmacyAdminService = pharmacyAdminService;
	}
	
	@PostMapping(value = "new")
	public void create(@RequestBody NewOrderDTO order, HttpSession session) {
		User user = (User)session.getAttribute("loggedInUser");
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in!");
		}
		if (user.getRole() != Role.PHARMACY_ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have required permissions!");
		}
		try {
			PharmacyAdmin admin = pharmacyAdminService.findByUser(user);
			orderService.create(order, admin);
		} catch(UserDoesNotExistException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		} catch (NewOrderInvalidException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
