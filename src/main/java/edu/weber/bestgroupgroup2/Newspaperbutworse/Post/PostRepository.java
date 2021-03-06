package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

@Repository
public class PostRepository {

	private Logger logger = LoggerFactory.getLogger(PostRepository.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate; 

	private final String INSERT_POST = "INSERT INTO Post(user_id, create_on) VALUES(:userID, :create) ";
	private final String INSERT_ARTICLE	 = "INSERT INTO Article(post_id, title, content, access) VALUES(:postID, :title, :content, :access) ";

	private final String INSERT_COMMENT = "INSERT INTO Comment(post_id, content, parent_id)"
			+ " VALUES(:postID, :content, :parentID) ";

	private final String GET_ARTICLE = "SELECT * FROM Post p INNER JOIN Article a ON p.post_id = a.post_id WHERE p.post_id = :postID";
	private final String GET_ARTICLE_WITH_AUTHOR = "SELECT a.title, a.content, a.access, p.post_id, p.create_on, p.modified_on, u.user_id, CONCAT(u.first_name, ' ' ,u.last_name) AS NAME "
			+ "FROM  Post p "
			+ "INNER JOIN Article a ON a.post_id = p.post_id "
			+ "INNER JOIN `User` u  ON p.user_id = u.user_id "
			+ "WHERE p.post_id = :postID ";
	private final String GET_ALL_POSTS_WITH_AUTHORS = "SELECT a.title, a.content, a.access, p.post_id, p.create_on, p.modified_on, u.user_id, CONCAT(u.first_name, ' ' ,u.last_name) AS NAME "
			+ "FROM  Post p "
			+ "INNER JOIN Article a ON a.post_id = p.post_id "
			+ "INNER JOIN `User` u  ON p.user_id = u.user_id ";
	private final String GET_ALL_POSTS_WITH_AUTHORS_FOR_AUTHOR = "SELECT a.title, a.content, a.access, p.post_id, p.create_on, p.modified_on, u.user_id, CONCAT(u.first_name, ' ' ,u.last_name) AS NAME "
			+ "FROM  Post p "
			+ "INNER JOIN Article a ON a.post_id = p.post_id "
			+ "INNER JOIN `User` u  ON p.user_id = u.user_id "
			+ "WHERE p.user_id = :authorId ";
	private final String EDIT_ARTICLE = 
			"UPDATE Article "
					+ "SET title = :title, content = :content, access = :access "
					+ "WHERE post_id = :postID";
	private final String DELETE_ARTICLE = "DELETE FROM Article WHERE post_id = :postID ";
	private final String DELETE_POST = "DELETE FROM Post WHERE post_id = :postID ";
	private final String DELETE_COMMENTS = "DELETE FROM Comment WHERE parent_id = :postID";

	private final String DE_PA = "DELETE FROM Article WHERE post_id = :aID; DELETE FROM Post WHERE post_id :pID ";

	private static PostRepository INSTANCE;

	public PostRepository(){}

	@Autowired
	public PostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}


	//----------------
	// Get methods
	//----------------
	@Log
	public PostModel getArticleByID(String id) { 
		try {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("postID", id);

			return (PostModel)namedParameterJdbcTemplate.queryForObject(
					GET_ARTICLE, 
					parameters, new PostRowMapper());
		} catch(Exception e) {
			logger.error("Error occured: " + e.toString());
			return null;
		}
	}

	//@SuppressWarnings("unchecked")
	@Log
	public PostArticleModel getArticleWithAuthorByID(String id) {
		try {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("postID", id);

			PostArticleModel pam = (PostArticleModel) namedParameterJdbcTemplate.queryForObject(
					GET_ARTICLE_WITH_AUTHOR, parameters, new PostRowMapperPAM());
			return pam;
		}
		catch(Exception e) {
			logger.error("PostRepository - getArticleWithAuthorByID() " + e.toString());
			return null;
		}
	}

	@Log
	public List<PostArticleModel> getAllPosts() {
		return (List<PostArticleModel>) namedParameterJdbcTemplate.query(GET_ALL_POSTS_WITH_AUTHORS, new PostRowMapperPAM());
	}

	@Log
	public List<Comment> getCommentsFromArticle(int articleId){
		
		List<Comment> comments = new ArrayList<Comment>();

		String getCommentsSQL = "SELECT * FROM Comment WHERE parent_id = " + articleId + ";";
		String GET_COMMENTS_FROM_POST_WITH_USERS = "SELECT c.post_id, c.content, c.parent_id, u.user_id, u.username "
				+ "FROM  Post p "
				+ "INNER JOIN Comment c ON c.post_id = p.post_id "
				+ "INNER JOIN `User` u  ON p.user_id = u.user_id "
				+ "WHERE c.parent_id = " + articleId + ";";

		comments = namedParameterJdbcTemplate.query(GET_COMMENTS_FROM_POST_WITH_USERS, new CommentRowMapper());

		return comments;
	}

	@Log
	public List<PostArticleModel> getAllPostsForUserWithId(String authorId) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("authorId", authorId);
		
		return (List<PostArticleModel>) namedParameterJdbcTemplate.query(GET_ALL_POSTS_WITH_AUTHORS_FOR_AUTHOR, parameters, new PostRowMapperPAM());
	}

	//----------------
	// Save methods
	//----------------
	@Log
	public PostModel savePost(PostModel post) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("userID", post.getUserId())
					.addValue("create", post.getCreateDate());

			namedParameterJdbcTemplate.update(INSERT_POST, parameters, keyHolder);

			ArticleModel article = post.getArticle();
			int PostID = keyHolder.getKey().intValue();
			article.setPostId(PostID);
			saveArticle(article);
			return post;
		} catch(Exception e) 
		{
			logger.error("PostRepository - savePost() " + e.getLocalizedMessage());
		}
		return null;
	}

	@Log
	public ArticleModel saveArticle(ArticleModel article) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("postID", article.getPostId())
					.addValue("title", article.getTitle())
					.addValue("content", article.getContent())
					.addValue("access", "FR");
			namedParameterJdbcTemplate.update(INSERT_ARTICLE, parameters, keyHolder);

		} catch(Exception e) 
		{
			logger.error("PostRepository - saveArticle() "+e.toString());
		}
		return article;
	}

	@Log
	public Comment saveComment(Comment comment, int userID) {
		//This seems to only get the date not the time in the db
		long millis = System.currentTimeMillis();  
		java.sql.Date date = new java.sql.Date(millis);  
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("userID", userID)
					.addValue("create", date);

			namedParameterJdbcTemplate.update(INSERT_POST, parameters, keyHolder);

			Number key = keyHolder.getKey();
			int PostID = key.intValue();
			comment.setPostId(PostID);

			keyHolder = new GeneratedKeyHolder();
			parameters = new MapSqlParameterSource()
					.addValue("postID", comment.getPostId())
					.addValue("content", comment.getContent())
					.addValue("parentID", comment.getParentId());

			namedParameterJdbcTemplate.update(INSERT_COMMENT, parameters, keyHolder);

		}catch(Exception e) {
			logger.error("PostRepository - saveComment() "+e.toString());
		}
		return comment;
	}

	//----------------
	// Edit methods
	//----------------
	@Log
	public ArticleModel editArticle(ArticleModel article) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("postID", article.getPostId())
					.addValue("title", article.getTitle())
					.addValue("content", article.getContent())
					.addValue("access", article.getAccess());
			namedParameterJdbcTemplate.update(EDIT_ARTICLE, parameters);
		} catch(Exception e) 
		{
			logger.error("PostRepository - editArticle() "+e.toString());
		}
		return article;
	}

	//----------------
	// Delete methods
	//----------------

	// Returns true if the post was successfully deleted
	@Log
	public boolean deletePostArticle(String id) {
		try {
			deleteComments(id);
			deleteArticle(id);
			deletePost(id);
			
			return true;
		} catch(Exception e) {
			return false;
		}

	}

	@Log
	public boolean deleteArticle(String id) {
		boolean success = false;
		try {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("postID", id);

			int result = namedParameterJdbcTemplate.update(DELETE_ARTICLE, parameters);

			success = result == 1 ? true : false;

		} catch(Exception e) { 
			logger.error("Error occured: " + e.toString());
		}
		return success;
	}

	@Log
	public boolean deletePost(String id) {
		boolean success = false;
		try {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("postID", id);

			int result = namedParameterJdbcTemplate.update(DELETE_POST, parameters);

			success = result == 1 ? true : false;
		} catch(Exception e) {
			logger.error("Error occured: " + e.toString());
		}
		return success;
	}
	
	@Log
	public boolean deleteComments(String id) {
		boolean success = false;
		
		try {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("postID", id);
			
			int result = namedParameterJdbcTemplate.update(DELETE_COMMENTS, parameters);
			
		}catch(Exception e) {
			logger.error("Error occured: " + e.toString());
		}
		
		return success;
	}




	public static PostRepository getInstance() {
		if(INSTANCE == null){
			INSTANCE = new PostRepository();
		}
		return INSTANCE;
	}

}
