package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;

import isa.tim28.pharmacies.dtos.OfferSupplierDTO;
import isa.tim28.pharmacies.dtos.OfferSupplierProfileDTO;
import isa.tim28.pharmacies.dtos.OfferUpdateDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.OfferDoesNotExistException;
import isa.tim28.pharmacies.model.Offer;
import isa.tim28.pharmacies.model.Supplier;

public interface IOfferService {

	boolean createOffer(OfferSupplierDTO offer, Supplier supplier);

	boolean alreadySentOffer(OfferSupplierDTO offer, Supplier supplier);

	Set<OfferSupplierProfileDTO> allOffersForSupplier(Supplier supplier);

	void updateOffer(Supplier supplier, OfferUpdateDTO offer) throws OfferDoesNotExistException, ForbiddenOperationException;

	Offer getOfferById(long offerId);

}
