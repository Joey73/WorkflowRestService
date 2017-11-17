package com.joerg.rest.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDto {
	private String id;
	private String lastName;
	private String firstname;
	private String email;
	
	public UserDto(){
		super();
	}
	
	public UserDto(String id, String lastName, String firstname, String email){
		this.id = id;
		this.lastName = lastName;
		this.firstname = firstname;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
