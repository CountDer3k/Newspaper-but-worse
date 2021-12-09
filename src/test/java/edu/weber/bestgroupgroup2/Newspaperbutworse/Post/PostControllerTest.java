package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserDto;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

	PostController postController;
	private MockMvc mockMvc;
	@Mock
	PostService postService;
	@Mock
	BindingResult bind;
	@Mock 
	HttpServletRequest request;
	@Mock 
	WebRequest wRequest;
	@Mock
	Errors errors;
	@Mock
	Authentication authentication;

	@Before
	public void setup() {
		postController = new PostController(postService);
		mockMvc = MockMvcBuilders.standaloneSetup(this.postController).build();
	}

	@Test
	public void testShowArticleView() {
		ModelAndView expected = new ModelAndView("article/article");

		PostArticleModel pam = makePAM();
		List<Comment> comments = new ArrayList<Comment>();
		comments.add(new Comment());

		when(postService.getPostWithAuthorByID(any(String.class))).thenReturn(pam);
		when(postService.getCommentsFromArticle(any(int.class))).thenReturn(null);

		ModelAndView actual = postController.showArticleView("1");

		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}

	@Test
	public void testShowRegistrationForm() {
		ModelAndView expected = new ModelAndView("article/articleForm");

		ModelAndView actual = postController.showRegistrationForm(null);

		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}

	@Test
	public void testEditableArticleDirect() {
		ModelAndView expected = new ModelAndView("article/article_edit");

		ModelAndView actual = postController.showEditableArticleDirect(any(String.class));

		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}

	@Test
	public void testShowAuthorArticle() {
		ModelAndView expected = new ModelAndView("article/article_edit");

		ModelAndView actual = postController.showEditableArticleDirect(any(String.class));

		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}

	@Test
	public void testUpdateArticleView() {
		ModelAndView expected = new ModelAndView("author/articleList");

		PostDto dto = new PostDto();

		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		when(postService.getAllPostsForUserWithId(any(String.class))).thenReturn(posts);
		when(bind.hasErrors()).thenReturn(false);
		when(authentication.getPrincipal()).thenReturn(new User());
		when(postService.isValidPost(any(PostDto.class))).thenReturn(true);


		ModelAndView actual = postController.updateArticleView(dto, bind, request, errors, authentication, "2");

		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}
	
	
	
	@Test
	public void testRegisterUserArticle() {
		ModelAndView expected = new ModelAndView("redirect:/");
		PostDto dto = new PostDto();
		User u = new User();
		u.setUserId(2);
		PostModel pm = new PostModel();
		
		when(bind.hasErrors()).thenReturn(false);	
		when(postService.isValidPost(dto)).thenReturn(true);
		when(authentication.getPrincipal()).thenReturn(u);
		when(postService.addNewPost(dto, 2)).thenReturn(pm);
		
		ModelAndView actual = postController.registerUserArticle(dto, bind, request, errors, authentication);
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}
	
	@Test
	public void testRegisterUserArticle_error() {
		ModelAndView expected = new ModelAndView("error");
		PostDto dto = new PostDto();
		
		when(bind.hasErrors()).thenReturn(true);		
		
		ModelAndView actual = postController.registerUserArticle(dto, bind, request, errors, authentication);
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}
	
	@Test
	public void testAddComment() {
		ModelAndView expected = new ModelAndView("redirect:/articles/articleNum/0");
		CommentDto dto = new CommentDto();
		User u = new User();
		u.setUserId(0);
		Comment comment = new Comment();
		
		when(bind.hasErrors()).thenReturn(false);	
		when(authentication.getPrincipal()).thenReturn(u);
		when(postService.addNewComment(dto, 0)).thenReturn(comment);
		
		ModelAndView actual = postController.addComment(dto, bind, request, errors, authentication);
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}
	
	@Test
	public void testAddComment_bindError() {
		ModelAndView expected = new ModelAndView("error");
		CommentDto dto = new CommentDto();
		
		when(bind.hasErrors()).thenReturn(true);		
		
		ModelAndView actual = postController.addComment(dto, bind, request, errors, authentication);
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}
	
	@Test
	public void testRegisterUserArticle_error2() {
		ModelAndView expected = new ModelAndView("/error");
		PostDto dto = new PostDto();
		User u = new User();
		u.setUserId(2);
		PostModel pm = new PostModel();
		
		when(bind.hasErrors()).thenReturn(false);	
		when(postService.isValidPost(dto)).thenReturn(false);
		when(authentication.getPrincipal()).thenReturn(u);
		when(postService.addNewPost(dto, 2)).thenReturn(pm);
		
		ModelAndView actual = postController.registerUserArticle(dto, bind, request, errors, authentication);
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}
	
	@Test
	public void testShowAuthorArticles() {
		ModelAndView expected = new ModelAndView("author/articleList");
		User u = new User();
		u.setUserId(2);
		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		when(authentication.getPrincipal()).thenReturn(u);
		when(postService.getAllPostsForUserWithId(any(String.class))).thenReturn(posts);
		
		
		ModelAndView actual = postController.showAuthorArticles(authentication);
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}

	@Test
	public void testShowEditArticleView() {
		ModelAndView expected = new ModelAndView("article/article_edit");
		
		PostArticleModel pam = makePAM();
		when(postService.getPostWithAuthorByID("2")).thenReturn(pam);
		
		
		ModelAndView actual = postController.showEditArticleView("2", wRequest);
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}

	@Test(expected = Exception.class)
	public void testShowEditArticleView_Exception() {
		ModelAndView expected = new ModelAndView("error");
		
		PostArticleModel pam = makePAM();
		when(postService.getPostWithAuthorByID("2")).thenThrow(new Exception());
		
		
		ModelAndView actual = postController.showEditArticleView("2", wRequest);
		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}
	
	@Test
	public void testUpdateArticleView_errors() {
		ModelAndView expected = new ModelAndView("error");

		PostDto dto = new PostDto();

		when(bind.hasErrors()).thenReturn(true);

		ModelAndView actual = postController.updateArticleView(dto, bind, request, errors, authentication, "2");

		Assert.assertEquals(expected.getViewName(), actual.getViewName());
	}

	@Test
	public void testDeleteArticle() {
		String expected = "index";
		String actual = postController.deleteArticle("2");
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

	public PostModel makePost() {
		PostModel post = new PostModel();
		ArticleModel article = makeArticle();

		post.setUserId(1);
		long millis = System.currentTimeMillis();  
		post.setCreateDate(new Date(millis));
		post.setArticle(article);

		return post;
	}

	public PostDto makePostDto() {
		PostDto article = new PostDto();

		article.setTitle("another testing article");
		article.setContent("This is yet another article with some nice content... yum ;)");
		article.setAccess("FR");

		return article;
	}

	public ArticleModel makeArticle() {
		ArticleModel article = new ArticleModel();
		article.setTitle("another testing article");
		article.setContent("This is yet another article with some nice content... yum ;)");
		article.setAccess("FR");

		return article;
	}
}
