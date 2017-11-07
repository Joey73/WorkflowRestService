package com.joerg.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.joerg.db.WorkflowDb;
import com.joerg.rest.dtos.ProcessDataDto;
import com.joerg.rest.dtos.ProcessDataDtoList;

@Path("/processdata")
public class ProcessData {

	@GET
	@Path("/getall")
	@Produces("application/json")
	public ProcessDataDtoList getAllProcessData(){
		//http://localhost:8080/workflowrestservice/rest
		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getAllProcessData();
	}

	@GET
	@Path("/get")
	@Produces("application/json")
	public ProcessDataDto getProcessData(){
		//http://localhost:8080/workflowrestservice/rest
		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getProcessData("6b92c153-b9d2-11e7-a421-02429089a5c8");
	}
}
