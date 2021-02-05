package isa.tim28.pharmacies.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.MedicineCodeDTO;
import isa.tim28.pharmacies.dtos.MedicineForPharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.SearchMedicineDTO;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.dtos.MedicineInfoDTO;
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
	public List<Medicine> getAllMedicine() {
		return this.medicineRepository.findAll();
	}

	@Override
	public Medicine getByName(String name) {
		return this.medicineRepository.findByName(name);
	}

	@Override
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

	@Override
	public Set<MedicineForPharmacyAdminDTO> getAll(Pharmacy pharmacy) {
		List<Medicine> medicines = medicineRepository.findAll();
		Set<MedicineForPharmacyAdminDTO> ret = new HashSet<MedicineForPharmacyAdminDTO>();
		for (Medicine m : medicines)
			ret.add(new MedicineForPharmacyAdminDTO(m.getId(), m.getCode(), m.getName(), m.getType().toString(), m.getManufacturer(), pharmacy.offers(m)));
		return ret;
	}

	@Override
	public Medicine findById(long id) {
		Optional<Medicine> medicine = medicineRepository.findById(id);
		if(medicine.isEmpty())
			return null;
		return medicine.get();
	}

	@Override
	public Set<MedicineForPharmacyAdminDTO> getAllOffered(Pharmacy pharmacy) {
		List<Medicine> medicines = medicineRepository.findAll();
		Set<MedicineForPharmacyAdminDTO> ret = new HashSet<MedicineForPharmacyAdminDTO>();
		for (Medicine m : medicines)
			if(pharmacy.offers(m))
				ret.add(new MedicineForPharmacyAdminDTO(m.getId(), m.getCode(), m.getName(), m.getType().toString(), m.getManufacturer(), true));
		return ret;
	}

	@Override
	public Set<MedicineForPharmacyAdminDTO> search(SearchMedicineDTO dto, Pharmacy pharmacy) {
		Set<MedicineForPharmacyAdminDTO> ret = getAllOffered(pharmacy);
		ret = searchByCode(dto.getCode(), ret);
		ret = searchByName(dto.getName(), ret);
		ret = searchByManufacturer(dto.getManufacturer(), ret);
		return ret;
	}
	
	private Set<MedicineForPharmacyAdminDTO> searchByCode(String code, Set<MedicineForPharmacyAdminDTO> medicines) {
		Set<MedicineForPharmacyAdminDTO> ret = new HashSet<MedicineForPharmacyAdminDTO>();
		if(code.length() == 0) return medicines;
		String[] tokens = formatSearchCriterion(code).split(" ");
		for(MedicineForPharmacyAdminDTO m : medicines ) {
			boolean hasAllTokens = true;
			for(String token : tokens)
				if(!m.getCode().contains(token)) {
					hasAllTokens = false;
					break;
				}
			if (hasAllTokens)
				ret.add(m);
		}
		return ret;
	}
	
	private Set<MedicineForPharmacyAdminDTO> searchByName(String name, Set<MedicineForPharmacyAdminDTO> medicines) {
		Set<MedicineForPharmacyAdminDTO> ret = new HashSet<MedicineForPharmacyAdminDTO>();
		if(name.length() == 0) return medicines;
		String[] tokens = formatSearchCriterion(name).split(" ");
		for(MedicineForPharmacyAdminDTO m : medicines ) {
			boolean hasAllTokens = true;
			for(String token : tokens)
				if(!m.getName().contains(token)) {
					hasAllTokens = false;
					break;
				}
			if (hasAllTokens)
				ret.add(m);
		}
		return ret;
	}
	
	private Set<MedicineForPharmacyAdminDTO> searchByManufacturer(String manufacturer, Set<MedicineForPharmacyAdminDTO> medicines) {
		Set<MedicineForPharmacyAdminDTO> ret = new HashSet<MedicineForPharmacyAdminDTO>();
		if(manufacturer.length() == 0) return medicines;
		String[] tokens = formatSearchCriterion(manufacturer).split(" ");
		for(MedicineForPharmacyAdminDTO m : medicines ) {
			boolean hasAllTokens = true;
			for(String token : tokens)
				if(!m.getManufacturer().contains(token)) {
					hasAllTokens = false;
					break;
				}
			if (hasAllTokens)
				ret.add(m);
		}
		return ret;
	}
	
	private String formatSearchCriterion(String criterion) {
		return criterion.trim().replaceAll(" +", " ").toLowerCase();
	}

	@Override
	public Set<MedicineForPharmacyAdminDTO> getAllNotOffered(Pharmacy pharmacy) {
		List<Medicine> medicines = medicineRepository.findAll();
		Set<MedicineForPharmacyAdminDTO> ret = new HashSet<MedicineForPharmacyAdminDTO>();
		for (Medicine m : medicines)
			if(!pharmacy.offers(m))
				ret.add(new MedicineForPharmacyAdminDTO(m.getId(), m.getCode(), m.getName(), m.getType().toString(), m.getManufacturer(), false));
		return ret;
	}

}
