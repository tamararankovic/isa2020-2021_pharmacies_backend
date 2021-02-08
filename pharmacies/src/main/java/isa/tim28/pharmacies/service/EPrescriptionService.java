package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.model.EPrescription;
import isa.tim28.pharmacies.repository.EPrescriptionRepository;
import isa.tim28.pharmacies.service.interfaces.IEPrescriptionService;

@Service
public class EPrescriptionService implements IEPrescriptionService{

	private EPrescriptionRepository ePrescriptionRepository;

	@Autowired
	public EPrescriptionService(EPrescriptionRepository ePrescriptionRepository) {
		super();
		this.ePrescriptionRepository = ePrescriptionRepository;
	}
	
	@Override
	public EPrescription save(EPrescription pres) {
		EPrescription newPrescription = ePrescriptionRepository.save(pres);
		return newPrescription;
	}

}
