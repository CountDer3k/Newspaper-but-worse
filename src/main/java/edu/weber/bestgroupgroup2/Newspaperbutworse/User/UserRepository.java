package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.weber.bestgroupgroup2.Newspaperbutworse.REST.RESTPostController;
import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

@Repository
public class UserRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(UserRepository.class);

	private final String INSERT_USER = "INSERT INTO User (username, password, email, first_name, last_name, created_on, modified_on) VALUES (:username, :password, :email, :firstName, :lastName, :createdOn, :modifiedOn)";
	private final String INSERT_USER_ROLE = "INSERT INTO User_Role (user_id, role_id) VALUES (:userId, :roleId);";
	private final String SELECT_USER_WITH_ROLES = "SELECT \n" + 
			"    u.user_id,\n" + 
			"    u.username,\n" + 
			"    u.password,\n" + 
			"    u.email,\n" + 
			"    u.first_name,\n" + 
			"    u.last_name,\n" + 
			"    u.created_on,\n" + 
			"    u.modified_on,\n" + 
			"    r.role_id,\n" + 
			"    r.role_name,\n" + 
			"    r.created_on,\n" + 
			"    r.modified_on,\n" + 
			"    p.perm_id,\n" + 
			"    p.perm_name,\n" + 
			"    p.created_on,\n" + 
			"    p.modified_on\n" + 
			"FROM User AS u\n" + 
			"    JOIN User_Role AS ur ON u.user_id = ur.user_id\n" + 
			"    JOIN Role AS r ON ur.role_id = r.role_id\n" + 
			"    JOIN Role_Permission AS rp ON r.role_id = rp.role_id\n" + 
			"    JOIN Permission AS p ON rp.perm_id = p.perm_id";
	private final String UPDATE_USER = "UPDATE User SET first_name = :firstName, last_name = :lastName, email = :email WHERE user_id = :userId";

	@Autowired
	public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Log
	public List<User> getAllUsers() {
		String sql = SELECT_USER_WITH_ROLES + ";";
		UserRowCallbackHandler callbackHandler = new UserRowCallbackHandler();
		namedParameterJdbcTemplate.query(sql, callbackHandler);
		return callbackHandler.getUserList();
	}

	@Log
	public User getUserByUsername(String username) {
		String sql = SELECT_USER_WITH_ROLES + " WHERE username = :username;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("username", username);
		UserRowCallbackHandler callbackHandler = new UserRowCallbackHandler();
		namedParameterJdbcTemplate.query(sql, parameters, callbackHandler);
		return callbackHandler.getUser();
	}

	@Log
	public User getUserByEmail(String email) {
		String sql = SELECT_USER_WITH_ROLES + " WHERE email = :email";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("email", email);
		UserRowCallbackHandler callbackHandler = new UserRowCallbackHandler();
		namedParameterJdbcTemplate.query(sql, parameters, callbackHandler);
		return callbackHandler.getUser();
	}

	@Log
	public User getUserByID(int id) {
		String sql = SELECT_USER_WITH_ROLES + " WHERE u.user_id = :userId;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("userId", id);
		UserRowCallbackHandler callbackHandler = new UserRowCallbackHandler();
		namedParameterJdbcTemplate.query(sql, parameters, callbackHandler);
		return callbackHandler.getUser();
	}

	@Log
	public User save(User user) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("username", user.getUsername())
					.addValue("password", user.getPassword())
					.addValue("email", user.getEmail())
					.addValue("firstName", user.getFirstName())
					.addValue("lastName", user.getLastName())
					.addValue("createdOn", new Timestamp(System.currentTimeMillis()))
					.addValue("modifiedOn", new Timestamp(System.currentTimeMillis()));
			namedParameterJdbcTemplate.update(INSERT_USER, parameters, keyHolder);


			if (user.getRoles() == null || user.getRoles().isEmpty() || user.getRoles().size() == 0) {
				namedParameterJdbcTemplate.update(INSERT_USER_ROLE, new MapSqlParameterSource().addValue("userId", keyHolder.getKey()).addValue("roleId", 2));
			}
			else {
				for (Role role : user.getRoles()) {
					namedParameterJdbcTemplate.update(INSERT_USER_ROLE, new MapSqlParameterSource().addValue("userId", keyHolder.getKey()).addValue("roleId", role.getRoleId()));
				}
			}
			user.setUserId(keyHolder.getKey().intValue());
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return user;
	}

	@Log
	public User updateUser(User user) {
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("firstName", user.getFirstName())
				.addValue("lastName", user.getLastName())
				.addValue("email", user.getEmail())
				.addValue("userId", user.getUserId());
		int status = namedParameterJdbcTemplate.update(UPDATE_USER, parameters);

		if(status != 0){
			System.out.println("User data updated for ID " + user.getUserId());
		}
		else {
			System.out.println("No User found with ID " + user.getUserId());
		}
		return user;
	}

}
