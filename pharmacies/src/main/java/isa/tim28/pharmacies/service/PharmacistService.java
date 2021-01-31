package isa.tim28.pharmacies.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.repository.PharmacistRepository;
import isa.tim28.pharmacies.service.interfaces.IPharmacistService;

@Service
public class PharmacistService implements IPharmacistService {
	
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	public PharmacistService(PharmacistRepository pharmacistRepository) {
		super();
		this.pharmacistRepository = pharmacistRepository;
	}

	@Override
	public Set<Pharmacist> findAllByPharmacyId(long pharmacyId) {
		Set<Pharmacist> ret = new HashSet<Pharmacist>();
		for(Pharmacist p : pharmacistRepository.findAll())
			if (p.getEngegementInPharmacy() != null 
				&& p.getEngegementInPharmacy().getPharmacy().getId() == pharmacyId)
				ret.add(p);
		return ret;
	}
}
