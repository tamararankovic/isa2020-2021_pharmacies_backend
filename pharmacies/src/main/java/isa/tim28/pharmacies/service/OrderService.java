package isa.tim28.pharmacies.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.MedicineForPharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.MedicineForSupplierDTO;
import isa.tim28.pharmacies.dtos.NewMedicineQuantityDTO;
import isa.tim28.pharmacies.dtos.NewOrderDTO;
import isa.tim28.pharmacies.dtos.OfferDTO;
import isa.tim28.pharmacies.dtos.OrderForPharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.OrderForSupplierDTO;
import isa.tim28.pharmacies.dtos.OrderedMedicineDTO;
import isa.tim28.pharmacies.dtos.OrderedMedicineForSupplierDTO;
import isa.tim28.pharmacies.dtos.UpdateOrderDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.NewOrderInvalidException;
import isa.tim28.pharmacies.exceptions.OrderNotFoundException;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Offer;
import isa.tim28.pharmacies.model.Order;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.repository.OrderRepository;
import isa.tim28.pharmacies.service.interfaces.IMedicineService;
import isa.tim28.pharmacies.service.interfaces.IOrderService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;

@Service
public class OrderService implements IOrderService {
	
	private OrderRepository orderRepository;
	private IMedicineService medicineService;
	private IPharmacyService pharmacyService;
	private EmailService emailService;

	@Autowired
	public OrderService(OrderRepository orderRepository, IMedicineService medicineService, IPharmacyService pharmacyService, EmailService emailService) {
		super();
		this.orderRepository = orderRepository;
		this.medicineService = medicineService;
		this.pharmacyService = pharmacyService;
		this.emailService = emailService;
	}


	@Override
	public Order getOrderById(long id) {
		Optional<Order> order = orderRepository.findById(id);
		if(order.isEmpty())
			return null;
		return order.get();
	}
	
	@Override
	public void create(NewOrderDTO order, PharmacyAdmin admin) throws NewOrderInvalidException {
		Set<MedicineQuantity> medicines = new HashSet<MedicineQuantity>();
		if (order.getMedicines() == null || order.getMedicines().size() == 0)
			throw new NewOrderInvalidException("You can't make an empty order!");
		if(order.getDeadline().isBefore(LocalDateTime.now()))
			throw new NewOrderInvalidException("Deadline can't be set in the past!");
		for(NewMedicineQuantityDTO mq : order.getMedicines()) {
			Medicine m = medicineService.findById(mq.getMedicineId());
			if (m == null) {
				throw new NewOrderInvalidException("Medicine not found!");
			}
			medicines.add(new MedicineQuantity(m, (int)mq.getOrderedQuantity()));
		}
		Order o = new Order(medicines, order.getDeadline(), admin);
		orderRepository.save(o);
		for(MedicineQuantity mq : o.getMedicineQuantities())
			if(!admin.getPharmacy().offers(mq.getMedicine()))
				pharmacyService.addNewmedicine(admin.getPharmacy(), mq.getMedicine());
	}

	@Override
	public Set<Order> getAllOrdersForSupplier(){
		return orderRepository.findAll().stream().collect(Collectors.toSet());
	}
	
	@Override
	public Set<OrderForSupplierDTO> getAllOrders(){
		Set<Order> allOrders = orderRepository.findAll().stream().collect(Collectors.toSet());
		Set<OrderForSupplierDTO> orders = new HashSet<OrderForSupplierDTO>();
		for(Order o : allOrders) {
			String state;
			if(o.isWaitingOffers())
				state = "WAITING OFFERS";
			else if (o.hasWinner())
				state = "PROCESSED";
			else
				state = "WAITING ON THE WINNER OFFER";
			
			Set<OrderedMedicineForSupplierDTO> medicines = new HashSet<OrderedMedicineForSupplierDTO>();
			for(MedicineQuantity mq : o.getMedicineQuantities()) {
				medicines.add(new OrderedMedicineForSupplierDTO(new MedicineForSupplierDTO(mq.getMedicine().getId(), mq.getMedicine().getCode(), mq.getMedicine().getName(), mq.getMedicine().getType().toString(), mq.getMedicine().getManufacturer()), mq.getQuantity()));
			}
			
			String pharmacyName = o.getAdminCreator().getPharmacy().getName();
			
			orders.add(new OrderForSupplierDTO(o.getId(), medicines, o.getDeadline(), state, pharmacyName));
		}
		return orders;
	}
	@Override
	public Set<OrderForPharmacyAdminDTO> get(PharmacyAdmin admin) {
		Set<Order> allOrders = getByPharmacy(admin.getPharmacy());
		Set<OrderForPharmacyAdminDTO> orders = new HashSet<OrderForPharmacyAdminDTO>();
		for(Order o : allOrders) {
			String state;
			if(o.isWaitingOffers())
				state = "WAITING OFFERS";
			else if (o.hasWinner())
				state = "PROCESSED";
			else
				state = "WAITING ON THE WINNER OFFER";
			
			boolean editable = false;
			if (o.isWaitingOffers() && !o.hasOffers() && o.getAdminCreator().getId() == admin.getId())
				editable = true;
			
			boolean canChooseOffer = false;
			if (!o.isWaitingOffers() && o.hasOffers() && o.getAdminCreator().getId() == admin.getId() && !o.hasWinner())
				canChooseOffer = true;
			
			Set<OrderedMedicineDTO> medicines = new HashSet<OrderedMedicineDTO>();
			for(MedicineQuantity mq : o.getMedicineQuantities()) {
				medicines.add(new OrderedMedicineDTO(new MedicineForPharmacyAdminDTO(mq.getMedicine().getId(), mq.getMedicine().getCode(), mq.getMedicine().getName(), mq.getMedicine().getType().toString(), mq.getMedicine().getManufacturer(), admin.getPharmacy().offers(mq.getMedicine())), mq.getQuantity()));
			}
			
			Set<OfferDTO> offers = new HashSet<OfferDTO>();
			for(Offer offer : o.getOffers()) {
				offers.add(new OfferDTO(offer.getId(), offer.getSupplier().getUser().getFullName(), offer.getTotalPrice(), offer.getDeadline()));
			}
			Offer w = o.getWinningOffer();
			OfferDTO winning = null;
			if(w != null) {
				winning = new OfferDTO(w.getId(), w.getSupplier().getUser().getFullName(), w.getTotalPrice(), w.getDeadline());
			}
			
			orders.add(new OrderForPharmacyAdminDTO(o.getId(), medicines, o.getDeadline(), editable, state, canChooseOffer, offers, winning));
			
		}
		return orders;
	}
	
	private Set<Order> getByPharmacy(Pharmacy pharmacy) {
		return orderRepository.findAll().stream().filter(o -> o.getAdminCreator().getPharmacy().getId() == pharmacy.getId()).collect(Collectors.toSet());
	}

	@Override
	public void chooseWinningOffer(long orderid, long offerId, PharmacyAdmin admin) throws OrderNotFoundException, ForbiddenOperationException, MessagingException {
		Optional<Order> orderOpt = orderRepository.findById(orderid);
		if(orderOpt.isEmpty())
			throw new OrderNotFoundException("You are trying to change order that does not exist!");
		Order order = orderOpt.get();
		if(order.getAdminCreator().getId() != admin.getId())
			throw new ForbiddenOperationException("You cann't change orders that you hadn't created!");
		if(order.hasWinner())
			throw new ForbiddenOperationException("Selected order already has a winner!");
		order.SetWinner(offerId);
		pharmacyService.addMedicines(admin.getPharmacy(), order.getMedicineQuantities());
		orderRepository.save(order);
		for(Offer o : order.getOffers()) {
			emailService.sendEmailToSupplier(o.getSupplier().getUser().getEmail(), o.getSupplier().getUser().getFullName(), o.isAccepted() ? "prihvaćena" : "odbijena", o.getId());
		}
	}

	@Override
	public void update(UpdateOrderDTO dto, PharmacyAdmin admin) throws OrderNotFoundException, ForbiddenOperationException, NewOrderInvalidException {
		Optional<Order> orderOpt = orderRepository.findById(dto.getId());
		if(orderOpt.isEmpty())
			throw new OrderNotFoundException("You are trying to change order that does not exist!");
		Order order = orderOpt.get();
		if(order.hasOffers())
			throw new ForbiddenOperationException("Order already has offers!");
		if(!order.isWaitingOffers())
			throw new ForbiddenOperationException("Deadline passed!!");
		if(order.getAdminCreator().getId() != admin.getId())
			throw new ForbiddenOperationException("You can't edit orders that you hadn't made!");
		if(dto.getOrder().getMedicines() == null || dto.getOrder().getMedicines().size() == 0)
			throw new ForbiddenOperationException("You can't leave the order empty!");
		if(dto.getOrder().getDeadline().isBefore(LocalDateTime.now()))
			throw new ForbiddenOperationException("Deadline can't be set in the past!");
		Set<MedicineQuantity> medicines = new HashSet<MedicineQuantity>();
		for(NewMedicineQuantityDTO mq : dto.getOrder().getMedicines()) {
			Medicine m = medicineService.findById(mq.getMedicineId());
			if (m == null) {
				throw new NewOrderInvalidException("Medicine not found!");
			}
			medicines.add(new MedicineQuantity(m, (int)mq.getOrderedQuantity()));
		}
		order.setDeadline(dto.getOrder().getDeadline());
		order.setMedicineQuantities(medicines);
		orderRepository.save(order);
	}

	@Override
	public void delete(long orderid, PharmacyAdmin admin) throws OrderNotFoundException, ForbiddenOperationException {
		Optional<Order> orderOpt = orderRepository.findById(orderid);
		if(orderOpt.isEmpty())
			throw new OrderNotFoundException("You are trying to delete order that does not exist!");
		Order order = orderOpt.get();
		if(order.hasOffers())
			throw new ForbiddenOperationException("Order already has offers!");
		if(!order.isWaitingOffers())
			throw new ForbiddenOperationException("Deadline passed!!");
		if(order.getAdminCreator().getId() != admin.getId())
			throw new ForbiddenOperationException("You can't delete orders that you hadn't made!");
		orderRepository.delete(order);
	}
	
	@Override
	public Order save(Order order) {
		Order newOrder = orderRepository.save(order);
		return newOrder;
	}

}
