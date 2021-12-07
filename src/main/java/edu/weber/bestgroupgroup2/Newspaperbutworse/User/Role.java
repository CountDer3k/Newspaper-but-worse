package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

public class Role implements Serializable{
	
	private int roleId;
	private String name;
	private ZonedDateTime createdOn;
	private ZonedDateTime modifiedOn;
	private List<Permission> permissions;
	
	public Role() {
		
	}
	
	public Role(String id) {
		this.roleId = Integer.parseInt(id);
	}

	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ZonedDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(ZonedDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public ZonedDateTime getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(ZonedDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	public void addPermission(Permission permission) {
		this.permissions.add(permission);
	}

}
