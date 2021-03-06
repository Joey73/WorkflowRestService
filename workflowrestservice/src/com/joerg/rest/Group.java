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
import com.joerg.rest.dtos.GroupDto;
import com.joerg.rest.dtos.GroupListDto;

@Path("/group")
public class Group {
	private WorkflowDb workflowDb = null;
	
	public Group(){
		workflowDb = new WorkflowDb();
	}

	@GET
	@Path("/getall")
	@Produces("application/json")
	public Response getAllGroups() throws SQLException, JsonProcessingException{
		GroupListDto allGroups = workflowDb.getAllGroups();
		
		ObjectMapper om = new ObjectMapper();
		String valueAsString = om.writeValueAsString(allGroups);
		
		return Response.status(200).entity(valueAsString).build();
	}

	@GET
	@Path("/get/{groupId}")
	@Produces("application/json")
	public GroupDto getGroup(@PathParam("componentId") String groupId) throws SQLException{
		System.out.println("groupId: " + groupId);

		return workflowDb.getGroup(groupId);
	}
	
	@POST
    @Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addGroup(GroupDto groupDto) throws SQLException {
		if(groupDto == null) {
			return Response.status(204).build();
		}
		System.out.println("groupDto.getGroupId(): " + groupDto.getGroupId());
		System.out.println("groupDto.getDescription(): " + groupDto.getDescription());
		
		workflowDb.addGroup(groupDto);
		
		return Response.status(200).entity(groupDto).build();
	}
	
	@PUT
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateGroup(GroupDto groupDto) throws SQLException {
		if(groupDto == null) {
			return Response.status(204).build();
		}
		System.out.println("groupDto.getGroupId(): " + groupDto.getGroupId());
		System.out.println("groupDto.getDescription(): " + groupDto.getDescription());
		
		workflowDb.updateGroup(groupDto);
		
		return Response.status(200).entity(groupDto).build();
	}
	
	@DELETE
    @Path("/delete/{groupId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteGroup(@PathParam("groupId") String groupId) throws SQLException {
		System.out.println("groupId(): " + groupId);
		
		workflowDb.deleteGroup(groupId);
	}
}
