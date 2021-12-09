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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
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
public class NewspaperButWorseApplicationTest {

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
		jwtTokenProvider = new JwtTokenProvider(userService);
		secConfig = new SecurityConfig(appContext, jwtTokenProvider);
		homeController = new HomeController(postService);
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
//		Duration ttl = Duration.ofMinutes(30);
//		Date now = new Date();
//		Date expiration = new Date(now.getTime() + ttl.toMillis());

		String token = jwtTokenProvider.createToken(user, null);

		when(jwtTokenProvider.getJwtTokenFromRequest(request)).thenReturn(token);

		Assert.assertNull(jwtTokenProvider.getAuthentication(request));

	}

	@Test
	public void testCreateTokenBypassAuth() {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("Pass");
//		Duration ttl = Duration.ofMinutes(30);
//		Date now = new Date();
//		Date expiration = new Date(now.getTime() + ttl.toMillis());

		String token = jwtTokenProvider.createToken(user, null);

		jwtTokenProvider.validateToken(token);
		jwtTokenProvider.validateToken("haha I am a token lol");

		Assert.assertNull(jwtTokenProvider.getAuthentication(token));

	}

	@Test
	public void testShowHomeView() {
		ModelAndView expected = new ModelAndView("index");

		ArrayList<PostArticleModel> list = new ArrayList<PostArticleModel>();
		PostArticleModel pam = makePAM();
		list.add(pam);

		when(postService.getAllPosts()).thenReturn(list);

		ModelAndView actual = homeController.home();

		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}
	
	@Test public void testShowHomeViewNoArticles() {
		ModelAndView expected = new ModelAndView("index");

		ArrayList<PostArticleModel> list = new ArrayList<PostArticleModel>();
		
		when(postService.getAllPosts()).thenReturn(list);

		ModelAndView actual = homeController.home();

		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}

//	@Test
//	public void testSecurityConfigMethod() {
//		
//		HttpSecurity httpSec = null;
//		try {
//			secConfig.configure(httpSec);
//		} catch (Exception e) {
//			System.out.println();
//		}
//	}

	// ------------------------
	// Helper creation methods
	// ------------------------

	public PostArticleModel makePAM() {
		return makePAM(makePost());
	}

	public PostArticleModel makePAM(PostModel post) {
		PostArticleModel pam = new PostArticleModel();
		pam.setPost(post);
		pam.setName("Dobby Elf");
		return pam;
	}
	
	public PostModel makePost() {
		PostModel post = new PostModel();
		ArticleModel article = makeArticle();

		post.setUserId(1);
		long millis = System.currentTimeMillis();  
//		post.setCreateDate(new Date(millis));
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
