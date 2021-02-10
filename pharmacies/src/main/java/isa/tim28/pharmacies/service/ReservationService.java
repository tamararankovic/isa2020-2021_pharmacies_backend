package isa.tim28.pharmacies.service;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.ReservationDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.MedicineDoesNotExistException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;

import isa.tim28.pharmacies.model.CancelledReservation;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Pharmacy;

import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.model.ReservationStatus;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.CancelledReservationRepository;
import isa.tim28.pharmacies.repository.ReservationRepository;
import isa.tim28.pharmacies.service.interfaces.IMedicineService;
import isa.tim28.pharmacies.service.interfaces.IPatientService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;
import isa.tim28.pharmacies.service.interfaces.IReservationService;

@Service
public class ReservationService implements IReservationService {
	
	private ReservationRepository reservationRepository;
	private IPatientService patientService;
	private IPharmacyService pharmacyService;
	private CancelledReservationRepository cancelledReservationRepository;
	private IMedicineService medicineService;
	private EmailService emailService;
	
	@Autowired
	public ReservationService(ReservationRepository reservationRepository, IPatientService patientService,IPharmacyService pharmacyService
			,IMedicineService medicineService,CancelledReservationRepository cancelledReservationRepository, EmailService emailService) {
		super();
		this.reservationRepository = reservationRepository;
		this.patientService = patientService;
		this.cancelledReservationRepository = cancelledReservationRepository;
		this.medicineService = medicineService;
		this.pharmacyService = pharmacyService;
		this.emailService = emailService;
	}
	
	@Override
	public List<ReservationDTO> getReservationByPatient(long userId) {
		
		long id = 0;
		try {
			id = patientService.getPatientById(userId).getId();
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<ReservationDTO> result = new ArrayList<ReservationDTO>();
		List<Reservation> reservations = reservationRepository.findByPatient_Id(id);
		List<CancelledReservation> canReservations = cancelledReservationRepository.findByPatient_Id(id);
		
		for(CancelledReservation cr : canReservations) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); 
			String date = cr.getDueDate().format(formatter);
				
			ReservationDTO dto = new ReservationDTO(cr.getId(),cr.getMedicine(),cr.getPharmacy(),date,cr.getStatus().toString(),false);
			result.add(dto);
		}
		
		for (Reservation r : reservations) {
			String res;
			if(r.isReceived()) {
				res = "RECEIVED";
			}else {
				res = "NOT RECEIVED";
			}
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); 
			String date = r.getDueDate().format(formatter);
			
			boolean cancellable = isCancellable(r);
			ReservationDTO dto = new ReservationDTO(r.getId(),r.getMedicine().getName(),r.getPharmacy().getName(),date,res,cancellable);
			result.add(dto);
		}
		return result;
	}
	
	public boolean isCancellable(Reservation r) {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime checkDate = r.getDueDate();
		
		if(today.isBefore(checkDate.minus(Period.ofDays(1))) && !r.isReceived()) {
			return true;
		}
		return false;
	}

	@Override
	public List<ReservationDTO> cancelReservation(ReservationDTO dto, long id) {
		
		CancelledReservation cancelled  = new CancelledReservation();
		cancelled.setMedicine(dto.getMedicine());
		cancelled.setPharmacy(dto.getPharmacy());
		try {
			cancelled.setPatient(patientService.getPatientById(id));
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); 
		LocalDateTime dateTime = LocalDateTime.parse(dto.getDate(), formatter);

		cancelled.setDueDate(dateTime);
		if(dto.getReceived().equals("RECEIVED"))
			cancelled.setReceived(true);
		cancelled.setReceived(false);
		cancelled.setStatus(ReservationStatus.CANCELLED);
		
		cancelledReservationRepository.save(cancelled);		
		reservationRepository.deleteById(dto.getId());;
		
		return getReservationByPatient(id);
	}
	
	@Override
	public Reservation makeReservation(ReservationDTO dto, User loggedInUser) {
		Reservation res = new Reservation();
		res.setMedicine(medicineService.getByName(dto.getMedicine()));
		try {
			res.setPatient(patientService.getPatientById(loggedInUser.getId()));
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.setPharmacy(pharmacyService.getByName(dto.getPharmacy()));
		res.setReceived(false);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); 
		LocalDateTime dateTime = LocalDateTime.parse(dto.getDate(), formatter);
		
		res.setDueDate(dateTime);
		
		Reservation reservation = reservationRepository.save(res);
		try {
			emailService.sendReservationMadeEmailAsync(loggedInUser.getFullName(),loggedInUser.getEmail(),dto.getMedicine(), reservation.getId());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	public Set<Reservation> getAllByPharamcy(Pharmacy pharmacy) {
		return reservationRepository.findAll().stream().filter(r -> r.getPharmacy().getId() == pharmacy.getId()).collect(Collectors.toSet());

	}
	
	@Override
	public boolean pharmacyHasActiveReservationsForMedicine(Pharmacy pharmacy, Medicine med) {
		Set<Reservation> reservations = getAllByPharamcy(pharmacy).stream().filter(r -> r.getMedicine().getId() == med.getId()).collect(Collectors.toSet());
		for(Reservation r : reservations)
			if(r.isActive())
				return true;
		return false;
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
		pharmacyService.savePharmacy(pharmacy);
	}
	


}
