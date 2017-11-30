package com.joerg.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joerg.db.WorkflowDb;
import com.joerg.rest.dtos.ProcessDataDto;
import com.joerg.rest.dtos.ProcessDataDtoList;

@Path("/processdata")
public class ProcessData {
	private WorkflowDb workflowDb = null;
	
	public ProcessData() {
		workflowDb = new WorkflowDb();
	}
	
	@GET
	@Path("/getall")
	@Produces("application/json")
	public Response getAllProcessData() throws SQLException, JsonProcessingException{
		ProcessDataDtoList allProcessData = workflowDb.getAllProcessData();
		
		ObjectMapper om = new ObjectMapper();
		String valueAsString = om.writeValueAsString(allProcessData);
		
		return Response.status(200).entity(valueAsString).build();
	}
	
	@GET
	@Path("/get/{processInstanceId}")
	@Produces("application/json")
	public ProcessDataDto getProcessData(@PathParam("processInstanceId") String processInstanceId) throws SQLException{
		System.out.println("processInstanceId: " + processInstanceId);

		return workflowDb.getProcessData(processInstanceId);
	}

	@POST
    @Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProcessData(ProcessDataDto processDataDto) throws SQLException {
		if(processDataDto == null) {
			return Response.status(204).build();
		}
		System.out.println("processDataDto.getProcessInstanceId(): " + processDataDto.getProcessInstanceId());
		
		workflowDb.addProcessData(processDataDto);
		
		return Response.status(200).entity(processDataDto).build();
	}
	
	@POST
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProcessData(ProcessDataDto processDataDto) throws SQLException {
		if(processDataDto == null) {
			return Response.status(204).build();
		}
		System.out.println("processDataDto.getProcessInstanceId(): " + processDataDto.getProcessInstanceId());
		System.out.println("processDataDto.getField1(): " + processDataDto.getField1());
		System.out.println("processDataDto.getField2(): " + processDataDto.getField2());
		System.out.println("processDataDto.getField3(): " + processDataDto.getField3());
		System.out.println("processDataDto.getField4(): " + processDataDto.getField4());
		System.out.println("processDataDto.getField5(): " + processDataDto.getField5());
		System.out.println("processDataDto.getField6(): " + processDataDto.getField6());
		
		workflowDb.updateProcessData(processDataDto);
		
		return Response.status(200).entity(processDataDto).build();
	}
}
