package isa.tim28.pharmacies.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DermatologistExaminationForPatientDTO;
import isa.tim28.pharmacies.dtos.ItemPriceDTO;
import isa.tim28.pharmacies.dtos.PharmacyAddAdminDTO;
import isa.tim28.pharmacies.dtos.PharmacyBasicInfoDTO;
import isa.tim28.pharmacies.dtos.PharmacyInfoForPatientDTO;
import isa.tim28.pharmacies.dtos.PriceListDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.MedicineDoesNotExistException;
import isa.tim28.pharmacies.exceptions.PharmacyDataInvalidException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.PriceInvalidException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicinePrice;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.PriceList;
import isa.tim28.pharmacies.model.Rating;
import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.repository.PharmacyRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistAppointmentService;
import isa.tim28.pharmacies.service.interfaces.IDermatologistService;
import isa.tim28.pharmacies.service.interfaces.IMedicineService;
import isa.tim28.pharmacies.service.interfaces.IPharmacistService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;
import isa.tim28.pharmacies.service.interfaces.IReservationService;

@Service
public class PharmacyService implements IPharmacyService {

	private PharmacyRepository pharmacyRepository;
	private IPharmacistService pharmacistService;
	private IDermatologistService dermatologistService;
	private IDermatologistAppointmentService appointmentService;
	private IReservationService reservationService;
	private IMedicineService medicineService;

	@Autowired
	public PharmacyService(PharmacyRepository pharmacyRepository, IPharmacistService pharmacistService,
			IDermatologistService dermatologistService, IDermatologistAppointmentService appointmentService,
			IReservationService reservationService, IMedicineService medicineService) {
		super();
		this.pharmacyRepository = pharmacyRepository;
		this.pharmacistService = pharmacistService;
		this.dermatologistService = dermatologistService;
		this.appointmentService = appointmentService;
		this.reservationService = reservationService;
		this.medicineService = medicineService;
	}

	@Override
	public ArrayList<PharmacyInfoForPatientDTO> getAllPharmacies(String name, String address)throws PharmacyNotFoundException {
		
		ArrayList<PharmacyInfoForPatientDTO> pharmacies = new ArrayList<PharmacyInfoForPatientDTO>();

		if (name.equals("") && address.equals("")) {
			List<Pharmacy> pharm = pharmacyRepository.findAll();
			for (Pharmacy p : pharm) {
				pharmacies.add(getPharmacyInfo(p.getId()));
			}
			return pharmacies;
		}else {
			List<Pharmacy> pharm = findAllPharmaciesWithCriteria(name, address);
			for (Pharmacy p : pharm) {
				pharmacies.add(getPharmacyInfo(p.getId()));
			}
			return pharmacies;
		}
	}

	@Override
	public PharmacyInfoForPatientDTO getPharmacyInfo(long pharmacyId) throws PharmacyNotFoundException {
		if (pharmacyRepository.findById(pharmacyId).isEmpty())
			throw new PharmacyNotFoundException();
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).get();
		PharmacyInfoForPatientDTO dto = new PharmacyInfoForPatientDTO();
		dto.setId(pharmacyId);
		dto.setName(pharmacy.getName());
		dto.setDescription(pharmacy.getDescription());
		dto.setAddress(pharmacy.getAddress());
		double sumOfRatings = 0;
		for (Rating r : pharmacy.getRatings())
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
			appDto.setDermatologist(
					a.getDermatologist().getUser().getName() + " " + a.getDermatologist().getUser().getSurname());
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
		for (MedicineQuantity mq : pharmacy.getMedicines()){
			if (mq.getQuantity() > 0) {
				ret.add(mq.getMedicine());
				}
			}
		return ret;
	}


	private List<Pharmacy> findAllPharmaciesWithCriteria(String name, String address) {
		List<Pharmacy> ret = new ArrayList<Pharmacy>();
		for (Pharmacy p : pharmacyRepository.findAll()) {
			if (p.getName().toLowerCase().contains(name.toLowerCase())
					&& p.getAddress().toLowerCase().contains(address.toLowerCase()))
				ret.add(p);
		}
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
	
	@Override
	public Pharmacy save(Pharmacy pharmacy) {
		Pharmacy newPharmacy = pharmacyRepository.save(pharmacy);
		return newPharmacy;
	}

	@Override
	public List<PharmacyAddAdminDTO> getAllPharmacies() {
		return pharmaciesToDtos(pharmacyRepository.findAll());
	}
	
	private List<PharmacyAddAdminDTO> pharmaciesToDtos(List<Pharmacy> pharmacies){
		List<PharmacyAddAdminDTO> dtos = new ArrayList<PharmacyAddAdminDTO>();
		for(Pharmacy p : pharmacies) {
			dtos.add(new PharmacyAddAdminDTO(p.getId(),p.getName(),p.getDescription(),p.getAddress()));
		}
		return dtos;
	}

	@Override
	public Pharmacy getPharmacyById(long pharmacyId) throws PharmacyNotFoundException {
		
		if (pharmacyRepository.findById(pharmacyId).isEmpty())
			throw new PharmacyNotFoundException();
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).get();
		return pharmacy;
	}
	
	@Override
	public List<PharmacyBasicInfoDTO> getPharmacyByMedicineId(long medicineId) throws PharmacyNotFoundException {
		List<PharmacyBasicInfoDTO> res = new ArrayList<PharmacyBasicInfoDTO>();
		List<Pharmacy> pharmacies = pharmacyRepository.findAll();
		for(Pharmacy p : pharmacies) {
			Set<Medicine> allMedicine = findAllInStockByPharmacyId(p.getId());
			for(Medicine m : allMedicine) {
				if(medicineId ==m.getId()) {
					PharmacyBasicInfoDTO pharm = new PharmacyBasicInfoDTO(p.getName(),p.getDescription(),p.getAddress());
					res.add(pharm);
					continue;
				}
			}
		}
		return res;
	}

	@Override
	public Pharmacy getByName(String name) {
		return pharmacyRepository.findByName(name);
	}

	@Override
	public void addNewmedicine(Pharmacy pharmacy, Medicine medicine) {
		if(!pharmacy.offers(medicine)) {
			pharmacy.getMedicines().add(new MedicineQuantity(medicine, 0));
			pharmacyRepository.save(pharmacy);
		}
	}

	@Override
	public void addMedicines(Pharmacy pharmacy, Set<MedicineQuantity> medicines) throws ForbiddenOperationException {
		for(MedicineQuantity mq : medicines)
			pharmacy.addMedicine(mq);
		pharmacyRepository.save(pharmacy);
	}

	@Override
	public void addNewMedicines(Pharmacy pharmacy, Set<Long> medicineIds) throws MedicineDoesNotExistException {
		for(long medId : medicineIds) {
			Medicine m = medicineService.findById(medId);
			if(m != null)
				addNewmedicine(pharmacy, m);
			else 
				throw new MedicineDoesNotExistException("Medicine does not exist in the system!");
		}
	}

	@Override
	public void deleteMedicine(Pharmacy pharmacy, long medicineId) throws MedicineDoesNotExistException, ForbiddenOperationException {
		Medicine medicine = medicineService.findById(medicineId);
		if(medicine == null)
			throw new MedicineDoesNotExistException("Medicine does not exist in the system!");
		Optional<MedicineQuantity> medOpt = pharmacy.getMedicines().stream().filter(m -> m.getMedicine().getId() == medicine.getId()).findFirst();
		if(medOpt.isEmpty()) {
			throw new MedicineDoesNotExistException("You can't delete a medicine that the pharmacy doesn't offer!");
		}
		MedicineQuantity med = medOpt.get();
		if(pharmacyHasActiveReservationsForMedicine(pharmacy, med.getMedicine()))
			throw new ForbiddenOperationException("You can't delete a medicine that has active reservations!");
		pharmacy.getMedicines().remove(med);
		pharmacyRepository.save(pharmacy);
	}
	
	private boolean pharmacyHasActiveReservationsForMedicine(Pharmacy pharmacy, Medicine med) {
		Set<Reservation> reservations = reservationService.getAllByPharamcy(pharmacy).stream().filter(r -> r.getMedicine().getId() == med.getId()).collect(Collectors.toSet());
		for(Reservation r : reservations)
			if(r.isActive())
				return true;
		return false;
	}

	@Override
	public PriceListDTO getCurrentPriceList(Pharmacy pharmacy) {
		PriceListDTO priceList = new PriceListDTO();
		Set<Medicine> allMedicines = pharmacy.getAllOfferedMedicines();
		for(Medicine m : allMedicines) {
			if(pharmacy.isPriceDefined(m))
				priceList.getMedicinePrices().add(new ItemPriceDTO(m.getId(), m.getName(), pharmacy.getCurrentPrice(m), false));
			else
				priceList.getMedicinePrices().add(new ItemPriceDTO(m.getId(), m.getName(), 0, true));
		}
		if (pharmacy.isDermatologistAppointmentPriceDefined())
			priceList.setDermatologistAppointmentPrice(new ItemPriceDTO(0, "", pharmacy.getDermatologistAppointmentCurrentPrice(), false));
		else
			priceList.setDermatologistAppointmentPrice(new ItemPriceDTO(0, "", 0, true));
		if (pharmacy.isPharmacistAppointmentPriceDefined())
			priceList.setPharmacistAppointmentPrice(new ItemPriceDTO(0, "", pharmacy.getPharmacistAppointmentCurrentPrice(), false));
		else priceList.setPharmacistAppointmentPrice(new ItemPriceDTO(0, "", 0, true));
		return priceList;
	}

	@Override
	public void updatePriceLists(PriceListDTO dto, Pharmacy pharmacy) throws MedicineDoesNotExistException, ForbiddenOperationException, PriceInvalidException {
		if(dto.getStartDate().isBefore(LocalDate.now()))
			throw new ForbiddenOperationException("You can't set price list with start date in the past");
		if (pharmacy.hasPriceListDefinedOnDate(dto.getStartDate())) {
			updatePriceList(dto, pharmacy);
		} else {
			createPriceList(dto, pharmacy);
		}
	}
	
	private void updatePriceList(PriceListDTO dto, Pharmacy pharmacy) throws MedicineDoesNotExistException, PriceInvalidException {
		if (!dto.getDermatologistAppointmentPrice().isUndefined()) {
			pharmacy.getPriceListDefinedOnDate(dto.getStartDate()).setDermAppPriceDefined(true);
			pharmacy.getPriceListDefinedOnDate(dto.getStartDate()).setDefaultDermatologistAppointmentPrice(dto.getDermatologistAppointmentPrice().getPrice());
		}
		
		if(!dto.getPharmacistAppointmentPrice().isUndefined()) {
			pharmacy.getPriceListDefinedOnDate(dto.getStartDate()).setPharmAppPriceDefined(true);
			pharmacy.getPriceListDefinedOnDate(dto.getStartDate()).setPharmacistAppointmentPrice(dto.getPharmacistAppointmentPrice().getPrice());
		}
		
		for(ItemPriceDTO mp : dto.getMedicinePrices()) {
			Medicine m = medicineService.findById(mp.getItemId());
			if (m != null) {
				pharmacy.getPriceListDefinedOnDate(dto.getStartDate()).setPrice(m, mp.getPrice());
			} else throw new MedicineDoesNotExistException("You are trying to add price to a medicine that doesn't exist!");
		}
		
		pharmacyRepository.save(pharmacy);
	}
	
	private void createPriceList(PriceListDTO dto, Pharmacy pharmacy) throws MedicineDoesNotExistException, PriceInvalidException {
		PriceList pl = new PriceList();
		pl.setStartDate(dto.getStartDate());
		
		if (dto.getDermatologistAppointmentPrice().isUndefined()) {
			pl.setDermAppPriceDefined(false);
		} else {
			pl.setDermAppPriceDefined(true);
			pl.setDefaultDermatologistAppointmentPrice(dto.getDermatologistAppointmentPrice().getPrice());
		}
		
		if(dto.getPharmacistAppointmentPrice().isUndefined()) {
			pl.setPharmAppPriceDefined(false);
		} else {
			pl.setPharmAppPriceDefined(true);
			pl.setPharmacistAppointmentPrice(dto.getPharmacistAppointmentPrice().getPrice());
		}
		
		for(ItemPriceDTO mp : dto.getMedicinePrices()) {
			Medicine m = medicineService.findById(mp.getItemId());
			if (m != null) {
				pl.getMedicinePrices().add(new MedicinePrice(m, mp.getPrice()));
			} else throw new MedicineDoesNotExistException("You are trying to add price to a medicine that doesn't exist!");
		}
		
		pharmacy.getPriceLists().add(pl);
		pharmacyRepository.save(pharmacy);
	}
}
