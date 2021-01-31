package isa.tim28.pharmacies.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.EngagementInPharmacy;
import isa.tim28.pharmacies.repository.DermatologistRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistService;

@Service
public class DermatologistService implements IDermatologistService {

	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	public DermatologistService(DermatologistRepository dermatologistRepository) {
		super();
		this.dermatologistRepository = dermatologistRepository;
	}

	@Override
	public Set<Dermatologist> findAllByPharmacyId(long pharmacyId) {
		Set<Dermatologist> ret = new HashSet<Dermatologist>();
		for(Dermatologist d : dermatologistRepository.findAll())
			for (EngagementInPharmacy ep : d.getEngegementInPharmacies())
				if (ep.getPharmacy().getId() == pharmacyId)
					ret.add(d);
		return ret;
	}

}
