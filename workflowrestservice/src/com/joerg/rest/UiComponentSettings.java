package com.joerg.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.joerg.db.WorkflowDbDummyData;
import com.joerg.rest.dtos.UiComponentSettingsDto;
import com.joerg.rest.dtos.UiComponentSettingsListDto;

@Path("/uicomponentsettings")
public class UiComponentSettings {

	@GET
	@Path("/getall")
	@Produces("application/json")
	public UiComponentSettingsListDto getAllUiComponentSettings(){
		//http://localhost:18080/workflowrestservice/rest/uicomponentsettings/getall
		
		WorkflowDbDummyData workflowDbDummyData = new WorkflowDbDummyData();
		UiComponentSettingsListDto uiComponentSettingsListDto = workflowDbDummyData.getAllUiComponentSettings();
		
		return uiComponentSettingsListDto;

//		WorkflowDb workflowDb = new WorkflowDb();
//		return workflowDb.getAllUiComponentSettings();
	}

	@GET
	@Path("/get/{componentId}")
	@Produces("application/json")
	public UiComponentSettingsDto getComponentSettings(@PathParam("componentId") String componentId){
		//http://localhost:18080/workflowrestservice/rest/uicomponentsettings/get/{componentId}
		
		System.out.println("componentId: " + componentId);

		WorkflowDbDummyData workflowDbDummyData = new WorkflowDbDummyData();
		UiComponentSettingsDto uiComponentSettingsDto = workflowDbDummyData.getUiComponentSettings(componentId);
		System.out.println("uiComponentSettings.isEnabled: " + uiComponentSettingsDto.isEnabled());
		return uiComponentSettingsDto;

//		WorkflowDb workflowDb = new WorkflowDb();
//		return workflowDb.getProcessData(processInstanceId);
	}
	
	@POST
    @Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUiComponentSetting(UiComponentSettingsDto uiComponentSettingsDto) {
		//http://localhost:18080/workflowrestservice/rest/uicomponentsettings/update
		if(uiComponentSettingsDto == null) {
			return Response.status(204).build();
		}
		System.out.println("uiComponentSettingsDto.getComponentId(): " + uiComponentSettingsDto.getComponentId());
		System.out.println("uiComponentSettingsDto.isVisible(): " + uiComponentSettingsDto.isVisible());
		System.out.println("uiComponentSettingsDto.isEnabled(): " + uiComponentSettingsDto.isEnabled());
		System.out.println("uiComponentSettingsDto.isRequired(): " + uiComponentSettingsDto.isRequired());
		String output = "POST: Jersey says: " + uiComponentSettingsDto.getComponentId();
		return Response.status(200).entity(uiComponentSettingsDto).build();
	}
}
