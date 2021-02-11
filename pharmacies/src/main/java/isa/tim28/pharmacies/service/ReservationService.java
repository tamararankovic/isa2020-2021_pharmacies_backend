package isa.tim28.pharmacies.service;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DoctorRatingDTO;
import isa.tim28.pharmacies.dtos.ReservationDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.MedicineDoesNotExistException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.CancelledReservation;
import isa.tim28.pharmacies.model.Loyalty;
import isa.tim28.pharmacies.model.LoyaltyPoints;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.Pharmacy;

import isa.tim28.pharmacies.model.Rating;

import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.model.ReservationStatus;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.CancelledReservationRepository;
import isa.tim28.pharmacies.repository.LoyaltyPointsRepository;
import isa.tim28.pharmacies.repository.MedicineRepository;
import isa.tim28.pharmacies.repository.ReservationRepository;
import isa.tim28.pharmacies.service.interfaces.IMedicineService;
import isa.tim28.pharmacies.service.interfaces.IOrderService;
import isa.tim28.pharmacies.service.interfaces.IPatientService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;
import isa.tim28.pharmacies.service.interfaces.IRatingService;
import isa.tim28.pharmacies.service.interfaces.IReservationService;

@Service
public class ReservationService implements IReservationService {

	private ReservationRepository reservationRepository;
	private IPatientService patientService;
	private IPharmacyService pharmacyService;
	private CancelledReservationRepository cancelledReservationRepository;
	private IMedicineService medicineService;
	private EmailService emailService;
	private IOrderService orderService;
	private IRatingService ratingService;
	private MedicineRepository medicineRepository;
	private LoyaltyPointsRepository loyaltyPointsRepository;
	
	@Autowired
	public ReservationService(ReservationRepository reservationRepository, IPatientService patientService,IPharmacyService pharmacyService
			,IMedicineService medicineService,CancelledReservationRepository cancelledReservationRepository, EmailService emailService, IOrderService orderService,
			 LoyaltyPointsRepository loyaltyPointsRepository,IRatingService ratingService, MedicineRepository medicineRepository) {
		super();
		this.reservationRepository = reservationRepository;
		this.patientService = patientService;
		this.cancelledReservationRepository = cancelledReservationRepository;
		this.medicineService = medicineService;
		this.pharmacyService = pharmacyService;
		this.emailService = emailService;
		this.ratingService = ratingService;
		this.medicineRepository = medicineRepository;
		this.orderService = orderService;
		this.loyaltyPointsRepository = loyaltyPointsRepository;

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

		for (CancelledReservation cr : canReservations) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			String date = cr.getDueDate().format(formatter);

			ReservationDTO dto = new ReservationDTO(cr.getId(), cr.getMedicine(), cr.getPharmacy(), date,
					cr.getStatus().toString(), false);
			result.add(dto);
		}

		for (Reservation r : reservations) {
			String res;
			if (r.isReceived()) {
				res = "RECEIVED";
			} else {
				res = "NOT RECEIVED";
			}

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			String date = r.getDueDate().format(formatter);

			boolean cancellable = isCancellable(r);
			ReservationDTO dto = new ReservationDTO(r.getId(), r.getMedicine().getName(), r.getPharmacy().getName(),
					date, res, cancellable);
			result.add(dto);
		}
		return result;
	}

	public boolean isCancellable(Reservation r) {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime checkDate = r.getDueDate();

		if (today.isBefore(checkDate.minus(Period.ofDays(1))) && !r.isReceived()) {
			return true;
		}
		return false;
	}

	@Override
	public List<ReservationDTO> cancelReservation(ReservationDTO dto, long id) {

		CancelledReservation cancelled = new CancelledReservation();
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
		if (dto.getReceived().equals("RECEIVED"))
			cancelled.setReceived(true);
		cancelled.setReceived(false);
		cancelled.setStatus(ReservationStatus.CANCELLED);

		cancelledReservationRepository.save(cancelled);
		reservationRepository.deleteById(dto.getId());
		;

		return getReservationByPatient(id);
	}

	@Override
	public Reservation makeReservation(ReservationDTO dto, User loggedInUser) throws UserDoesNotExistException, MessagingException {
		Reservation res = new Reservation();
		res.setMedicine(medicineService.getByName(dto.getMedicine()));
		
		res.setPatient(patientService.getPatientById(loggedInUser.getId()));
		
		//loyalty program
		if(loyaltyPointsRepository.findAll() == null) {
			res.setPrice(pharmacyService.getByName(dto.getPharmacy()).getCurrentPrice(medicineService.getByName(dto.getMedicine())));
		}else 
		{
			List<LoyaltyPoints> points = loyaltyPointsRepository.findAll();
			if(!points.isEmpty()) {
				LoyaltyPoints lp = points.get(points.size() - 1);
				
				double price = pharmacyService.getByName(dto.getPharmacy()).getCurrentPrice(medicineService.getByName(dto.getMedicine()));
				if(patientService.getPatientById(loggedInUser.getId()).getCategory().equals(Loyalty.REGULAR)) {
					res.setPrice(price);
				}else if(patientService.getPatientById(loggedInUser.getId()).getCategory().equals(Loyalty.SILVER)) {
					double procentage = price*(lp.getDiscountForSilver()/100);
					res.setPrice(price-procentage);
				}else if(patientService.getPatientById(loggedInUser.getId()).getCategory().equals(Loyalty.GOLD)) {
					double procentage = price*(lp.getDiscountForGold()/100);
					res.setPrice(price-procentage);
				}
			}else { 	
				res.setPrice(pharmacyService.getByName(dto.getPharmacy()).getCurrentPrice(medicineService.getByName(dto.getMedicine())));

			}
		}
		
		res.setPharmacy(pharmacyService.getByName(dto.getPharmacy()));
		res.setReceived(false);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(dto.getDate(), formatter);

		res.setDueDate(dateTime);

		Reservation reservation = reservationRepository.save(res);
		try {
			emailService.sendReservationMadeEmailAsync(loggedInUser.getFullName(), loggedInUser.getEmail(),
					dto.getMedicine(), reservation.getId());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	public Set<Reservation> getAllByPharamcy(Pharmacy pharmacy) {
		return reservationRepository.findAll().stream().filter(r -> r.getPharmacy().getId() == pharmacy.getId())
				.collect(Collectors.toSet());

	}

	@Override
	public boolean pharmacyHasActiveReservationsForMedicine(Pharmacy pharmacy, Medicine med) {
		Set<Reservation> reservations = getAllByPharamcy(pharmacy).stream()
				.filter(r -> r.getMedicine().getId() == med.getId()).collect(Collectors.toSet());
		for (Reservation r : reservations)
			if (r.isActive())
				return true;
		return false;
	}

	@Override
	public void deleteMedicine(Pharmacy pharmacy, long medicineId)
			throws MedicineDoesNotExistException, ForbiddenOperationException {
		Medicine medicine = medicineService.findById(medicineId);
		if (medicine == null)
			throw new MedicineDoesNotExistException("Medicine does not exist in the system!");
		Optional<MedicineQuantity> medOpt = pharmacy.getMedicines().stream()
				.filter(m -> m.getMedicine().getId() == medicine.getId()).findFirst();
		if (medOpt.isEmpty()) {
			throw new MedicineDoesNotExistException("You can't delete a medicine that the pharmacy doesn't offer!");
		}
		MedicineQuantity med = medOpt.get();
		if (pharmacyHasActiveReservationsForMedicine(pharmacy, med.getMedicine()))
			throw new ForbiddenOperationException("You can't delete a medicine that has active reservations!");
		if(orderService.pharmacyHasActiveOrdersForMedicine(pharmacy, medicine))
			throw new ForbiddenOperationException("You can't delete a medicine that has order that is waiting offers or a winner!");
		pharmacy.getMedicines().remove(med);
		pharmacyService.savePharmacy(pharmacy);
	}

	@Override
	public List<DoctorRatingDTO> getMedicineForRating(long userId) {

		long id = 0;
		try {
			id = patientService.getPatientById(userId).getId();
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Reservation> reservations = reservationRepository.findByPatient_Id(id);
		List<DoctorRatingDTO> result = new ArrayList<DoctorRatingDTO>();

		for (Reservation r : reservations) {
			if (r.isReceived()) {

				List<Rating> savedRatings = new ArrayList<Rating>();
				try {
					savedRatings = getRatingsByMedicine(r.getMedicine().getId(), id);
				} catch (UserDoesNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (Rating ra : savedRatings) {
					DoctorRatingDTO dto = new DoctorRatingDTO(r.getMedicine().getId(), r.getPharmacy().getId(),
							r.getMedicine().getName(), ra.getRating());
					result.add(dto);
				}

				DoctorRatingDTO dto = new DoctorRatingDTO(r.getMedicine().getId(), r.getPharmacy().getId(),
						r.getMedicine().getName(), 0);

				if (!result.isEmpty()) {
					boolean contains = false;
					for (DoctorRatingDTO d : result) {
						if (d.getId() == dto.getId()) {
							contains = true;
						}
					}
					if (!contains) {
						result.add(dto);
					}
				} else {
					result.add(dto);
				}

			}
		}
		return result;
	}

	public List<Rating> getRatingsByMedicine(long medId, long patientId) throws UserDoesNotExistException {

		List<Rating> allRatings = ratingService.getRatingsByPatientId(patientId);
		List<Rating> result = new ArrayList<Rating>();

		Medicine medicine = medicineRepository.findById(medId).get();
		Set<Rating> medRatings = medicine.getRatings();

		for (Rating r : medRatings) {
			for (Rating r1 : allRatings) {
				if (r.getId() == r1.getId()) {
					result.add(r1);
				}
			}
		}
		return result;
	}

	@Override
	public Rating saveMedicineRating(DoctorRatingDTO dto, long id) {
		Medicine medicine = medicineRepository.findById(dto.getId()).get();

		Rating r = new Rating();
		r.setRating(dto.getRating());
		try {
			r.setPatient(patientService.getPatientById(id));
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Rating saved = ratingService.saveRating(r);

		medicine.getRatings().add(saved);
		medicineRepository.save(medicine);

		return saved;
	}

	@Override
	public List<DoctorRatingDTO> getPharmaciesFromReservations(long id) throws PharmacyNotFoundException {
		List<DoctorRatingDTO> res = new ArrayList<DoctorRatingDTO>();
		if (!getMedicineForRating(id).isEmpty()) {

			List<DoctorRatingDTO> medicine = getMedicineForRating(id);
			

			for (DoctorRatingDTO dto : medicine) {
				Pharmacy pharmacy = pharmacyService.getPharmacyById(dto.getPharmacyId());
				DoctorRatingDTO newPharmacy = new DoctorRatingDTO(dto.getPharmacyId(), dto.getPharmacyId(),
						pharmacy.getName(), 0);

				if (!res.isEmpty()) {
					boolean contains = false;
					for (DoctorRatingDTO d : res) {
						if (d.getId() == newPharmacy.getId()) {
							contains = true;
						}
					}
					if (!contains) {
						res.add(newPharmacy);
					}
				} else {
					res.add(newPharmacy);
				}
			}
		}
		return res;
	}

}
