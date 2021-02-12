package isa.tim28.pharmacies.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.ComplaintDTO;
import isa.tim28.pharmacies.dtos.DermatologistForComplaintDTO;
import isa.tim28.pharmacies.dtos.DoctorRatingDTO;
import isa.tim28.pharmacies.dtos.NewPharmacistDTO;
import isa.tim28.pharmacies.dtos.PharmacistAppointmentDTO;
import isa.tim28.pharmacies.dtos.PharmacistDTO;
import isa.tim28.pharmacies.dtos.PharmacistProfileDTO;

import isa.tim28.pharmacies.dtos.ShowCounselingDTO;

import isa.tim28.pharmacies.exceptions.CreatePharmacistException;

import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadNewEmailException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.CreatePharmacistException;
import isa.tim28.pharmacies.exceptions.InvalidComplaintException;
import isa.tim28.pharmacies.exceptions.InvalidDeleteUserAttemptException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.mapper.PharmacistMapper;
import isa.tim28.pharmacies.model.DailyEngagement;
import isa.tim28.pharmacies.model.EngagementInPharmacy;

import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Patient;

import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.PharmacistAppointment;
import isa.tim28.pharmacies.model.PharmacistComplaint;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Rating;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.PharmacistAppointmentRepository;
import isa.tim28.pharmacies.repository.PharmacistComplaintRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.PharmacistRepository;
import isa.tim28.pharmacies.repository.PharmacyRepository;
import isa.tim28.pharmacies.repository.RatingRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.IPatientService;
import isa.tim28.pharmacies.service.interfaces.IPharmacistAppointmentService;
import isa.tim28.pharmacies.service.interfaces.IPharmacistService;
import isa.tim28.pharmacies.service.interfaces.IRatingService;


@Service
public class PharmacistService implements IPharmacistService {

	private PharmacistRepository pharmacistRepository;
	private UserRepository userRepository;
	private PharmacistMapper pharmacistMapper;
	private IPharmacistAppointmentService appointmentService;
	private PharmacistAppointmentRepository appointmentRepository;
	private PharmacistComplaintRepository pharmacistComplaintRepository;
	private IRatingService ratingService;
	private PatientRepository patientRepository;
	private PharmacyRepository pharmacyRepository;
	private RatingRepository ratingRepository;

	
	@Autowired
	public PharmacistService(PharmacistRepository pharmacistRepository, UserRepository userRepository,
			PharmacistMapper pharmacistMapper, IPharmacistAppointmentService appointmentService,  
			PharmacistAppointmentRepository appointmentRepository, PharmacistComplaintRepository pharmacistComplaintRepository,
			IRatingService ratingService, PatientRepository patientRepository,PharmacyRepository pharmacyRepository) {
		super();
		this.pharmacistRepository = pharmacistRepository;
		this.userRepository = userRepository;
		this.pharmacistMapper = pharmacistMapper;
		this.appointmentService = appointmentService;
		this.appointmentRepository = appointmentRepository;
		this.pharmacistComplaintRepository = pharmacistComplaintRepository;
		this.ratingService = ratingService;
		this.patientRepository = patientRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public boolean createComplaint(Patient patient, ComplaintDTO dto) throws InvalidComplaintException, UserDoesNotExistException {
		if(!dto.isTextValid()) 
			throw new InvalidComplaintException("Complaint must have between 2 and 3000 characters. Try again.");
		Optional<Pharmacist> pahrmacistOp = pharmacistRepository.findById(dto.getId());
		if(pahrmacistOp.isEmpty())
			throw new UserDoesNotExistException("You attempted to write complaint to a pharmacist that doesn't exist!");
		Pharmacist pharmacist = pahrmacistOp.get();
		
	    boolean  canComplain = false;
		Set<PharmacistAppointment> pharmAppointments = appointmentRepository.findAllByPharmacist_Id(pharmacist.getId());
		for(PharmacistAppointment ap : pharmAppointments) {
			
				try {	if(ap.getPatient().getId() == patient.getId() && ap.isPatientWasPresent()==true && ap.isDone()==true) {
				canComplain = true;
				break;
				}
		   	} catch(NullPointerException e) {
		   		canComplain = false;
		   		break;
		}
		
		}
		if(canComplain == true) {
			PharmacistComplaint complaint = new PharmacistComplaint();
			complaint.setPharmacist(pharmacist);
			complaint.setPatient(patient);
			complaint.setReply("");
			complaint.setText(dto.getText());
			pharmacistComplaintRepository.save(complaint);
			return true;
		}else {
			return false;
		}
		
	}
	@Override
	public List<DermatologistForComplaintDTO> getAllPharmacists(){
		List<DermatologistForComplaintDTO> result = new ArrayList<DermatologistForComplaintDTO>();
		List<Pharmacist> pharmacists = pharmacistRepository.findAll();
		for(Pharmacist p : pharmacists) {
			DermatologistForComplaintDTO dto = new DermatologistForComplaintDTO(p.getId(), p.getUser().getName(), p.getUser().getSurname());
			result.add(dto);
		}
		return result;
	}
	@Override
	public Pharmacist getPharmacistById(long id) throws UserDoesNotExistException {
		if (pharmacistRepository.findById(id).isEmpty())
			throw new UserDoesNotExistException("Pharmacist does not exist!");
		else
			return pharmacistRepository.findById(id).get();
	}

	@Override
	public User getUserPart(long id) throws UserDoesNotExistException {
		User user = userRepository.findOneById(id);
		if (user == null)
			throw new UserDoesNotExistException("Pharmacist does not exist!");
		else
			return user;
	}

	@Override
	public User updatePharmacist(PharmacistProfileDTO newUser, long id)
			throws BadNameException, BadSurnameException, BadNewEmailException, UserDoesNotExistException {
		User user;
		user = getUserPart(id);
		user.setName(newUser.getName());
		user.setSurname(newUser.getSurname());
		user.setEmail(newUser.getEmail());

		if (!user.isNameValid())
			throw new BadNameException("Bad name. Try again.");
		if (!user.isSurnameValid())
			throw new BadSurnameException("Bad surname. Try again.");
		if (!user.isEmailValid())
			throw new BadNewEmailException("Bad email. Try again.");

		userRepository.save(user);
		return user;
	}

	@Override
	public boolean checkOldPassword(long id, String oldPassword)
			throws UserDoesNotExistException, PasswordIncorrectException {
		User user = getUserPart(id);

		if (!user.getPassword().equals(oldPassword))
			throw new PasswordIncorrectException("Old password is incorrect.");

		return true;
	}

	@Override
	public void changePassword(long id, String newPassword) throws UserDoesNotExistException {
		User user = getUserPart(id);
		user.setPassword(newPassword);

		userRepository.save(user);
	}

	@Override
	public Set<Pharmacist> findAllByPharmacyId(long pharmacyId) {
		Set<Pharmacist> ret = new HashSet<Pharmacist>();
		for (Pharmacist p : pharmacistRepository.findAll())
			if (p.getEngegementInPharmacy() != null && p.getEngegementInPharmacy().getPharmacy().getId() == pharmacyId)
				ret.add(p);
		return ret;
	}

	@Override
	public Pharmacist getPharmacistFromUser(long userId) {
		return pharmacistRepository.findOneByUser_Id(userId);
	}

	public Set<PharmacistDTO> findAllByPharmacyAdmin(PharmacyAdmin admin) {
		Set<PharmacistDTO> dtos = new HashSet<PharmacistDTO>();
		Set<Pharmacist> pharmacists = pharmacistRepository.findAll().stream()
				.filter(p -> p.getEngegementInPharmacy().getPharmacy().getId() == admin.getPharmacy().getId())
				.collect(Collectors.toSet());
		for (Pharmacist pharmacist : pharmacists)
			dtos.add(pharmacistMapper.pharmacistToPharmacistDTO(pharmacist));
		return dtos;
	}

	@Override
	public Set<PharmacistDTO> findAll() {
		Set<PharmacistDTO> dtos = new HashSet<PharmacistDTO>();
		List<Pharmacist> pharmacists = pharmacistRepository.findAll();
		for (Pharmacist pharmacist : pharmacists)
			dtos.add(pharmacistMapper.pharmacistToPharmacistDTO(pharmacist));
		return dtos;
	}

	@Override
	public void deleteByPharmacyAdmin(long pharmacistId, PharmacyAdmin admin)
			throws UserDoesNotExistException, InvalidDeleteUserAttemptException {
		Optional<Pharmacist> pharmacistOptional = pharmacistRepository.findById(pharmacistId);
		if (pharmacistOptional.isEmpty())
			throw new UserDoesNotExistException("You attempted to delete a pharmacist that does not exist!");
		Pharmacist pharmacist = pharmacistOptional.get();
		if (pharmacist.getEngegementInPharmacy().getPharmacy().getId() != admin.getPharmacy().getId())
			throw new InvalidDeleteUserAttemptException(
					"It is not allowed to delete a pharmacist from another pharmacy!");
		if (appointmentService.pharmacistHasIncomingAppointments(pharmacist))
			throw new InvalidDeleteUserAttemptException("Pharmacist has incoming appointments!");
		pharmacistRepository.delete(pharmacist);
	}

	@Override
	public Set<PharmacistDTO> search(String fullName) {
		return search(findAll(), fullName);
	}

	@Override
	public Set<PharmacistDTO> searchByPharmacyAdmin(String fullName, PharmacyAdmin admin) {
		return search(findAllByPharmacyAdmin(admin), fullName);
	}

	private String formatFullName(String fullName) {
		return fullName.trim().replaceAll(" +", " ").toLowerCase();
	}

	private Set<PharmacistDTO> search(Set<PharmacistDTO> pharmacists, String fullName) {
		Set<PharmacistDTO> ret = new HashSet<PharmacistDTO>();
		if (fullName.length() == 0)
			return pharmacists;
		String[] tokens = formatFullName(fullName).split(" ");
		for (PharmacistDTO p : pharmacists) {
			boolean hasAllTokens = true;
			for (String token : tokens)
				if (!formatFullName(p.getName() + " " + p.getSurname()).contains(token)) {
					hasAllTokens = false;
					break;
				}
			if (hasAllTokens)
				ret.add(p);
		}
		return ret;
	}

	@Override
	public void create(NewPharmacistDTO dto, Pharmacy pharmacy) throws CreatePharmacistException {

		User user = new User();
		user.setName(dto.getName());
		user.setSurname(dto.getSurname());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setRole(Role.PHARMACIST);

		if (!user.isNameValid())
			throw new CreatePharmacistException("Name must have between 2 and 30 characters. Try again.");
		if (!user.isSurnameValid())
			throw new CreatePharmacistException("Surname must have between 2 and 30 characters. Try again.");
		if (!user.isEmailValid())
			throw new CreatePharmacistException(
					"Email must have between 3 and 30 characters and contain @. Try again.");
		if (!user.isPasswordValid())
			throw new CreatePharmacistException("Password must have between 4 and 30 characters. Try again.");
		
		if(dto.getMondayStart().isAfter(dto.getMondayEnd()))
			throw new CreatePharmacistException("Start time must be before end time!");
		if(dto.getTuesdayStart().isAfter(dto.getTuesdayEnd()))
			throw new CreatePharmacistException("Start time must be before end time!");
		if(dto.getWednesdayStart().isAfter(dto.getWednesdayEnd()))
			throw new CreatePharmacistException("Start time must be before end time!");
		if(dto.getThursdayStart().isAfter(dto.getThursdayEnd()))
			throw new CreatePharmacistException("Start time must be before end time!");
		if(dto.getFridayStart().isAfter(dto.getFridayEnd()))
			throw new CreatePharmacistException("Start time must be before end time!");
		if(dto.getSaturdayStart().isAfter(dto.getSaturdayEnd()))
			throw new CreatePharmacistException("Start time must be before end time!");
		if(dto.getSundayStart().isAfter(dto.getSundayEnd()))
			throw new CreatePharmacistException("Start time must be before end time!");

		EngagementInPharmacy engagement = new EngagementInPharmacy();
		Set<DailyEngagement> dailyEngagements = new HashSet<DailyEngagement>();
		if (dto.isMonday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.MONDAY, dto.getMondayStart().toLocalTime(),
					dto.getMondayEnd().toLocalTime()));
		if (dto.isTuesday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.TUESDAY, dto.getTuesdayStart().toLocalTime(),
					dto.getTuesdayEnd().toLocalTime()));
		if (dto.isWednesday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.WEDNESDAY, dto.getWednesdayStart().toLocalTime(),
					dto.getWednesdayEnd().toLocalTime()));
		if (dto.isThursday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.THURSDAY, dto.getThursdayStart().toLocalTime(),
					dto.getThursdayEnd().toLocalTime()));
		if (dto.isFriday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.FRIDAY, dto.getFridayStart().toLocalTime(),
					dto.getFridayEnd().toLocalTime()));
		if (dto.isSaturday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.SATURDAY, dto.getSaturdayStart().toLocalTime(),
					dto.getSaturdayEnd().toLocalTime()));
		if (dto.isSunday())
			dailyEngagements.add(new DailyEngagement(DayOfWeek.SUNDAY, dto.getSundayStart().toLocalTime(),
					dto.getSundayEnd().toLocalTime()));
		engagement.setDailyEngagements(dailyEngagements);
		engagement.setPharmacy(pharmacy);

		Pharmacist pharmacist = new Pharmacist();
		pharmacist.setUser(user);
		pharmacist.setEngegementInPharmacy(engagement);
		pharmacistRepository.save(pharmacist);
	}

	@Override
	public List<PharmacistDTO> getAvailablePharmacistsByPharmacy(PharmacistAppointmentDTO dto) {
		Set<Pharmacist> all = findAllByPharmacyId(dto.getPharmacistId());
		List<PharmacistDTO> res = new ArrayList<PharmacistDTO>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime date = LocalDateTime.parse(dto.getDate(), formatter);

		for (Pharmacist pharm : all) {
			double sumOfRatings = 0;
			for (Rating r : pharm.getRatings())
				sumOfRatings += r.getRating();

			if (appointmentService.isPharmacistAvailable(pharm, date)
					&& appointmentService.isPharmacistInPharmacy(pharm, date)) {
				res.add(new PharmacistDTO(pharm.getId(), pharm.getUser().getName(), pharm.getUser().getSurname(),
						pharm.getRatings().size() > 0 ? sumOfRatings / pharm.getRatings().size() : 0, ""));
			}
		}
		return res;

	}

	@Override
	public List<Rating> getRatingsByPharmacist(long pharmId, long patientId) throws UserDoesNotExistException {

		List<Rating> allRatings = ratingRepository.findByPatient_Id(patientId);
		List<Rating> result = new ArrayList<Rating>();

		Pharmacist pharmacist = getPharmacistById(pharmId);
		Set<Rating> pharmRatings = pharmacist.getRatings();

		for (Rating r : pharmRatings) {
			for (Rating r1 : allRatings) {
				if (r.getId() == r1.getId()) {
					result.add(r1);
				}
			}
		}
		return result;
	}
	
	

	@Override
	public List<DoctorRatingDTO> getAllDoctorsForRating(long id) {
		List<DoctorRatingDTO> result = new ArrayList<DoctorRatingDTO>();
		List<ShowCounselingDTO> past = appointmentService.getAllIncomingCounsellings(id, true);
		for (ShowCounselingDTO s : past) {

			List<Rating> savedRatings = new ArrayList<Rating>();
			try {
				savedRatings = getRatingsByPharmacist(s.getDoctorId(), patientRepository.findOneByUser_Id(id).getId());
			} catch (UserDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Rating r : savedRatings) {
				DoctorRatingDTO dto = new DoctorRatingDTO(s.getDoctorId(), s.getPharmacyId(),s.getPharmacistName(), r.getRating());
				result.add(dto);
			}

			DoctorRatingDTO doctor = new DoctorRatingDTO(s.getDoctorId(),s.getPharmacyId(), s.getPharmacistName(), 0);

			if (!result.isEmpty()) {
				boolean contains = false;
				for (DoctorRatingDTO d : result) {
					if (d.getId() == doctor.getId()) {
						contains = true;
					}
				}
				if (!contains) {
					result.add(doctor);
				}
			} else {
				result.add(doctor);
			}
		}
		return result;
	}
	
	@Override
	public Rating savePharmacistRating(DoctorRatingDTO dto, long id) {
		Pharmacist pharmacist = pharmacistRepository.findById(dto.getId()).get();
		
		Rating r = new Rating();
		r.setRating(dto.getRating());
		r.setPatient(patientRepository.findOneByUser_Id(id));
		Rating saved = ratingService.saveRating(r);
		
		pharmacist.getRatings().add(saved);
		pharmacistRepository.save(pharmacist);
		
		return saved;
	}
	
	@Override
	public List<DoctorRatingDTO> getPharmaciesFromReservations(long id) throws PharmacyNotFoundException {
		List<DoctorRatingDTO> res = new ArrayList<DoctorRatingDTO>();
		if (!getAllDoctorsForRating(id).isEmpty()) {

			List<DoctorRatingDTO> medicine = getAllDoctorsForRating(id);
			

			for (DoctorRatingDTO dto : medicine) {
				Pharmacy pharmacy = pharmacyRepository.findById(dto.getPharmacyId()).get();
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
	
	@Override
	public Pharmacist getPharmacistByPharmacistId(long id) {
		Pharmacist pharmacist  = pharmacistRepository.findById(id).get();
		return pharmacist;
	}

}
