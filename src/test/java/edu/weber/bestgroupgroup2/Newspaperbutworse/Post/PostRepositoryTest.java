package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserDto;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserRepository;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;

@RunWith(MockitoJUnitRunner.class)
public class PostRepositoryTest {

	PostRepository repo;
	PostRowMapper mapper;
	PostRowMapperPAM mapperPAM;
	CommentRowMapper mapperComment;
	@Mock
	NamedParameterJdbcTemplate template;
	@Mock
	KeyHolder keyHolder;
	@Mock
	UserRepository userRepo;
	@Mock
	PasswordEncoder encoder;
	@Mock
	ResultSetExtractor<List<PostArticleModel>> rse;
	@Mock
	ResultSet rs;
	@Mock
	PostRowMapperPAM rowPAM;

	
	
	@Before
	public void setup() {
		repo = new PostRepository(template);
		keyHolder = new GeneratedKeyHolder();
		mapper = new PostRowMapper();
		mapperPAM = new PostRowMapperPAM();
		mapperComment = new CommentRowMapper();
	}
 
	
	@Test
	public void testGetArticleByID() throws SQLException {
		String id = "1";
		mockKeyHolder(); 

		PostModel post = makePost();
		repo.savePost(post);
		
		when(template.queryForObject(ArgumentMatchers.any(String.class), ArgumentMatchers.any(SqlParameterSource.class), (RowMapper<PostModel>) ArgumentMatchers.any(RowMapper.class)))
		.thenReturn(post);
		
		PostModel actual = repo.getArticleByID(id);
		PostModel expected = post;

		Assert.assertEquals(expected, actual);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetArticleWithAuthorByID() {
		PostArticleModel expected = makePAM();
		
		when(template.queryForObject(any(String.class), any(SqlParameterSource.class), any(PostRowMapperPAM.class))).thenReturn(expected);
		
		PostArticleModel actual = repo.getArticleWithAuthorByID("1");
		Assert.assertEquals(expected, actual);
	}

	
	@Test
	public void testGetAllPosts() {
		List<PostArticleModel> expected = new ArrayList<PostArticleModel>();
		
		mockKeyHolder();
		PostModel post = makePost();
		repo.savePost(post);
		
		//when(template.query(ArgumentMatchers.any(String.class), ArgumentMatchers.any(ResultSetExtractor.class))).thenReturn(expected);
				
		List<PostArticleModel> actual = repo.getAllPosts();

		
		Assert.assertEquals(expected.size(), actual.size());
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
	
	
	@Test
	public void testEditArticle() {
		ArticleModel expected = new ArticleModel();
		expected.setPostId(1);
		expected.setTitle("Title");
		expected.setContent("Something");
		expected.setAccess("FR");
		
		//when(repo.editArticle(expected)).thenReturn(expected);
		
		ArticleModel actual = repo.editArticle(expected);
		Assert.assertEquals(expected.getContent(), actual.getContent());
	}
	
	@Test
	public void testDeletePostArticle() {
		Assert.assertTrue(repo.deletePostArticle("1"));
	}
	
	@Test
	public void testDeleteArticle() {
		when(template.update(any(String.class), any(SqlParameterSource.class))).thenReturn(1);
		
		boolean actual = repo.deleteArticle("1");
		Assert.assertEquals(true, actual);
	}
	
	@Test
	public void testDeletePost() {
		when(template.update(any(String.class), any(SqlParameterSource.class))).thenReturn(1);
		
		boolean actual = repo.deletePost("1");
		Assert.assertEquals(true, actual);
	}
	
	
	@Test
	public void testSaveComment() {
		Comment expected = new Comment();
		expected.setContent("what an article!");
		
		//when(template.update(any(String.class), any(SqlParameterSource.class), keyHolder)).thenReturn(expected);
		when(keyHolder.getKey()).thenReturn((Number) 2);

		Comment actual = repo.saveComment(expected, 1);
		Assert.assertEquals(expected.getContent(), actual.getContent());
	}
	
	@Test
	public void testGetCommentsFromArticle() {
		List<Comment> comments = new ArrayList<Comment>();
		
		
		List<Comment> actual = repo.getCommentsFromArticle(1);
		Assert.assertEquals(comments.size(), actual.size());
	}
	
	@Test
	public void testGetAllPostsForUserWithId() {
		List<PostArticleModel> expected = new ArrayList<PostArticleModel>();
		
		
		List<PostArticleModel> actual = repo.getAllPostsForUserWithId("1");
		Assert.assertEquals(expected.size(), actual.size());
	}
	
	
	//----------
	// RowMapper
	//----------
	@Test
	public void testRowMapper() throws SQLException {
		PostModel expected = makePost();
		
		when(rs.getString("title")).thenReturn(expected.getArticle().getTitle());
		
		PostModel actual = mapper.mapRow(rs, 1);
		
		Assert.assertEquals(expected.getArticle().getTitle(), actual.getArticle().getTitle());
	}
	
	@Test
	public void testRowMapperPAM() throws SQLException {
		PostArticleModel expected = makePAM();
	
		when(rs.getString("title")).thenReturn(expected.getPost().getArticle().getTitle());
		
		PostArticleModel actual = mapperPAM.mapRow(rs, 1);
		
		Assert.assertEquals(expected.getPost().getArticle().getTitle(), actual.getPost().getArticle().getTitle());
	}
	
	@Test
	public void testCommentRowMapper() throws SQLException{
		Comment comment = new Comment();
		comment.setContent("Something");
		
		when(rs.getString("content")).thenReturn(comment.getContent());
		
		Comment actual = mapperComment.mapRow(rs, 0);
		Assert.assertEquals(comment.getContent(), actual.getContent());
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
