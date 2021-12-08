package edu.weber.bestgroupgroup2.Newspaperbutworse;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.mockito.junit.MockitoJUnitRunner;

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

}
