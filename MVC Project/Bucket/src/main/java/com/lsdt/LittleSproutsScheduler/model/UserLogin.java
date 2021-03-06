package com.lsdt.LittleSproutsScheduler.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lsdt.LittleSproutsScheduler.java.Encrypt;

public class UserLogin {
	@NotEmpty
	@Size(min=1, max=20)
	private String userName;
		
	@NotEmpty
	@Size(min=1, max=32)
	private String password;
	
	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = Encrypt.encrypt(password);
		//this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
