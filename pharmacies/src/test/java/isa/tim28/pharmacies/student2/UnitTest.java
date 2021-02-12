package isa.tim28.pharmacies.student2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import isa.tim28.pharmacies.dtos.MedicineInPharmacyDTO;
import isa.tim28.pharmacies.dtos.NewPharmacistDTO;
import isa.tim28.pharmacies.dtos.SearchMedicineDTO;
import isa.tim28.pharmacies.dtos.StatisticalDataDTO;
import isa.tim28.pharmacies.exceptions.CreatePharmacistException;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.PharmacyDataInvalidException;
import isa.tim28.pharmacies.mapper.PharmacistMapper;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.EngagementInPharmacy;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineConsumption;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Order;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.repository.DermatologistAppointmentRepository;
import isa.tim28.pharmacies.repository.DermatologistComplaintRepository;
import isa.tim28.pharmacies.repository.DermatologistRepository;
import isa.tim28.pharmacies.repository.EngagementInPharmacyRepository;
import isa.tim28.pharmacies.repository.MedicineConsumptionRepository;
import isa.tim28.pharmacies.repository.MedicineRepository;
import isa.tim28.pharmacies.repository.OrderRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.PharmacistAppointmentRepository;
import isa.tim28.pharmacies.repository.PharmacistComplaintRepository;
import isa.tim28.pharmacies.repository.PharmacistRepository;
import isa.tim28.pharmacies.repository.PharmacyRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.DermatologistService;
import isa.tim28.pharmacies.service.EmailService;
import isa.tim28.pharmacies.service.MedicineService;
import isa.tim28.pharmacies.service.OrderService;
import isa.tim28.pharmacies.service.PharmacistAppointmentService;
import isa.tim28.pharmacies.service.PharmacistService;
import isa.tim28.pharmacies.service.RatingService;
import isa.tim28.pharmacies.service.StatisticsService;
import isa.tim28.pharmacies.service.interfaces.IMedicineService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTest {
	
	private Pharmacy pharmacy = new Pharmacy();

	@Mock
	private PharmacistAppointmentRepository pharmAppRepositoryMock;
	@Mock
	private DermatologistAppointmentRepository dermAppRepositoryMock;
	@Mock
	private MedicineConsumptionRepository medConsRepositoryMock;
	@Mock
	private PharmacistRepository pharmacistRepositoryMock;
	@Mock
	private UserRepository userRepositoryMock;
	@Mock
	private PharmacistMapper pharmacistMapperMock;
	@Mock
	private PharmacistAppointmentService appointmentServiceMock;
	@Mock
	private PharmacistAppointmentRepository appointmentRepositoryMock;
	@Mock
	private PharmacistComplaintRepository pharmacistComplaintRepositoryMock;
	@Mock
	private RatingService ratingServiceMock;
	@Mock
	private PatientRepository patientRepositoryMock;
	@Mock
	private PharmacyRepository pharmacyRepositoryMock;
	@Mock
	private DermatologistRepository dermatologistRepositoryMock;
	@Mock
	private EngagementInPharmacyRepository engagementRepositoryMock;
	@Mock
	private DermatologistComplaintRepository dermatologistComplaintRepositoryMock;
	@Mock
	private MedicineRepository medicineRepositoryMock;
	@Mock
	private OrderRepository orderRepositoryMock;
	@Mock
	private IMedicineService medicineServiceMock;
	@Mock
	private IPharmacyService pharmacyServiceMock;
	@Mock
	private EmailService emailServiceMock;
	
	@InjectMocks
	private StatisticsService statisticsService;
	@InjectMocks
	private PharmacistService pharmacistService;
	@InjectMocks
	private DermatologistService dermatologistService;
	@InjectMocks
	private MedicineService medicineService;
	@InjectMocks
	private OrderService orderService;
	
	@Test
	public void CalculateMedicineConsumptionByQuarterTest() throws ForbiddenOperationException {
		pharmacy.setId(1);
		List<MedicineConsumption> medicines = new ArrayList<MedicineConsumption>();
		LocalDate date1 = LocalDate.of(2020, 6, 14);
		LocalDate date2 = LocalDate.of(2020, 10, 14);
		medicines.add(new MedicineConsumption(1, new Medicine(), pharmacy, 5, date1));
		medicines.add(new MedicineConsumption(2, new Medicine(), pharmacy, 5, date1));
		medicines.add(new MedicineConsumption(3, new Medicine(), pharmacy, 5, date2));
		when(medConsRepositoryMock.findAll()).thenReturn(medicines);
		
		List<StatisticalDataDTO> res = statisticsService.getMedicineConsumptionByQuarter(pharmacy);
		
		assertThat(res).hasSize(4);
		verify(medConsRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(medConsRepositoryMock);
	}
	
	@Test(expected = CreatePharmacistException.class)
	@Transactional
	public void InvalidWorkingHoursForNewPharmacistTest() throws Throwable {
		NewPharmacistDTO newPharm = new NewPharmacistDTO();
		newPharm.setName("Pera");
		newPharm.setSurname("Peric");
		newPharm.setEmail("email@email.com");
		newPharm.setPassword("pera");
		newPharm.setMonday(false);
		newPharm.setTuesday(false);
		newPharm.setWednesday(true);
		newPharm.setThursday(true);
		newPharm.setFriday(false);
		newPharm.setSaturday(true);
		newPharm.setSunday(true);
		LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
		LocalDateTime validEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 0));
		LocalDateTime invalidEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0));
		newPharm.setMondayStart(startTime);
		newPharm.setTuesdayStart(startTime);
		newPharm.setWednesdayStart(startTime);
		newPharm.setThursdayStart(startTime);
		newPharm.setFridayStart(startTime);
		newPharm.setSaturdayStart(startTime);
		newPharm.setSundayStart(startTime);
		newPharm.setMondayEnd(validEndTime);
		newPharm.setTuesdayEnd(validEndTime);
		newPharm.setWednesdayEnd(invalidEndTime);
		newPharm.setThursdayEnd(validEndTime);
		newPharm.setFridayEnd(validEndTime);
		newPharm.setSaturdayEnd(validEndTime);
		newPharm.setSundayEnd(validEndTime);
		
		pharmacistService.create(newPharm, new Pharmacy());
	}
	
	@Test
	public void searchDermatologistsTest() throws PharmacyDataInvalidException {
		Medicine m1 = new Medicine();
		m1.setId(1);
		m1.setName("Lek1");
		m1.setCode("C123");
		m1.setManufacturer("man1");
		
		Medicine m2 = new Medicine();
		m2.setId(1);
		m2.setName("Lek2");
		m2.setCode("C124");
		m2.setManufacturer("man1");
		
		Medicine m3 = new Medicine();
		m3.setId(1);
		m3.setName("Lek3");
		m3.setCode("C125");
		m3.setManufacturer("man2");
		
		List<Medicine> meds = new ArrayList<Medicine>();
		meds.add(m1);
		meds.add(m2);
		meds.add(m3);
		
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(1);
		Set<MedicineQuantity> medicines = new HashSet<MedicineQuantity>();
		medicines.add(new MedicineQuantity(m1, 1));
		medicines.add(new MedicineQuantity(m2, 2));
		medicines.add(new MedicineQuantity(m3, 3));
		pharmacy.setMedicines(medicines);
		
		SearchMedicineDTO searchCriteria = new SearchMedicineDTO("C", "lek", "man1");
		when(medicineRepositoryMock.findAll()).thenReturn(meds);
		
		Set<MedicineInPharmacyDTO> res = medicineService.search(searchCriteria, pharmacy);
		
		assertThat(res).hasSize(2);
		assertThat(res).allMatch(m -> m.getName().toLowerCase().contains("lek"));
		assertThat(res).allMatch(m -> m.getCode().toLowerCase().contains("c"));
		assertThat(res).allMatch(m -> m.getManufacturer().toLowerCase().contains("man1"));
	}
	
	@Test
	public void getAllDermatologistsWorkingInPharmacy() {
		Dermatologist d1 = new Dermatologist();
		d1.setId(1);
		Dermatologist d2 = new Dermatologist();
		d2.setId(1);
		Dermatologist d3 = new Dermatologist();
		d3.setId(1);
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(1);
		Pharmacy pharmacy2 = new Pharmacy();
		pharmacy.setId(2);
		EngagementInPharmacy eng1 = new EngagementInPharmacy();
		eng1.setPharmacy(pharmacy);
		EngagementInPharmacy eng2 = new EngagementInPharmacy();
		eng2.setPharmacy(pharmacy2);
		Set<EngagementInPharmacy> engs1 = new HashSet<EngagementInPharmacy>();
		engs1.add(eng1);
		Set<EngagementInPharmacy> engs2 = new HashSet<EngagementInPharmacy>();
		engs2.add(eng2);
		engs2.add(eng1);
		d1.setEngegementInPharmacies(engs1);
		d2.setEngegementInPharmacies(new HashSet<EngagementInPharmacy>());
		d3.setEngegementInPharmacies(engs2);
		List<Dermatologist> allDermatologists = new ArrayList<Dermatologist>();
		allDermatologists.add(d1);
		allDermatologists.add(d2);
		allDermatologists.add(d3);
		
		when(dermatologistRepositoryMock.findAll()).thenReturn(allDermatologists);
		
		Set<Dermatologist> res = dermatologistService.findAllByPharmacyId(pharmacy.getId());
		
		assertThat(res).hasSize(2);
		assertThat(res).allMatch(d -> d.hasEngagementInPharmacy(pharmacy));
	}
	
	@Test
	public void checkIfPharmacyHasActiveOrdersForMedicineTest() {
		Medicine m1 = new Medicine();
		m1.setId(1);
		m1.setName("Lek1");
		m1.setCode("C123");
		m1.setManufacturer("man1");
		
		Medicine m2 = new Medicine();
		m2.setId(2);
		m2.setName("Lek2");
		m2.setCode("C124");
		m2.setManufacturer("man1");
		
		Medicine m3 = new Medicine();
		m3.setId(3);
		m3.setName("Lek3");
		m3.setCode("C125");
		m3.setManufacturer("man2");
		
		List<Medicine> meds = new ArrayList<Medicine>();
		meds.add(m1);
		meds.add(m2);
		meds.add(m3);
		
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(1);
		Set<MedicineQuantity> medicines = new HashSet<MedicineQuantity>();
		medicines.add(new MedicineQuantity(m1, 1));
		medicines.add(new MedicineQuantity(m2, 2));
		medicines.add(new MedicineQuantity(m3, 3));
		pharmacy.setMedicines(medicines);
		
		PharmacyAdmin admin = new PharmacyAdmin();
		admin.setId(1);
		admin.setPharmacy(pharmacy);
		
		Order order = new Order();
		order.setDeadline(LocalDateTime.now().plusDays(5));
		order.setId(1);
		order.setAdminCreator(admin);
		Set<MedicineQuantity> medQuantities = new HashSet<MedicineQuantity>();
		medQuantities.add(new MedicineQuantity(m1, 5));
		order.setMedicineQuantities(medQuantities);
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);
		
		when(orderRepositoryMock.findAll()).thenReturn(orders);
		
		boolean res1 = orderService.pharmacyHasActiveOrdersForMedicine(pharmacy, m1);
		
		assertThat(res1).isEqualTo(true);
		
		boolean res2 = orderService.pharmacyHasActiveOrdersForMedicine(pharmacy, m2);
		
		assertThat(res2).isEqualTo(false);
	}
}
