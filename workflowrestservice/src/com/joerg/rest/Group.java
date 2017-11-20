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
import com.joerg.rest.dtos.GroupDto;
import com.joerg.rest.dtos.GroupListDto;

@Path("/group")
public class Group {
	@GET
	@Path("/getall")
	@Produces("application/json")
	public GroupListDto getAllGroups(){
		//http://localhost:18080/workflowrestservice/rest/group/getall
		
		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getAllGroups();
	}

	@GET
	@Path("/get/{groupId}")
	@Produces("application/json")
	public GroupDto getGroup(@PathParam("componentId") String groupId){
		//http://localhost:18080/workflowrestservice/rest/group/get/{groupId}
		System.out.println("groupId: " + groupId);

		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getGroup(groupId);
	}
	
	@POST
    @Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addGroup(GroupDto groupDto) {
		//http://localhost:18080/workflowrestservice/rest/group/add
		if(groupDto == null) {
			return Response.status(204).build();
		}
		System.out.println("groupDto.getGroupId(): " + groupDto.getGroupId());
		System.out.println("groupDto.getDescription(): " + groupDto.getDescription());
		
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.addGroup(groupDto);
		
		return Response.status(200).entity(groupDto).build();
	}
	
	@PUT
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateGroup(GroupDto groupDto) {
		//http://localhost:18080/workflowrestservice/rest/group/update
		if(groupDto == null) {
			return Response.status(204).build();
		}
		System.out.println("groupDto.getGroupId(): " + groupDto.getGroupId());
		System.out.println("groupDto.getDescription(): " + groupDto.getDescription());
		
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.updateGroup(groupDto);
		
		return Response.status(200).entity(groupDto).build();
	}
	
	@DELETE
    @Path("/delete/{groupId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteGroup(@PathParam("groupId") String groupId) {
		//http://localhost:18080/workflowrestservice/rest/group/delete/{groupId}
//		if(groupId == null) {
//			return Response.status(204).build();
//		}
		System.out.println("groupId(): " + groupId);
		
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.deleteGroup(groupId);
		
//		return Response.status(200).entity("Deleted").build();
	}
}
