package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.repository.PharmacyAdminRepository;
import isa.tim28.pharmacies.service.interfaces.IPharmacyAdminService;

@Service
public class PharmacyAdminService implements IPharmacyAdminService{
private PharmacyAdminRepository pharmacyAdminRepository;
	
	@Autowired
	public PharmacyAdminService(PharmacyAdminRepository pharmacyAdminRepository) {
		super();
		this.pharmacyAdminRepository = pharmacyAdminRepository;
	}
	
	@Override
	public PharmacyAdmin save(PharmacyAdmin pharmacyAdmin) {
		PharmacyAdmin newPharmacyAdmin = pharmacyAdminRepository.save(pharmacyAdmin);
		return newPharmacyAdmin;
	}
}
