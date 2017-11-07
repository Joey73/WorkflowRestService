package com.joerg.rest.dtos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responseList")
public class ProcessDataDtoList {
	public ProcessDataDtoList() {
		super();
	}
	
	private List<ProcessDataDto> processDataDtoList = new ArrayList<ProcessDataDto>();

	public List<ProcessDataDto> getProcessDataDtoList() {
		return processDataDtoList;
	}

	public void setProcessDataDtoList(List<ProcessDataDto> processDataDtoList) {
		this.processDataDtoList = processDataDtoList;
	}
	
	public void addProcessDataDto(ProcessDataDto processDataDto){
		this.processDataDtoList.add(processDataDto);
	}
}
