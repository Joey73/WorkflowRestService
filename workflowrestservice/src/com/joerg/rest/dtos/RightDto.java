package com.joerg.rest.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RightDto {
	private String rightId;
	private String description;
	
	public RightDto(){
		super();
	}
	
	public RightDto(String rightId, String description){
		this.rightId = rightId;
		this.description = description;
	}

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
