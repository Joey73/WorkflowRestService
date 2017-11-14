package com.joerg.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.joerg.rest.ProcessData;
import com.joerg.rest.dtos.ProcessDataDto;
import com.joerg.rest.dtos.ProcessDataDtoList;
import com.joerg.rest.dtos.UiComponentSettingsDto;
import com.joerg.rest.dtos.UiComponentSettingsListDto;
import com.joerg.rest.dtos.UserDto;
import com.joerg.rest.dtos.UserListDto;

public class WorkflowDb {
	//public static final String CONNECTION_STRING = "jdbc:mysql://172.17.0.2:3306/workflowdb";
	public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/workflowdb";
	public static final String USER = "root";
	public static final String PASSWORD = "12345";

	static Connection workflowDbConnection = null;
	static PreparedStatement prepareStat = null;
	
	public WorkflowDb(){
		makeJDBCConnection();
	}

	public static void main(String[] args){
		WorkflowDb workflowDb = new WorkflowDb();
		workflowDb.getAllProcessData();
	}
	
	public void makeJDBCConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			log("MySQL JDBC Driver Registered");
		} catch (ClassNotFoundException e) {
			log("Couldn't find JDBC driver.");
			e.printStackTrace();
			return;
		}
 
		try {
			workflowDbConnection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
			if (workflowDbConnection != null) {
				log("Connection Successful.");
			} else {
				log("Connection failed");
			}
		} catch (SQLException e) {
			log("MySQL-Connection failed");
			e.printStackTrace();
			return;
		}
	}

	public ProcessDataDtoList getAllProcessData() {
		ProcessDataDtoList processDataDtoList = new ProcessDataDtoList();
		try {
			String selectStatement = "SELECT * FROM workflowdb.processdata";
 
			prepareStat = workflowDbConnection.prepareStatement(selectStatement);
 
			ResultSet rs = prepareStat.executeQuery();
 
			while (rs.next()) {
				processDataDtoList.addProcessDataDto(new ProcessDataDto(rs.getString("processInstanceID"), rs.getString("field1"), rs.getString("field2"), rs.getString("field3")));

				String processInstanceID = rs.getString("processInstanceID");
				String field1 = rs.getString("field1");
				String field2 = rs.getString("field2");
				String field3 = rs.getString("field3");
 
				System.out.format("%s, %s, %s, %s\n", processInstanceID, field1, field2, field3);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return processDataDtoList; 
	}

	public ProcessDataDto getProcessData(String processInstanceId) {
		ProcessDataDto processDataDto = new ProcessDataDto();
		try{
			String selectStatement = "Select * from workflowdb.processdata where processInstanceID = '" + processInstanceId + "'";
			prepareStat = workflowDbConnection.prepareStatement(selectStatement);
			 
			ResultSet rs = prepareStat.executeQuery();
			
			while (rs.next()) {
				processDataDto = new ProcessDataDto(rs.getString("processInstanceID"), rs.getString("field1"), rs.getString("field2"), rs.getString("field3"));

				String processInstanceID = rs.getString("processInstanceID");
				String field1 = rs.getString("field1");
				String field2 = rs.getString("field2");
				String field3 = rs.getString("field3");
 
				System.out.format("%s, %s, %s, %s\n", processInstanceID, field1, field2, field3);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return processDataDto;
	}

	public boolean updateProcessData(ProcessDataDto newProcessDataDto){
		try{
			String updateStatement = 
					"update workflowdb.processdata "
					+ "set field1 = ?, "
					+ "field2 = ?, "
					+ "field3 = ? "
					+ "where processInstanceID = ?";
			prepareStat = workflowDbConnection.prepareStatement(updateStatement);
			prepareStat.setString(1, newProcessDataDto.getField1());
			prepareStat.setString(2, newProcessDataDto.getField2());
			prepareStat.setString(3, newProcessDataDto.getField3());
			prepareStat.setString(4, newProcessDataDto.getProcessInstanceId());

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}
	
	public UiComponentSettingsListDto getAllUiComponentSettings() {
		UiComponentSettingsListDto uiComponentSettingsListDto = new UiComponentSettingsListDto();
		try {
			String selectStatement = "SELECT * FROM workflowdb.uicomponentsettings";
 
			prepareStat = workflowDbConnection.prepareStatement(selectStatement);
 
			ResultSet rs = prepareStat.executeQuery();
 
			while (rs.next()) {
				uiComponentSettingsListDto.addUiComponentSettingsDto(
					new UiComponentSettingsDto(
						rs.getString("componentId"),
						(rs.getInt("visible") == 1),
						(rs.getInt("enabled") == 1),
						(rs.getInt("required") == 1)
					)
				);

				String componentId = rs.getString("componentId");
				boolean visible = (rs.getInt("visible") == 1);
				boolean enabled = (rs.getInt("enabled") == 1);
				boolean required = (rs.getInt("required") == 1);
 
				System.out.format("%s, %s, %s, %s\n", componentId, visible, enabled, required);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return uiComponentSettingsListDto; 
	}
	
	public UiComponentSettingsDto getUiComponentSettings(String componentId) {
		UiComponentSettingsDto uiComponentSettingsDto = new UiComponentSettingsDto();
		try{
			String selectStatement = "Select * from workflowdb.uicomponentsettings where componentID = '" + componentId + "'";
			prepareStat = workflowDbConnection.prepareStatement(selectStatement);
			 
			ResultSet rs = prepareStat.executeQuery();
			
			while (rs.next()) {
				uiComponentSettingsDto = new UiComponentSettingsDto(
						rs.getString("componentId"),
						(rs.getInt("visible") == 1),
						(rs.getInt("enabled") == 1),
						(rs.getInt("required") == 1)
				);

				String compId = rs.getString("componentId");
				boolean visible = (rs.getInt("visible") == 1);
				boolean enabled = (rs.getInt("enabled") == 1);
				boolean required = (rs.getInt("required") == 1);
 
				System.out.format("%s, %s, %s, %s\n", compId, visible, enabled, required);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return uiComponentSettingsDto;
	}
	
	public boolean updateUiComponentSetting(UiComponentSettingsDto newUiComponentSettingsDto){
		try{
			String updateStatement = 
					"update workflowdb.uicomponentsettings "
					+ "set enabled = ?, "
					+ "required = ?, "
					+ "visible = ? "
					+ "where componentID = ?";
			prepareStat = workflowDbConnection.prepareStatement(updateStatement);
			prepareStat.setInt(1, newUiComponentSettingsDto.isEnabled() ? 1 : 0);
			prepareStat.setInt(2, newUiComponentSettingsDto.isRequired() ? 1 : 0);
			prepareStat.setInt(3, newUiComponentSettingsDto.isVisible() ? 1 : 0);
			prepareStat.setString(4, newUiComponentSettingsDto.getComponentId());

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}

	public UserListDto getAllUsers() {
		UserListDto userListDto = new UserListDto();
		try {
			String selectStatement = "SELECT * FROM workflowdb.user";
 
			prepareStat = workflowDbConnection.prepareStatement(selectStatement);
 
			ResultSet rs = prepareStat.executeQuery();
 
			while (rs.next()) {
				userListDto.addUserDto(
					new UserDto(
						rs.getString("userId"),
						rs.getString("lastname"),
						rs.getString("firstname"),
						rs.getString("email")
					)
				);

				String userId = rs.getString("userId");
				String lastname = rs.getString("lastname");
				String firstname = rs.getString("firstname");
				String email = rs.getString("email");
 
				System.out.format("%s, %s, %s, %s\n", userId, lastname, firstname, email);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userListDto; 
	}
	
	public UserDto getUser(String userId) {
		UserDto userDto = new UserDto();
		try{
			String selectStatement = "Select * from workflowdb.user where userID = '" + userId + "'";
			prepareStat = workflowDbConnection.prepareStatement(selectStatement);
			 
			ResultSet rs = prepareStat.executeQuery();
			
			while (rs.next()) {
				userDto = new UserDto(
						rs.getString("userId"),
						rs.getString("lastname"),
						rs.getString("firstname"),
						rs.getString("email")
				);

				String uId = rs.getString("userId");
				String lastname = rs.getString("lastname");
				String firstname = rs.getString("firstname");
				String email = rs.getString("email");
 
				System.out.format("%s, %s, %s, %s\n", uId, lastname, firstname, email);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userDto;
	}
	
	public boolean updateUser(UserDto newUserDto){
		log(newUserDto.getUserId() + " " + newUserDto.getLastname() + " " + newUserDto.getFirstname() + " " + newUserDto.getEmail());
		try{
			String updateStatement = 
					"update workflowdb.user "
					+ "set lastname = ?, "
					+ "firstname = ?, "
					+ "email = ? "
					+ "where userID = ?";
			prepareStat = workflowDbConnection.prepareStatement(updateStatement);
			prepareStat.setString(1, newUserDto.getLastname());
			prepareStat.setString(2, newUserDto.getFirstname());
			prepareStat.setString(3, newUserDto.getEmail());
			prepareStat.setString(4, newUserDto.getUserId());

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}
	
	public boolean addUser(UserDto newUserDto){
		log(newUserDto.getUserId() + " " + newUserDto.getLastname() + " " + newUserDto.getFirstname() + " " + newUserDto.getEmail());
		try{
			String insertStatement = 
					"insert into workflowdb.user "
					+ "(userID, lastname, firstname, email)"
					+ "values (?, ?, ?, ?)";
			prepareStat = workflowDbConnection.prepareStatement(insertStatement);
			prepareStat.setString(1, newUserDto.getUserId());
			prepareStat.setString(2, newUserDto.getLastname());
			prepareStat.setString(3, newUserDto.getFirstname());
			prepareStat.setString(4, newUserDto.getEmail());

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}

	public boolean deleteUser(String userId){
		log("deleteUser(...) - userId: " + userId);
		try{
			String deleteStatement = 
					"delete from workflowdb.user "
					+ "where userID = ?";
			prepareStat = workflowDbConnection.prepareStatement(deleteStatement);
			prepareStat.setString(1, userId);

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}
	
	private static void log(String string) {
		System.out.println(string);
 
	}
}
