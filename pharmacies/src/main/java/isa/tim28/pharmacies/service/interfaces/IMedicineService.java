package isa.tim28.pharmacies.service.interfaces;

import java.util.List;
import isa.tim28.pharmacies.model.Medicine;
import java.util.Set;

import isa.tim28.pharmacies.dtos.MedicineCodeDTO;
import isa.tim28.pharmacies.dtos.MedicineForPharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.MedicineInPharmacyDTO;
import isa.tim28.pharmacies.dtos.MedicineInfoDTO;
import isa.tim28.pharmacies.dtos.SearchMedicineDTO;
import isa.tim28.pharmacies.model.Pharmacy;

public interface IMedicineService {
	
	List<MedicineCodeDTO> getAllMedicines();
	
	Medicine getMedicineByCode(String code);
	
	Medicine save(Medicine medicine);
	
	Set<MedicineForPharmacyAdminDTO> getAll(Pharmacy pharmacy);
	
	Set<MedicineInPharmacyDTO> getAllOffered(Pharmacy pharmacy);
	
	Set<MedicineForPharmacyAdminDTO> getAllNotOffered(Pharmacy pharmacy);
	
	Medicine findById(long id);
	
	Set<MedicineInPharmacyDTO> search(SearchMedicineDTO dto, Pharmacy pharmacy);

	List<Medicine> getAllMedicine();

	Medicine getByName(String name);
	
	public List<MedicineInfoDTO> getAllMedicineInfo(String name, String form, String type, String manu);

}
