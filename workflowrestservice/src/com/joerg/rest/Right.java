package com.joerg.rest;

import java.sql.SQLException;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joerg.db.WorkflowDb;
import com.joerg.rest.dtos.RightDto;
import com.joerg.rest.dtos.RightListDto;

@Path("/right")
public class Right {
	private WorkflowDb workflowDb = null;
	
	public Right(){
		workflowDb = new WorkflowDb();
	}
	
	@GET
	@Path("/getall")
	@Produces("application/json")
	public Response getAllRights() throws SQLException, JsonProcessingException{
		RightListDto allRights = workflowDb.getAllRights();
		
		ObjectMapper om = new ObjectMapper();
		String valueAsString = om.writeValueAsString(allRights);
		
		return Response.status(200).entity(valueAsString).build();
	}

	@GET
	@Path("/get/{rightId}")
	@Produces("application/json")
	public RightDto getRight(@PathParam("componentId") String rightId) throws SQLException{
		System.out.println("rightId: " + rightId);

		return workflowDb.getRight(rightId);
	}

	@GET
	@Path("/getviauser/{userId}")
	@Produces("application/json")
	public RightDto getRightViaUser(@PathParam("userId") String userId) throws SQLException{
		System.out.println("rightId: " + userId);

		return workflowDb.getRightViaUser(userId);
	}

	@POST
    @Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRight(RightDto rightDto) throws SQLException {
		if(rightDto == null) {
			return Response.status(204).build();
		}
		System.out.println("rightDto.getRightId(): " + rightDto.getRightId());
		System.out.println("rightDto.getDescription(): " + rightDto.getDescription());
		
		workflowDb.addRight(rightDto);
		
		return Response.status(200).entity(rightDto).build();
	}
	
	@PUT
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRight(RightDto rightDto) throws SQLException {
		if(rightDto == null) {
			return Response.status(204).build();
		}
		System.out.println("rightDto.getRightId(): " + rightDto.getRightId());
		System.out.println("rightDto.getDescription(): " + rightDto.getDescription());
		
		workflowDb.updateRight(rightDto);
		
		return Response.status(200).entity(rightDto).build();
	}
	
	@DELETE
    @Path("/delete/{rightId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteRight(@PathParam("rightId") String rightId) throws SQLException {
		System.out.println("rightId(): " + rightId);
		
		workflowDb.deleteRight(rightId);
	}
}
