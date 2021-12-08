package edu.weber.bestgroupgroup2.Newspaperbutworse;

import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.mockito.junit.MockitoJUnitRunner;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;


@RunWith(MockitoJUnitRunner.class)
public class NewspaperButWorseApplicationTest {

	@Mock
	UserService userService;
	
	@Mock
	Authentication auth;
	
	JwtTokenProvider jwtTokenProvider;
	
	@Mock
	HttpServletRequest request;
	
	@Before
	public void setup() {
		jwtTokenProvider = new JwtTokenProvider(userService);
	}
	
	@Test
	public void testCreateCookie() {
		
		Cookie cookie = jwtTokenProvider.createCookieTokenString(auth);
		
		Assert.assertNotNull(cookie);
	}
	
	@Test
	public void testGetCookieFromRequest() {
		
		Cookie cookie = new Cookie(AppConstants.JWT_COOKIE_NAME, "test");
		Cookie[] arr = new Cookie[1];
		arr[0] = cookie;
		when(request.getCookies()).thenReturn(arr);
		
		Assert.assertNotNull(jwtTokenProvider.getJwtTokenFromRequest(request));
	}
	
	@Test
	public void testGetNullCookieFromRequest() {
		Assert.assertNull(jwtTokenProvider.getJwtTokenFromRequest(request));
	}
	

	@Test
	public void testCreateTokenNull() {
		Duration ttl = Duration.ofMinutes(30);
		Date now = new Date();
		Date expiration = new Date(now.getTime() + ttl.toMillis());
		
		when(auth.isAuthenticated()).thenReturn(true);
		when(auth.getPrincipal() instanceof User).thenReturn(true);
		
		Assert.assertNull(jwtTokenProvider.createToken(auth, expiration));
	}
	
	@Test
	public void testCreateTokenWithUserWithValidation() {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("Pass");
		Duration ttl = Duration.ofMinutes(30);
		Date now = new Date();
		Date expiration = new Date(now.getTime() + ttl.toMillis());
		
		String token = jwtTokenProvider.createToken(user, expiration);
		
		when(jwtTokenProvider.getJwtTokenFromRequest(request)).thenReturn(token);
		
		jwtTokenProvider.getAuthentication(request);
		
	}

}
