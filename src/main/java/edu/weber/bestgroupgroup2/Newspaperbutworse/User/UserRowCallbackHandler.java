package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

public class UserRowCallbackHandler implements RowCallbackHandler {
	private List<User> userList;
	
	public UserRowCallbackHandler() {
		userList = new ArrayList<User>();
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("USER_ID"));
		user.setUsername(rs.getString("USERNAME"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setEmail(rs.getString("EMAIL"));
		user.setFirstName(rs.getString("FIRST_NAME"));
		user.setLastName(rs.getString("LAST_NAME"));
		userList.add(user);
	}

	public User getUser() {
		if (userList.isEmpty()) {
			return null;
		}
		else {
			return userList.get(0);
		}
	}
	
	
}
