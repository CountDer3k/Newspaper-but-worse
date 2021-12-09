package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	UserController userController;
	private MockMvc mockMvc;
	@Mock
	UserService userService;
	@Mock
	BindingResult bindingResult;
	
	@Before
	public void setup() {
		userController = new UserController(userService);
		mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
	}
	
	@Test
	public void testShowRegistrationForm() {
		ModelAndView expected = new ModelAndView("user/registration");
		ModelAndView actual = userController.showRegistrationForm();
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
		Assert.assertNotNull(actual.getModelMap().get("user"));
	}
	
	@Test
	public void testRegisterUserAccount() throws Exception {
		UserDto userDto = getGwenStacyDto();
		User user = getGwenStacy();
		//when(userService.isValidUser(Mockito.any())).thenReturn(true);
		when(userService.registerNewUserAccount(Mockito.any())).thenReturn(user);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/registration", userDto))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/?msg=Registration+Confirmed%21"))
			.andExpect(MockMvcResultMatchers.status().isFound());
	}
	
	@Test
	public void testShowLoginForm() {
		ModelAndView expected = new ModelAndView("login");
		ModelAndView actual = userController.showLoginForm();
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
		Assert.assertNotNull(actual.getModelMap().get("user"));
	}
	
	@Test
	public void testShowUserList() {
		when(userService.getAllUsers()).thenReturn(getUsers());
		ModelAndView actual = userController.showUserList();
		Assert.assertNotNull(actual.getModelMap().get("users"));
	}
	
	@Test
	public void testShowUserListEmpty() {
		ModelAndView expected = new ModelAndView("user/userList");
		ModelAndView actual = userController.showUserList();
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}
	
	@Test
	public void testShowUser() {
		ModelAndView expected = new ModelAndView("user/user");
		User user = getFlashThompson();
		when(userService.loadUserByUsername(Mockito.anyString())).thenReturn(user);
		ModelAndView actual = userController.showUser(user.getUsername());
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
		Assert.assertNotNull(actual.getModelMap().get("user"));
	}
	
	@Test
	public void testShowUserForm() {
		ModelAndView expected = new ModelAndView("user/userForm");
		User user = getBettyBrant();
		when(userService.loadUserByUsername(Mockito.anyString())).thenReturn(user);
		ModelAndView actual = userController.showUserForm(user.getUsername());
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
		Assert.assertNotNull(actual.getModelMap().get("user"));
	}
	
	@Test
	public void testEditUser() throws Exception {
		User user = getBettyBrant();
		when(userService.editUser(Mockito.any())).thenReturn(user);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/edit/{username}", user))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/user/list?msg=Registration+Confirmed%21"))
			.andExpect(MockMvcResultMatchers.status().isFound());
	}
	
	
	/* Helper Methods */
	
	public UserDto getGwenStacyDto() {
		UserDto user = new UserDto();
		user.setUsername("GhostSpider");
		user.setPassword("123456789");
		user.setMatchingPassword("123456789");
		user.setEmail("gwen.stacy@gmail.com");
		user.setFirstName("Gwendolyne");
		user.setLastName("Stacy");
		
		ArrayList<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		role.setRoleId(4);
		roles.add(role);
		user.setRoles(roles);
		return user;
	}
	
	public User getGwenStacy() {
		User user = new User();
		user.setUsername("GhostSpider");
		user.setPassword("123456789");
		user.setEmail("gwen.stacy@gmail.com");
		user.setFirstName("Gwendolyne");
		user.setLastName("Stacy");
		
		ArrayList<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		role.setRoleId(4);
		roles.add(role);
		user.setRoles(roles);
		return user;
	}
	
	public User getFlashThompson() {
		User user = new User();
		user.setUsername("CallMeFlash");
		user.setPassword("antivenom");
		user.setEmail("flashthompson15@gmail.com");
		user.setFirstName("Eugene");
		user.setLastName("Thompson");
		user.setRoles(new ArrayList<Role>());
		
		Role role = new Role();
		role.setRoleId(2);
		user.addRole(role);
		return user;
	}
	
	public User getBettyBrant() {
		User user = new User();
		user.setUsername("GirlFriday");
		user.setPassword("123456789");
		user.setEmail("bettybrant4@gmail.com");
		user.setFirstName("Elizabeth");
		user.setLastName("Brant");
		user.setRoles(new ArrayList<Role>());
		
		Role role = new Role();
		role.setRoleId(3);
		user.addRole(role);
		return user;
	}
	
	public ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<>();
		users.add(getFlashThompson());
		users.add(getBettyBrant());
		return users;
	}
	
}
