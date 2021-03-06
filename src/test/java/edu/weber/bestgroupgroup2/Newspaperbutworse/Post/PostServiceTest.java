package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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


@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

	PostService postService;

	@Mock
	PostRepository postRepository;


	@Before
	public void setup() {
		postService = new PostService(postRepository);
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
	public void testGetAllPosts_NoPosts() {
		List<PostArticleModel> expected = new ArrayList<PostArticleModel>();
		List<PostArticleModel> actual = postService.getAllPosts();	
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testGetAllPosts_HasPosts() {
		List<PostArticleModel> expected = new ArrayList<PostArticleModel>();

		PostDto postDto = makePostDto();
		PostModel post = makePost();
		PostArticleModel pam = makePAM(post);
		expected.add(pam);
		postService.addNewPost(postDto, 1);

		when(postRepository.getAllPosts()).thenReturn(expected);

		List<PostArticleModel> actual = postService.getAllPosts();	
		Assert.assertEquals(expected.size(), actual.size());
	}

	@Test
	public void testGetAllPosts_wrongPage() {
		List<PostArticleModel> expected = new ArrayList<PostArticleModel>();

		PostDto postDto = makePostDto();
		postService.addNewPost(postDto, 1);
		//when(postRepository.getAllPosts()).thenReturn(expected);

		List<PostArticleModel> actual = postService.getAllPosts(2, 100);	
		Assert.assertEquals(expected.size(), actual.size());
	}
	
//	This should never happen based on how the program calls the id
	@Test
	public void testGetAllPostsForUserWithId_FakeID() {
		List<PostArticleModel> expected = new ArrayList<PostArticleModel>();
		
	}

	@Test
	public void testGetAllPostsForUserWithId_ValidID() {
		List<PostArticleModel> expected = new ArrayList<PostArticleModel>();
		
		PostDto dto = makePostDto();
		PostModel post = makePost();
		PostArticleModel pam = makePAM(post);
		PostArticleModel pam2 = makePAM(post);
		expected.add(pam);
		expected.add(pam2);
		
		when(postRepository.getAllPostsForUserWithId("1")).thenReturn(expected);
		
		List<PostArticleModel> actual = postService.getAllPostsForUserWithId("1");	
		Assert.assertEquals(expected.size(), actual.size());
	}
	
	@Test
	public void testGetAllPostsForUserWithId_wrongPage() {
		List<PostArticleModel> expected = new ArrayList<PostArticleModel>();

		when(postRepository.getAllPostsForUserWithId("1")).thenReturn(expected);
		
		List<PostArticleModel> actual = postService.getAllPostsForUserWithId("1", 2, 100);	
		Assert.assertEquals(expected.size(), actual.size());
	}
	
	@Test
	public void testAddNewPost() {

		PostDto dto = new PostDto();
		int userID = 1;

		PostModel actual = postService.addNewPost(dto, userID);

		PostModel expected = postService.getPostByID("1");

		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testEditPost() {
		PostDto dto = new PostDto();
		dto.setTitle("title");
		dto.setContent("something");
		dto.setAccess("FR");
		
		ArticleModel expected = new ArticleModel();
		expected.setTitle("title");
		expected.setContent("something");
		expected.setAccess("FR");
		expected.setPostId(3);
		
		when(postRepository.editArticle(any())).thenReturn(expected);
		
		ArticleModel actual = postService.editPost(dto, 3);
		Assert.assertEquals(expected.getContent(), actual.getContent());
	}

	@Test
	public void testDeletePost_success() {
		when(postRepository.deletePostArticle("1")).thenReturn(true);
		boolean successFlag = postService.deletePost("1");
		Assert.assertEquals(true, successFlag);
	}
	@Test
	public void testDeletePost_failed() {
		when(postRepository.deletePostArticle("1")).thenReturn(false);
		boolean successFlag = postService.deletePost("1");
		Assert.assertEquals(false, successFlag);
	}
	
	@Test
	public void testAddNewComment() {
		Comment expected = new Comment();
		expected.setContent("What an informative article!");
		expected.setParentId(1);
		
		CommentDto dto = new CommentDto();
		dto.setContent("What an informative artcile!");
		dto.setParentId(1);
		
		when(postRepository.saveComment(any(), eq(1))).thenReturn(expected);
		
		Comment actual = postService.addNewComment(dto, 1);
	}

	@Test
	public void testCommentsFromArticle() {
		List<Comment> expected = new ArrayList<Comment>();
		Comment c = new Comment();
		c.setUsername("ancientEvil");
		c.setContent("This article is now cursed");
		c.setPostId(1);
		c.setParentId(1);
		
		when(postRepository.getCommentsFromArticle(1)).thenReturn(expected);
		
		List<Comment> actual = postService.getCommentsFromArticle(1);
		Assert.assertEquals(expected.size(), actual.size());
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
