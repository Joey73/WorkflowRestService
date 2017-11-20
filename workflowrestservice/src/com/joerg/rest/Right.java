package com.joerg.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.joerg.db.WorkflowDb;
import com.joerg.rest.dtos.RightDto;
import com.joerg.rest.dtos.RightListDto;

@Path("/right")
public class Right {
	@GET
	@Path("/getall")
	@Produces("application/json")
	public RightListDto getAllRights(){
		//http://localhost:18080/workflowrestservice/rest/right/getall
		
		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getAllRights();
	}

	@GET
	@Path("/get/{rightId}")
	@Produces("application/json")
	public RightDto getRight(@PathParam("componentId") String rightId){
		//http://localhost:18080/workflowrestservice/rest/right/get/{rightId}
		System.out.println("rightId: " + rightId);

		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getRight(rightId);
	}

	@GET
	@Path("/getviauser/{userId}")
	@Produces("application/json")
	public RightDto getRightViaUser(@PathParam("userId") String userId){
		System.out.println("rightId: " + userId);

		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getRightViaUser(userId);
	}

	@POST
    @Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRight(RightDto rightDto) {
		//http://localhost:18080/workflowrestservice/rest/right/add
		if(rightDto == null) {
			return Response.status(204).build();
		}
		System.out.println("rightDto.getRightId(): " + rightDto.getRightId());
		System.out.println("rightDto.getDescription(): " + rightDto.getDescription());
		
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.addRight(rightDto);
		
		return Response.status(200).entity(rightDto).build();
	}
	
	@PUT
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRight(RightDto rightDto) {
		//http://localhost:18080/workflowrestservice/rest/right/update
		if(rightDto == null) {
			return Response.status(204).build();
		}
		System.out.println("rightDto.getRightId(): " + rightDto.getRightId());
		System.out.println("rightDto.getDescription(): " + rightDto.getDescription());
		
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.updateRight(rightDto);
		
		return Response.status(200).entity(rightDto).build();
	}
	
	@DELETE
    @Path("/delete/{rightId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteRight(@PathParam("rightId") String rightId) {
		//http://localhost:18080/workflowrestservice/rest/right/delete/{rightId}
//		if(rightId == null) {
//			return Response.status(204).build();
//		}
		System.out.println("rightId(): " + rightId);
		
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.deleteRight(rightId);
		
//		return Response.status(200).entity("Deleted").build();
	}
}
