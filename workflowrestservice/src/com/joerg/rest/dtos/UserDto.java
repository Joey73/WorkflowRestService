package com.joerg.rest.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDto {
	private String userId;
	private String lastname;
	private String firstname;
	private String email;
	
	public UserDto(){
		super();
	}
	
	public UserDto(String userId, String lastname, String firstname, String email){
		this.userId = userId;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
