package isa.tim28.pharmacies.service.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.PharmacyInfoForPatientDTO;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.model.Medicine;

public interface IPharmacyService {

	PharmacyInfoForPatientDTO getPharmacyInfo(long pharmacyId) throws PharmacyNotFoundException;
	Set<Medicine> findAllInStockByPharmacyId(long pharmacyId) throws PharmacyNotFoundException;
	ArrayList<PharmacyInfoForPatientDTO> getAllPharmacies(String name, String address) throws PharmacyNotFoundException;
}
