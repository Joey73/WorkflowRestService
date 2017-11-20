package com.joerg.rest.dtos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responseList")
public class RightUiComponentListDto {
	public RightUiComponentListDto() {
		super();
	}
	
	private List<RightUiComponentDto> rightUiComponentListDto = new ArrayList<RightUiComponentDto>();

	public List<RightUiComponentDto> getRightUiComponentListDto() {
		return rightUiComponentListDto;
	}

	public void setRightUiComponentListDto(List<RightUiComponentDto> rightUiComponentListDto) {
		this.rightUiComponentListDto = rightUiComponentListDto;
	}
	
	public void addRightUiComponentDto(RightUiComponentDto rightUiComponentDto){
		this.rightUiComponentListDto.add(rightUiComponentDto);
	}
}
