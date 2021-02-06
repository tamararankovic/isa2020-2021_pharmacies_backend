package isa.tim28.pharmacies.service.interfaces;

import java.util.List;

import javax.mail.MessagingException;

import isa.tim28.pharmacies.dtos.DealPromotionDTO;
import isa.tim28.pharmacies.dtos.PharmacyAddAdminDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.Subscription;

public interface ISubscriptionService {
	
	Subscription save(Subscription subscription);
	
	void subscribe(long userId, long pharmacyId) throws UserDoesNotExistException, PharmacyNotFoundException;

	boolean alreadySubscribed(long userId, long pharmacyId) throws UserDoesNotExistException, PharmacyNotFoundException;

	List<PharmacyAddAdminDTO> getAllSubscribedPharmacies(long userId) throws UserDoesNotExistException;
	
	 void cancelSubscription(long userId, long pharmacyId) throws UserDoesNotExistException, PharmacyNotFoundException;
	 
	 void sendDealOrPromotionToAllSubscribed(DealPromotionDTO dto, Pharmacy pharmacy) throws MessagingException, ForbiddenOperationException;
}
