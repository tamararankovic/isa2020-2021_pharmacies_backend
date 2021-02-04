package isa.tim28.pharmacies.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.NewMedicineQuantityDTO;
import isa.tim28.pharmacies.dtos.NewOrderDTO;
import isa.tim28.pharmacies.exceptions.NewOrderInvalidException;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Order;
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
}
