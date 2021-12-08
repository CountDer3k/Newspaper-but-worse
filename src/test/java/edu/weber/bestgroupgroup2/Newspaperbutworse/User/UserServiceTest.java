package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.util.ArrayList;
import java.util.List;

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
public class UserServiceTest {
	
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
		when(userRepository.save(Mockito.any())).then(returnsFirstArg());
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
		List<User> userList = new ArrayList<User>();
		userList.add(getFlashThompson());
		userList.add(getBettyBrant());
		
		when(userRepository.getAllUsers()).thenReturn(userList);
		List<User> actual = userService.getAllUsers();
		Assert.assertEquals(userList, actual);
	}
	
	@Test
	public void testGetAllUsersEmpty() {
		List<User> userList = new ArrayList<User>();
		userList.add(getFlashThompson());
		userList.add(getBettyBrant());
		
		when(userRepository.getAllUsers()).thenReturn(userList);
		List<User> result = userService.getAllUsers(50, 2);
		Assert.assertTrue(result.isEmpty());
	}
	
	@Test
	public void testEditUser() {
		UserDto user = getGwenStacy();
		
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn(user.getPassword());
		when(userRepository.save(Mockito.any())).then(returnsFirstArg());
		User expected = userService.registerNewUserAccount(user);
		expected.setUserId(1);
		
		when(userRepository.getUserByUsername(Mockito.anyString())).thenReturn(expected);
		when(userRepository.updateUser(Mockito.any())).then(returnsFirstArg());
		User actual = userService.editUser(expected);
		Assert.assertEquals(expected, actual);
	}
	
	
	/* Helper Methods */
	
	public User getFlashThompson() {
		User user = new User();
		user.setUsername("CallMeFlash");
		user.setPassword(passwordEncoder.encode("antivenom"));
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
		user.setPassword(passwordEncoder.encode("123456789"));
		user.setEmail("bettybrant4@gmail.com");
		user.setFirstName("Elizabeth");
		user.setLastName("Brant");
		user.setRoles(new ArrayList<Role>());
		
		Role role = new Role();
		role.setRoleId(3);
		user.addRole(role);
		return user;
	}
	
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
