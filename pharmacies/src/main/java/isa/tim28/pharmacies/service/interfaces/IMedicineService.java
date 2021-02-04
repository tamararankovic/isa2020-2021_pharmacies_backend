package isa.tim28.pharmacies.service.interfaces;



import java.util.List;
import isa.tim28.pharmacies.model.Medicine;


import java.util.List;

import isa.tim28.pharmacies.dtos.MedicineCodeDTO;
import isa.tim28.pharmacies.model.Medicine;

public interface IMedicineService {
	List<MedicineCodeDTO> getAllMedicines();
	Medicine getMedicineByCode(String code);
	Medicine save(Medicine medicine);
	List<Medicine> getAllMedicine();
	Medicine getByName(String name);


}
