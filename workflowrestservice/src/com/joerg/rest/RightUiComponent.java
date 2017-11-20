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
import com.joerg.rest.dtos.RightUiComponentDto;
import com.joerg.rest.dtos.RightUiComponentListDto;

@Path("/rightuicomponent")
public class RightUiComponent {

	@GET
	@Path("/getall/{rightId}")
	@Produces("application/json")
	public RightUiComponentListDto getAllRightUiComponents(@PathParam("rightId") String rightId){
		//http://localhost:18080/workflowrestservice/rest/rightuicomponent/getall
		if(rightId == null) {
			return new RightUiComponentListDto();
		}
		WorkflowDb workflowDb = new WorkflowDb();
		RightUiComponentListDto allRightUiComponents = workflowDb.getAllRightUiComponents(rightId);
		return allRightUiComponents;
	}

	@GET
	@Path("/get/{componentId}")
	@Produces("application/json")
	public RightUiComponentDto getRightUiComponents(@PathParam("componentId") String componentId){
		//http://localhost:18080/workflowrestservice/rest/rightuicomponent/get/{componentId}
		System.out.println("componentId: " + componentId);

		WorkflowDb workflowDb = new WorkflowDb();
		return workflowDb.getRightUiComponent(componentId);
	}
	
	@POST
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRightUiComponent(RightUiComponentDto rightUiComponentDto) {
		//http://localhost:18080/workflowrestservice/rest/rightuicomponent/update
		if(rightUiComponentDto == null) {
			return Response.status(204).build();
		}
		System.out.println("rightUiComponentDto.getUicomponentId(): " + rightUiComponentDto.getUicomponentId());
		System.out.println("rightUiComponentDto.isVisible(): " + rightUiComponentDto.isVisible());
		System.out.println("rightUiComponentDto.isEnabled(): " + rightUiComponentDto.isEnabled());
		System.out.println("rightUiComponentDto.isRequired(): " + rightUiComponentDto.isRequired());
		
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.updateRightUiComponent(rightUiComponentDto);
		
		return Response.status(200).entity(rightUiComponentDto).build();
	}
}
