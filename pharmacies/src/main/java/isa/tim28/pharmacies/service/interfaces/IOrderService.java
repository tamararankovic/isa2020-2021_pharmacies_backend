package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;

import isa.tim28.pharmacies.dtos.NewOrderDTO;
import isa.tim28.pharmacies.dtos.OrderForPharmacyAdminDTO;
import isa.tim28.pharmacies.exceptions.NewOrderInvalidException;
import isa.tim28.pharmacies.model.PharmacyAdmin;

public interface IOrderService {

	public void create(NewOrderDTO order, PharmacyAdmin admin) throws NewOrderInvalidException;
	
	public Set<OrderForPharmacyAdminDTO> get(PharmacyAdmin admin);
}
