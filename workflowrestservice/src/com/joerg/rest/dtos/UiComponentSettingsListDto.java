package com.joerg.rest.dtos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responseList")
public class UiComponentSettingsListDto {
	public UiComponentSettingsListDto() {
		super();
	}
	
	private List<UiComponentSettingsDto> uiComponentSettingsListDto = new ArrayList<UiComponentSettingsDto>();

	public List<UiComponentSettingsDto> getUiComponentSettingsListDto() {
		return uiComponentSettingsListDto;
	}

	public void setUiComponentSettingsListDto(List<UiComponentSettingsDto> uiComponentSettingsListDto) {
		this.uiComponentSettingsListDto = uiComponentSettingsListDto;
	}
	
	public void addUiComponentSettingsDto(UiComponentSettingsDto uiComponentSettingsDto){
		this.uiComponentSettingsListDto.add(uiComponentSettingsDto);
	}
}
