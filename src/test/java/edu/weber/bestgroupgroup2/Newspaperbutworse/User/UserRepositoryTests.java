package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJdbcTest
public class UserRepositoryTests {
	
	@Autowired
	UserRepository userRepository;
	
	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Before
	public void setup() {
		userRepository = new UserRepository(namedParameterJdbcTemplate);
	}
	
	
	@Test
	public void testGetUserByUsername() {
		String username = "spiderman";
		System.out.println(username);
		List<User> expectedList = entities()
				.map(userRepository::save)
				.filter(user -> user.getUsername().equals(username))
				.collect(Collectors.toList());
		List<User> actualList = new ArrayList<>();
		actualList.add(userRepository.getUserByUsername(username));
		
		Assert.assertEquals(expectedList, actualList);
	}
	
	private Stream<User> entities() {
		return Stream.of(
				new User("spiderman", "pparker@dailybugle.net", "Peter", "Parker"),
				new User("jjjjr", "jjjameson@dailybugle.net", "J Jonah", "Jameson")
			);
	}
	
}

