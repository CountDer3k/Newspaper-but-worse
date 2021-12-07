package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
	
	UserService userService;
	
	@Mock
    private UserRepository userRepository;
	@Mock
    private PasswordEncoder passwordEncoder;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Before
	public void setup() {
		userService = new UserService(userRepository, passwordEncoder);
	}
	
	@Test
	public void testIsValidUserFalse_Username() {
		UserDto user = getGwenStacy();
		user.setUsername("");
		Assert.assertFalse(userService.isValidUser(user));
	}
	
	@Test
	public void testIsValidUserFalse_Email() {
		UserDto user = getGwenStacy();
		user.setEmail("");
		Assert.assertFalse(userService.isValidUser(user));
	}
	
	@Test
	public void testIsValidUserFalse_Password() {
		UserDto user = getGwenStacy();
		user.setPassword("");
		Assert.assertFalse(userService.isValidUser(user));
	}
	
	@Test
	public void testIsValidUserFalse_MatchingPassword() {
		UserDto user = getGwenStacy();
		user.setMatchingPassword("987654321");
		Assert.assertFalse(userService.isValidUser(user));
	}
	
	@Test
	public void testIsValidUserTrue() {
		UserDto user = getGwenStacy();
		Assert.assertTrue(userService.isValidUser(user));
	}
	
	@Test
	public void testGetUserByID() {
		UserDto user = getGwenStacy();
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn(user.getPassword());
		
		User expected = userService.registerNewUserAccount(user);
		User actual = userService.getUserByID(1);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testRegisterNewUserAccount() {
		UserDto user = getGwenStacy();
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn(user.getPassword());

		User expected = userService.getUserByID(1);
		User actual = userService.registerNewUserAccount(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testLoadUserByUsername() {
		UserDto user = getGwenStacy();
		
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn(user.getPassword());
		User expected = userService.registerNewUserAccount(user);
		
		when(userRepository.getUserByUsername(Mockito.anyString())).thenReturn(expected);
		User actual = (User) userService.loadUserByUsername(user.getUsername());
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testLoadUserByUsernameException() {
		exceptionRule.expect(UsernameNotFoundException.class);
		userService.loadUserByUsername("");
	}
	
	@Test
	public void testGetAllUsers() {
		
	}
	
	@Test
	public void testGetAllUsersPagination() {
		
	}
	
	@Test
	public void testEditUser() {
		UserDto user = getGwenStacy();
		
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn(user.getPassword());
		User expected = userService.registerNewUserAccount(user);
		expected.setUserId(1);
		
		when(userRepository.getUserByUsername(Mockito.anyString())).thenReturn(expected);
		User actual = userService.editUser(expected);
		Assert.assertEquals(expected, actual);
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
