package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import org.junit.Test;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserDto;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserRepository;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;


@RunWith(MockitoJUnitRunner.class)
public class PostRepositoryTest {

	PostRepository repo;

	@Mock
	NamedParameterJdbcTemplate template;
	@Mock
	KeyHolder keyHolder;
	@Mock
	UserRepository userRepo;
	@Mock
	PasswordEncoder encoder;
	
	@Before
	public void setup() {
		repo = new PostRepository(template);
		keyHolder = new GeneratedKeyHolder();
	}


	@Test
	public void testGetArticleByID() {
		String id = "1";
		mockKeyHolder();

		PostModel post = makePost();
		repo.savePost(post);

		PostModel actual = repo.getArticleByID(id);
		PostModel expected = post;//repo.getArticleByID(id);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testGetArticleWithAuthorByID() {
		String id = "1";
		mockKeyHolder();

		PostModel post = makePost();
		repo.savePost(post);

		PostArticleModel expected = new PostArticleModel();
		expected.setName("Dobby Elf");
		expected.setPost(post);
		
		createNewUser();
		
		PostArticleModel actual = repo.getArticleWithAuthorByID(id);
		Assert.assertEquals(expected, actual);
	}


	@Test
	public void testSavePost() {
		
		mockKeyHolder();
		PostModel post = makePost();
		
		PostModel actual = repo.savePost(post);
		PostModel expected = post;
		
		
		Assert.assertEquals(expected, actual);
	}


	@Test
	public void testSaveArticle(){
		ArticleModel expected = makeArticle();
		mockKeyHolder();
		
		ArticleModel actual = repo.saveArticle(expected);
		Assert.assertEquals(expected, actual);
	}	
	
	
	//------------------------
	// Helper creation methods
	//------------------------
	
	
	public void mockKeyHolder() {
		when(template.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class), Mockito.any(GeneratedKeyHolder.class))).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Map<String, Object> keyMap = new HashMap<String, Object>();
				keyMap.put("", 1);
				((GeneratedKeyHolder)args[2]).getKeyList().add(keyMap);
				return 1;
			}
		}).thenReturn(1);
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
