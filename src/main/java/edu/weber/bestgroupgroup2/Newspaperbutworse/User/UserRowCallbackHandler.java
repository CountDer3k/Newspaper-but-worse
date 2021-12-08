package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

public class UserRowCallbackHandler implements RowCallbackHandler {
	private List<User> userList;
	
	public UserRowCallbackHandler() {
		userList = new ArrayList<User>();
	}

	@Override
	@Log
	public void processRow(ResultSet rs) throws SQLException {
		User user;
		int userId = rs.getInt("u.USER_ID");
		if (userList.stream().anyMatch(u -> u.getUserId() == userId)) {
			user = userList.stream()
					.filter(u -> u.getUserId() == userId)
					.findFirst()
					.get();
		}
		else {
			
			user = new User();
			user.setUserId(userId);
			user.setUsername(rs.getString("u.USERNAME"));
			user.setPassword(rs.getString("u.PASSWORD"));
			user.setEmail(rs.getString("u.EMAIL"));
			user.setFirstName(rs.getString("u.FIRST_NAME"));
			user.setLastName(rs.getString("u.LAST_NAME"));
			user.setCreatedOn(ZonedDateTime.ofInstant(Instant.ofEpochMilli(rs.getTimestamp("u.CREATED_ON").getTime()), ZoneOffset.UTC));
			user.setModifiedOn(ZonedDateTime.ofInstant(Instant.ofEpochMilli(rs.getTimestamp("u.MODIFIED_ON").getTime()), ZoneOffset.UTC));
			user.setRoles(new ArrayList<Role>());
			userList.add(user);
		}
		
		Role role;
		int roleId = rs.getInt("r.ROLE_ID");
		if(user.getRoles().stream().anyMatch(r -> r.getRoleId() == roleId)) {
			role = user.getRoles().stream()
					.filter(r -> r.getRoleId() == roleId)
					.findFirst()
					.get();
		}
		else {
			role = new Role();
			role.setRoleId(roleId);
			role.setName(rs.getString("r.ROLE_NAME"));
			role.setCreatedOn(ZonedDateTime.ofInstant(Instant.ofEpochMilli(rs.getTimestamp("r.CREATED_ON").getTime()), ZoneOffset.UTC));
			role.setModifiedOn(ZonedDateTime.ofInstant(Instant.ofEpochMilli(rs.getTimestamp("r.MODIFIED_ON").getTime()), ZoneOffset.UTC));
			role.setPermissions(new ArrayList<Permission>());
			user.addRole(role);
		}
		
		Permission permission = new Permission();
		permission.setPermissionId(rs.getInt("p.PERM_ID"));
		permission.setName(rs.getString("p.PERM_NAME"));
		permission.setCreatedOn(ZonedDateTime.ofInstant(Instant.ofEpochMilli(rs.getTimestamp("p.CREATED_ON").getTime()), ZoneOffset.UTC));
		permission.setModifiedOn(ZonedDateTime.ofInstant(Instant.ofEpochMilli(rs.getTimestamp("p.MODIFIED_ON").getTime()), ZoneOffset.UTC));
		role.addPermission(permission);
	}

	@Log
	public User getUser() {
		if (userList.isEmpty()) {
			return null;
		}
		else {
			return userList.get(0);
		}
	}
	
	@Log
	public List<User> getUserList() {
		return userList;
	}
	
	
}
