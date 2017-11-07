package com.joerg.rest.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcessDataDto {
	private String processInstanceId;
	private String field1;
	private String field2;
	private String field3;
	
	public ProcessDataDto(){
		super();
	}
	
	public ProcessDataDto(String processInstanceId, String field1, String field2, String field3){
		this.processInstanceId = processInstanceId;
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
}