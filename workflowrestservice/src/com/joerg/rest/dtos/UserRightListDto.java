package com.joerg.rest.dtos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responseList")
public class UserRightListDto {
	public UserRightListDto() {
		super();
	}
	
	private List<UserRightDto> userRightListDto = new ArrayList<UserRightDto>();

	public List<UserRightDto> getUserRightListDto() {
		return userRightListDto;
	}

	public void setUserRightListDto(List<UserRightDto> userRightListDto) {
		this.userRightListDto = userRightListDto;
	}
	
	public void addUserRightDto(UserRightDto userRightDto){
		this.userRightListDto.add(userRightDto);
	}
}
