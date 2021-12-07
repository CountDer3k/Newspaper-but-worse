package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

	PostController postController;
	private MockMvc mockMvc;
	@Mock
	PostService postService;
	
	
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
