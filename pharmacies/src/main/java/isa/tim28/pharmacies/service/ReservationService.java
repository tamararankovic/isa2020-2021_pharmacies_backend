package isa.tim28.pharmacies.service;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.ReservationDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.repository.ReservationRepository;
import isa.tim28.pharmacies.service.interfaces.IReservationService;

@Service
public class ReservationService implements IReservationService {
	
	private ReservationRepository reservationRepository;
	private PatientService patientService;
	
	@Autowired
	public ReservationService(ReservationRepository reservationRepository, PatientService patientService) {
		super();
		this.reservationRepository = reservationRepository;
		this.patientService = patientService;
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
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
			String date = r.getDueDate().format(formatter);
			
			boolean cancellable = isCancellable(r);
			ReservationDTO dto = new ReservationDTO(r.getId(),r.getMedicine().getName(),r.getPharmacy().getName(),date,res,cancellable);
			result.add(dto);
		}
		return result;
	}
	
	public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
	public boolean isCancellable(Reservation r) {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime checkDate = r.getDueDate();
		
		if(today.isBefore(checkDate.minus(Period.ofDays(1))) && !r.isReceived()) {
			return true;
		}
		return false;
	}

	@Override
	public Set<Reservation> getAllByPharamcy(Pharmacy pharmacy) {
		return reservationRepository.findAll().stream().filter(r -> r.getPharmacy().getId() == pharmacy.getId()).collect(Collectors.toSet());
	}
	
	
	
	


}
