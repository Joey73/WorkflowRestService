package com.joerg.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.joerg.db.WorkflowDb;
import com.joerg.rest.dtos.ProcessDataDto;
import com.joerg.rest.dtos.ProcessDataDtoList;
import com.joerg.rest.dtos.UiComponentSettingsDto;

@Path("/processdata")
public class ProcessData {

	@GET
	@Path("/getall")
	@Produces("application/json")
	public ProcessDataDtoList getAllProcessData(){
		//http://localhost:8080/workflowrestservice/rest/processdata/getall
		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getAllProcessData();
	}

	@GET
	@Path("/get/{processInstanceId}")
	@Produces("application/json")
	public ProcessDataDto getProcessData(@PathParam("processInstanceId") String processInstanceId){
		//http://localhost:8080/workflowrestservice/rest/processdata/get/{processInstanceId}
		System.out.println("processInstanceId: " + processInstanceId);

		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getProcessData(processInstanceId);
	}
	
	@POST
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProcessData(ProcessDataDto processDataDto) {
		//http://localhost:18080/workflowrestservice/rest/processdata/update
		if(processDataDto == null) {
			return Response.status(204).build();
		}
		System.out.println("uiComponentSettingsDto.getProcessInstanceId(): " + processDataDto.getProcessInstanceId());
		System.out.println("uiComponentSettingsDto.getField1(): " + processDataDto.getField1());
		System.out.println("uiComponentSettingsDto.getField2(): " + processDataDto.getField2());
		System.out.println("uiComponentSettingsDto.getField3(): " + processDataDto.getField3());
		
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.updateProcessData(processDataDto);
		
		return Response.status(200).entity(processDataDto).build();
	}
}
