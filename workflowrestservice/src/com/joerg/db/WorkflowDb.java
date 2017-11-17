package com.joerg.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.joerg.rest.ProcessData;
import com.joerg.rest.dtos.GroupDto;
import com.joerg.rest.dtos.GroupListDto;
import com.joerg.rest.dtos.ProcessDataDto;
import com.joerg.rest.dtos.ProcessDataDtoList;
import com.joerg.rest.dtos.RightDto;
import com.joerg.rest.dtos.RightListDto;
import com.joerg.rest.dtos.UiComponentSettingsDto;
import com.joerg.rest.dtos.UiComponentSettingsListDto;
import com.joerg.rest.dtos.UserDto;
import com.joerg.rest.dtos.UserListDto;

public class WorkflowDb {
	public static final String CONNECTION_STRING = "jdbc:mysql://172.17.0.2:3306/workflowdb";
	//public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/workflowdb";
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
		log(newUserDto.getId() + " " + newUserDto.getLastName() + " " + newUserDto.getFirstName() + " " + newUserDto.getEmail());
		try{
			String updateStatement = 
					"update workflowdb.user "
					+ "set lastname = ?, "
					+ "firstname = ?, "
					+ "email = ? "
					+ "where userID = ?";
			prepareStat = workflowDbConnection.prepareStatement(updateStatement);
			prepareStat.setString(1, newUserDto.getLastName());
			prepareStat.setString(2, newUserDto.getFirstName());
			prepareStat.setString(3, newUserDto.getEmail());
			prepareStat.setString(4, newUserDto.getId());

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}
	
	public boolean addUser(UserDto newUserDto){
		log(newUserDto.getId() + " " + newUserDto.getLastName() + " " + newUserDto.getFirstName() + " " + newUserDto.getEmail());
		try{
			String insertStatement = 
					"insert into workflowdb.user "
					+ "(userID, lastname, firstname, email)"
					+ "values (?, ?, ?, ?)";
			prepareStat = workflowDbConnection.prepareStatement(insertStatement);
			prepareStat.setString(1, newUserDto.getId());
			prepareStat.setString(2, newUserDto.getLastName());
			prepareStat.setString(3, newUserDto.getFirstName());
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
	
	public RightListDto getAllRights() {
		RightListDto rightListDto = new RightListDto();
		try {
			String selectStatement = "SELECT * FROM workflowdb.right";
 
			prepareStat = workflowDbConnection.prepareStatement(selectStatement);
 
			ResultSet rs = prepareStat.executeQuery();
 
			while (rs.next()) {
				rightListDto.addRightDto(
					new RightDto(
						rs.getString("rightId"),
						rs.getString("description")
					)
				);

				String rightId = rs.getString("rightId");
				String description = rs.getString("description");
 
				System.out.format("%s, %s\n", rightId, description);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rightListDto; 
	}
	
	public RightDto getRight(String rightId) {
		RightDto rightDto = new RightDto();
		try{
			String selectStatement = "Select * from workflowdb.right where rightID = '" + rightId + "'";
			prepareStat = workflowDbConnection.prepareStatement(selectStatement);
			 
			ResultSet rs = prepareStat.executeQuery();
			
			while (rs.next()) {
				rightDto = new RightDto(
						rs.getString("rightId"),
						rs.getString("description")
				);

				String uId = rs.getString("rightId");
				String description = rs.getString("description");
 
				System.out.format("%s, %s\n", uId, description);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rightDto;
	}
	
	public boolean updateRight(RightDto newRightDto){
		log(newRightDto.getRightId() + " " + newRightDto.getDescription());
		try{
			String updateStatement = 
					"update workflowdb.right "
					+ "set description = ? "
					+ "where rightID = ?";
			prepareStat = workflowDbConnection.prepareStatement(updateStatement);
			prepareStat.setString(1, newRightDto.getDescription());
			prepareStat.setString(2, newRightDto.getRightId());

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}
	
	public boolean addRight(RightDto newRightDto){
		log(newRightDto.getRightId() + " " + newRightDto.getDescription());
		try{
			String insertStatement = 
					"insert into workflowdb.right "
					+ "(rightID, description)"
					+ "values (?, ?)";
			prepareStat = workflowDbConnection.prepareStatement(insertStatement);
			prepareStat.setString(1, newRightDto.getRightId());
			prepareStat.setString(2, newRightDto.getDescription());

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}

	public boolean deleteRight(String rightId){
		log("deleteRight(...) - rightId: " + rightId);
		try{
			String deleteStatement = 
					"delete from workflowdb.right "
					+ "where rightID = ?";
			prepareStat = workflowDbConnection.prepareStatement(deleteStatement);
			prepareStat.setString(1, rightId);

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}

	public GroupListDto getAllGroups() {
		GroupListDto groupListDto = new GroupListDto();
		try {
			String selectStatement = "SELECT * FROM workflowdb.group";
 
			prepareStat = workflowDbConnection.prepareStatement(selectStatement);
 
			ResultSet rs = prepareStat.executeQuery();
 
			while (rs.next()) {
				groupListDto.addGroupDto(
					new GroupDto(
						rs.getString("groupId"),
						rs.getString("description")
					)
				);

				String groupId = rs.getString("groupId");
				String description = rs.getString("description");
 
				System.out.format("%s, %s\n", groupId, description);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return groupListDto; 
	}
	
	public GroupDto getGroup(String groupId) {
		GroupDto groupDto = new GroupDto();
		try{
			String selectStatement = "Select * from workflowdb.group where groupID = '" + groupId + "'";
			prepareStat = workflowDbConnection.prepareStatement(selectStatement);
			 
			ResultSet rs = prepareStat.executeQuery();
			
			while (rs.next()) {
				groupDto = new GroupDto(
						rs.getString("groupId"),
						rs.getString("description")
				);

				String uId = rs.getString("groupId");
				String description = rs.getString("description");
 
				System.out.format("%s, %s\n", uId, description);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return groupDto;
	}
	
	public boolean updateGroup(GroupDto newGroupDto){
		log(newGroupDto.getGroupId() + " " + newGroupDto.getDescription());
		try{
			String updateStatement = 
					"update workflowdb.group "
					+ "set description = ? "
					+ "where groupID = ?";
			prepareStat = workflowDbConnection.prepareStatement(updateStatement);
			prepareStat.setString(1, newGroupDto.getDescription());
			prepareStat.setString(2, newGroupDto.getGroupId());

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}
	
	public boolean addGroup(GroupDto newGroupDto){
		log(newGroupDto.getGroupId() + " " + newGroupDto.getDescription());
		try{
			String insertStatement = 
					"insert into workflowdb.group "
					+ "(groupID, description)"
					+ "values (?, ?)";
			prepareStat = workflowDbConnection.prepareStatement(insertStatement);
			prepareStat.setString(1, newGroupDto.getGroupId());
			prepareStat.setString(2, newGroupDto.getDescription());

			prepareStat.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    }
	
		return true;
	}

	public boolean deleteGroup(String groupId){
		log("deleteGroup(...) - groupId: " + groupId);
		try{
			String deleteStatement = 
					"delete from workflowdb.group "
					+ "where groupID = ?";
			prepareStat = workflowDbConnection.prepareStatement(deleteStatement);
			prepareStat.setString(1, groupId);

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
