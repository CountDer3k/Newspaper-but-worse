package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Permission implements Serializable {
	
	private int permissionId;
	private String name;
	private ZonedDateTime createdOn;
	private ZonedDateTime modifiedOn;
	
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
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
}
