package com.joerg.rest.dtos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responseList")
public class UserListDto {
	public UserListDto() {
		super();
	}
	
	private List<UserDto> userListDto = new ArrayList<UserDto>();

	public List<UserDto> getUserListDto() {
		return userListDto;
	}

	public void setUserListDto(List<UserDto> userListDto) {
		this.userListDto = userListDto;
	}
	
	public void addUserDto(UserDto userDto){
		this.userListDto.add(userDto);
	}
}
