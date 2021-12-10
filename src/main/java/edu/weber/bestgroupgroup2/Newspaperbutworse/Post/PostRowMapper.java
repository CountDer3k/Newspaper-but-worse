package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PostRowMapper implements RowMapper{

	@Override
	public PostModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		PostModel post = new PostModel();
		ArticleModel article = new ArticleModel();
		article.setTitle(rs.getString("title"));
		article.setContent(rs.getString("content"));
		article.setAccess(rs.getString("access"));
		post.setId(rs.getInt("post_id"));
		
		post.setArticle(article);
		post.setId(rs.getInt("post_id"));
		post.setUserId(rs.getInt("user_id"));
		post.setCreateDate(rs.getDate("create_on"));
		post.setModifiedDate(rs.getDate("modified_on"));
		return post;
	}
}
