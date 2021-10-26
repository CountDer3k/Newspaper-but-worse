package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.util.HashMap;
import java.util.Map;

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

	private Logger logger = LoggerFactory.getLogger(PostRepository.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final String INSERT_QUERY = "INSERT INTO User (username, password, email, first_name, last_name) VALUES (:username, :password, :email, :firstName, :lastName)";
	private final String INSERT_POST = "INSERT INTO Post(user_id, create_on) VALUES(:userID, :create) ";
	private final String INSERT_ARTICLE	 = "INSERT INTO Article(post_id, title, content, access) VALUES(:postID, :title, :content, :access) ";

	@Autowired
	public PostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}


	public PostModel savePost(PostModel post) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("userID", post.getUserId())
					.addValue("create", post.getCreateDate());

			namedParameterJdbcTemplate.update(INSERT_POST, parameters, keyHolder);

			if(post != null) {
				ArticleModel article = post.getArticle();
				int PostID = keyHolder.getKey().intValue();
				logger.error("Post ID: "+PostID);
				article.setPostId(PostID);
				logger.equals("Post ID stroed: " + article.getPostId());
				saveArticle(article);
			} else {
				logger.error("post is empty");
			}
		} catch(Exception e) 
		{
			logger.error("PostRepository - savePost() " + e.getLocalizedMessage() + e.getStackTrace());
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

}
