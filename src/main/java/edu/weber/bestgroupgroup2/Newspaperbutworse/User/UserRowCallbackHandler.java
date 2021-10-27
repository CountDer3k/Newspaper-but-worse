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
		// TODO Auto-generated method stub
		
	}
}
