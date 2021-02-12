package isa.tim28.pharmacies.student4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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

import isa.tim28.pharmacies.dtos.AllComplaintsDTO;
import isa.tim28.pharmacies.exceptions.PharmacyDataInvalidException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.DermatologistComplaint;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Offer;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.PharmacistComplaint;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.DermatologistComplaintRepository;
import isa.tim28.pharmacies.repository.OfferRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.PharmacistComplaintRepository;
import isa.tim28.pharmacies.repository.PharmacyComplaintRepository;
import isa.tim28.pharmacies.repository.PharmacyRepository;
import isa.tim28.pharmacies.repository.SubscriptionRepository;
import isa.tim28.pharmacies.service.OfferService;
import isa.tim28.pharmacies.service.PatientService;
import isa.tim28.pharmacies.service.PharmacyService;
import isa.tim28.pharmacies.service.SubscriptionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTests {
	
	@Mock
	private OfferRepository offerRepository;
	
	@Mock
	private Offer offerMock;
	@Mock
	private PatientRepository patientRepository;
	@Mock
	private SubscriptionRepository subscriptionRepository;
	@Mock
	private Patient patientMock;
	@Mock
	private PharmacyComplaintRepository pharmacyComplaintRepository;
	@Mock
	private PharmacistComplaintRepository pharmacistComplaintRepository;
	@Mock
	private DermatologistComplaintRepository dermatologistComplaintRepository;
	@Mock
	private PharmacyRepository pharmacyRepository;
	
	@InjectMocks
	private OfferService offerService;
	
	@InjectMocks
	private SubscriptionService subscriptionService;
	
	@InjectMocks
	private PatientService patientService;
	
	@InjectMocks
	private PharmacyService pharmacyService;
	
	
	@Test
	public void testGetOfferById() {
		when(offerRepository.findById((long) 1)).thenReturn(Optional.of(offerMock));
		
		Offer offer = offerService.getOfferById((long) 1);
		
		assertEquals(offerMock, offer);
		
		verify(offerRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(offerRepository);
		
	}
	
	@Test
	public void testGetAllDermatologistComplaints() {
		Patient patient = new Patient();
		User user = new User();
		user.setName("Emina");
		user.setSurname("Turkovic");
		patient.setUser(user);
		
		Dermatologist dermatologist = new Dermatologist();
		User user3 = new User();
		user3.setName("Miljana");
		user3.setSurname("Djordjevic");
		dermatologist.setUser(user3);
		
		Dermatologist dermatologist2 = new Dermatologist();
		User user4 = new User();
		user4.setName("Tamara");
		user4.setSurname("Djordjevic");
		dermatologist2.setUser(user4);
		
		DermatologistComplaint dermatologistComplaint = new DermatologistComplaint();
		dermatologistComplaint.setId(1);
		dermatologistComplaint.setPatient(patient);
		dermatologistComplaint.setText("los dermatolog");
		dermatologistComplaint.setReply("");
		dermatologistComplaint.setDermatologist(dermatologist);
				
		DermatologistComplaint dermatologistComplaint2 = new DermatologistComplaint();
		dermatologistComplaint2.setId(2);
		dermatologistComplaint2.setPatient(patient);
		dermatologistComplaint2.setText("los dermatolog");
		dermatologistComplaint2.setReply("");
		dermatologistComplaint2.setDermatologist(dermatologist2);

		List<DermatologistComplaint> dermatologistComplaints= new ArrayList<DermatologistComplaint>();

		dermatologistComplaints.add(dermatologistComplaint);
		dermatologistComplaints.add(dermatologistComplaint2);
		
		when(dermatologistComplaintRepository.findAll()).thenReturn(dermatologistComplaints);
		
		List<AllComplaintsDTO> result = pharmacyService.getAllDermatologistComplaints();
		
		assertThat(result).hasSize(2);
		
	}
	@Test
	public void testGetAllPharmacistComplaints() {
		Patient patient = new Patient();
		User user = new User();
		user.setName("Emina");
		user.setSurname("Turkovic");
		patient.setUser(user);
		
		Pharmacist pharmacist = new Pharmacist();
		User user2 = new User();
		user2.setName("Anja");
		user2.setSurname("Meseldzic");
		pharmacist.setUser(user2);
		
		Pharmacist pharmacist2 = new Pharmacist();
		User user3 = new User();
		user3.setName("Anjaa");
		user3.setSurname("Meseldzic");
		pharmacist2.setUser(user3);
		
		PharmacistComplaint pharmacistComplaint = new PharmacistComplaint();
		pharmacistComplaint.setId(1);
		pharmacistComplaint.setPatient(patient);
		pharmacistComplaint.setText("los farmaceut");
		pharmacistComplaint.setReply("");
		pharmacistComplaint.setPharmacist(pharmacist);
		
		PharmacistComplaint pharmacistComplaint2 = new PharmacistComplaint();
		pharmacistComplaint2.setId(2);
		pharmacistComplaint2.setPatient(patient);
		pharmacistComplaint2.setText("los farmaceut");
		pharmacistComplaint2.setReply("");
		pharmacistComplaint2.setPharmacist(pharmacist2);
	
		List<PharmacistComplaint> pharmacistComplaints= new ArrayList<PharmacistComplaint>();

		pharmacistComplaints.add(pharmacistComplaint);
		pharmacistComplaints.add(pharmacistComplaint2);
		
		when(pharmacistComplaintRepository.findAll()).thenReturn(pharmacistComplaints);
		
		List<AllComplaintsDTO> result = pharmacyService.getAllPharmacistComplaints();
		
		assertThat(result).hasSize(2);
		
	}
	
	@Test
	public void testFindAllPharmaciesWithCriteria() throws PharmacyDataInvalidException {
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setName("Biopharm");
		pharmacy.setAddress("Zeleznicka 8.");	
		
		Pharmacy pharmacy2 = new Pharmacy();
		pharmacy2.setName("biopharm");
		pharmacy2.setAddress("Zeleznicka 8.");	
		
		List<Pharmacy> result = new ArrayList<Pharmacy>();
		result.add(pharmacy);
		result.add(pharmacy2);
		
		when(pharmacyRepository.findAll()).thenReturn(result);
		
		List<Pharmacy> result2 = pharmacyService.findAllPharmaciesWithCriteria("Biopharm", "Zeleznicka 8.");
		List<Pharmacy> result3 = pharmacyService.findAllPharmaciesWithCriteria("Biopharm", "Zeleznicka 8.");
		List<Pharmacy> result4 = pharmacyService.findAllPharmaciesWithCriteria("Krsenkovic", "Zeleznicka 8.");

		
		assertThat(result2).hasSize(2);
		assertThat(result3).hasSize(2);
		assertThat(result4).hasSize(0);
		
		
	}
	@Test
	public void findAllInStockByPharmacyId() throws PharmacyNotFoundException {
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(1);
		Medicine medicine1 = new Medicine();
		medicine1.setId(1);
		medicine1.setName("Andol");
		medicine1.setCode("AA");
		medicine1.setManufacturer("hemofarm");
		
		Medicine medicine2 = new Medicine();
		medicine2.setId(2);
		medicine2.setName("Brufen");
		medicine2.setCode("BB");
		medicine2.setManufacturer("hemofarm");
		
		Medicine medicine3 = new Medicine();
		medicine3.setId(3);
		medicine3.setName("Panadol");
		medicine3.setCode("PP");
		medicine3.setManufacturer("hemofarm");
				
		Set<MedicineQuantity> medicines = new HashSet<MedicineQuantity>();
		medicines.add(new MedicineQuantity(medicine1, 1));
		medicines.add(new MedicineQuantity(medicine2, 2));
		medicines.add(new MedicineQuantity(medicine3, 3));
		pharmacy.setMedicines(medicines);

		when(pharmacyRepository.findById((long) 1)).thenReturn(Optional.of(pharmacy));
		
		Set<Medicine> result = pharmacyService.findAllInStockByPharmacyId(1);
		
		assertThat(result).hasSize(3);
	}
	
}
