package edu.weber.bestgroupgroup2.Newspaperbutworse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostController;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.ModelAndView;
import org.mockito.junit.MockitoJUnitRunner;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.ArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.Comment;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostDto;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;

@RunWith(MockitoJUnitRunner.class)
public class SecurityConfigTest {

	@Mock
	UserService userService;

	@Mock
	Authentication auth;

	JwtTokenProvider jwtTokenProvider;

	@Mock
	HttpServletRequest request;

	@Mock
	JwtTokenProvider mockJTP;

//	@Mock
//	HttpSecurity httpSec;

	@Mock
	ApplicationContext appContext;
	
	@Mock
	PostService postService;
	
	@Mock
	PostController postController;

	HomeController homeController;
	SecurityConfig secConfig;

	@Before
	public void setup() {
		secConfig = new SecurityConfig(appContext, jwtTokenProvider);
	}

	@Test
	public void testEncoder() {
		PasswordEncoder pe = secConfig.encoder();
		Assert.assertNotNull(pe);
	}
	
//	@Test
//	public void testUserDetailsServiceBean() throws Exception {
//		UserDetailsService uds = secConfig.userDetailsServiceBean();
//		System.out.println(uds.toString());
//		Assert.assertNotNull(uds);
//	}
	
	@Test
	public void testSucessHandler() throws Exception {
		AuthenticationSuccessHandler a = secConfig.successHandler();
		Assert.assertNotNull(a);
	}
	
}
