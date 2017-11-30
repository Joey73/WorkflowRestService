package com.joerg.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;
import com.joerg.rest.dtos.GroupDto;
import com.joerg.rest.dtos.GroupListDto;
import com.joerg.rest.dtos.ProcessDataDto;
import com.joerg.rest.dtos.ProcessDataDtoList;
import com.joerg.rest.dtos.RightDto;
import com.joerg.rest.dtos.RightListDto;
import com.joerg.rest.dtos.RightUiComponentDto;
import com.joerg.rest.dtos.RightUiComponentListDto;
import com.joerg.rest.dtos.UserDto;
import com.joerg.rest.dtos.UserListDto;
import com.joerg.rest.dtos.UserRightDto;
import com.joerg.rest.dtos.UserRightListDto;

public class WorkflowDb {
	public static final String DB = "workflowdb";
	private Context context = null;
	static Connection workflowDbConnection = null;
	
	public WorkflowDb(){
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/workflowdb");
			workflowDbConnection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ProcessDataDtoList getAllProcessData() {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String selectStatement = "SELECT * FROM " + DB + ".processdata";
		ProcessDataDtoList processDataDtoList = new ProcessDataDtoList();
		try {
 
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
 
			rs = preparedStatement.executeQuery();
 
			while (rs.next()) {
				processDataDtoList.addProcessDataDto(
						new ProcessDataDto(
								rs.getString("processInstanceID"),
								rs.getString("field1"),
								rs.getString("field2"),
								rs.getString("field3"),
								rs.getString("field4"),
								rs.getString("field5"),
								rs.getString("field6")
						)
				);

				String processInstanceID = rs.getString("processInstanceID");
				String field1 = rs.getString("field1");
				String field2 = rs.getString("field2");
				String field3 = rs.getString("field3");
				String field4 = rs.getString("field4");
				String field5 = rs.getString("field5");
				String field6 = rs.getString("field6");
 
				System.out.format("%s, %s, %s, %s, %s, %s, %s\n", processInstanceID, field1, field2, field3, field4, field5, field6);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            closeEverthing(rs, preparedStatement);
        }		
		return processDataDtoList; 
	}

	public ProcessDataDto getProcessData(String processInstanceId) {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		ProcessDataDto processDataDto = new ProcessDataDto();
		try{
			String selectStatement = "Select * from " + DB + ".processdata where processInstanceID = '" + processInstanceId + "'";
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
			 
			rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				processDataDto = new ProcessDataDto(
						rs.getString("processInstanceID"),
						rs.getString("field1"),
						rs.getString("field2"),
						rs.getString("field3"),
						rs.getString("field4"),
						rs.getString("field5"),
						rs.getString("field6"));

				String processInstanceID = rs.getString("processInstanceID");
				String field1 = rs.getString("field1");
				String field2 = rs.getString("field2");
				String field3 = rs.getString("field3");
				String field4 = rs.getString("field4");
				String field5 = rs.getString("field5");
 
				System.out.format("%s, %s, %s, %s\n", processInstanceID, field1, field2, field3);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return processDataDto;
	}

	public boolean addProcessData(ProcessDataDto processDataDto){
		PreparedStatement preparedStatement = null;
		log("addProcessData(...) - processDataDto.getProcessInstanceId(): " + processDataDto.getProcessInstanceId());
		try{
			String insertStatement = 
					"insert into " + DB + ".processdata "
					+ "(processInstanceID)"
					+ "values (?)";
			preparedStatement = workflowDbConnection.prepareStatement(insertStatement);
			preparedStatement.setString(1, processDataDto.getProcessInstanceId());

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}

	public boolean updateProcessData(ProcessDataDto newProcessDataDto){
		PreparedStatement preparedStatement = null;
		try{
			String updateStatement = 
					"update " + DB + ".processdata "
					+ "set field1 = ?, "
					+ "field2 = ?, "
					+ "field3 = ?, "
					+ "field4 = ?, "
					+ "field5 = ?, "
					+ "field6 = ? "
					+ "where processInstanceID = ?";
			preparedStatement = workflowDbConnection.prepareStatement(updateStatement);
			preparedStatement.setString(1, newProcessDataDto.getField1());
			preparedStatement.setString(2, newProcessDataDto.getField2());
			preparedStatement.setString(3, newProcessDataDto.getField3());
			preparedStatement.setString(4, newProcessDataDto.getField4());
			preparedStatement.setString(5, newProcessDataDto.getField5());
			preparedStatement.setString(6, newProcessDataDto.getField6());
			preparedStatement.setString(7, newProcessDataDto.getProcessInstanceId());

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}
	
	public RightUiComponentListDto getAllRightUiComponents(String rightId) {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		RightUiComponentListDto rightUiComponentListDto = new RightUiComponentListDto();
		try {
			String selectStatement = "SELECT * FROM " + DB + ".right_uicomponent WHERE rightId = ?";
 
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
			preparedStatement.setString(1, rightId);
 
			rs = preparedStatement.executeQuery();
 
			while (rs.next()) {
				rightUiComponentListDto.addRightUiComponentDto(
					new RightUiComponentDto(
						rs.getString("rightId"),
						rs.getString("uicomponentId"),
						(rs.getInt("visible") == 1),
						(rs.getInt("enabled") == 1),
						(rs.getInt("required") == 1)
					)
				);

				rightId = rs.getString("rightId");
				String uicomponentId = rs.getString("uicomponentId");
				boolean visible = (rs.getInt("visible") == 1);
				boolean enabled = (rs.getInt("enabled") == 1);
				boolean required = (rs.getInt("required") == 1);
 
				System.out.format("%s, %s, %s, %s, %s\n", rightId, uicomponentId, visible, enabled, required);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return rightUiComponentListDto; 
	}

	public UserRightListDto getAllUserRights(String userId) {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		UserRightListDto userRightListDto = new UserRightListDto();
		try {
			String selectStatement = "SELECT * FROM " + DB + ".user_right WHERE userId = ?";
 
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
			preparedStatement.setString(1, userId);
 
			rs = preparedStatement.executeQuery();
 
			while (rs.next()) {
				userRightListDto.addUserRightDto(
					new UserRightDto(
						rs.getString("userId"),
						rs.getString("rightId")
					)
				);

				userId = rs.getString("userId");
				String rightId = rs.getString("rightId");
 
				System.out.format("%s, %s\n", userId, rightId);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return userRightListDto; 
	}
	
	public RightUiComponentDto getRightUiComponent(String rightId, String uicomponentId) {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		RightUiComponentDto rightUiComponentDto = new RightUiComponentDto();
		try{
			String selectStatement = "Select * from " + DB + ".right_uicomponent where rightid = '" + rightId + "' and uicomponentId = '" + uicomponentId + "'";
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
			 
			rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				rightUiComponentDto = new RightUiComponentDto(
						rs.getString("rightId"),
						rs.getString("uicomponentId"),
						(rs.getInt("visible") == 1),
						(rs.getInt("enabled") == 1),
						(rs.getInt("required") == 1)
				);

				rightId = rs.getString("rightId");
				uicomponentId = rs.getString("uicomponentId");
				boolean visible = (rs.getInt("visible") == 1);
				boolean enabled = (rs.getInt("enabled") == 1);
				boolean required = (rs.getInt("required") == 1);
 
				System.out.format("%s, %s, %s, %s, %s\n", rightId, uicomponentId, visible, enabled, required);
			}
			//workflowDbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return rightUiComponentDto;
	}

	public boolean updateRightUiComponent(RightUiComponentDto newRightUiComponentDto){
		PreparedStatement preparedStatement = null;
		try{
			String updateStatement = 
					"update " + DB + ".right_uicomponent "
					+ "set enabled = ?, "
					+ "required = ?, "
					+ "visible = ? "
					+ "where uicomponentId = ? and rightId = ?";
			preparedStatement = workflowDbConnection.prepareStatement(updateStatement);
			preparedStatement.setInt(1, newRightUiComponentDto.isEnabled() ? 1 : 0);
			preparedStatement.setInt(2, newRightUiComponentDto.isRequired() ? 1 : 0);
			preparedStatement.setInt(3, newRightUiComponentDto.isVisible() ? 1 : 0);
			preparedStatement.setString(4, newRightUiComponentDto.getUicomponentId());
			preparedStatement.setString(5, newRightUiComponentDto.getRightId());

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}

	public UserListDto getAllUsers() {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		UserListDto userListDto = new UserListDto();
		try {
			String selectStatement = "SELECT * FROM " + DB + ".user";
 
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
 
			rs = preparedStatement.executeQuery();
 
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
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return userListDto; 
	}
	
	public UserDto getUser(String userId) {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		UserDto userDto = new UserDto();
		try{
			String selectStatement = "Select * from " + DB + ".user where userID = '" + userId + "'";
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
			 
			rs = preparedStatement.executeQuery();
			
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
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return userDto;
	}
	
	public boolean updateUser(UserDto newUserDto){
		PreparedStatement preparedStatement = null;
		log(newUserDto.getId() + " " + newUserDto.getLastName() + " " + newUserDto.getFirstName() + " " + newUserDto.getEmail());
		try{
			String updateStatement = 
					"update " + DB + ".user "
					+ "set lastname = ?, "
					+ "firstname = ?, "
					+ "email = ? "
					+ "where userID = ?";
			preparedStatement = workflowDbConnection.prepareStatement(updateStatement);
			preparedStatement.setString(1, newUserDto.getLastName());
			preparedStatement.setString(2, newUserDto.getFirstName());
			preparedStatement.setString(3, newUserDto.getEmail());
			preparedStatement.setString(4, newUserDto.getId());

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}
	
	public boolean addUser(UserDto newUserDto){
		PreparedStatement preparedStatement = null;
		log(newUserDto.getId() + " " + newUserDto.getLastName() + " " + newUserDto.getFirstName() + " " + newUserDto.getEmail());
		try{
			String insertStatement = 
					"insert into " + DB + ".user "
					+ "(userID, lastname, firstname, email)"
					+ "values (?, ?, ?, ?)";
			preparedStatement = workflowDbConnection.prepareStatement(insertStatement);
			preparedStatement.setString(1, newUserDto.getId());
			preparedStatement.setString(2, newUserDto.getLastName());
			preparedStatement.setString(3, newUserDto.getFirstName());
			preparedStatement.setString(4, newUserDto.getEmail());

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}

	public boolean deleteUser(String userId){
		PreparedStatement preparedStatement = null;
		log("deleteUser(...) - userId: " + userId);
		try{
			String deleteStatement = 
					"delete from " + DB + ".user "
					+ "where userID = ?";
			preparedStatement = workflowDbConnection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, userId);

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}
	
	public RightListDto getAllRights() {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		RightListDto rightListDto = new RightListDto();
		try {
			String selectStatement = "SELECT * FROM " + DB + ".right";
 
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
 
			rs = preparedStatement.executeQuery();
 
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
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return rightListDto; 
	}
	
	public RightDto getRight(String rightId) {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		RightDto rightDto = new RightDto();
		try{
			String selectStatement = "Select * from " + DB + ".right where rightID = '" + rightId + "'";
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
			 
			rs = preparedStatement.executeQuery();
			
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
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return rightDto;
	}

	public RightDto getRightViaUser(String userId) {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		RightDto rightDto = new RightDto();
		try{
			String selectStatement = "select r.*"
					+ " from " + DB + ".right r"
					+ " inner join " + DB + ".user_right ur on r.rightID = ur.rightId"
					+ " where ur.userId = '" + userId + "'";
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
			 
			rs = preparedStatement.executeQuery();
			
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
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return rightDto;
	}
	
	public boolean updateRight(RightDto newRightDto){
		PreparedStatement preparedStatement = null;
		log(newRightDto.getRightId() + " " + newRightDto.getDescription());
		try{
			String updateStatement = 
					"update " + DB + ".right "
					+ "set description = ? "
					+ "where rightID = ?";
			preparedStatement = workflowDbConnection.prepareStatement(updateStatement);
			preparedStatement.setString(1, newRightDto.getDescription());
			preparedStatement.setString(2, newRightDto.getRightId());

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}
	
	public boolean addRight(RightDto newRightDto){
		PreparedStatement preparedStatement = null;
		log(newRightDto.getRightId() + " " + newRightDto.getDescription());
		try{
			String insertStatement = 
					"insert into " + DB + ".right "
					+ "(rightID, description)"
					+ "values (?, ?)";
			preparedStatement = workflowDbConnection.prepareStatement(insertStatement);
			preparedStatement.setString(1, newRightDto.getRightId());
			preparedStatement.setString(2, newRightDto.getDescription());

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}

	public boolean addUserRight(UserRightDto newUserRightDto){
		PreparedStatement preparedStatement = null;
		log("newUserRightDto.getUserId():" + newUserRightDto.getUserId() + ", newUserRightDto.getRightId(): " + newUserRightDto.getRightId());
		try{
			String insertStatement = 
					"insert into " + DB + ".user_right "
					+ "(userId, rightId)"
					+ "values (?, ?)";
			preparedStatement = workflowDbConnection.prepareStatement(insertStatement);
			preparedStatement.setString(1, newUserRightDto.getUserId());
			preparedStatement.setString(2, newUserRightDto.getRightId());

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}
	
	public boolean deleteRight(String rightId){
		PreparedStatement preparedStatement = null;
		log("deleteRight(...) - rightId: " + rightId);
		try{
			String deleteStatement = 
					"delete from " + DB + ".right "
					+ "where rightID = ?";
			preparedStatement = workflowDbConnection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, rightId);

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}

	public boolean deleteUserRight(String userId, String rightId){
		PreparedStatement preparedStatement = null;
		log("userId:" + userId + ", rightId: " + rightId);
		try{
			String deleteStatement = 
					"delete from " + DB + ".user_right "
					+ "where userId = ? and rightId = ?";
			preparedStatement = workflowDbConnection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, rightId);

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}
	
	public GroupListDto getAllGroups() {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		GroupListDto groupListDto = new GroupListDto();
		try {
			String selectStatement = "SELECT * FROM " + DB + ".group";
 
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
 
			rs = preparedStatement.executeQuery();
 
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
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return groupListDto; 
	}
	
	public GroupDto getGroup(String groupId) {
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		GroupDto groupDto = new GroupDto();
		try{
			String selectStatement = "Select * from " + DB + ".group where groupID = '" + groupId + "'";
			preparedStatement = workflowDbConnection.prepareStatement(selectStatement);
			 
			rs = preparedStatement.executeQuery();
			
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
		} finally {
            closeEverthing(rs, preparedStatement);
        }
		
		return groupDto;
	}
	
	public boolean updateGroup(GroupDto newGroupDto){
		PreparedStatement preparedStatement = null;
		log(newGroupDto.getGroupId() + " " + newGroupDto.getDescription());
		try{
			String updateStatement = 
					"update " + DB + ".group "
					+ "set description = ? "
					+ "where groupID = ?";
			preparedStatement = workflowDbConnection.prepareStatement(updateStatement);
			preparedStatement.setString(1, newGroupDto.getDescription());
			preparedStatement.setString(2, newGroupDto.getGroupId());

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}
	
	public boolean addGroup(GroupDto newGroupDto){
		PreparedStatement preparedStatement = null;
		log(newGroupDto.getGroupId() + " " + newGroupDto.getDescription());
		try{
			String insertStatement = 
					"insert into " + DB + ".group "
					+ "(groupID, description)"
					+ "values (?, ?)";
			preparedStatement = workflowDbConnection.prepareStatement(insertStatement);
			preparedStatement.setString(1, newGroupDto.getGroupId());
			preparedStatement.setString(2, newGroupDto.getDescription());

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}

	public boolean deleteGroup(String groupId){
		PreparedStatement preparedStatement = null;

		log("deleteGroup(...) - groupId: " + groupId);
		try{
			String deleteStatement = 
					"delete from " + DB + ".group "
					+ "where groupID = ?";
			preparedStatement = workflowDbConnection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, groupId);

			preparedStatement.executeUpdate();
	      
			//workflowDbConnection.close();
	    }catch (Exception e){
	        System.err.println("Got an exception! ");
	        System.err.println(e.getMessage());
	    } finally {
            closeEverthing(preparedStatement);
        }
	
		return true;
	}
	
	private static void log(String string) {
		System.out.println(string);
 
	}

	private void closeEverthing(PreparedStatement preparedStatement) {
		this.closeEverthing(null, preparedStatement);
	}

	private void closeEverthing(ResultSet rs, PreparedStatement preparedStatement) {
		if (rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (preparedStatement != null){
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (workflowDbConnection != null){
			try {
				workflowDbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
