package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;


import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.ArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostDto;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserDto;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserRepository;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;


@RunWith(MockitoJUnitRunner.class)
public class RESTUserTest {
	
	RESTUserController controller;
	@Mock
	UserService service;
	
	@Before
	public void setup() {
		controller = new RESTUserController(service);
	}
	
	@Test
	public void testGetAllUsers() {
		List<User> ul = new ArrayList<User>();
		ResponseEntity<List<User>> expected = ResponseEntity.ok(ul);

		when(service.getAllUsers(1, 1)).thenReturn(ul);

		ResponseEntity<List<User>> actual = controller.getAllUsers("1","1");

		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllUsers_null() {
		List<User> ul = new ArrayList<User>();
		ResponseEntity<List<User>> expected = ResponseEntity.ok(ul);

		when(service.getAllUsers(1, 1)).thenReturn(ul);

		ResponseEntity<List<User>> actual = controller.getAllUsers("1",null);

		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllUsers_exception() {
		List<User> ul = new ArrayList<User>();
		ResponseEntity<List<User>> expected = ResponseEntity.ok(ul);


		ResponseEntity<List<User>> actual = controller.getAllUsers("abc","1");

		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetUser() {
		User u = new User();
		ResponseEntity<Object> expected = ResponseEntity.ok(u);

		when(service.getUserByID(1)).thenReturn(u);

		ResponseEntity<Object> actual = controller.getUser("1");

		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetUser_null() {
		User u = new User();
		ResponseEntity<Object> expected = ResponseEntity.ok(u);

		when(service.getUserByID(1)).thenReturn(null);

		ResponseEntity<Object> actual = controller.getUser("1");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLoginUser() {
		User u = new User();
		u.setFirstName("a");
		u.setLastName("a");
		u.setUsername("ab");
		u.setPassword("132");
		u.setUserId(1);
		u.setEmail("2@mail.com");
		UserDto dto = new UserDto();
		ResponseEntity<Object> expected = ResponseEntity.ok(u);

		when(service.registerNewUserAccount(dto)).thenReturn(u);

		ResponseEntity<Object> actual = controller.loginUser(dto);

		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testLoginUser_failed() {
		User u = new User();
		UserDto dto = new UserDto();
		ResponseEntity<Object> expected = ResponseEntity.badRequest().body("Failed to add new user");

		when(service.registerNewUserAccount(dto)).thenReturn(u);

		ResponseEntity<Object> actual = controller.loginUser(any(UserDto.class));

		Assert.assertEquals(expected, actual);
	}
	
	
	
}
