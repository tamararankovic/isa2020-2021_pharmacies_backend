package isa.tim28.pharmacies.student1;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import isa.tim28.pharmacies.dtos.PatientProfileDTO;
import isa.tim28.pharmacies.dtos.PharmacyAdminDTO;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

	
	private static final String PATIENT_URL = "/patient";
	private static final String PATIENT_EMAIL = "isa.mejl.za.usere@gmail.com";
	private static final String PATIENT_PASSWORD = "1234";
	
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
	public void testPatientProfile() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(PATIENT_URL + "/get").session(setSession());
		
		mockMvc.perform(builder).andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.email").value(PATIENT_EMAIL));
		
		MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders.get(PATIENT_URL + "/get").session(new MockHttpSession());
		
		mockMvc.perform(builder2).andExpect(status().isForbidden());
	}
	
	
	
	
	public static MockHttpSession setSession() {
		User user = new User();
		user.setRole(Role.PATIENT);
		user.setActive(true);
		user.setEmail(PATIENT_EMAIL);
		user.setPassword(PATIENT_PASSWORD);
		user.setId(1);
		user.setLoged(true);
		user.setName("Tamara");
		user.setSurname("Rankovic");
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("loggedInUser", user);
		return session;
	}
}
