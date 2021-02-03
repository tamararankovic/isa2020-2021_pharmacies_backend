package isa.tim28.pharmacies.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DermatologistExaminationForPatientDTO;
import isa.tim28.pharmacies.dtos.PharmacyBasicInfoDTO;
import isa.tim28.pharmacies.dtos.PharmacyInfoForPatientDTO;
import isa.tim28.pharmacies.exceptions.PharmacyDataInvalidException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Rating;
import isa.tim28.pharmacies.repository.PharmacyRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistAppointmentService;
import isa.tim28.pharmacies.service.interfaces.IDermatologistService;
import isa.tim28.pharmacies.service.interfaces.IPharmacistService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;

@Service
public class PharmacyService implements IPharmacyService {
	
	private PharmacyRepository pharmacyRepository;
	private IPharmacistService pharmacistService;
	private IDermatologistService dermatologistService;
	private IDermatologistAppointmentService appointmentService;
	
	@Autowired
	public PharmacyService(PharmacyRepository pharmacyRepository, IPharmacistService pharmacistService,
			IDermatologistService dermatologistService, IDermatologistAppointmentService appointmentService) {
		super();
		this.pharmacyRepository = pharmacyRepository;
		this.pharmacistService = pharmacistService;
		this.dermatologistService = dermatologistService;
		this.appointmentService = appointmentService;
	}

	@Override
	public PharmacyInfoForPatientDTO getPharmacyInfo(long pharmacyId) throws PharmacyNotFoundException {
		if (pharmacyRepository.findById(pharmacyId).isEmpty())
			throw new PharmacyNotFoundException();
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).get();
		PharmacyInfoForPatientDTO dto = new PharmacyInfoForPatientDTO();
		dto.setName(pharmacy.getName());
		dto.setDescription(pharmacy.getDescription());
		dto.setAddress(pharmacy.getAddress());
		double sumOfRatings = 0;
		for(Rating r : pharmacy.getRatings())
			sumOfRatings += r.getRating();
		dto.setAvgRating(pharmacy.getRatings().size() > 0 ? sumOfRatings / pharmacy.getRatings().size() : 0);
		
		Set<Pharmacist> pharmacists = pharmacistService.findAllByPharmacyId(pharmacyId);
		Set<String> dtoPharmacists = new HashSet<String>();
		for (Pharmacist p : pharmacists)
			dtoPharmacists.add(p.getUser().getName() + " " + p.getUser().getSurname());
		dto.setPharmacists(dtoPharmacists);
		
		Set<Dermatologist> dermatologists = dermatologistService.findAllByPharmacyId(pharmacyId);
		Set<String> dtoDermatologists = new HashSet<String>();
		for (Dermatologist d : dermatologists)
			dtoDermatologists.add(d.getUser().getName() + " " + d.getUser().getSurname());
		dto.setDermatologists(dtoDermatologists);
		
		Set<Medicine> medicines = findAllInStockByPharmacyId(pharmacyId);
		Set<String> dtoMedicines = new HashSet<String>();
		for (Medicine m : medicines)
			dtoMedicines.add(m.getName());
		dto.setMedicines(dtoMedicines);
		
		Set<DermatologistAppointment> appointments = appointmentService.findAllAvailableByPharmacyId(pharmacyId);
		Set<DermatologistExaminationForPatientDTO> appDtos = new HashSet<DermatologistExaminationForPatientDTO>();
		for (DermatologistAppointment a : appointments) {
			DermatologistExaminationForPatientDTO appDto = new DermatologistExaminationForPatientDTO();
			appDto.setStartDateTime(a.getStartDateTime());
			appDto.setDermatologist(a.getDermatologist().getUser().getName() + " " + a.getDermatologist().getUser().getSurname());
			appDto.setDuration(a.getDurationInMinutes());
			appDto.setPrice(a.getPrice());
			appDtos.add(appDto);
		}
		dto.setExaminations(appDtos);
		
		return dto;
	}

	@Override
	public Set<Medicine> findAllInStockByPharmacyId(long pharmacyId) throws PharmacyNotFoundException {
		if (pharmacyRepository.findById(pharmacyId).isEmpty())
			throw new PharmacyNotFoundException();
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).get();
		Set<Medicine> ret = new HashSet<Medicine>();
		for(MedicineQuantity mq : pharmacy.getMedicines())
			if(mq.getQuantity() > 0)
				ret.add(mq.getMedicine());
		return ret;
	}

	@Override
	public PharmacyBasicInfoDTO getBasicInfo(PharmacyAdmin admin) throws PharmacyNotFoundException {
		Optional<Pharmacy> pharmacy = pharmacyRepository.findById(admin.getPharmacy().getId());
		if(pharmacy.isEmpty())
			throw new PharmacyNotFoundException("Pharmacy admin's pharmacy not found");
		Pharmacy ret = pharmacy.get();
		return new PharmacyBasicInfoDTO(ret.getName(), ret.getDescription(), ret.getAddress());
	}

	@Override
	public void update(PharmacyAdmin admin, PharmacyBasicInfoDTO dto) throws PharmacyNotFoundException, PharmacyDataInvalidException {
		Optional<Pharmacy> pharmacyOpt = pharmacyRepository.findById(admin.getPharmacy().getId());
		if(pharmacyOpt.isEmpty())
			throw new PharmacyNotFoundException("Pharmacy admin's pharmacy not found");
		Pharmacy pharmacy = pharmacyOpt.get();
		pharmacy.setName(dto.getName());
		pharmacy.setAddress(dto.getAddress());
		pharmacy.setDescription(dto.getDescription());
		pharmacyRepository.save(pharmacy);
	}
	
}
