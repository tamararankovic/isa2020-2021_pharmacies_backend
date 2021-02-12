package isa.tim28.pharmacies.student3;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.dtos.MyPatientDTO;
import isa.tim28.pharmacies.dtos.PharmAppByWeekDTO;
import isa.tim28.pharmacies.dtos.PharmAppDTO;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.DermatologistAppointmentRepository;
import isa.tim28.pharmacies.repository.DermatologistLeaveRequestRepository;
import isa.tim28.pharmacies.repository.DermatologistReportRepository;
import isa.tim28.pharmacies.repository.DermatologistRepository;
import isa.tim28.pharmacies.repository.LoyaltyPointsRepository;
import isa.tim28.pharmacies.repository.MedicineMissingNotificationRepository;
import isa.tim28.pharmacies.repository.MedicineQuantityRepository;
import isa.tim28.pharmacies.repository.MedicineRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.PharmacistAppointmentRepository;
import isa.tim28.pharmacies.service.DermatologistAppointmentService;
import isa.tim28.pharmacies.service.EmailService;
import isa.tim28.pharmacies.service.SystemAdminService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTests {

	@Mock
	private DermatologistAppointmentRepository appointmentRepository;
	
	@Mock
	private PatientRepository patientRepository;
	
	@Mock
	private MedicineRepository medicineRepository;
	
	@Mock
	private DermatologistReportRepository dermatologistReportRepository;
	
	@Mock
	private MedicineQuantityRepository medicineQuantityRepository;
	
	@Mock
	private MedicineMissingNotificationRepository medicineMissingNotificationRepository;
	
	@Mock
	private DermatologistLeaveRequestRepository dermatologistLeaveRequestRepository;
	
	@Mock
	private PharmacistAppointmentRepository pharmacistAppointmentRepository;
	
	@Mock
	private DermatologistRepository dermatologistRepository;
	
	@Mock
	private LoyaltyPointsRepository loyaltyPointsRepository;
	
	@Mock
	private SystemAdminService systemAdminService;
	
	@Mock
	private EmailService emailService;

	@Mock
	private DermatologistAppointment appointmentMock;
	
	
	@InjectMocks
	private DermatologistAppointmentService dermatologistAppointmentService;
	
	
	@Test
	public void testGetAppointmentById() {
		when(appointmentRepository.findById((long) 1)).thenReturn(Optional.of(appointmentMock));
		
		DermatologistAppointment dbAppointment = dermatologistAppointmentService.getAppointmentById((long) 1);
		
		assertEquals(appointmentMock, dbAppointment);
		
		verify(appointmentRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(appointmentRepository);
	}
	
	@Test
	public void testCheckAllergies() {
		Patient patient = new Patient();
		Medicine allergy = new Medicine();
		allergy.setId(1);
		Set<Medicine> allergies = new HashSet<Medicine>();
		allergies.add(allergy);
		patient.setAllergies(allergies);
		when(patientRepository.findById((long) 1)).thenReturn(Optional.of(patient));
		
		boolean isAllergic = dermatologistAppointmentService.checkAllergies(1, 1);
		boolean isAllergic2 = dermatologistAppointmentService.checkAllergies(1, 2);
		
		assertEquals(false, isAllergic);
		assertEquals(true, isAllergic2);
		
	}
	
	@Test
	public void testCheckIfMedicineIsAvailable() {
		Medicine medicine = new Medicine();
		medicine.setId(1);
		MedicineQuantity mq = new MedicineQuantity();
		mq.setMedicine(medicine);
		mq.setQuantity(5);
		Set<MedicineQuantity> medicineQuantities = new HashSet<MedicineQuantity>();
		medicineQuantities.add(mq);
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setMedicines(medicineQuantities);
		DermatologistAppointment da = new DermatologistAppointment();
		da.setPharmacy(pharmacy);
		when(appointmentRepository.findById((long) 1)).thenReturn(Optional.of(da));
		
		MedicineQuantityCheckDTO mqc = dermatologistAppointmentService.checkIfMedicineIsAvailable(1, 1);
		
		assertEquals(true, mqc.isAvailable());
	}
	
	@Test
	public void testGetAppointmentsByWeek() {
		PharmAppByWeekDTO dto = new PharmAppByWeekDTO(LocalDate.of(2021, 2, 10), LocalDate.of(2021, 2, 17));
		Dermatologist dermatologist = new Dermatologist();
		dermatologist.setId(1);
		Patient patient = new Patient();
		User user = new User();
		user.setName("Aaaaa");
		user.setSurname("Bbbbb");
		patient.setUser(user);
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(1);
		
		DermatologistAppointment da = new DermatologistAppointment();
		da.setStartDateTime(LocalDateTime.of(2021, 2, 11, 10, 0));
		da.setDurationInMinutes(30);
		da.setId(1);
		da.setPharmacy(pharmacy);
		da.setDone(false);
		da.setScheduled(true);
		da.setPatient(patient);
		
		//appointment koji ne upada u interval
		DermatologistAppointment da2 = new DermatologistAppointment();
		da2.setStartDateTime(LocalDateTime.of(2021, 3, 11, 10, 0));
		da2.setDurationInMinutes(30);
		da2.setId(2);
		da2.setPharmacy(pharmacy);
		da2.setDone(false);
		da2.setScheduled(true);
		da2.setPatient(patient);
		
		//appointment koji je gotov
		DermatologistAppointment da3 = new DermatologistAppointment();
		da3.setStartDateTime(LocalDateTime.of(2021, 2, 11, 10, 0));
		da3.setDurationInMinutes(30);
		da3.setId(3);
		da3.setPharmacy(pharmacy);
		da3.setDone(true);
		da3.setScheduled(true);
		da3.setPatient(patient);
		
		Set<DermatologistAppointment> dermAppointments = new HashSet<DermatologistAppointment>();
		dermAppointments.add(da);
		dermAppointments.add(da2);
		dermAppointments.add(da3);
		
		when(dermatologistRepository.findOneByUser_Id((long) 1)).thenReturn(dermatologist);
		when(appointmentRepository.findAllByDermatologist_Id((long) 1)).thenReturn(dermAppointments);
		
		List<PharmAppDTO> pa = dermatologistAppointmentService.getAppointmentsByWeek(dto, 1, 1);
		
		assertEquals(1, pa.size());
		assertEquals(1, pa.get(0).getAppointmentId());
	}
	
	@Test
	public void testPatientWasNotPresent() {
		DermatologistAppointment da = new DermatologistAppointment();
		da.setDone(false);
		da.setPatientWasPresent(true);
		
		Patient patient = new Patient();
		patient.setPenalties(0);
		da.setPatient(patient);
		
		when(appointmentRepository.findById((long) 1)).thenReturn(Optional.of(da));
		when(appointmentRepository.save(da)).thenReturn(da);
		when(patientRepository.save(patient)).thenReturn(patient);
		when(appointmentRepository.findById((long) 1)).thenReturn(Optional.of(da));
		when(patientRepository.findById((long) 1)).thenReturn(Optional.of(patient));
		
		dermatologistAppointmentService.patientWasNotPresent(1);
		DermatologistAppointment da2 = dermatologistAppointmentService.getAppointmentById((long) 1);
		Patient patient2 = patientRepository.findById((long) 1).get();
		
		assertEquals(true, da2.isDone());
		assertEquals(false, da2.isPatientWasPresent());
		assertEquals(1, patient2.getPenalties());
	}
	
	@Test
	public void testMyPatients() {
		Dermatologist dermatologist = new Dermatologist();
		dermatologist.setId(1);
		Patient patient = new Patient();
		patient.setId(1);
		User user = new User();
		user.setName("Aaaaa");
		user.setSurname("Bbbbb");
		patient.setUser(user);
		
		Patient patient2 = new Patient();
		patient2.setId(2);
		patient2.setUser(user);
		
		DermatologistAppointment da = new DermatologistAppointment();
		da.setStartDateTime(LocalDateTime.of(2021, 2, 5, 10, 0));
		da.setDurationInMinutes(30);
		da.setId(1);
		da.setDone(true);
		da.setScheduled(true);
		da.setPatient(patient);
		da.setPatientWasPresent(true);
		
		DermatologistAppointment da2 = new DermatologistAppointment();
		da2.setStartDateTime(LocalDateTime.of(2021, 2, 5, 10, 0));
		da2.setDurationInMinutes(30);
		da2.setId(2);
		da2.setDone(true);
		da2.setScheduled(true);
		da2.setPatient(patient2);
		da2.setPatientWasPresent(false);
		
		Set<DermatologistAppointment> dermAppointments = new HashSet<DermatologistAppointment>();
		dermAppointments.add(da);
		dermAppointments.add(da2);
		
		when(dermatologistRepository.findOneByUser_Id((long) 1)).thenReturn(dermatologist);
		when(appointmentRepository.findAllByDermatologist_Id((long) 1)).thenReturn(dermAppointments);
		
		List<MyPatientDTO> dtos = dermatologistAppointmentService.myPatients(1);
		
		assertEquals(1, dtos.size());
		assertEquals(1, dtos.get(0).getPatientId());
	}
}




