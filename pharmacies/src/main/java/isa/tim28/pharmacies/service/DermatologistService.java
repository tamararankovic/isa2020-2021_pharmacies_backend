package isa.tim28.pharmacies.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DermPharmacyDTO;
import isa.tim28.pharmacies.dtos.DermatologistDTO;
import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.dtos.DermatologistToEmployDTO;
import isa.tim28.pharmacies.dtos.DoctorRatingDTO;
import isa.tim28.pharmacies.dtos.NewDermatologistInPharmacyDTO;
import isa.tim28.pharmacies.exceptions.AddingDermatologistToPharmacyException;
import isa.tim28.pharmacies.dtos.PatientSearchDTO;
import isa.tim28.pharmacies.dtos.ShowCounselingDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.InvalidDeleteUserAttemptException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.mapper.DermatologistMapper;
import isa.tim28.pharmacies.model.DailyEngagement;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.EngagementInPharmacy;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Rating;
import isa.tim28.pharmacies.repository.EngagementInPharmacyRepository;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.DermatologistRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistAppointmentService;
import isa.tim28.pharmacies.service.interfaces.IDermatologistService;
import isa.tim28.pharmacies.service.interfaces.IRatingService;

@Service
public class DermatologistService implements IDermatologistService {

	private DermatologistRepository dermatologistRepository;
	private UserRepository userRepository;
	private DermatologistMapper dermatologistMapper;
	private IDermatologistAppointmentService appointmentService;
	private EngagementInPharmacyRepository engagementRepository;
	private PatientRepository patientRepository;
	private IRatingService ratingService;
	
	@Autowired
	public DermatologistService(DermatologistRepository dermatolgistRepository, UserRepository userRepository, IRatingService ratingService, DermatologistMapper dermatologistMapper, IDermatologistAppointmentService appointmentService, EngagementInPharmacyRepository engagementRepository, PatientRepository patientRepository) {
		super();
		this.dermatologistRepository = dermatolgistRepository;
		this.userRepository = userRepository;
		this.dermatologistMapper = dermatologistMapper;
		this.appointmentService = appointmentService;
		this.engagementRepository = engagementRepository;
		this.patientRepository = patientRepository;
		this.ratingService = ratingService;
	}
	
	@Override
	public Dermatologist getDermatologistById(long id) throws UserDoesNotExistException {
		if (dermatologistRepository.findById(id).isEmpty())
			throw new UserDoesNotExistException("Dermatologist does not exist!");
		else 
			return dermatologistRepository.findById(id).get();
	}

	@Override
	public User getUserPart(long id) throws UserDoesNotExistException {
		User user = userRepository.findOneById(id);
		if (user == null)
			throw new UserDoesNotExistException("Dermatologist does not exist!");
		else 
			return user;
	}

	@Override
	public User updateDermatologist(DermatologistProfileDTO newUser, long id) throws BadNameException, BadSurnameException, BadNewEmailException, UserDoesNotExistException {
		User user;
		user = getUserPart(id);
		user.setName(newUser.getName());
		user.setSurname(newUser.getSurname());
		user.setEmail(newUser.getEmail());
		System.out.println(newUser.getName());
		
		if(!user.isNameValid()) throw new BadNameException("Bad name. Try again.");
		if(!user.isSurnameValid()) throw new BadSurnameException("Bad surname. Try again.");
		if(!user.isEmailValid()) throw new BadNewEmailException("Bad email. Try again.");

		userRepository.save(user);
		return user;
	}

	@Override
	public boolean checkOldPassword(long id, String oldPassword) throws UserDoesNotExistException, PasswordIncorrectException {
		User user = getUserPart(id);
		
		if(!user.getPassword().equals(oldPassword)) throw new PasswordIncorrectException("Old password is incorrect.");
		
		return true;
	}

	@Override
	public void changePassword(long id, String newPassword) throws UserDoesNotExistException {
		User user = getUserPart(id);
		user.setPassword(newPassword);
		
		userRepository.save(user);
	}

	@Override
	public Set<Dermatologist> findAllByPharmacyId(long pharmacyId) {
		Set<Dermatologist> ret = new HashSet<Dermatologist>();
		for(Dermatologist d : dermatologistRepository.findAll())
			for (EngagementInPharmacy ep : d.getEngegementInPharmacies())
				if (ep.getPharmacy().getId() == pharmacyId)
					ret.add(d);
		return ret;
	}

	@Override
	public Set<DermatologistDTO> findAllByPharmacyAdmin(PharmacyAdmin admin) {
		Set<DermatologistDTO> dtos = new HashSet<DermatologistDTO>();
		List<Dermatologist> dermatologists = dermatologistRepository.findAll();
		for(Dermatologist dermatologist : dermatologists)
			if (dermatologist.hasEngagementInPharmacy(admin.getPharmacy()))
				dtos.add(dermatologistMapper.dermatologistToDermatologistDTO(dermatologist));
		return dtos;
	}

	@Override
	public Set<DermatologistDTO> findAll() {
		Set<DermatologistDTO> dtos = new HashSet<DermatologistDTO>();
		List<Dermatologist> dermatologists = dermatologistRepository.findAll();
		for(Dermatologist dermatologist : dermatologists)
			dtos.add(dermatologistMapper.dermatologistToDermatologistDTO(dermatologist));
		return dtos;
	}

	@Override
	public void deleteByPharmacyAdmin(long dermatologistId, PharmacyAdmin admin)
			throws UserDoesNotExistException, InvalidDeleteUserAttemptException {
		Optional<Dermatologist> dermatologistOptional = dermatologistRepository.findById(dermatologistId);
		if(dermatologistOptional.isEmpty())
			throw new UserDoesNotExistException("You attempted to delete a pharmacist that does not exist!");
		Dermatologist dermatologist = dermatologistOptional.get();
		if(!dermatologist.hasEngagementInPharmacy(admin.getPharmacy()))
			throw new InvalidDeleteUserAttemptException("It is not allowed to delete a dermatologist that does not work in your pharmacy!");
		if(appointmentService.dermatologistHasIncomingAppointmentsInPharmacy(dermatologist, admin.getPharmacy()))
			throw new InvalidDeleteUserAttemptException("Dermatologist has incoming appointments!");
		
		Set<Long> engagementIds = new HashSet<Long>();
		for(EngagementInPharmacy e : dermatologist.getEngegementInPharmacies())
			if(e.getPharmacy().getId() == admin.getPharmacy().getId())
				engagementIds.add(e.getId());
		dermatologist.getEngegementInPharmacies().removeIf(e -> e.getPharmacy().getId() == admin.getPharmacy().getId());
		dermatologistRepository.save(dermatologist);
		for (Long engid : engagementIds)
			engagementRepository.deleteById(engid);
		appointmentService.deleteUnscheduledAppointments(dermatologist);
	}

	@Override
	public Set<DermatologistDTO> search(String fullName) {
		return search(findAll(), fullName);
	}

	@Override
	public Set<DermatologistDTO> searchByPharmacyAdmin(String fullName, PharmacyAdmin admin) {
		return search(findAllByPharmacyAdmin(admin), fullName);
	}
	
	private String formatFullName(String fullName) {
		return fullName.trim().replaceAll(" +", " ").toLowerCase();
	}

	private Set<DermatologistDTO> search(Set<DermatologistDTO> dermatologists, String fullName) {
		Set<DermatologistDTO> ret = new HashSet<DermatologistDTO>();
		if(fullName.length() == 0) return dermatologists;
		String[] tokens = formatFullName(fullName).split(" ");
		for(DermatologistDTO d : dermatologists) {
			boolean hasAllTokens = true;
			for(String token : tokens)
				if(!formatFullName(d.getName() + " " + d.getSurname()).contains(token)) {
					hasAllTokens = false;
					break;
				}
			if (hasAllTokens)
				ret.add(d);
		}
		return ret;
	}

	@Override
	public Set<DermatologistToEmployDTO> findUnemployedByPharmacyAdmin(Pharmacy pharmacy) {
		Set<DermatologistToEmployDTO> ret = new HashSet<DermatologistToEmployDTO>();
		for(Dermatologist d : dermatologistRepository.findAll())
			if(!d.hasEngagementInPharmacy(pharmacy))
				ret.add(dermatologistMapper.dermatologistToDermatologistToEmployDTO(d));
		return ret;
	}

	@Override
	public void addToPharmacy(NewDermatologistInPharmacyDTO dto, Pharmacy pharmacy)
			throws AddingDermatologistToPharmacyException, UserDoesNotExistException {
		Optional<Dermatologist> dermatologistOptional = dermatologistRepository.findById(dto.getDermatologistId());
		if(dermatologistOptional.isEmpty())
			throw new UserDoesNotExistException("You attempted to add a dermatologist that does not exist!");
		Dermatologist dermatologist = dermatologistOptional.get();
		if(dermatologist.hasEngagementInPharmacy(pharmacy))
			throw new AddingDermatologistToPharmacyException("Dermatologist is already employed in the pharmacy!");
		for(EngagementInPharmacy engagement : dermatologist.getEngegementInPharmacies()) {
			for (DailyEngagement dailyEngagement : engagement.getDailyEngagements()) {
				if(dto.isMonday() && dailyEngagement.getDayOfWeek() == DayOfWeek.MONDAY) {
					if (dailyEngagement.isOverlappingWith(dto.getMondayStart().toLocalTime(), dto.getMondayEnd().toLocalTime())) {
						throw new AddingDermatologistToPharmacyException("Working hours overlapping with other pharmacies. Day: MONDAY");
					}
				}
				else if(dto.isTuesday() && dailyEngagement.getDayOfWeek() == DayOfWeek.TUESDAY) {
					if (dailyEngagement.isOverlappingWith(dto.getTuesdayStart().toLocalTime(), dto.getTuesdayEnd().toLocalTime())) {
						throw new AddingDermatologistToPharmacyException("Working hours overlapping with other pharmacies. Day: TUESDAY");
					}
				}
				else if(dto.isWednesday() && dailyEngagement.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
					if (dailyEngagement.isOverlappingWith(dto.getWednesdayStart().toLocalTime(), dto.getWednesdayEnd().toLocalTime())) {
						throw new AddingDermatologistToPharmacyException("Working hours overlapping with other pharmacies. Day: WEDNESDAY");
					}
				}
				else if(dto.isThursday() && dailyEngagement.getDayOfWeek() == DayOfWeek.THURSDAY) {
					if (dailyEngagement.isOverlappingWith(dto.getThursdayStart().toLocalTime(), dto.getThursdayEnd().toLocalTime())) {
						throw new AddingDermatologistToPharmacyException("Working hours overlapping with other pharmacies. Day: THURSDAY");
					}
				}
				else if(dto.isFriday() && dailyEngagement.getDayOfWeek() == DayOfWeek.FRIDAY) {
					if (dailyEngagement.isOverlappingWith(dto.getFridayStart().toLocalTime(), dto.getFridayEnd().toLocalTime())) {
						throw new AddingDermatologistToPharmacyException("Working hours overlapping with other pharmacies. Day: FRIDAY");
					}
				}
				else if(dto.isSaturday() && dailyEngagement.getDayOfWeek() == DayOfWeek.SATURDAY) {
					if (dailyEngagement.isOverlappingWith(dto.getSaturdayStart().toLocalTime(), dto.getSaturdayEnd().toLocalTime())) {
						throw new AddingDermatologistToPharmacyException("Working hours overlapping with other pharmacies. Day: SATURDAY");
					}
				}
				else if(dto.isSunday() && dailyEngagement.getDayOfWeek() == DayOfWeek.SUNDAY) {
					if (dailyEngagement.isOverlappingWith(dto.getSundayStart().toLocalTime(), dto.getSundayEnd().toLocalTime())) {
						throw new AddingDermatologistToPharmacyException("Working hours overlapping with other pharmacies. Day: SUNDAY");
					}
				}
			}
		}
		EngagementInPharmacy engagement = new EngagementInPharmacy();
		Set<DailyEngagement> dailyEngagements = new HashSet<DailyEngagement>();
		if(dto.isMonday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.MONDAY, dto.getMondayStart().toLocalTime(), dto.getMondayEnd().toLocalTime()));
		if(dto.isTuesday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.TUESDAY, dto.getTuesdayStart().toLocalTime(), dto.getTuesdayEnd().toLocalTime()));
		if(dto.isWednesday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.WEDNESDAY, dto.getWednesdayStart().toLocalTime(), dto.getWednesdayEnd().toLocalTime()));
		if(dto.isThursday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.THURSDAY, dto.getThursdayStart().toLocalTime(), dto.getThursdayEnd().toLocalTime()));
		if(dto.isFriday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.FRIDAY, dto.getFridayStart().toLocalTime(), dto.getFridayEnd().toLocalTime()));
		if(dto.isSaturday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.SATURDAY, dto.getSaturdayStart().toLocalTime(), dto.getSaturdayEnd().toLocalTime()));
		if(dto.isSunday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.SUNDAY, dto.getSundayStart().toLocalTime(), dto.getSundayEnd().toLocalTime()));
		engagement.setDailyEngagements(dailyEngagements);
		engagement.setPharmacy(pharmacy);
		dermatologist.getEngegementInPharmacies().add(engagement);
		dermatologistRepository.save(dermatologist);
	}

	public List<PatientSearchDTO> getAllPatientsByNameAndSurname(String name, String surname) {
		if (name.equals("") && surname.equals("")) return patientsToDtos(patientRepository.findAll());
		else return patientsToDtos(findAllPatientsWithCriteria(name, surname));
	}

	private List<PatientSearchDTO> patientsToDtos(List<Patient> patients) {
		List<PatientSearchDTO> dtos = new ArrayList<PatientSearchDTO>();
		for (Patient p : patients) {
			dtos.add(new PatientSearchDTO(p.getId(), p.getUser().getName(), p.getUser().getSurname()));
		}
		return dtos;
	}
	
	private List<Patient> findAllPatientsWithCriteria(String name, String surname) {
		List<Patient> ret = new ArrayList<Patient>();
		for(Patient p : patientRepository.findAll()) {
			if(p.getUser().getName().toLowerCase().contains(name.toLowerCase()) &&
					p.getUser().getSurname().toLowerCase().contains(surname.toLowerCase()))
				ret.add(p);
		}
		return ret;
	}
	
	@Override
	public Dermatologist save(Dermatologist dermatologist) {
		Dermatologist newDermatoligist = dermatologistRepository.save(dermatologist);
		return newDermatoligist;
	}

	@Override
	public List<DermPharmacyDTO> getAllPharmaciesByDermatologist(long userId) {
		try {
			List<DermPharmacyDTO> dtos = new ArrayList<DermPharmacyDTO>();
			Dermatologist derm = dermatologistRepository.findOneByUser_Id(userId);
			for(EngagementInPharmacy ep : derm.getEngegementInPharmacies()) {
				Pharmacy pharmacy = ep.getPharmacy();
				DermPharmacyDTO dto = new DermPharmacyDTO(pharmacy.getId(), pharmacy.getName());
				dtos.add(dto);
			}
			return dtos;
		} catch(Exception e) {
			return new ArrayList<DermPharmacyDTO>();
		}
	}

	@Override
	public void createPredefinedAppointment(long dermatologistId, LocalDateTime startDateTime, int durationInMinutes,
			long price, Pharmacy pharmacy) throws UserDoesNotExistException, ForbiddenOperationException {
		if (startDateTime.isBefore(LocalDateTime.now()))
			throw new ForbiddenOperationException("You can't create an examination that has time set in the past!");
		if (findAllByPharmacyId(pharmacy.getId()).stream().noneMatch(d -> d.getId() == dermatologistId))
			throw new ForbiddenOperationException("You can't create an examination for dermatologist that is not employed in your pharmacy!");
		if (price <= 0 || durationInMinutes <= 0)
			throw new ForbiddenOperationException("Duration and price must be greater than 0!");
		appointmentService.createPredefinedAppointment(getDermatologistById(dermatologistId), startDateTime, durationInMinutes, price, pharmacy);
	}
	
	
	@Override
	public List<DoctorRatingDTO> getAllDoctorsForRating(long id){
		List<DoctorRatingDTO> result = new ArrayList<DoctorRatingDTO>();
		List<ShowCounselingDTO> past = appointmentService.getAllIncomingAppointments(id, true);
		for(ShowCounselingDTO s : past) {
			
			List<Rating> savedRatings = new ArrayList<Rating>();
			try {
				savedRatings = getRatingsByDermatologist(s.getDoctorId(), patientRepository.findOneByUser_Id(id).getId());
			} catch (UserDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Rating r : savedRatings) {
				DoctorRatingDTO dto = new DoctorRatingDTO(s.getDoctorId(), s.getPharmacistName(), r.getRating());
				result.add(dto);
			}
			
			DoctorRatingDTO doctor = new DoctorRatingDTO(s.getDoctorId(),s.getPharmacistName(),0);
			
			if(!result.isEmpty()) {
				boolean contains = false;
				for(DoctorRatingDTO d : result) {
					if(d.getId() == doctor.getId()) {
						contains = true;
					}
				}
				if(!contains) {
					result.add(doctor);
				}
			}else {
				result.add(doctor);
			}
		}
		return result;
	}
	
	@Override
	public List<Rating> getRatingsByDermatologist(long dermId, long patientId) throws UserDoesNotExistException{
		
		List<Rating> allRatings = ratingService.getRatingsByPatientId(patientId);
		List<Rating> result = new ArrayList<Rating>();
		
		Dermatologist dermatologist = getDermatologistById(dermId);
		Set<Rating> pharmRatings = dermatologist.getRatings();
		
		for(Rating r : pharmRatings ) {
			for(Rating r1 : allRatings) {
				if(r.getId() == r1.getId()) {
					result.add(r1);
				}
			}
		}
		return result;
	}
	
	@Override
	public Rating saveDermatologistRating(DoctorRatingDTO dto, long id) {
		Dermatologist derm = dermatologistRepository.findById(dto.getId()).get();
		
		Rating r = new Rating();
		r.setRating(dto.getRating());
		r.setPatient(patientRepository.findOneByUser_Id(id));
		Rating saved = ratingService.saveRating(r);
		
		derm.getRatings().add(saved);
		dermatologistRepository.save(derm);
		
		return saved;
	}

}
