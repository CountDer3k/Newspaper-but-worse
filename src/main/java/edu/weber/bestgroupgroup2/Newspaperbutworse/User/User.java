package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
	
	private static final long serialVersionUID = 7468253868850092337L;
	private int userId;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private ZonedDateTime createdOn;
	private ZonedDateTime modifiedOn;
	private List<Role> roles;
	
	public User() {
		
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Optional.ofNullable(this.roles)
				.orElseGet(Collections::emptyList)
				.stream()
				.filter(Objects::nonNull)
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//Used by JwtTokenProvider
//	public Object getPermissions() {
//		return Optional.ofNullable(this.roles)
//				.orElseGet(Collections::emptyList)
//				.stream()
//				.filter(role -> role.getPermissions() != null && role.getPermissions().size() > 0)
//				.flatMap(role -> role.getPermissions().stream())
//				.filter(Objects::nonNull)
//				.collect(Collectors.toSet());
//	}


}
