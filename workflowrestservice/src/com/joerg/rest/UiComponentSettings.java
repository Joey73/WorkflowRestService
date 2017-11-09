package com.joerg.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.joerg.db.WorkflowDbDummyData;
import com.joerg.rest.dtos.UiComponentSettingsDto;
import com.joerg.rest.dtos.UiComponentSettingsListDto;

@Path("/uicomponentsettings")
public class UiComponentSettings {

	@GET
	@Path("/getall")
	@Produces("application/json")
	public UiComponentSettingsListDto getAllUiComponentSettings(){
		//http://localhost:8080/workflowrestservice/rest/uicomponentsettings/getall
		
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
		//http://localhost:8080/workflowrestservice/rest/uicomponentsettings/get/{componentId}
		
		System.out.println("componentId: " + componentId);

		WorkflowDbDummyData workflowDbDummyData = new WorkflowDbDummyData();
		UiComponentSettingsDto uiComponentSettings = workflowDbDummyData.getUiComponentSettings(componentId);
		System.out.println("uiComponentSettings.isEnabled: " + uiComponentSettings.isEnabled());
		return uiComponentSettings;

//		WorkflowDb workflowDb = new WorkflowDb();
//		return workflowDb.getProcessData(processInstanceId);
	}
}
