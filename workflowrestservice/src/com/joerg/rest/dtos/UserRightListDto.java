package com.joerg.rest.dtos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;

@XmlRootElement(name = "responseList")
public class UserRightListDto {
	//@JsonFormat(with = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<UserRightDto> userRightListDto = new ArrayList<UserRightDto>();

	public UserRightListDto() {
		super();
	}

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
