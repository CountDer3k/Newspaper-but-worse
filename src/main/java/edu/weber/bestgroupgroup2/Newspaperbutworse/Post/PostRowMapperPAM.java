package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PostRowMapperPAM implements RowMapper{
	@Override
	public PostArticleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		PostArticleModel pam = new PostArticleModel();
		PostModel post = new PostModel();
		ArticleModel article = new ArticleModel();
		
		article.setTitle(rs.getString("title"));
		article.setContent(rs.getString("content"));
		article.setAccess(rs.getString("access"));
		article.setPostId(rs.getInt("post_id"));
		
		post.setArticle(article);
		
		Date date = new Date(0);
		post.setCreateDate(rs.getDate("create_on"));
		post.setModifiedDate(rs.getDate("modified_on"));
		post.setId(rs.getInt("post_id"));
		post.setUserId(rs.getInt("user_id"));
		
		pam.setName(rs.getString("NAME"));
		pam.setPost(post);
		
		return pam;
	}
}
