package isa.tim28.pharmacies.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.AllComplaintsDTO;
import isa.tim28.pharmacies.dtos.AnswerOnComplaintDTO;
import isa.tim28.pharmacies.dtos.CompatibleMedicinesDTO;
import isa.tim28.pharmacies.dtos.ComplaintDTO;
import isa.tim28.pharmacies.dtos.DermatologistExaminationForPatientDTO;
import isa.tim28.pharmacies.dtos.ItemPriceDTO;
import isa.tim28.pharmacies.dtos.MedicineSearchDTO;
import isa.tim28.pharmacies.dtos.MedicineSpecificationDTO;
import isa.tim28.pharmacies.dtos.PharmaciesCounselingDTO;
import isa.tim28.pharmacies.dtos.PharmacyAddAdminDTO;
import isa.tim28.pharmacies.dtos.PharmacyBasicInfoDTO;
import isa.tim28.pharmacies.dtos.PharmacyForMedSearchDTO;
import isa.tim28.pharmacies.dtos.PharmacyInfoForPatientDTO;
import isa.tim28.pharmacies.dtos.PriceListDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.InvalidComplaintException;
import isa.tim28.pharmacies.exceptions.MedicineDoesNotExistException;
import isa.tim28.pharmacies.exceptions.PharmacyDataInvalidException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.PriceInvalidException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.DermatologistComplaint;
import isa.tim28.pharmacies.model.EPrescription;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicinePrice;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.PharmacistAppointment;
import isa.tim28.pharmacies.model.PharmacistComplaint;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.PharmacyComplaint;
import isa.tim28.pharmacies.model.PriceList;
import isa.tim28.pharmacies.model.Rating;
import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.repository.DermatologistAppointmentRepository;
import isa.tim28.pharmacies.repository.DermatologistComplaintRepository;
import isa.tim28.pharmacies.repository.DermatologistRepository;
import isa.tim28.pharmacies.repository.EPrescriptionRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.PharmacistAppointmentRepository;
import isa.tim28.pharmacies.repository.PharmacistComplaintRepository;
import isa.tim28.pharmacies.repository.PharmacistRepository;
import isa.tim28.pharmacies.repository.PharmacyComplaintRepository;
import isa.tim28.pharmacies.repository.PharmacyRepository;
import isa.tim28.pharmacies.repository.ReservationRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistAppointmentService;
import isa.tim28.pharmacies.service.interfaces.IDermatologistService;
import isa.tim28.pharmacies.service.interfaces.IMedicineService;
import isa.tim28.pharmacies.service.interfaces.IPharmacistAppointmentService;
import isa.tim28.pharmacies.service.interfaces.IPharmacistService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;

@Service
public class PharmacyService implements IPharmacyService {

	private PharmacyRepository pharmacyRepository;
	private IPharmacistService pharmacistService;
	private IDermatologistService dermatologistService;
	private IDermatologistAppointmentService appointmentService;
	private IMedicineService medicineService;
	private PharmacyComplaintRepository pharmacyComplaintRepository;
	private ReservationRepository reservationRepository;
	private EPrescriptionRepository ePrescriptionRepository;
	private DermatologistAppointmentRepository dermatologistAppointmentRepository;
	private PharmacistAppointmentRepository pharmacistAppointmentRepository;
	private PharmacistComplaintRepository pharmacistComplaintRepository;
	private DermatologistComplaintRepository dermatologistComplaintRepository;
	private EmailService emailService;
	private PatientRepository patientRepository;
	private DermatologistRepository dermatologistRepository;
	private PharmacistRepository pharmacistRepository;
	private IPharmacistAppointmentService pharmacistAppointmentService;
	
	@Autowired
	public PharmacyService(PharmacyRepository pharmacyRepository, IPharmacistService pharmacistService,
			IDermatologistService dermatologistService, IDermatologistAppointmentService appointmentService,
		    IMedicineService medicineService,  PharmacyComplaintRepository pharmacyComplaintRepository, 
		    ReservationRepository reservationRepository, EPrescriptionRepository ePrescriptionRepository,
		    DermatologistAppointmentRepository dermatologistAppointmentRepository, PharmacistAppointmentRepository pharmacistAppointmentRepository,
		    PharmacistComplaintRepository pharmacistComplaintRepository, DermatologistComplaintRepository dermatologistComplaintRepository, 
		    EmailService emailService,  PatientRepository patientRepository, DermatologistRepository dermatologistRepository,
		    PharmacistRepository pharmacistRepository,  IPharmacistAppointmentService pharmacistAppointmentService) {

		super();
		this.pharmacyRepository = pharmacyRepository;
		this.pharmacistService = pharmacistService;
		this.dermatologistService = dermatologistService;
		this.appointmentService = appointmentService;
		this.medicineService = medicineService;
		this.pharmacyComplaintRepository = pharmacyComplaintRepository;
		this.reservationRepository = reservationRepository;
		this.dermatologistAppointmentRepository = dermatologistAppointmentRepository;
		this.pharmacistAppointmentRepository = pharmacistAppointmentRepository;
		this.pharmacistComplaintRepository = pharmacistComplaintRepository;
		this.dermatologistComplaintRepository = dermatologistComplaintRepository;
		this.emailService = emailService;
		this.patientRepository = patientRepository;
		this.dermatologistRepository = dermatologistRepository;
		this.pharmacistRepository = pharmacistRepository;
		this.pharmacistAppointmentService = pharmacistAppointmentService;
	}

	@Override
	public boolean answerOnComplaint(AnswerOnComplaintDTO answer) throws MessagingException {
		System.out.println(answer.getType());
		if(answer.getType().equals("PHARMACY")) {
			PharmacyComplaint pc = pharmacyComplaintRepository.findById(answer.getComplaintId()).get();
			if(pc.getReply().equals(" ")) {
			    pc.setReply(answer.getAnswer());
				pharmacyComplaintRepository.save(pc);
				Patient patient = patientRepository.findById(pc.getPatient().getId()).get();
				Pharmacy pharmacy = pharmacyRepository.findById(pc.getPharmacy().getId()).get();
				emailService.sendResponseOnComplaint(String.join(" ", patient.getUser().getName(), patient.getUser().getSurname()), answer.getAnswer(), patient.getUser().getEmail(), pharmacy.getName());
				return true;
			}else {
				return false;
			}		
	}	
		else if(answer.getType().equals("DERMATOLOGIST")) {
			DermatologistComplaint dc = dermatologistComplaintRepository.findById(answer.getComplaintId()).get();
			if(dc.getReply().equals("")) {
				dc.setReply(answer.getAnswer());
				dermatologistComplaintRepository.save(dc);
				Patient patient = patientRepository.findById(dc.getPatient().getId()).get();
				Dermatologist dermatologist = dermatologistRepository.findById(dc.getDermatologist().getId()).get();
				emailService.sendResponseOnComplaint(String.join(" ", patient.getUser().getName(), patient.getUser().getSurname()), answer.getAnswer(), patient.getUser().getEmail(), String.join(" ", dermatologist.getUser().getName(),dermatologist.getUser().getSurname()));
				return true;
			}else {
				return false;
			}
	}	
		else {
			PharmacistComplaint pc = pharmacistComplaintRepository.findById(answer.getComplaintId()).get();
			if(pc.getReply().equals("")) {
				pc.setReply(answer.getAnswer());
				pharmacistComplaintRepository.save(pc);
				Patient patient = patientRepository.findById(pc.getPatient().getId()).get();
				Pharmacist pharmacist = pharmacistRepository.findById(pc.getPharmacist().getId()).get();
				emailService.sendResponseOnComplaint(String.join(" ", patient.getUser().getName(), patient.getUser().getSurname()), answer.getAnswer(), patient.getUser().getEmail(), String.join(" ", pharmacist.getUser().getName(),pharmacist.getUser().getSurname()));
				return true;
			}else {
				return false;
			}
		}
	
}
	@Override
	public List<AllComplaintsDTO> getAllComplaints(){
		List<PharmacyComplaint> pharmacies = pharmacyComplaintRepository.findAll();
		List<PharmacistComplaint> pharmacists= pharmacistComplaintRepository.findAll();
		List<DermatologistComplaint> dermatologists = dermatologistComplaintRepository.findAll();
		
		List<AllComplaintsDTO> result = new ArrayList<AllComplaintsDTO>();
		for(PharmacyComplaint pc : pharmacies) {
			AllComplaintsDTO dto = new AllComplaintsDTO(pc.getId(), pc.getText(), pc.getPharmacy().getName(), String.join(" ",pc.getPatient().getUser().getName(), pc.getPatient().getUser().getSurname()), "PHARMACY");
			result.add(dto);
		}
		for(PharmacistComplaint pc : pharmacists) {
			AllComplaintsDTO dto = new AllComplaintsDTO(pc.getId(), pc.getText(), String.join(" ", pc.getPharmacist().getUser().getName(), pc.getPharmacist().getUser().getSurname()), String.join(" ",pc.getPatient().getUser().getName(), pc.getPatient().getUser().getSurname()), "PHARMACIST");
			result.add(dto);
		}
		for(DermatologistComplaint pc : dermatologists) {
			AllComplaintsDTO dto = new AllComplaintsDTO(pc.getId(), pc.getText(), String.join(" ", pc.getDermatologist().getUser().getName(), pc.getDermatologist().getUser().getSurname()), String.join(" ",pc.getPatient().getUser().getName(), pc.getPatient().getUser().getSurname()), "DERMATOLOGIST");
			result.add(dto);
		}
		
		return result;
	}
	
	@Override
	public boolean createComplaint(Patient patient, ComplaintDTO dto) throws InvalidComplaintException, UserDoesNotExistException {
		if(!dto.isTextValid()) 
			throw new InvalidComplaintException("Complaint must have between 2 and 3000 characters. Try again.");
		Optional<Pharmacy> pahrmacyOp = pharmacyRepository.findById(dto.getId());
		if(pahrmacyOp.isEmpty())
			throw new UserDoesNotExistException("You attempted to write complaint to a pharmacy that doesn't exist!");
		Pharmacy pharmacy = pahrmacyOp.get();
		
		boolean reservedMed = false;
		try {
		List<Reservation> reservations = reservationRepository.findAllByPatient_Id(patient.getId());
		if(!reservations.isEmpty()) {
			for(Reservation r : reservations) {
				if(r.getPharmacy().getId() == dto.getId() && r.isReceived() == true) {
					reservedMed = true;
					break;
					}
				}
			}
		}
		catch(NullPointerException e) {
			reservedMed = false;
		}
		
		boolean ePrescription = false;
		try {
			List<EPrescription> ePrescriptions = ePrescriptionRepository.findAllByPatient_Id(patient.getId());
			if(ePrescriptions.isEmpty() == false ) {
			for(EPrescription ep : ePrescriptions) {
				if(ep.getPharmacy().getId() == dto.getId()) {
					ePrescription = true;
					break;
			  }
		  }
	  }
    }catch(NullPointerException e){
			ePrescription=false;
		}
	
		boolean dermAppointment = false;
	try {
		Set<DermatologistAppointment> dermAppointments = dermatologistAppointmentRepository.findAllByPatient_Id(patient.getId());
		if(!dermAppointments.isEmpty()) {
			for(DermatologistAppointment da : dermAppointments) {
				if(da.getPharmacy().getId() == dto.getId()) {
					dermAppointment = true;
					break;
				}
			}
		}
	}catch(NullPointerException e){
		dermAppointment=false;
	}
		boolean pharmAppointment = false;
	try {	
		Set<PharmacistAppointment> pharmAppointments = pharmacistAppointmentRepository.findAllByPatient_Id(patient.getId());
		if(!pharmAppointments.isEmpty()) {
			for(PharmacistAppointment pa : pharmAppointments) {
				if(pa.getPharmacist().getEngegementInPharmacy().getPharmacy().getId() == dto.getId()) {
					pharmAppointment = true;
					break;
				}
			}
		}
	}   catch(NullPointerException e){
		pharmAppointment=false;
	}
			
		if(ePrescription == true || reservedMed == true || dermAppointment == true || pharmAppointment == true) {
			PharmacyComplaint complaint = new PharmacyComplaint();
			complaint.setPharmacy(pharmacy);
			complaint.setPatient(patient);
			complaint.setReply(" ");
			complaint.setText(dto.getText());
			pharmacyComplaintRepository.save(complaint);
			return true;
		}else {
			return false;
		}
		
	}
	@Override
	public List<MedicineSearchDTO> searchMedicineByName(String name){
		List<MedicineSearchDTO> result = new ArrayList<MedicineSearchDTO>();
		if (name.equals("")) {
			for (Medicine m : medicineService.getAllMedicine()) {
				double averageRating = 0.0;
				int sumOfRatings = 0;
				Set<Rating> ratings = m.getRatings();
				if (!ratings.isEmpty()) {
					for (Rating r : ratings) {
						sumOfRatings += r.getRating();
					}
					averageRating = sumOfRatings / ratings.size();
				}

				Set<CompatibleMedicinesDTO> compatibleMedicines = new HashSet<CompatibleMedicinesDTO>();

				for (String code : m.getCompatibleMedicineCodes()) {
					Medicine me = medicineService.getMedicineByCode(code);
					CompatibleMedicinesDTO med = new CompatibleMedicinesDTO(me.getName(), code);
					compatibleMedicines.add(med);
				}
				MedicineSpecificationDTO specification = new MedicineSpecificationDTO(m.getSideEffects(),
						m.getAdvisedDailyDose(), m.getIngredients(), compatibleMedicines);

				Set<PharmacyForMedSearchDTO> pharmacies = new HashSet<PharmacyForMedSearchDTO>();
				for(Pharmacy pharmacy : getAll()) {
					for(MedicineQuantity medi : pharmacy.getMedicines()) {
						if(medi.getMedicine().getId() == m.getId()) {
							double price = 0.0;
							for (PriceList pl : pharmacy.getPriceLists()) {
								for (MedicinePrice mp : pl.getMedicinePrices()) {
									if (mp.getMedicine().getId() == m.getId()) {
										price = mp.getPrice();
									}
								}
							}
							PharmacyForMedSearchDTO ph = new PharmacyForMedSearchDTO(pharmacy.getName(), price);
							pharmacies.add(ph);
						}
					}
				}

				MedicineSearchDTO dto = new MedicineSearchDTO(m.getId(), m.getName(), m.getType().toString(),
						averageRating, specification, pharmacies);
				result.add(dto);
			}
			return result;
		}

		else {
			for (Medicine m : findAllMedicineByName(name)) {
				double averageRating = 0.0;
				int sumOfRatings = 0;
				Set<Rating> ratings = m.getRatings();
				if (!ratings.isEmpty()) {
					for (Rating r : ratings) {
						sumOfRatings += r.getRating();
					}
					averageRating = sumOfRatings / ratings.size();
				}

				Set<CompatibleMedicinesDTO> compatibleMedicines = new HashSet<CompatibleMedicinesDTO>();

				for (String code : m.getCompatibleMedicineCodes()) {
					Medicine me = medicineService.getMedicineByCode(code);
					CompatibleMedicinesDTO med = new CompatibleMedicinesDTO(me.getName(), code);
					compatibleMedicines.add(med);
				}
				MedicineSpecificationDTO specification = new MedicineSpecificationDTO(m.getSideEffects(),
						m.getAdvisedDailyDose(), m.getIngredients(), compatibleMedicines);

				Set<PharmacyForMedSearchDTO> pharmacies = new HashSet<PharmacyForMedSearchDTO>();
				for(Pharmacy pharmacy : getAll()) {
					for(MedicineQuantity medi : pharmacy.getMedicines()) {
						if(medi.getMedicine().getId() == m.getId()) {
							double price = 0.0;
							for (PriceList pl : pharmacy.getPriceLists()) {
								for (MedicinePrice mp : pl.getMedicinePrices()) {
									if (mp.getMedicine().getId() == m.getId()) {
										price = mp.getPrice();
									}
								}
							}
							PharmacyForMedSearchDTO ph = new PharmacyForMedSearchDTO(pharmacy.getName(), price);
							pharmacies.add(ph);
						}
					}
				}

				MedicineSearchDTO dto = new MedicineSearchDTO(m.getId(), m.getName(), m.getType().toString(),
						averageRating, specification, pharmacies);
				result.add(dto);

			}
			return result;
		}
	}

	public List<Medicine> findAllMedicineByName(String name) {
		List<Medicine> ret = new ArrayList<Medicine>();
		for (Medicine m : medicineService.getAllMedicine()) {
			if (m.getName().toLowerCase().contains(name.toLowerCase()))
				ret.add(m);
		}
		return ret;
	}

	@Override
	public Pharmacy savePharmacy(Pharmacy pharmacy) {
		Pharmacy newPharmacy = pharmacyRepository.save(pharmacy);
		return newPharmacy;
	}

	@Override
	public ArrayList<PharmacyInfoForPatientDTO> getAllPharmacies(String name, String address)
			throws PharmacyNotFoundException {

		ArrayList<PharmacyInfoForPatientDTO> pharmacies = new ArrayList<PharmacyInfoForPatientDTO>();

		if (name.equals("") && address.equals("")) {
			List<Pharmacy> pharm = pharmacyRepository.findAll();
			for (Pharmacy p : pharm) {
				pharmacies.add(getPharmacyInfo(p.getId()));
			}
			return pharmacies;
		} else {
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
		for (MedicineQuantity mq : pharmacy.getMedicines()) {
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
		if (pharmacy.isEmpty())
			throw new PharmacyNotFoundException("Pharmacy admin's pharmacy not found");
		Pharmacy ret = pharmacy.get();
		return new PharmacyBasicInfoDTO(ret.getName(), ret.getDescription(), ret.getAddress(), ret.getAvgRating());
	}

	@Override
	public void update(PharmacyAdmin admin, PharmacyBasicInfoDTO dto)
			throws PharmacyNotFoundException, PharmacyDataInvalidException {
		Optional<Pharmacy> pharmacyOpt = pharmacyRepository.findById(admin.getPharmacy().getId());
		if (pharmacyOpt.isEmpty())
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

	private List<PharmacyAddAdminDTO> pharmaciesToDtos(List<Pharmacy> pharmacies) {
		List<PharmacyAddAdminDTO> dtos = new ArrayList<PharmacyAddAdminDTO>();
		for (Pharmacy p : pharmacies) {
			dtos.add(new PharmacyAddAdminDTO(p.getId(), p.getName(), p.getDescription(), p.getAddress()));
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
		for (Pharmacy p : pharmacies) {
			Set<Medicine> allMedicine = findAllInStockByPharmacyId(p.getId());
			for(Medicine m : allMedicine) {
				if(medicineId ==m.getId()) {
					PharmacyBasicInfoDTO pharm = new PharmacyBasicInfoDTO(p.getName(),p.getDescription(),p.getAddress(), p.getAvgRating());
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
		if (!pharmacy.offers(medicine)) {
			pharmacy.getMedicines().add(new MedicineQuantity(medicine, 0));
			pharmacyRepository.save(pharmacy);
		}
	}

	@Override
	public void addMedicines(Pharmacy pharmacy, Set<MedicineQuantity> medicines) throws ForbiddenOperationException {
		for (MedicineQuantity mq : medicines)
			pharmacy.addMedicine(mq);
		pharmacyRepository.save(pharmacy);
	}

	@Override
	public void addNewMedicines(Pharmacy pharmacy, Set<Long> medicineIds) throws MedicineDoesNotExistException {
		for (long medId : medicineIds) {
			Medicine m = medicineService.findById(medId);
			if (m != null)
				addNewmedicine(pharmacy, m);
			else
				throw new MedicineDoesNotExistException("Medicine does not exist in the system!");
		}
	}

	@Override
	public PriceListDTO getCurrentPriceList(Pharmacy pharmacy) {
		PriceListDTO priceList = new PriceListDTO();
		Set<Medicine> allMedicines = pharmacy.getAllOfferedMedicines();
		for (Medicine m : allMedicines) {
			if (pharmacy.isPriceDefined(m))
				priceList.getMedicinePrices()
						.add(new ItemPriceDTO(m.getId(), m.getName(), pharmacy.getCurrentPrice(m), false));
			else
				priceList.getMedicinePrices().add(new ItemPriceDTO(m.getId(), m.getName(), 0, true));
		}
		if (pharmacy.isDermatologistAppointmentPriceDefined())
			priceList.setDermatologistAppointmentPrice(
					new ItemPriceDTO(0, "", pharmacy.getDermatologistAppointmentCurrentPrice(), false));
		else
			priceList.setDermatologistAppointmentPrice(new ItemPriceDTO(0, "", 0, true));
		if (pharmacy.isPharmacistAppointmentPriceDefined())
			priceList.setPharmacistAppointmentPrice(
					new ItemPriceDTO(0, "", pharmacy.getPharmacistAppointmentCurrentPrice(), false));
		else
			priceList.setPharmacistAppointmentPrice(new ItemPriceDTO(0, "", 0, true));
		return priceList;
	}

	@Override
	public void updatePriceLists(PriceListDTO dto, Pharmacy pharmacy)
			throws MedicineDoesNotExistException, ForbiddenOperationException, PriceInvalidException {
		if (dto.getStartDate().isBefore(LocalDate.now()))
			throw new ForbiddenOperationException("You can't set price list with start date in the past");
		if (pharmacy.hasPriceListDefinedOnDate(dto.getStartDate())) {
			updatePriceList(dto, pharmacy);
		} else {
			createPriceList(dto, pharmacy);
		}
	}

	private void updatePriceList(PriceListDTO dto, Pharmacy pharmacy)
			throws MedicineDoesNotExistException, PriceInvalidException {
		if (!dto.getDermatologistAppointmentPrice().isUndefined()) {
			pharmacy.getPriceListDefinedOnDate(dto.getStartDate()).setDermAppPriceDefined(true);
			pharmacy.getPriceListDefinedOnDate(dto.getStartDate())
					.setDefaultDermatologistAppointmentPrice(dto.getDermatologistAppointmentPrice().getPrice());
		}

		if (!dto.getPharmacistAppointmentPrice().isUndefined()) {
			pharmacy.getPriceListDefinedOnDate(dto.getStartDate()).setPharmAppPriceDefined(true);
			pharmacy.getPriceListDefinedOnDate(dto.getStartDate())
					.setPharmacistAppointmentPrice(dto.getPharmacistAppointmentPrice().getPrice());
		}

		for (ItemPriceDTO mp : dto.getMedicinePrices()) {
			Medicine m = medicineService.findById(mp.getItemId());
			if (m != null) {
				pharmacy.getPriceListDefinedOnDate(dto.getStartDate()).setPrice(m, mp.getPrice());
			} else
				throw new MedicineDoesNotExistException(
						"You are trying to add price to a medicine that doesn't exist!");
		}

		pharmacyRepository.save(pharmacy);
	}

	private void createPriceList(PriceListDTO dto, Pharmacy pharmacy)
			throws MedicineDoesNotExistException, PriceInvalidException {
		PriceList pl = new PriceList();
		pl.setStartDate(dto.getStartDate());

		if (dto.getDermatologistAppointmentPrice().isUndefined()) {
			pl.setDermAppPriceDefined(false);
		} else {
			pl.setDermAppPriceDefined(true);
			pl.setDefaultDermatologistAppointmentPrice(dto.getDermatologistAppointmentPrice().getPrice());
		}

		if (dto.getPharmacistAppointmentPrice().isUndefined()) {
			pl.setPharmAppPriceDefined(false);
		} else {
			pl.setPharmAppPriceDefined(true);
			pl.setPharmacistAppointmentPrice(dto.getPharmacistAppointmentPrice().getPrice());
		}

		for (ItemPriceDTO mp : dto.getMedicinePrices()) {
			Medicine m = medicineService.findById(mp.getItemId());
			if (m != null) {
				pl.getMedicinePrices().add(new MedicinePrice(m, mp.getPrice()));
			} else
				throw new MedicineDoesNotExistException(
						"You are trying to add price to a medicine that doesn't exist!");
		}

		pharmacy.getPriceLists().add(pl);
		pharmacyRepository.save(pharmacy);
	}

	@Override
	public List<Pharmacy> getAll() {
		List<Pharmacy> pharm = pharmacyRepository.findAll();
		return pharm;
	}

	@Override
	public List<PharmaciesCounselingDTO> getPharmaciesWithAvailablePharmacists(LocalDateTime date) {
		List<Pharmacy> all = getAll();
		List<PharmaciesCounselingDTO> result = new ArrayList<PharmaciesCounselingDTO>();
		boolean hasAvailablePharmacists = false;
		
		for (Pharmacy p : all) {
			hasAvailablePharmacists = false;
			Set<Pharmacist> pharmacists = pharmacistService.findAllByPharmacyId(p.getId());
			for (Pharmacist pharm : pharmacists) {
				if (pharmacistAppointmentService.isPharmacistAvailable(pharm, date)) {
					hasAvailablePharmacists = true;
				}
			}
			double sumOfRatings = 0;
			for (Rating r : p.getRatings())
				sumOfRatings += r.getRating();
			if (hasAvailablePharmacists) {
				result.add(new PharmaciesCounselingDTO(p.getId(), p.getName(), p.getAddress(),
						p.getRatings().size() > 0 ? sumOfRatings / p.getRatings().size() : 0,
						p.getPharmacistAppointmentCurrentPrice()));
			}
		}
		return result;
	}
}
