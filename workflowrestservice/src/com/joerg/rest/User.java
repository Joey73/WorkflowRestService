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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joerg.db.WorkflowDb;
import com.joerg.rest.dtos.UserDto;
import com.joerg.rest.dtos.UserListDto;

@Path("/user")
public class User {
	private WorkflowDb workflowDb = null;
	
	public User(){
		workflowDb = new WorkflowDb();
	}

	@GET
	@Path("/getall")
	@Produces("application/json")
	public Response getAllUsers() throws JsonProcessingException{
		UserListDto allUsers = workflowDb.getAllUsers();
		
		ObjectMapper om = new ObjectMapper();
		String valueAsString = om.writeValueAsString(allUsers);
		
		return Response.status(200).entity(valueAsString).build();
	}

	@GET
	@Path("/get/{userId}")
	@Produces("application/json")
	public UserDto getUser(@PathParam("componentId") String userId){
		System.out.println("userId: " + userId);

		return workflowDb.getUser(userId);
	}
	
	@POST
    @Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(UserDto userDto) {
		if(userDto == null) {
			return Response.status(204).build();
		}
		System.out.println("userDto.getUserId(): " + userDto.getId());
		System.out.println("userDto.getLastname(): " + userDto.getLastName());
		System.out.println("userDto.getFirstname(): " + userDto.getFirstName());
		System.out.println("userDto.getEmail(): " + userDto.getEmail());
		
		workflowDb.addUser(userDto);
		
		return Response.status(200).entity(userDto).build();
	}
	
	@PUT
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(UserDto userDto) {
		if(userDto == null) {
			return Response.status(204).build();
		}
		System.out.println("userDto.getUserId(): " + userDto.getId());
		System.out.println("userDto.getLastname(): " + userDto.getLastName());
		System.out.println("userDto.getFirstname(): " + userDto.getFirstName());
		System.out.println("userDto.getEmail(): " + userDto.getEmail());
		
		workflowDb.updateUser(userDto);
		
		return Response.status(200).entity(userDto).build();
	}
	
	@DELETE
    @Path("/delete/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteUser(@PathParam("userId") String userId) {
		System.out.println("userId(): " + userId);
		
		workflowDb.deleteUser(userId);
	}
}
