package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;

@Repository
public class PostRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private static final Map<String, PostModel> posts = new HashMap<>();

	@Autowired
	public PostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<PostModel> getAllPosts(){
		String sql = "SELECT * FROM POSTS ";
		List<PostModel> posts = namedParameterJdbcTemplate.query(sql, new 
				BeanPropertyRowMapper<>(PostModel.class));

		return posts;
	}
}
