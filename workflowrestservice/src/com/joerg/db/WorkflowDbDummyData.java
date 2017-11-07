package com.joerg.db;

import com.joerg.rest.dtos.ProcessDataDto;
import com.joerg.rest.dtos.ProcessDataDtoList;

public class WorkflowDbDummyData {
	private ProcessDataDtoList processDataDtoList = new ProcessDataDtoList();
	
	public WorkflowDbDummyData() {
		super();
		this.processDataDtoList.addProcessDataDto(new ProcessDataDto("449bd85f-c075-11e7-9e48-00090ffe0001", "11", "12", "13"));
		this.processDataDtoList.addProcessDataDto(new ProcessDataDto("668c3228-c075-11e7-9e48-00090ffe0001", "21", "22", "23"));
		this.processDataDtoList.addProcessDataDto(new ProcessDataDto("909b593c-ba66-11e7-a86e-00090ffe0001", "31", "32", "33"));
	}
	
	public ProcessDataDto getProcessData(String processInstanceId) {
		for (ProcessDataDto processDataDto : this.processDataDtoList.getProcessDataDtoList()) {
			if(processDataDto.getProcessInstanceId().equals(processInstanceId)) {
				return processDataDto;
			}
		}
		return null;
	}
}
