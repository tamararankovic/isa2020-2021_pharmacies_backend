package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;

import javax.mail.MessagingException;

import isa.tim28.pharmacies.dtos.NewOrderDTO;
import isa.tim28.pharmacies.dtos.OrderForPharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.UpdateOrderDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.NewOrderInvalidException;
import isa.tim28.pharmacies.exceptions.OrderNotFoundException;
import isa.tim28.pharmacies.model.PharmacyAdmin;

public interface IOrderService {

	public void create(NewOrderDTO order, PharmacyAdmin admin) throws NewOrderInvalidException;
	
	public Set<OrderForPharmacyAdminDTO> get(PharmacyAdmin admin);
	
	public void chooseWinningOffer(long orderid, long offerId, PharmacyAdmin admin) throws OrderNotFoundException, ForbiddenOperationException, MessagingException;

	public void update(UpdateOrderDTO dto, PharmacyAdmin admin) throws OrderNotFoundException, ForbiddenOperationException, NewOrderInvalidException;
	
	public void delete(long orderid, PharmacyAdmin admin) throws OrderNotFoundException, ForbiddenOperationException;
}
