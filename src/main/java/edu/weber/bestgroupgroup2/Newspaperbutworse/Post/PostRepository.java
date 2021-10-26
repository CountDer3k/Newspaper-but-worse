package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


@Repository
public class PostRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	//private static final Map<String, PostModel> posts = new HashMap<>();
	Logger logger = LoggerFactory.getLogger(PostRepository.class);

	@Autowired
	public PostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<PostModel> getAllPosts(){
//		String sql = "SELECT * FROM Post p INNER JOIN Article a ON p.post_id = a.post_id ";
//		List<PostModel> posts = namedParameterJdbcTemplate.query(sql, new 
//				BeanPropertyRowMapper<>(PostModel.class));
//
//		return posts;
		return null;
	}

//	public List<PostModel> getPosts(String access){
//		List<PostModel> posts = new ArrayList<PostModel>();
//		
//		Map<String, Object> params = new HashMap<>();
//		// Get all posts that correspond to the access type of a user
//		params.put("access", access);
//		
//		Object o = namedParameterJdbcTemplate.queryForObject(GET_POSTS, params, (RowMapper<PostModel>) (rs, rowNum) -> {}); 
//			
//		return posts; 	
//	}	

	public boolean POST_post(PostModel post) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("userID", post.getUserId())
					.addValue("create", post.getCreateDate());
			namedParameterJdbcTemplate.update(INSERT_POST, params, keyHolder);

			// gets post_id to give to the article
			ArticleModel article = post.getArticle();
			int postID = keyHolder.getKey().intValue();
			article.setPostId(postID);

			// POSTS to the article table
			POST_article(article);

		} catch(Exception e) {
			logger.error("PostRepository - POST_post(): " + e.toString());
			return false;
		}
		return true;
	}

	public boolean POST_article(ArticleModel article) throws PostArticleException {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("postID", article.getPostId())
					.addValue("title", article.getTitle())
					.addValue("content", article.getContent())
					.addValue("access", article.getAccess());
			namedParameterJdbcTemplate.update(INSERT_ARTICLE, params, keyHolder);
		} catch(Exception e) {
			throw new PostArticleException(e.toString());
		}
		return true;
	}


	// Custom Exception
	public class PostArticleException extends Exception{
		PostArticleException(String msg){
			super(msg);
		}
	}


	private final String GET_POSTS = "SELECT * FROM Post p INNER JOIN Article a ON p.post_id = a.post_id ";
	private final String INSERT_POST = "INSERT INTO post(user_id, create_on) "
			+ "VALUES(:userID, :create) ";
	private final String INSERT_ARTICLE = "INSERT INTO article(post_id, title, content, access) "
			+ "VALUES(:postID, :title, :content, :access) ";


}
