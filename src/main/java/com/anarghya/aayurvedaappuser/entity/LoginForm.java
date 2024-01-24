package com.anarghya.aayurvedaappuser.entity;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

@Component
public class LoginForm {
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPazzwd() {
		return pazzwd;
	}
	public void setPazzwd(String pazzwd) {
		this.pazzwd = pazzwd;
	}
	
	@NotEmpty(message = "Email must not be null")
	private String userEmail;
	@NotEmpty(message = "Password must not be null")
	private String pazzwd;

}
