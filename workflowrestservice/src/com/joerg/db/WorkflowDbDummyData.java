package com.joerg.db;

import com.joerg.rest.dtos.ProcessDataDto;
import com.joerg.rest.dtos.ProcessDataDtoList;
import com.joerg.rest.dtos.UiComponentSettingsDto;
import com.joerg.rest.dtos.UiComponentSettingsListDto;

public class WorkflowDbDummyData {
	private ProcessDataDtoList processDataDtoList = new ProcessDataDtoList();
	private UiComponentSettingsListDto uiComponentSettingsListDto = new UiComponentSettingsListDto();
	
	public WorkflowDbDummyData() {
		super();
		
//		this.processDataDtoList.addProcessDataDto(new ProcessDataDto("449bd85f-c075-11e7-9e48-00090ffe0001", "11", "12", "13"));
//		this.processDataDtoList.addProcessDataDto(new ProcessDataDto("668c3228-c075-11e7-9e48-00090ffe0001", "21", "22", "23"));
//		this.processDataDtoList.addProcessDataDto(new ProcessDataDto("909b593c-ba66-11e7-a86e-00090ffe0001", "31", "32", "33"));
		this.processDataDtoList.addProcessDataDto(new ProcessDataDto("6b92c153-b9d2-11e7-a421-02429089a5c8", "11", "12", "13"));
		this.processDataDtoList.addProcessDataDto(new ProcessDataDto("7ef26b93-bd02-11e7-b20d-0242c8cd4ab7", "21", "22", "23"));
		this.processDataDtoList.addProcessDataDto(new ProcessDataDto("96fd17b3-bf2a-11e7-8ffb-02427793a44f", "31", "32", "33"));
		
		this.uiComponentSettingsListDto.addUiComponentSettingsDto(new UiComponentSettingsDto("field1", false, true, true));
		this.uiComponentSettingsListDto.addUiComponentSettingsDto(new UiComponentSettingsDto("field2", true, false, true));
		this.uiComponentSettingsListDto.addUiComponentSettingsDto(new UiComponentSettingsDto("field3", true, true, true));
	}
	
	public ProcessDataDto getProcessData(String processInstanceId) {
		for (ProcessDataDto processDataDto : this.processDataDtoList.getProcessDataDtoList()) {
			if(processDataDto.getProcessInstanceId().equals(processInstanceId)) {
				return processDataDto;
			}
		}
		return null;
	}
	
	public UiComponentSettingsListDto getAllUiComponentSettings() {
		return this.uiComponentSettingsListDto;
	}
	
	public UiComponentSettingsDto getUiComponentSettings(String componentId) {
		for (UiComponentSettingsDto uiComponentSettingsDto : this.uiComponentSettingsListDto.getUiComponentSettingsListDto()) {
			if(uiComponentSettingsDto.getComponentId().equals(componentId)) {
				return uiComponentSettingsDto;
			}
		}
		return null;
	}
	
	public boolean updateUiComponentSetting(UiComponentSettingsDto newUiComponentSettingsDto){
		if(this.uiComponentSettingsListDto == null || this.uiComponentSettingsListDto.getUiComponentSettingsListDto().size() == 0){
			return false;
		}
		for (UiComponentSettingsDto uiComponentSettingsDto : this.uiComponentSettingsListDto.getUiComponentSettingsListDto()) {
			if(uiComponentSettingsDto.getComponentId().equals(newUiComponentSettingsDto.getComponentId())){
				uiComponentSettingsDto.setEnabled(newUiComponentSettingsDto.isEnabled());
				uiComponentSettingsDto.setRequired(newUiComponentSettingsDto.isRequired());
				uiComponentSettingsDto.setVisible(newUiComponentSettingsDto.isVisible());
			}
		}
		return true;
	}
}
