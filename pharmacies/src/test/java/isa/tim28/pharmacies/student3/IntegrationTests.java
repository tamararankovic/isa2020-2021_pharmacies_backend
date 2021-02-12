package isa.tim28.pharmacies.student3;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import isa.tim28.pharmacies.dtos.DermatologistProfileDTO;
import isa.tim28.pharmacies.dtos.PasswordChangeDTO;
import isa.tim28.pharmacies.dtos.PatientReportAllergyDTO;
import isa.tim28.pharmacies.dtos.PatientSearchDTO;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

	private static final String DERM_URL = "/derm";
	private static final String DERM_EMAIL = "email3@gmail.com";
	private static final String DERM_PASSWORD = "1234";
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype());
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testDermProfile() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(DERM_URL + "/get").session(setSession());
		
		mockMvc.perform(builder).andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.email").value(DERM_EMAIL));
		
		MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders.get(DERM_URL + "/get").session(new MockHttpSession());
		
		mockMvc.perform(builder2).andExpect(status().isForbidden());
	}
	
	@Test
	public void testUpdateDermatologist() throws Exception {
		DermatologistProfileDTO dto = new DermatologistProfileDTO();
		String newEmail = "email250@gmail.com";
		dto.setEmail(newEmail);
		dto.setName("Pera");
		dto.setSurname("Peric");
		String newDermatologist = json(dto);
		
		MockHttpServletRequestBuilder update = MockMvcRequestBuilders.post(DERM_URL + "/update").session(setSession());
		
		mockMvc.perform(update.content(newDermatologist).contentType(contentType)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.email").value("email250@gmail.com"));
		
		MockHttpServletRequestBuilder getUpdated = MockMvcRequestBuilders.get(DERM_URL + "/get").session(setSession());
		
		mockMvc.perform(getUpdated).andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.email").value("email250@gmail.com"));
	}
	
	@Test
	public void testGetPatients() throws Exception {
		PatientSearchDTO dto = new PatientSearchDTO();
		dto.setId(1);
		dto.setName("ta");
		dto.setSurname("");
		String dtoJSON = json(dto);
		
		PatientSearchDTO dto2 = dto;
		dto2.setName("mil");
		String dto2JSON = json(dto2);
		
		MockHttpServletRequestBuilder patients = MockMvcRequestBuilders.post(DERM_URL + "/patients").session(setSession());
		
		mockMvc.perform(patients.content(dtoJSON).contentType(contentType)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.[*].name").value("Tamara"));
		
		mockMvc.perform(patients.content(dto2JSON).contentType(contentType)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.[0].name").value("Milijana"))
			.andExpect(jsonPath("$.[1].name").value("Milijana"));
		
	}
	
	@Test
	public void testCheckAllergies() throws Exception {
		PatientReportAllergyDTO dto = new PatientReportAllergyDTO();
		dto.setMedicineId(2);
		dto.setPatientId(1);
		String dtoJSON = json(dto);
		
		PatientReportAllergyDTO dto2 = dto;
		dto2.setMedicineId(1);
		String dto2JSON = json(dto2);
		
		MockHttpServletRequestBuilder checkAllergies = MockMvcRequestBuilders.post(DERM_URL + "/allergies").session(setSession());
		
		mockMvc.perform(checkAllergies.content(dtoJSON).contentType(contentType)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.allergic").value(true));
		
		mockMvc.perform(checkAllergies.content(dto2JSON).contentType(contentType)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.allergic").value(false));
	}
	
	@Test
	public void testStartAppointmentForPatient() throws Exception {
		MockHttpServletRequestBuilder successful = MockMvcRequestBuilders.get(DERM_URL + "/isDermatologistInAppointment").session(setSession());
		
		mockMvc.perform(successful).andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.hasAppointment").value(false));
		
		MockHttpServletRequestBuilder unsuccessful = MockMvcRequestBuilders.get(DERM_URL + "/isDermatologistInAppointment").session(setSession());
		
		mockMvc.perform(unsuccessful).andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.hasAppointment").value(true));
		
		MockHttpServletRequestBuilder endAppointment = MockMvcRequestBuilders.get(DERM_URL + "/endCurrent").session(setSession());
		
		mockMvc.perform(endAppointment).andExpect(status().isOk());
		
		mockMvc.perform(successful).andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.hasAppointment").value(false));
	}
	
	@Test
	public void testChangePassword() throws Exception {
		PasswordChangeDTO dto = new PasswordChangeDTO();
		dto.setOldPassword(DERM_PASSWORD);
		dto.setNewPassword("12341234");
		dto.setNewPasswordRepeat("12341234");
		String dtoJSON = json(dto);
		
		PasswordChangeDTO dto2 = dto;
		dto2.setNewPasswordRepeat("adwdhbRTHRTHRTH");
		String dto2JSON = json(dto2);
		
		MockHttpServletRequestBuilder changePassword = MockMvcRequestBuilders.post(DERM_URL + "/changePassword").session(setSession());
		
		mockMvc.perform(changePassword.content(dto2JSON).contentType(contentType)).andExpect(status().isBadRequest());
		
		mockMvc.perform(changePassword.content(dtoJSON).contentType(contentType)).andExpect(status().isOk());
		
		mockMvc.perform(changePassword.content(dtoJSON).contentType(contentType)).andExpect(status().isBadRequest());
		
	}
	
	
	//Metoda vraća JSON reprezentaciju prosleđenog objekta.
	public static String json(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
	}
	
	//Metoda vraca sesiju sa ulogovanim korisnikom
	public static MockHttpSession setSession() {
		User user = new User();
		user.setRole(Role.DERMATOLOGIST);
		user.setActive(true);
		user.setEmail(DERM_EMAIL);
		user.setPassword(DERM_PASSWORD);
		user.setId(3);
		user.setLoged(true);
		user.setName("Tamara");
		user.setSurname("Rankovic");
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("loggedInUser", user);
		return session;
	}
}
