package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {

	private Logger logger = LoggerFactory.getLogger(PostRepository.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final String INSERT_POST = "INSERT INTO Post(user_id, create_on) VALUES(:userID, :create) ";
	private final String INSERT_ARTICLE	 = "INSERT INTO Article(post_id, title, content, access) VALUES(:postID, :title, :content, :access) ";
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
	
	
	private static PostRepository INSTANCE;
	 
	public PostRepository(){}
	
	@Autowired
	public PostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	
	public PostModel getArticleByID(String id) { 
		try {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("postID", id);

			return namedParameterJdbcTemplate.queryForObject(
					GET_ARTICLE, 
					parameters, 
					(RowMapper<PostModel>) 
					(rs, rowNum) -> {
						PostModel post = new PostModel();
						ArticleModel article = new ArticleModel();
						article.setTitle(rs.getString("title"));
						article.setContent(rs.getString("content"));
						article.setAccess(rs.getString("access"));
						
						post.setArticle(article);
						
						post.setCreateDate(rs.getDate("create_on"));
						post.setModifiedDate(rs.getDate("modified_on"));
						return post;
					});
		} catch(Exception e) {
			logger.error("Error occured: " + e.toString());
			return null;
		}
	}
	
	public PostArticleModel getArticleWithAuthorByID(String id) {
		try {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("postID", id);

			return namedParameterJdbcTemplate.queryForObject(
					GET_ARTICLE_WITH_AUTHOR, 
					parameters, 
					(RowMapper<PostArticleModel>) 
					(rs, rowNum) -> {
						PostArticleModel pam = new PostArticleModel();
						PostModel post = new PostModel();
						ArticleModel article = new ArticleModel();
						
						article.setTitle(rs.getString("title"));
						article.setContent(rs.getString("content"));
						article.setAccess(rs.getString("access"));
						article.setPostId(rs.getInt("p.post_id"));
						
						post.setArticle(article);
						
						post.setCreateDate(rs.getDate("create_on"));
						post.setModifiedDate(rs.getDate("modified_on"));
						post.setUserId(rs.getInt("user_id"));
						
						pam.setName(rs.getString("NAME"));
						pam.setPost(post);
						
						return pam;
					});
		}
		catch(Exception e) {
			logger.error("PostRepository - getArticleWithAuthorByID() " + e.toString());
			return null;
		}
	}
	
	public List<PostArticleModel> getAllPosts() {
		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		
		posts = namedParameterJdbcTemplate.query(GET_ALL_POSTS_WITH_AUTHORS, new ResultSetExtractor<List<PostArticleModel>>(){
		    @Override
		    public List<PostArticleModel> extractData(ResultSet rs) throws SQLException,DataAccessException {
		    	
		    	List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		    	while(rs.next()) {
		    		PostArticleModel pam = new PostArticleModel();
		    		PostModel post = new PostModel();
		    		ArticleModel article = new ArticleModel();
		    		
		    		article.setTitle(rs.getString("title"));
					article.setContent(rs.getString("content"));
					article.setAccess(rs.getString("access"));
					article.setPostId(rs.getInt("p.post_id"));
					
					post.setArticle(article);
					
					Date date = new Date(0);
					post.setCreateDate(date);
					post.setModifiedDate(date);
					post.setUserId(rs.getInt("user_id"));
					
					pam.setName(rs.getString("NAME"));
					pam.setPost(post);
		    		
		    		posts.add(pam);
		    	}
		    	return posts;
		    }
		});
		
		return posts;
	}

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
		} catch(Exception e) 
		{
			logger.error("PostRepository - savePost() " + e.getLocalizedMessage());
		}
		return post;
	}

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
	
	 public static PostRepository getInstance() {
			if(INSTANCE == null){
				INSTANCE = new PostRepository();
			}
			return INSTANCE;
		}

}
