package isa.tim28.pharmacies.student1;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import isa.tim28.pharmacies.dtos.MedicineForPharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.PharmacyBasicInfoDTO;
import isa.tim28.pharmacies.exceptions.PharmacyDataInvalidException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.model.Rating;
import isa.tim28.pharmacies.model.Reservation;
import isa.tim28.pharmacies.model.SystemAdmin;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.PharmacistRepository;
import isa.tim28.pharmacies.repository.PharmacyRepository;
import isa.tim28.pharmacies.repository.RatingRepository;
import isa.tim28.pharmacies.repository.ReservationRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.PatientService;
import isa.tim28.pharmacies.service.PharmacistService;
import isa.tim28.pharmacies.service.PharmacyService;
import isa.tim28.pharmacies.service.RatingService;
import isa.tim28.pharmacies.service.ReservationService;
import isa.tim28.pharmacies.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTests {
	
	@Mock
	private PharmacistRepository pharmacistRepository;
	
	@Mock
	private PharmacyRepository pharmacyRepository;
	
	@Mock
	private PatientRepository patientRepository;
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RatingRepository ratingRepository;
	
	@Mock
	private ReservationRepository reservationRepository;
	
	@Mock
	private Pharmacist pharmacistMock;
	
	@Mock
	private Patient patientMock;
	@Mock
	private Reservation reservationMock;
	
	@InjectMocks
	private PharmacistService pharmacistService;
	
	@InjectMocks
	private RatingService ratingService;
	 
	@InjectMocks
	private PharmacyService pharmacyService;
	
	@InjectMocks
	private PatientService patientService;
	
	@InjectMocks
	private ReservationService reservationService;
	
	
	
	@Test
	public void testGetPharmacistById() {
		when(pharmacistRepository.findById((long) 1)).thenReturn(Optional.of(pharmacistMock));
		
		Pharmacist pharmacist = pharmacistService.getPharmacistByPharmacistId((long) 1);
		
		assertEquals(pharmacistMock, pharmacist);
		
		verify(pharmacistRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(pharmacistRepository);
        
	}
	@Test
	public void testGetRatingsByPatient() {
		
		
		Rating r = new Rating();
		Patient p = new Patient();
		p.setId(1);
		
		r.setId(1);
		r.setPatient(p);
		r.setRating(5);

		
		List<Rating> rat = new ArrayList<Rating>();
		rat.add(r);
		
		when(ratingRepository.findByPatient_Id((long) 1)).thenReturn(rat);
		List<Rating> ratings = ratingService.getRatingsByPatientId(1);
		
		assertEquals(rat.size(), ratings.size());
		
	}
	
	
	@Test
	public void testIsCancellable() {
		
		
		Reservation r = new Reservation();
		Reservation r1 = new Reservation();
		LocalDateTime today = LocalDateTime.now();
		r.setDueDate(today);
		r1.setDueDate(today.plusDays(3));
		
		boolean isCancellable = reservationService.isCancellable(r);
		boolean isCancellable1 = reservationService.isCancellable(r1);
		
		assertEquals(false, isCancellable);
		assertEquals(true, isCancellable1);
		
	}
	
	@Test
	public void testGetBasicInfo() throws PharmacyDataInvalidException, PharmacyNotFoundException {
		
		Pharmacy p = new Pharmacy();
		PharmacyAdmin a = new PharmacyAdmin();
		a.setPharmacy(p);
		p.setAddress("Stevana Mokranjca 14");
		p.setDescription("Neki opis");
		p.setId(1);
		
		Set<Rating> ratings = new HashSet<Rating>();
		
	
		Rating r = new Rating();
		r.setId(1);
		r.setRating(5);
		ratings.add(r);
		
		p.setRatings(ratings);
		a.setPharmacy(p);
		
		when(pharmacyRepository.findById((long) 1)).thenReturn(Optional.of(p));
		PharmacyBasicInfoDTO pbi = pharmacyService.getBasicInfo(a);
		
		assertEquals(pbi.getAddress(), p.getAddress() );
		
	}
	
	
	@Test
	public void testGetUserPart() throws UserDoesNotExistException{
		
		User u = new User();
		u.setId(1);
		u.setName("Anja");
		
		when(userRepository.findOneById((long) 1)).thenReturn(u);
		User user = patientService.getUserPart(u.getId());
		
		assertEquals(user.getName(), u.getName());
		
	}
	
	
	
}
