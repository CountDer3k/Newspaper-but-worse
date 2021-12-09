package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CommentRowMapper implements RowMapper{

	@Override
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Comment comment = new Comment();

		comment.setPostId(rs.getInt("post_id"));
		comment.setParentId(rs.getInt("parent_id"));
		comment.setContent(rs.getString("content"));
		comment.setUsername(rs.getString("username"));
		
		return comment;
	}

}
