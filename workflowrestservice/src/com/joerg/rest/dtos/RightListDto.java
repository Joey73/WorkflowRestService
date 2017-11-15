package com.joerg.rest.dtos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responseList")
public class RightListDto {
	public RightListDto() {
		super();
	}
	
	private List<RightDto> rightListDto = new ArrayList<RightDto>();

	public List<RightDto> getRightListDto() {
		return rightListDto;
	}

	public void setRightListDto(List<RightDto> rightListDto) {
		this.rightListDto = rightListDto;
	}
	
	public void addRightDto(RightDto rightDto){
		this.rightListDto.add(rightDto);
	}
}
