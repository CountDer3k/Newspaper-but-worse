package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {
	
	UserRepository userRepository;
	
	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Mock
	KeyHolder keyHolder;
	
	@Before
	public void setup() {
		userRepository = new UserRepository(namedParameterJdbcTemplate);
		keyHolder = new GeneratedKeyHolder();
	}
	
	
}
