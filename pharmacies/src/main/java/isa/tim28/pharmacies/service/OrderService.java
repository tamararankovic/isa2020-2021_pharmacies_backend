package isa.tim28.pharmacies.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.MedicineForPharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.NewMedicineQuantityDTO;
import isa.tim28.pharmacies.dtos.NewOrderDTO;
import isa.tim28.pharmacies.dtos.OrderForPharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.OrderedMedicineDTO;
import isa.tim28.pharmacies.exceptions.NewOrderInvalidException;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineQuantity;
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

	@Autowired
	public OrderService(OrderRepository orderRepository, IMedicineService medicineService, IPharmacyService pharmacyService) {
		super();
		this.orderRepository = orderRepository;
		this.medicineService = medicineService;
		this.pharmacyService = pharmacyService;
	}

	@Override
	public void create(NewOrderDTO order, PharmacyAdmin admin) throws NewOrderInvalidException {
		Set<MedicineQuantity> medicines = new HashSet<MedicineQuantity>();
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
			
			orders.add(new OrderForPharmacyAdminDTO(o.getId(), medicines, o.getDeadline(), editable, state, canChooseOffer));
			
		}
		return orders;
	}
	
	private Set<Order> getByPharmacy(Pharmacy pharmacy) {
		return orderRepository.findAll().stream().filter(o -> o.getAdminCreator().getPharmacy().getId() == pharmacy.getId()).collect(Collectors.toSet());
	}
}
