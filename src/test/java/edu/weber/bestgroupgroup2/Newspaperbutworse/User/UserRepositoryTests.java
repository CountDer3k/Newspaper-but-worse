package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
	
	UserRepository userRepository;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private List<User> users = new ArrayList<User>(List.of(
			new User("spiderman", "pparker@dailybugle.net", "Peter", "Parker"),
			new User("jjjjr", "jjjameson@dailybugle.net", "J Jonah", "Jameson")));
	
	@Before
	public void setup() {
		userRepository = new UserRepository(namedParameterJdbcTemplate);
	}
	
	
	@Test
	public void testGetUserByUsername() {
		String username = "spiderman";
		users.stream().map(userRepository::save);
		List<User> expectedList = users.stream()
				.filter(user -> user.getUsername().equals(username))
				.collect(Collectors.toList());
		List<User> actualList = new ArrayList<>();
		actualList.add(userRepository.getUserByUsername(username));
		users.stream().forEach(user -> System.out.println(user));
		System.out.println(expectedList);
		System.out.println(actualList);
		Assert.assertEquals(expectedList, actualList);
	}
	
}

