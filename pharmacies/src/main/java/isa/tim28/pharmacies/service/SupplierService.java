package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import isa.tim28.pharmacies.model.Supplier;
import isa.tim28.pharmacies.repository.SupplierRepository;
import isa.tim28.pharmacies.service.interfaces.ISupplierService;

@Service
public class SupplierService implements ISupplierService {
	
private SupplierRepository supplierRepository;
	
	@Autowired
	public SupplierService(SupplierRepository supplierRepository) {
		super();
		this.supplierRepository = supplierRepository;
	}
	
	@Override
	public Supplier save(Supplier supplier) {
		Supplier newSupplier = supplierRepository.save(supplier);
		return newSupplier;
	}

}
