package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	UserController userController;
	private MockMvc mockMvc;
	@Mock
	UserService userService;
	
	@Before
	public void setup() {
		userController = new UserController(userService);
		mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
	}
	
	@Test
	public void testShowRegistrationForm() {
		
	}
}
