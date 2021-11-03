package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import org.junit.Test;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

	
	PostService postService;
	
	@Mock
	PostRepository postRepository;
	
	 
	
	@Before
	public void setup() {
		postService = new PostService(postRepository);
		
		// add a new user here
	}

	
	@Test
	public void testIsValidPostTrue() {
		PostDto post = new PostDto();
		post.setTitle("Testing title");
		post.setContent("Some information content for testing");
		post.setAccess("FR");
		
		boolean isValid = postService.isValidPost(post);
		
		Assert.assertTrue(isValid);
	}
	
	@Test
	public void testIsValidPostFalse_Title() {
		PostDto post = new PostDto();
		post.setTitle("");
		post.setContent("Some information content for testing");
		post.setAccess("FR");
		boolean isValid = postService.isValidPost(post);
		
		Assert.assertFalse(isValid);
	}
	
	@Test
	public void testIsValidPostFalse_Content() {
		PostDto post = new PostDto();
		post.setTitle("Testing title");
		post.setContent("");
		post.setAccess("FR");
		boolean isValid = postService.isValidPost(post);
		
		Assert.assertFalse(isValid);
	}
	
	@Test
	public void testIsValidPostFalse_Access() {
		PostDto post = new PostDto();
		post.setTitle("Testing title");
		post.setContent("Some information content for testing");
		post.setAccess("");
		boolean isValid = postService.isValidPost(post);
		
		Assert.assertFalse(isValid);
	}
	
	@Test
	public void testGetPostByID_Valid() {
		PostDto dto = new PostDto();
		int userID = 1;
		
		PostModel expected = postService.addNewPost(dto, userID);
		
		PostModel actual = postService.getPostByID("1");
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testgetPostWithAuthorByID() {
		String id = "1";
		
		PostArticleModel pam = new PostArticleModel();
		pam.setName("Rita Skeeter");
		
		when(postRepository.getArticleWithAuthorByID(id)).thenReturn(pam);
		
		PostArticleModel actual = postService.getPostWithAuthorByID(id);
		
		String name = actual.getName();
		Assert.assertEquals(name, "Rita Skeeter");
	}
	
	
	@Test
	public void testGetAllPosts() {
		Assert.assertFalse(true);
	}
	
	@Test
	public void testAddNewPost() {
		
		PostDto dto = new PostDto();
		int userID = 1;
		
		PostModel actual = postService.addNewPost(dto, userID);
		
		PostModel expected = postService.getPostByID("1");
		
		Assert.assertEquals(expected, actual);
	}
	
	 
	
	
	
	
	
}
