package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
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

	private final String INSERT_POST = "INSERT INTO Post(user_id, create_on) VALUES(:userID, :create) ";
	private final String INSERT_ARTICLE	 = "INSERT INTO Article(post_id, title, content, access) VALUES(:postID, :title, :content, :access) ";
	private final String GET_ARTICLE = "SELECT * FROM Post p INNER JOIN Article a ON p.post_id = a.post_id WHERE p.post_id = :postID";

	@Autowired
	public PostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public ArticleModel getArticleByID(String id) {
		try {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("postID", id);

			return namedParameterJdbcTemplate.queryForObject(GET_ARTICLE, parameters, (RowMapper<ArticleModel>) (rs, rowNum) -> {
				ArticleModel article = new ArticleModel();
				article.setTitle(rs.getString("title"));
				article.setContent(rs.getString("content"));
				article.setAccess(rs.getString("access"));
				return article;
			});
		} catch(Exception e) {
			logger.error("Error occured: " + e.toString());
			return null;
		}
	}


	//testing for all
	public PostModel getArticleByID2(String id) {
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
						// ?? This should be pulled from logged in user
						int userID = 1;
						post.setUserId(userID);
						// ?? store create_on & modified_on
						return post;
					});
		} catch(Exception e) {
			logger.error("Error occured: " + e.toString());
			return null;
		}
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
