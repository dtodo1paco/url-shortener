package org.dtodo1paco.samples.urlshortener.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.Indexed;

public class ServiceUser implements Serializable {
	private static final long serialVersionUID = 1L;
	@Indexed
	private String username;

	private String password;
	
	private String role;
	
	private String fullName;
	
	private Boolean enabled;
	
	
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String toString() {
		return "ServiceUser {username:"+ username +", role:"+role+"}";
	}
}
