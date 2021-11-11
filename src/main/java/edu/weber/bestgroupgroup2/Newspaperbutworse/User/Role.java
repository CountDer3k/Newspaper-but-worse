package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.List;

public class Role {
	
	private int roleId;
	private String name;
	private List<Permission> permissions;

	public List<Permission> getPermissions() {
		return permissions;
	}

}
