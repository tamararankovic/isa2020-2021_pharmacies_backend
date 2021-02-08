package isa.tim28.pharmacies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import isa.tim28.pharmacies.model.EPrescriptionMedicine;
import isa.tim28.pharmacies.repository.EPrescriptionMedicineRepository;
import isa.tim28.pharmacies.service.interfaces.IEPrescriptionMedicineService;

@Service
public class EPrescriptionMedicineService implements IEPrescriptionMedicineService {

	private EPrescriptionMedicineRepository ePrescriptionMedicineRepository;

	@Autowired
	public EPrescriptionMedicineService(EPrescriptionMedicineRepository ePrescriptionMedicineRepository) {
		super();
		this.ePrescriptionMedicineRepository = ePrescriptionMedicineRepository;
	}
	
	@Override
	public EPrescriptionMedicine save(EPrescriptionMedicine medicine) {
		EPrescriptionMedicine newMedicine = ePrescriptionMedicineRepository.save(medicine);
		return newMedicine;
	}

}
