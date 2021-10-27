package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private final String INSERT_USER = "INSERT INTO User (username, password, email, first_name, last_name) VALUES (:username, :password, :email, :firstName, :lastName)";
	private final String SELECT_USER_WITH_ROLES = "SELECT * FROM User";
	
	
	@Autowired
	public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public User getUserByUsername(String username) {
		String sql = SELECT_USER_WITH_ROLES + " WHERE username = :username";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("username", username);
		UserRowCallbackHandler callbackHandler = new UserRowCallbackHandler();
		namedParameterJdbcTemplate.query(sql, parameters, callbackHandler);
		return callbackHandler.getUser();
	}

	public User getUserByEmail(String email) {
		String sql = "SELECT * FROM User WHERE email = :email";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		return namedParameterJdbcTemplate.queryForObject(sql, parameters, (RowMapper<User>) (rs, rowNum) -> {
			User user = new User();
			user.setUserId(rs.getInt("USER_ID"));
			user.setUsername(rs.getString("USERNAME"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setEmail(rs.getString("EMAIL"));
			user.setFirstName(rs.getString("FIRST_NAME"));
			user.setLastName(rs.getString("LAST_NAME"));
			return user;
		});
	}

	public User save(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("username", user.getUsername())
				.addValue("password", user.getPassword())
				.addValue("email", user.getEmail())
				.addValue("firstName", user.getFirstName())
				.addValue("lastName", user.getLastName());
		
		namedParameterJdbcTemplate.update(INSERT_USER, parameters, keyHolder);
		user.setUserId(keyHolder.getKey().intValue());
		return user;
	}

}
