package isa.tim28.pharmacies.service.interfaces;

import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.PharmacyAddAdminDTO;
import isa.tim28.pharmacies.dtos.PharmacyBasicInfoDTO;
import isa.tim28.pharmacies.dtos.PharmacyInfoForPatientDTO;
import isa.tim28.pharmacies.exceptions.PharmacyDataInvalidException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;

public interface IPharmacyService {

	PharmacyInfoForPatientDTO getPharmacyInfo(long pharmacyId) throws PharmacyNotFoundException;
	
	Set<Medicine> findAllInStockByPharmacyId(long pharmacyId) throws PharmacyNotFoundException;
	
	Pharmacy save(Pharmacy pharmacy);
	
	List<PharmacyAddAdminDTO> getAllPharmacies();
	
	Pharmacy getPharmacyById(long pharmacyId) throws PharmacyNotFoundException;
	
	PharmacyBasicInfoDTO getBasicInfo(PharmacyAdmin admin) throws PharmacyNotFoundException;
	
	void update(PharmacyAdmin admin, PharmacyBasicInfoDTO dto) throws PharmacyNotFoundException, PharmacyDataInvalidException;

	void addNewmedicine(Pharmacy pharmacy, Medicine medicine);
}
