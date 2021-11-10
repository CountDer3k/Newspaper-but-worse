package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
	
	UserRepository userRepository;
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Before
	public void setup() {
		userRepository = new UserRepository(namedParameterJdbcTemplate);
	}
	
	
	@Test
	public void testGetUserByUsername() {
		String username = "spiderman";
		System.out.println(username);
		User expected = entities()
				.map(userRepository::save)
				.filter(user -> user.getUsername().equals(username)).
				findFirst().get();
		User actual = (userRepository.getUserByUsername(username));
		
		Assert.assertEquals(expected, actual);
	}
	
	private Stream<User> entities() {
		return Stream.of(
				new User("spiderman", "pparker@dailybugle.net", "Peter", "Parker"),
				new User("jjjjr", "jjjameson@dailybugle.net", "J Jonah", "Jameson")
			);
	}
	
}

