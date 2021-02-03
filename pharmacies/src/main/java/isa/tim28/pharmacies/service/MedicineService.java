package isa.tim28.pharmacies.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import isa.tim28.pharmacies.dtos.MedicineCodeDTO;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.repository.MedicineRepository;
import isa.tim28.pharmacies.service.interfaces.IMedicineService;

@Service
public class MedicineService implements IMedicineService {

private MedicineRepository medicineRepository;
	
	@Autowired
	public MedicineService(MedicineRepository medicineRepository) {
		super();
		this.medicineRepository = medicineRepository;
	}
	
	@Override
	public List<MedicineCodeDTO> getAllMedicines() {
		return medicinesToDtos(medicineRepository.findAll());
	}
	
	private List<MedicineCodeDTO> medicinesToDtos(List<Medicine> medicines){
		List<MedicineCodeDTO> dtos = new ArrayList<MedicineCodeDTO>();
		for(Medicine m : medicines) {
			dtos.add(new MedicineCodeDTO(m.getCode(),m.getName()));
			
		}
		return dtos;
	}
	
	@Override
	public Medicine getMedicineByCode(String code) {
		Medicine medicine = medicineRepository.findOneByCode(code);
		if (medicine == null)
			return null;
		else 
			return medicine;
	}
	
	@Override
	public Medicine save(Medicine medicine) {
		Medicine newMedicine = medicineRepository.save(medicine);
		return newMedicine;
	}

}
