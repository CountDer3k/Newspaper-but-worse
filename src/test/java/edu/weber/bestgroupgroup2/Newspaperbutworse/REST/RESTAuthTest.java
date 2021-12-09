package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.sql.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import edu.weber.bestgroupgroup2.Newspaperbutworse.JwtTokenProvider;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;

@RunWith(MockitoJUnitRunner.class)
public class RESTAuthTest {
	
	RESTAuthenticationController controller;
	@Mock
	JwtTokenProvider jot;
	
	@Before
	public void setup() {
		controller = new RESTAuthenticationController(jot);
	}
	
	@Test
	public void testCreateNewToken() throws UnsupportedEncodingException {
		String header = "Bearer ";
		String token = "U3ltYmlvdGljQnJvY2s6dmVub20=";
		User u = new User();
		ResponseEntity<String> expected = ResponseEntity.ok(token);
		
		when(jot.createToken(u, new Date(0))).thenReturn(token);
		
		ResponseEntity<String> actual = controller.createNewToken(header+token);
		
		Assert.assertEquals(expected, actual);
	}
}
