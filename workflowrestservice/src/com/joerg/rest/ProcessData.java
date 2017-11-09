package com.joerg.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.joerg.db.WorkflowDb;
import com.joerg.db.WorkflowDbDummyData;
import com.joerg.rest.dtos.ProcessDataDto;
import com.joerg.rest.dtos.ProcessDataDtoList;

@Path("/processdata")
public class ProcessData {

	@GET
	@Path("/getall")
	@Produces("application/json")
	public ProcessDataDto[] getAllProcessData(){
		//http://localhost:8080/workflowrestservice/rest/processdata/getall
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.getAllProcessData().getProcessDataDtoList();
		//return workflowDb.getAllProcessData().getProcessDataDtoList().toArray();
		return null;
	}

	@GET
	@Path("/get/{processInstanceId}")
	@Produces("application/json")
	public ProcessDataDto getProcessData(@PathParam("processInstanceId") String processInstanceId){
		//http://localhost:8080/workflowrestservice/rest/processdata/get/{processInstanceId}
		System.out.println("processInstanceId: " + processInstanceId);


		WorkflowDbDummyData workflowDbDummyData = new WorkflowDbDummyData();
		ProcessDataDto processData = workflowDbDummyData.getProcessData(processInstanceId);
		System.out.println("processData.field1: " + processData.getField1());
		return processData;

//		WorkflowDb workflowDb = new WorkflowDb();
//		return workflowDb.getProcessData(processInstanceId);
	}
}
