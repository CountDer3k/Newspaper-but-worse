package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import org.junit.Before;
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
	
	
}
