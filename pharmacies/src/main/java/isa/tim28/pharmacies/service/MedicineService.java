package isa.tim28.pharmacies.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.MedicineInfoDTO;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.repository.MedicineRepository;
import isa.tim28.pharmacies.service.interfaces.IMedicineService;

@Service
public class MedicineService implements IMedicineService {

	private MedicineRepository medicineRepository;

	@Autowired
	public MedicineService(MedicineRepository medicineRepository) {
		this.medicineRepository = medicineRepository;
	}

	@Override
	public List<Medicine> getAllMedicine() {
		return this.medicineRepository.findAll();
	}

	@Override
	public Medicine getByName(String name) {
		return this.medicineRepository.findByName(name);
	}

	public List<MedicineInfoDTO> getAllMedicineInfo(String name, String form, String type, String manu) {
		List<MedicineInfoDTO> res = new ArrayList<MedicineInfoDTO>();
		if (name.equals("") && form.equals("") && type.equals("") && manu.equals("")) {
		for (Medicine m : getAllMedicine()) {
			MedicineInfoDTO dto = new MedicineInfoDTO(m.getName(), m.getAdditionalInfo(), m.getAdvisedDailyDose(),
					m.getForm().toString(), m.getManufacturer(), m.getType().toString(), m.getPoints());
			res.add(dto);
		}
		return res;
		
		}else {
			for (Medicine m : findAllMedicineByCriteria(name, form,type, manu)) {
				MedicineInfoDTO dto = new MedicineInfoDTO(m.getName(), m.getAdditionalInfo(), m.getAdvisedDailyDose(),
						m.getForm().toString(), m.getManufacturer(), m.getType().toString(), m.getPoints());
				res.add(dto);
			}
			return res;
		}
	}
	
	public List<Medicine> findAllMedicineByCriteria(String name, String form, String type, String manu) {
		List<Medicine> ret = new ArrayList<Medicine>();
		for (Medicine m : getAllMedicine()) {
			if (m.getName().toLowerCase().contains(name.toLowerCase())
					&& m.getForm().toString().toLowerCase().contains(form.toLowerCase())
					&& m.getType().toString().toLowerCase().contains(type.toLowerCase())
					&& m.getManufacturer().toLowerCase().contains(manu.toLowerCase()))
				ret.add(m);
		}
		return ret;
		
	}
}
