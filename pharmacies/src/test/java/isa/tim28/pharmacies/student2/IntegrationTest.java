package isa.tim28.pharmacies.student2;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

import isa.tim28.pharmacies.dtos.PharmacyAdminDTO;
import isa.tim28.pharmacies.dtos.PredefinedExaminationDTO;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.util.TestUtil;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getPharmacyAdminPersonalInfo() throws Exception {
		User user = new User();
		user.setEmail("email6@gmail.com");
		user.setPassword("1234");
		user.setRole(Role.PHARMACY_ADMIN);
		user.setId(6);
		MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);
        
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/pharmacy-admin").session(session);
		
		mockMvc.perform(builder).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.name").value("Tamara"))
		.andExpect(jsonPath("$.surname").value("Rankovic"))
		.andExpect(jsonPath("$.email").value("email6@gmail.com"));
	}
	
	@Test
	@Transactional
	public void updatePharmacyAdminPersonalInfo() throws Exception {
		User user = new User();
		user.setEmail("email6@gmail.com");
		user.setPassword("1234");
		user.setRole(Role.PHARMACY_ADMIN);
		user.setId(6);
		MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);
        
        PharmacyAdminDTO dto = new PharmacyAdminDTO();
        dto.setName("Jovana");
        dto.setSurname("Rankovic");
        dto.setEmail("email6@gmail.com");
        
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/pharmacy-admin/update").contentType(contentType).content(TestUtil.json(dto)).session(session);
        mockMvc.perform(builder).andExpect(status().isOk());
        
        builder = MockMvcRequestBuilders.get("/pharmacy-admin").session(session);
		mockMvc.perform(builder).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.name").value("Jovana"))
		.andExpect(jsonPath("$.surname").value("Rankovic"))
		.andExpect(jsonPath("$.email").value("email6@gmail.com"));
	}
	
	@Test
	@Transactional
	public void invalidNewDermatologistExamination() throws Exception {
		User user = new User();
		user.setEmail("email6@gmail.com");
		user.setPassword("1234");
		user.setRole(Role.PHARMACY_ADMIN);
		user.setId(6);
		MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);
        
        PredefinedExaminationDTO dto = new PredefinedExaminationDTO();
        dto.setDermatologistId(1);
        dto.setPrice(100);
        dto.setDurationInMinutes(20);
        dto.setStartDateTime(LocalDateTime.of(LocalDate.of(2020, 3, 23), LocalTime.of(8, 0)));
        
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/derm/new-predefined").contentType(contentType).content(TestUtil.json(dto)).session(session);
        mockMvc.perform(builder).andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	public void declinePharmacistLeaveRequestTest() throws Exception {
		User user = new User();
		user.setEmail("email6@gmail.com");
		user.setPassword("1234");
		user.setRole(Role.PHARMACY_ADMIN);
		user.setId(6);
		MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);
        
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/leave/decline/1").contentType(contentType).content("reason").session(session);
        
        mockMvc.perform(builder).andExpect(status().isOk());
	}
	
	@Test
	public void checkIfAppointmentPricesAreValidTest() throws Exception {
		User user = new User();
		user.setEmail("email6@gmail.com");
		user.setPassword("1234");
		user.setRole(Role.PHARMACY_ADMIN);
		user.setId(6);
		MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);
        
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/pharmacy/current-price-list").session(session);
        
        mockMvc.perform(builder).andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.pharmacistAppointmentPrice.undefined").value(false))
		.andExpect(jsonPath("$.dermatologistAppointmentPrice.undefined").value(false))
		.andExpect(jsonPath("$.pharmacistAppointmentPrice.price").value(100))
		.andExpect(jsonPath("$.dermatologistAppointmentPrice.price").value(200));
	}
}
