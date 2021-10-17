package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private static final Map<String, User> users = new HashMap<>();
	
	@Autowired
	public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public User getUserByEmail(String email) {
		
		String sql = "SELECT * FROM USER WHERE email = :email";
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
		// TODO Auto-generated method stub
		return user;
	}

}
