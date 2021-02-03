package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;
import java.util.List;
import isa.tim28.pharmacies.dtos.PharmacyAddAdminDTO;
import isa.tim28.pharmacies.dtos.PharmacyInfoForPatientDTO;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Pharmacy;

public interface IPharmacyService {

	PharmacyInfoForPatientDTO getPharmacyInfo(long pharmacyId) throws PharmacyNotFoundException;
	Set<Medicine> findAllInStockByPharmacyId(long pharmacyId) throws PharmacyNotFoundException;
	Pharmacy save(Pharmacy pharmacy);
	List<PharmacyAddAdminDTO> getAllPharmacies();
	Pharmacy getPharmacyById(long pharmacyId) throws PharmacyNotFoundException;
}
