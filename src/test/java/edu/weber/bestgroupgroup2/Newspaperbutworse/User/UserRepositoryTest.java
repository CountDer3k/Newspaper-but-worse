package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;


@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {
	
	UserRepository userRepository;
	
	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock 
	KeyHolder keyHolder;
	
	@Before
	@Log
	public void setup() {
		userRepository = new UserRepository(namedParameterJdbcTemplate);
	}
	

	@Test
	@Log
	public void testSave() {
		int id = 1;
		User user = getFlashThompson();

		mockKeyHolder(id);
		User actual = userRepository.save(user);
		
		User expected = user;
		expected.setUserId(id);
		
		Assert.assertEquals(actual, expected);
	}	
	
	@Test
	@Log
	public void testGetUserByUsername() throws Exception {
		User user = getGwenStacy();
		final ResultSet resultSet = Mockito.mock(ResultSet.class);
		when(resultSet.getString("u.USERNAME")).thenReturn(user.getUsername());
		
		Mockito.doAnswer(invocation -> {
			RowCallbackHandler callbackHandler = invocation.getArgument(2);
			callbackHandler.processRow(resultSet);
			return null;
		}).when(namedParameterJdbcTemplate).query(
					Mockito.anyString(),
					Mockito.any(MapSqlParameterSource.class),
					Mockito.any(RowCallbackHandler.class));
		User actual = userRepository.getUserByUsername(user.getUsername());
		Assert.assertEquals(user, actual);
	}
	
	/* Helper Functions */
	
	public void mockKeyHolder(int id) {
		when(namedParameterJdbcTemplate.update(
				Mockito.anyString(), 
				Mockito.any(MapSqlParameterSource.class), 
				Mockito.any(GeneratedKeyHolder.class)))
		.thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Map<String, Object> keyMap = new HashMap<String, Object>();
				keyMap.put("", id);
				((GeneratedKeyHolder)args[2]).getKeyList().add(keyMap);
				return id;
			}
		}).thenReturn(id);
	}
	
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
	
	public User getGwenStacy() {
		User user = new User();
		user.setUsername("GhostSpider");
		user.setPassword(passwordEncoder.encode("123456789"));
		user.setEmail("gwen.stacy@gmail.com");
		user.setFirstName("Gwendolyne");
		user.setLastName("Stacy");
		user.setRoles(new ArrayList<Role>());
		
		Role role = new Role();
		role.setRoleId(4);
		user.addRole(role);
		return user;
	}
}

