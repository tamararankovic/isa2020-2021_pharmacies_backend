package isa.tim28.pharmacies.service;

import java.io.Console;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.WriteListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import isa.tim28.pharmacies.dtos.ReservationDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.CancelledReservation;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.model.ReservationStatus;
import isa.tim28.pharmacies.repository.CancelledReservationRepository;
import isa.tim28.pharmacies.repository.ReservationRepository;
import isa.tim28.pharmacies.service.interfaces.IReservationService;

@Service
public class ReservationService implements IReservationService {
	
	private ReservationRepository reservationRepository;
	private PatientService patientService;
	private MedicineService medicineService;
	private CancelledReservationRepository cancelledReservationRepository;
	
	@Autowired
	public ReservationService(ReservationRepository reservationRepository, PatientService patientService, MedicineService medicineService,CancelledReservationRepository cancelledReservationRepository) {
		super();
		this.reservationRepository = reservationRepository;
		this.patientService = patientService;
		this.medicineService = medicineService;
		this.cancelledReservationRepository = cancelledReservationRepository;
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
	public CancelledReservation cancelReservation(ReservationDTO dto, long id) {
		
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
		
		return cancelled;
	}
	
	
	
	


}
