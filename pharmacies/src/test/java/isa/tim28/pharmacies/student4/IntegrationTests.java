package isa.tim28.pharmacies.student4;

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
import org.springframework.web.context.WebApplicationContext;

import isa.tim28.pharmacies.dtos.PasswordChangeDTO;
import isa.tim28.pharmacies.dtos.SupplierProfileDTO;
import isa.tim28.pharmacies.model.Role;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getSupplierPersonalInfo() throws Exception {
		User user = new User();
		user.setEmail("isa.mejl.za.usere+3@gmail.com");
		user.setPassword("1234");
		user.setRole(Role.SUPPLIER);
		user.setId(4);
		MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);
        
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/supplier/get").session(session);
		
		mockMvc.perform(builder).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.name").value("Tamara"))
		.andExpect(jsonPath("$.surname").value("Rankovic"))
		.andExpect(jsonPath("$.email").value("isa.mejl.za.usere+3@gmail.com"));
	}
	@Test
	public void testUpdateSupplier() throws Exception {
		SupplierProfileDTO dto = new SupplierProfileDTO();
		String newEmail = "isa.mejl.za.usere+3@gmail.com";
		dto.setEmail(newEmail);
		dto.setName("Tamara");
		dto.setSurname("Rankovic");
		String newSupplier = TestUtil.json(dto);
		
		MockHttpServletRequestBuilder update = MockMvcRequestBuilders.post("/supplier/update").session(setSession());
		
		mockMvc.perform(update.content(newSupplier).contentType(contentType)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.email").value("isa.mejl.za.usere+3@gmail.com"));
		
		MockHttpServletRequestBuilder getUpdated = MockMvcRequestBuilders.get("/supplier/get").session(setSession());
		
		mockMvc.perform(getUpdated).andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.email").value("isa.mejl.za.usere+3@gmail.com"));
	}
	
	public static MockHttpSession setSession() {
		User user = new User();
		user.setRole(Role.SUPPLIER);
		user.setActive(true);
		user.setEmail("isa.mejl.za.usere+3@gmail.com");
		user.setPassword("1234");
		user.setId(4);
		user.setLoged(true);
		user.setName("Tamara");
		user.setSurname("Rankovic");
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("loggedInUser", user);
		return session;
	}
	@Test
	public void testChangePassword() throws Exception {
		PasswordChangeDTO dto = new PasswordChangeDTO();
		dto.setOldPassword("1234");
		dto.setNewPassword("perapera");
		dto.setNewPasswordRepeat("perapera");
		String dtoJSON = TestUtil.json(dto);
		
		PasswordChangeDTO dto2 = dto;
		dto2.setNewPasswordRepeat("novaSifra");
		String dto2JSON = TestUtil.json(dto2);
		
		MockHttpServletRequestBuilder changePassword = MockMvcRequestBuilders.post("/supplier" + "/changePassword").session(setSession());
		
		mockMvc.perform(changePassword.content(dto2JSON).contentType(contentType)).andExpect(status().isBadRequest());
		
		mockMvc.perform(changePassword.content(dtoJSON).contentType(contentType)).andExpect(status().isOk());
		
		mockMvc.perform(changePassword.content(dtoJSON).contentType(contentType)).andExpect(status().isBadRequest());
		
	}
	@Test
	public void getPharmacyBasicInfo() throws Exception {
		User user = new User();
		user.setEmail("email6@gmail.com");
		user.setPassword("1234");
		user.setRole(Role.PHARMACY_ADMIN);
		user.setId(6);
		MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);
        
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/pharmacy/basic-info").session(session);
        
        mockMvc.perform(builder).andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.name").value("Pharmacy 1"))
		.andExpect(jsonPath("$.address").value("Masarikova 1, Novi Sad"));
        
	}
	
	@Test
	public void testGetOffers() throws Exception {
		User user = new User();
		user.setEmail("isa.mejl.za.usere+3@gmail.com");
		user.setPassword("1234");
		user.setRole(Role.SUPPLIER);
		user.setId(4);
		MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);
        
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/offer/getOffers").session(session);
        
        mockMvc.perform(builder).andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.[0].totalPrice").value(5000));
	
		
	}
}
