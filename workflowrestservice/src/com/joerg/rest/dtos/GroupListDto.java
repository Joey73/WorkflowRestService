package com.joerg.rest.dtos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responseList")
public class GroupListDto {
	public GroupListDto() {
		super();
	}
	
	private List<GroupDto> groupListDto = new ArrayList<GroupDto>();

	public List<GroupDto> getGroupListDto() {
		return groupListDto;
	}

	public void setGroupListDto(List<GroupDto> groupListDto) {
		this.groupListDto = groupListDto;
	}
	
	public void addGroupDto(GroupDto groupDto){
		this.groupListDto.add(groupDto);
	}
}
