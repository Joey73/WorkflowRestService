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
import com.joerg.rest.dtos.RightUiComponentDto;
import com.joerg.rest.dtos.RightUiComponentListDto;

@Path("/rightuicomponent")
public class RightUiComponent {
	private WorkflowDb workflowDb = null;
	
	public RightUiComponent() {
		workflowDb = new WorkflowDb();
	}
	
	@GET
	@Path("/getall/{rightId}")
	@Produces("application/json")
	public Response getAllRightUiComponents(@PathParam("rightId") String rightId) throws SQLException, JsonProcessingException{
		if(rightId == null) {
			return Response.status(204).build();
		}
		RightUiComponentListDto allRightUiComponents = workflowDb.getAllRightUiComponents(rightId);
		
		ObjectMapper om = new ObjectMapper();
		String valueAsString = om.writeValueAsString(allRightUiComponents);
		
		return Response.status(200).entity(valueAsString).build();
	}

	@GET
	@Path("/get/{rightId}/{uicomponentId}")
	@Produces("application/json")
	public RightUiComponentDto getRightUiComponents(@PathParam("rightId") String rightId, @PathParam("uicomponentId") String uicomponentId) throws SQLException {
		System.out.println("componentId: " + uicomponentId);

		return workflowDb.getRightUiComponent(rightId, uicomponentId);
	}
	
	@POST
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRightUiComponent(RightUiComponentDto rightUiComponentDto) throws SQLException {
		if(rightUiComponentDto == null) {
			return Response.status(204).build();
		}
		System.out.println("rightUiComponentDto.getUicomponentId(): " + rightUiComponentDto.getUicomponentId());
		System.out.println("rightUiComponentDto.isVisible(): " + rightUiComponentDto.isVisible());
		System.out.println("rightUiComponentDto.isEnabled(): " + rightUiComponentDto.isEnabled());
		System.out.println("rightUiComponentDto.isRequired(): " + rightUiComponentDto.isRequired());
		
		workflowDb.updateRightUiComponent(rightUiComponentDto);
		
		return Response.status(200).entity(rightUiComponentDto).build();
	}
}
