package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.Base64;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import java.time.Duration;
import edu.weber.bestgroupgroup2.Newspaperbutworse.JwtTokenProvider;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;

@RunWith(MockitoJUnitRunner.class)
public class RESTAuthTest {
	
	RESTAuthenticationController controller;
	@Mock
	JwtTokenProvider jot;
	@Mock
	Base64.Decoder decoder;
	@Mock
	Date date;
	@Mock 
	User mockUser;
	
	@Before
	public void setup() {
		controller = new RESTAuthenticationController(jot);
	}
	
	@Test
	public void testCreateNewToken1() throws UnsupportedEncodingException {
		String authHeader = "A U3ltYmlvdGljQnJvY2s6dmVub20=";
		User u = new User();
		u.setUsername("SymbioticBrock");
		u.setPassword("venom");
		ResponseEntity<String> expected = ResponseEntity.ok(authHeader.split(" ")[1]);
		
		when(jot.createToken(any(User.class), any(Date.class))).thenReturn(authHeader);
		
		ResponseEntity<String> actual = controller.createNewToken(authHeader);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testCreateNewToken() throws UnsupportedEncodingException {
		String header = "Bearer";
		String token = "U3ltYmlvdGljQnJvY2s6dmVub20=";
		String authHeader = header + " " + token;
		User u = new User();
		u.setUsername("SymbioticBrock");
		u.setPassword("venom");
		ResponseEntity<String> expected = ResponseEntity.ok(token);
		
		Duration d = Duration.ofMinutes(30);
		Date sDate = new Date(0);
		Long da = sDate.getTime();
		
		//when(jot.createToken(any(Class.class), any(Class.class))).thenReturn(token);
		
		ResponseEntity<String> actual = controller.createNewToken(authHeader);
		
		Assert.assertEquals(expected, actual);
	}
}
