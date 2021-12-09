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
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserDto;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserRepository;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;


@RunWith(MockitoJUnitRunner.class)
public class RESTTest {

	RESTPostController controller;
	@Mock
	PostService service;
	@Mock
	UserRepository userRepo;
	@Mock
	PasswordEncoder encoder;
	
	@Before
	public void setup() {
		controller = new RESTPostController(service);
	}
	
	@Test
	public void testCreateArticle() {
		PostModel pm = makePost();
		pm.setId(1);
		ResponseEntity<Object> expected = ResponseEntity.ok(pm);
		PostDto dto = new PostDto();
		
		when(service.addNewPost(any(PostDto.class), anyInt())).thenReturn(pm);
		
		ResponseEntity<Object> actual = controller.createArticle("1", any(String.class), any(String.class));
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testCreateArticle_null() {
		PostModel pm = makePost();
		pm.setId(1);
		ResponseEntity<Object> expected = ResponseEntity.ok("Article could not be added");
		PostDto dto = new PostDto();
		
		when(service.addNewPost(dto, 3)).thenReturn(pm);
		
		ResponseEntity<Object> actual = controller.createArticle("1", any(String.class), any(String.class));
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllPost(){
		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		ResponseEntity<List<PostArticleModel>> expected = ResponseEntity.ok(posts);
		
		when(service.getAllPosts(anyInt(), anyInt())).thenReturn(posts);
		
		ResponseEntity<List<PostArticleModel>> actual = controller.getAllPost("2", "1");
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllPost_pageNum_Null(){
		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		ResponseEntity<List<PostArticleModel>> expected = ResponseEntity.ok(posts);
		
		when(service.getAllPosts(anyInt(), anyInt())).thenReturn(posts);
		
		ResponseEntity<List<PostArticleModel>> actual = controller.getAllPost("abc", null);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllPostForAuthor() {
		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		ResponseEntity<List<PostArticleModel>> expected = ResponseEntity.ok(posts);
		
		when(service.getAllPostsForUserWithId("2")).thenReturn(posts);
		
		ResponseEntity<List<PostArticleModel>> actual = controller.getAllPostForAuthor("2", "2" , "2");
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllPostForAuthor_Null() {
		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		ResponseEntity<List<PostArticleModel>> expected = ResponseEntity.ok(posts);
		
		when(service.getAllPostsForUserWithId("2")).thenReturn(posts);
		
		ResponseEntity<List<PostArticleModel>> actual = controller.getAllPostForAuthor("2", "2" , null);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllPostForAuthor_Exception() {
		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		ResponseEntity<List<PostArticleModel>> expected = ResponseEntity.ok(posts);
		
		when(service.getAllPostsForUserWithId("2")).thenReturn(posts);
		
		ResponseEntity<List<PostArticleModel>> actual = controller.getAllPostForAuthor("2", "abc" , null);
		Assert.assertEquals(expected, actual);
	}
	
	
	//------------------------
		// Helper creation methods
		//------------------------

		public PostArticleModel makePAM() {
			return makePAM(makePost());
		}
		
		public PostArticleModel makePAM(PostModel post) {
			PostArticleModel pam = new PostArticleModel();
			pam.setPost(post);
			pam.setName("Dobby Elf");
			return pam;
		}
		
		public void createNewUser() {
			UserDto user = new UserDto();
			
			user.setUsername("Dobby The House Elf");
			user.setFirstName("Dobby");
			user.setLastName("Elf");
			user.setEmail("dobby_with_hats@hogwarts.com");
			user.setPassword("harry-potter");
			user.setMatchingPassword("harry-potter");
			
			UserService us = new UserService(userRepo, encoder);
			us.registerNewUserAccount(user);
			
		} 

		
		public PostModel makePost() {
			PostModel post = new PostModel();
			ArticleModel article = makeArticle();

			post.setUserId(1);
			long millis = System.currentTimeMillis();  
			post.setCreateDate(new Date(millis));
			post.setArticle(article);
			
			return post;
		}

		public ArticleModel makeArticle() {
			ArticleModel article = new ArticleModel();
			article.setTitle("another testing article");
			article.setContent("This is yet another article with some nice content... yum ;)");
			article.setAccess("FR");
			
			return article;
		}
}
