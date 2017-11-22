package com.joerg.rest.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserRightDto {
	private String userId;
	private String rightId;
	
	public UserRightDto(){
		super();
	}
	
	public UserRightDto(String userId, String rightId){
		this.userId = userId;
		this.rightId = rightId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}
}
