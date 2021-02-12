package isa.tim28.pharmacies.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.OfferSupplierDTO;
import isa.tim28.pharmacies.dtos.OfferSupplierProfileDTO;
import isa.tim28.pharmacies.dtos.OfferUpdateDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.OfferDoesNotExistException;
import isa.tim28.pharmacies.model.Offer;
import isa.tim28.pharmacies.model.Order;
import isa.tim28.pharmacies.model.Supplier;
import isa.tim28.pharmacies.repository.OfferRepository;
import isa.tim28.pharmacies.service.interfaces.IOfferService;
import isa.tim28.pharmacies.service.interfaces.IOrderService;

@Service
public class OfferService implements IOfferService{
	
	private OfferRepository offerRepository;
	private IOrderService orderService;

	@Autowired
	public OfferService(OfferRepository offerRepository, IOrderService orderService) {
		super();
		this.offerRepository = offerRepository;
		this.orderService = orderService;
	}
	
	@Override
	public boolean createOffer(OfferSupplierDTO offer, Supplier supplier) {
		Offer newOffer = new Offer();
		newOffer.setSupplier(supplier);
		newOffer.setTotalPrice(offer.getTotalPrice());
		newOffer.setDeadline(offer.getDeadline());
		newOffer.setAccepted(false);
		
		Order order = orderService.getOrderById(offer.getIdOrder());
		if(order.isWaitingOffers()) 
		{	order.getOffers().add(newOffer);
			orderService.save(order);
			offerRepository.save(newOffer);
			return true;
		}else 
		{
		return false;
		}
	}
	
	@Override
	public boolean alreadySentOffer(OfferSupplierDTO offer, Supplier supplier) {
		Order order = orderService.getOrderById(offer.getIdOrder());
		Set<Offer> offers = order.getOffers();
		for(Offer o : offers) {
			if(o.getSupplier().getId() == supplier.getId()) {
				return false;
				}
			}
		return true;
	}
	
	@Override
	public Set<OfferSupplierProfileDTO> allOffersForSupplier(Supplier supplier) 
	{
		String filterValue = "";
		List<Offer> offersList = new ArrayList<Offer>();
		Set<OfferSupplierProfileDTO> offers = new HashSet<OfferSupplierProfileDTO>();
		List<Offer> allOffers = offerRepository.findAll();
		for(Offer o : allOffers) {
			if(o.getSupplier().getId() == supplier.getId()) {
				offersList.add(o);
			}
		}
		Set<Order> allOrders = orderService.getAllOrdersForSupplier();
		for(Order order : allOrders) {
			for(Offer offer : offersList) {
				if(order.getOffers().contains(offer)) {
					if(offer.isAccepted()) {
						filterValue = "ACCEPTED";
					}else if(!offer.isAccepted() && !order.hasWinner()) {
						filterValue = "WAITING FOR RESPONSE";
					}else if (!offer.isAccepted() && order.hasWinner()) {
						filterValue = "DENIED";
					}
					offers.add(new OfferSupplierProfileDTO(offer.getId(),offer.getDeadline(), filterValue, offer.getTotalPrice(), order.getAdminCreator().getPharmacy().getName()));
					
				}
			}
		}
		return offers;
		
	}	
	@Override
	public void updateOffer(Supplier supplier, OfferUpdateDTO offer) throws OfferDoesNotExistException, ForbiddenOperationException {
		Optional<Offer> offerForUpdate = offerRepository.findById(offer.getIdOffer());
		if(offerForUpdate.isEmpty())
			throw new OfferDoesNotExistException("You are trying to change offer that does not exist!");
		Offer foundOffer = offerForUpdate.get();
		Set<Order> allOrders = orderService.getAllOrdersForSupplier();
		for(Order o : allOrders) {
			if(o.getOffers().contains(foundOffer)) {
				if(!o.isWaitingOffers())
					throw new ForbiddenOperationException("Deadline passed!You cannot change offer informations.");
				
			}
			}
		if(foundOffer.getSupplier().getId() != supplier.getId())
			throw new ForbiddenOperationException("You can't edit offers that you hadn't made!");
		foundOffer.setDeadline(offer.getDeadline());
		foundOffer.setTotalPrice(offer.getTotalPrice());
		offerRepository.save(foundOffer);
	}
	
	@Override
	public Offer getOfferById(long offerId) {
		return offerRepository.findById(offerId).get();
	}

	
		
	
}
