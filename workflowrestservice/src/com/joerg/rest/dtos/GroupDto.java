package com.joerg.rest.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupDto {
	private String groupId;
	private String description;
	
	public GroupDto(){
		super();
	}
	
	public GroupDto(String groupId, String description){
		this.groupId = groupId;
		this.description = description;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
