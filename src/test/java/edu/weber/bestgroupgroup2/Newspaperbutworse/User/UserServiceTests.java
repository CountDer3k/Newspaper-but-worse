package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

public class UserServiceTests {
	
	UserService userService;
	
	@Mock
    private UserRepository userRepository;
	@Mock
    private PasswordEncoder passwordEncoder;
	
	@Before
	@Log
	public void setup() {
		userService = new UserService(userRepository, passwordEncoder);
	}
	
	@Test
	@Log
	public void testIsValidUserTrue() {
		UserDto user = getGwenStacy();
		Assert.assertTrue(userService.isValidUser(user));
	}
	
	@Test
	@Log
	public void testIsValidUserFalse_Username() {
		
	}
	
	/* Helper Methods */
	public UserDto getGwenStacy() {
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
	
}
