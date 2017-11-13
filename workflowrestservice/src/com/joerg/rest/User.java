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
import com.joerg.db.WorkflowDbDummyData;
import com.joerg.rest.dtos.UserDto;
import com.joerg.rest.dtos.UserListDto;

@Path("/user")
public class User {

	@GET
	@Path("/getall")
	@Produces("application/json")
	public UserListDto getAllUsers(){
		//http://localhost:18080/workflowrestservice/rest/user/getall
		
		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getAllUsers();
	}

	@GET
	@Path("/get/{userId}")
	@Produces("application/json")
	public UserDto getUser(@PathParam("componentId") String userId){
		//http://localhost:18080/workflowrestservice/rest/user/get/{userId}
		System.out.println("userId: " + userId);

		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getUser(userId);
	}
	
	@POST
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUiComponentSetting(UserDto userDto) {
		//http://localhost:18080/workflowrestservice/rest/user/update
		if(userDto == null) {
			return Response.status(204).build();
		}
		System.out.println("userDto.getUserId(): " + userDto.getUserId());
		System.out.println("userDto.getLastname(): " + userDto.getLastname());
		System.out.println("userDto.getFirstname(): " + userDto.getFirstname());
		System.out.println("userDto.getEmail(): " + userDto.getEmail());
		
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.updateUser(userDto);
		
		return Response.status(200).entity(userDto).build();
	}
}
