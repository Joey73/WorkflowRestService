package com.joerg.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import com.joerg.rest.dtos.UserRightDto;
import com.joerg.rest.dtos.UserRightListDto;

@Path("/userright")
public class UserRight {
	private WorkflowDb workflowDb = null;
	
	public UserRight(){
		workflowDb = new WorkflowDb();
	}

	@GET
	@Path("/getall/{userId}")
	@Produces("application/json")
	public Response getAllUserRights(@PathParam("userId") String userId) throws SQLException, JsonProcessingException{
		if(userId == null) {
			return Response.status(200).entity(new UserRightListDto()).build();
		}
		UserRightListDto allUserRights = workflowDb.getAllUserRights(userId);
		
		ObjectMapper om = new ObjectMapper();
		String valueAsString = om.writeValueAsString(allUserRights);
		
		return Response.status(200).entity(valueAsString).build();
	}

	@POST
    @Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUserRight(UserRightDto userRightDto) throws SQLException {
		if(userRightDto == null) {
			return Response.status(204).build();
		}
		System.out.println("updateUserRight.getUserId(): " + userRightDto.getUserId());
		System.out.println("updateUserRight.getRightId(): " + userRightDto.getRightId());
		
		workflowDb.addUserRight(userRightDto);
		
		return Response.status(200).entity(userRightDto).build();
	}
	
	@DELETE
    @Path("/delete/{userId}/{rightId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteRight(@PathParam("userId") String userId, @PathParam("rightId") String rightId) throws SQLException {
		System.out.println("deleteRight() - userId: " + userId);
		System.out.println("deleteRight() - userId: " + rightId);

		workflowDb.deleteUserRight(userId, rightId);
	}
}
