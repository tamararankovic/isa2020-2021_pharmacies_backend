package isa.tim28.pharmacies.service.interfaces;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import isa.tim28.pharmacies.dtos.AllComplaintsDTO;
import isa.tim28.pharmacies.dtos.AnswerOnComplaintDTO;
import isa.tim28.pharmacies.dtos.ComplaintDTO;
import isa.tim28.pharmacies.dtos.DoctorRatingDTO;
import isa.tim28.pharmacies.dtos.MedicineSearchDTO;
import isa.tim28.pharmacies.dtos.PharmaciesCounselingDTO;
import isa.tim28.pharmacies.dtos.PharmacyAddAdminDTO;
import isa.tim28.pharmacies.dtos.PharmacyBasicInfoDTO;
import isa.tim28.pharmacies.dtos.PharmacyInfoForPatientDTO;
import isa.tim28.pharmacies.dtos.PriceListDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.InvalidComplaintException;
import isa.tim28.pharmacies.exceptions.MedicineDoesNotExistException;
import isa.tim28.pharmacies.exceptions.PharmacyDataInvalidException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.PriceInvalidException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Rating;

public interface IPharmacyService {

	PharmacyInfoForPatientDTO getPharmacyInfo(long pharmacyId) throws PharmacyNotFoundException;
	
	Set<Medicine> findAllInStockByPharmacyId(long pharmacyId) throws PharmacyNotFoundException;

	ArrayList<PharmacyInfoForPatientDTO> getAllPharmacies(String name, String address) throws PharmacyNotFoundException;

	Pharmacy save(Pharmacy pharmacy);
	
	List<PharmacyAddAdminDTO> getAllPharmacies();
	
	Pharmacy getPharmacyById(long pharmacyId) throws PharmacyNotFoundException;
	
	PharmacyBasicInfoDTO getBasicInfo(PharmacyAdmin admin) throws PharmacyNotFoundException;
	
	void update(PharmacyAdmin admin, PharmacyBasicInfoDTO dto) throws PharmacyNotFoundException, PharmacyDataInvalidException;

	List<PharmacyBasicInfoDTO> getPharmacyByMedicineId(long medicineId) throws PharmacyNotFoundException;
	
	Pharmacy getByName(String name);
	
	List<Pharmacy> getAll();
	
	void addNewmedicine(Pharmacy pharmacy, Medicine medicine);
	
	void addNewMedicines(Pharmacy pharmacy, Set<Long> medicineIds) throws MedicineDoesNotExistException;
	
	void addMedicines(Pharmacy pharmacy, Set<MedicineQuantity> medicines) throws ForbiddenOperationException;
		
	PriceListDTO getCurrentPriceList(Pharmacy pharmacy);
	
	Pharmacy savePharmacy(Pharmacy pharmacy);

	List<MedicineSearchDTO> searchMedicineByName(String name);

	boolean createComplaint(Patient patient, ComplaintDTO dto) throws InvalidComplaintException, UserDoesNotExistException;

	List<AllComplaintsDTO> getAllComplaints();

	boolean answerOnComplaint(AnswerOnComplaintDTO answer) throws MessagingException;
	
	List<PharmaciesCounselingDTO> getPharmaciesWithAvailablePharmacists(LocalDateTime date);

	Rating savePharmacyRating(DoctorRatingDTO dto, long id);

	void updatePriceLists(PriceListDTO dto, Pharmacy pharmacy)
			throws MedicineDoesNotExistException, ForbiddenOperationException, PriceInvalidException;

}
