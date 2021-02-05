package isa.tim28.pharmacies.service.interfaces;

import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.MedicineCodeDTO;
import isa.tim28.pharmacies.dtos.MedicineForPharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.SearchMedicineDTO;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Pharmacy;

public interface IMedicineService {
	
	List<MedicineCodeDTO> getAllMedicines();
	
	Medicine getMedicineByCode(String code);
	
	Medicine save(Medicine medicine);
	
	Set<MedicineForPharmacyAdminDTO> getAll(Pharmacy pharmacy);
	
	Set<MedicineForPharmacyAdminDTO> getAllOffered(Pharmacy pharmacy);
	
	Set<MedicineForPharmacyAdminDTO> getAllNotOffered(Pharmacy pharmacy);
	
	Medicine findById(long id);
	
	Set<MedicineForPharmacyAdminDTO> search(SearchMedicineDTO dto, Pharmacy pharmacy);
}
