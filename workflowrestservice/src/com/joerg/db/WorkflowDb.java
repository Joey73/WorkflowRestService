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

	private final DataSource dataSource;

	public WorkflowDb() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/workflowdb");
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException("error lookup datasource", e);
		}
	}

	public ProcessDataDtoList getAllProcessData() throws SQLException {
		String selectStatement = "SELECT * FROM " + DB + ".processdata";
		ProcessDataDtoList processDataDtoList = new ProcessDataDtoList();

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				try (ResultSet rs = preparedStatement.executeQuery()) {
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

						System.out.format("%s, %s, %s, %s, %s, %s, %s\n", processInstanceID, field1, field2, field3,
								field4, field5, field6);

					}
				}
			}
		}

		return processDataDtoList;

	}

	public ProcessDataDto getProcessData(String processInstanceId) throws SQLException {
		String selectStatement = "Select * from " + DB + ".processdata where processInstanceID = ?";
		
		ProcessDataDto processDataDto = new ProcessDataDto();

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				preparedStatement.setString(1, processInstanceId);
				
				try (ResultSet rs = preparedStatement.executeQuery()) {
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
						String field6 = rs.getString("field6");

						System.out.format("%s, %s, %s, %s, %s, %s\n", processInstanceID, field1, field2, field3, field4, field5, field6);
					}
				}
			}
		}

		return processDataDto;
	}

	public boolean addProcessData(ProcessDataDto processDataDto) throws SQLException {
		String insertStatement = "insert into " + DB + ".processdata " + "(processInstanceID)" + "values (?)";

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertStatement)) {
				preparedStatement.setString(1, processDataDto.getProcessInstanceId());
				
				preparedStatement.executeUpdate();
			}
		}
		
		return true;
	}

	public boolean updateProcessData(ProcessDataDto newProcessDataDto) throws SQLException {
		String updateStatement = "update " + DB + ".processdata " + "set field1 = ?, " + "field2 = ?, "
				+ "field3 = ?, " + "field4 = ?, " + "field5 = ?, " + "field6 = ? " + "where processInstanceID = ?";

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
				preparedStatement.setString(1, newProcessDataDto.getField1());
				preparedStatement.setString(2, newProcessDataDto.getField2());
				preparedStatement.setString(3, newProcessDataDto.getField3());
				preparedStatement.setString(4, newProcessDataDto.getField4());
				preparedStatement.setString(5, newProcessDataDto.getField5());
				preparedStatement.setString(6, newProcessDataDto.getField6());
				preparedStatement.setString(7, newProcessDataDto.getProcessInstanceId());

				preparedStatement.executeUpdate();
			}
		}

		return true;
	}

	public RightUiComponentListDto getAllRightUiComponents(String rightId) throws SQLException {
		String selectStatement = "SELECT * FROM " + DB + ".right_uicomponent WHERE rightId = ?";
		
		RightUiComponentListDto rightUiComponentListDto = new RightUiComponentListDto();
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				preparedStatement.setString(1, rightId);
				
				try (ResultSet rs = preparedStatement.executeQuery()) {
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
				}
			}
		}

		return rightUiComponentListDto;
	}

	public UserRightListDto getAllUserRights(String userId) throws SQLException {
		String selectStatement = "SELECT * FROM " + DB + ".user_right WHERE userId = ?";
		
		UserRightListDto userRightListDto = new UserRightListDto();
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				preparedStatement.setString(1, userId);
				
				try (ResultSet rs = preparedStatement.executeQuery()) {
					while (rs.next()) {
						userRightListDto.addUserRightDto(new UserRightDto(rs.getString("userId"), rs.getString("rightId")));

						userId = rs.getString("userId");
						String rightId = rs.getString("rightId");

						System.out.format("%s, %s\n", userId, rightId);
					}
				}
			}
		}

		return userRightListDto;
	}

	public RightUiComponentDto getRightUiComponent(String rightId, String uicomponentId) throws SQLException {
		String selectStatement = "Select * from " + DB + ".right_uicomponent where rightid = ? and uicomponentId = ?";
		
		RightUiComponentDto rightUiComponentDto = new RightUiComponentDto();

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				preparedStatement.setString(1, rightId);
				preparedStatement.setString(2, uicomponentId);
				
				try (ResultSet rs = preparedStatement.executeQuery()) {
					while (rs.next()) {
						rightUiComponentDto = new RightUiComponentDto(rs.getString("rightId"), rs.getString("uicomponentId"),
								(rs.getInt("visible") == 1), (rs.getInt("enabled") == 1), (rs.getInt("required") == 1));

						rightId = rs.getString("rightId");
						uicomponentId = rs.getString("uicomponentId");
						boolean visible = (rs.getInt("visible") == 1);
						boolean enabled = (rs.getInt("enabled") == 1);
						boolean required = (rs.getInt("required") == 1);

						System.out.format("%s, %s, %s, %s, %s\n", rightId, uicomponentId, visible, enabled, required);
					}
				}
			}
		}

		return rightUiComponentDto;
	}

	public boolean updateRightUiComponent(RightUiComponentDto newRightUiComponentDto) throws SQLException {
		String updateStatement = "update " + DB + ".right_uicomponent " + "set enabled = ?, " + "required = ?, "
				+ "visible = ? " + "where uicomponentId = ? and rightId = ?";
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
				preparedStatement.setInt(1, newRightUiComponentDto.isEnabled() ? 1 : 0);
				preparedStatement.setInt(2, newRightUiComponentDto.isRequired() ? 1 : 0);
				preparedStatement.setInt(3, newRightUiComponentDto.isVisible() ? 1 : 0);
				preparedStatement.setString(4, newRightUiComponentDto.getUicomponentId());
				preparedStatement.setString(5, newRightUiComponentDto.getRightId());

				preparedStatement.executeUpdate();
			}
		}

		return true;
	}

	public UserListDto getAllUsers() throws SQLException {
		String selectStatement = "SELECT * FROM " + DB + ".user";
		
		UserListDto userListDto = new UserListDto();
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				try (ResultSet rs = preparedStatement.executeQuery()) {
					while (rs.next()) {
						userListDto.addUserDto(new UserDto(rs.getString("userId"), rs.getString("lastname"),
								rs.getString("firstname"), rs.getString("email")));

						String userId = rs.getString("userId");
						String lastname = rs.getString("lastname");
						String firstname = rs.getString("firstname");
						String email = rs.getString("email");

						System.out.format("%s, %s, %s, %s\n", userId, lastname, firstname, email);
					}
				}
			}
		}

		return userListDto;
	}

	public UserDto getUser(String userId) throws SQLException {
		String selectStatement = "Select * from " + DB + ".user where userID = ?";
		
		UserDto userDto = new UserDto();
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				preparedStatement.setString(1, userId);
				
				try (ResultSet rs = preparedStatement.executeQuery()) {
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
				}
			}
		}

		return userDto;
	}

	public boolean updateUser(UserDto newUserDto) throws SQLException {
		String updateStatement = "update " + DB + ".user " + "set lastname = ?, " + "firstname = ?, " + "email = ? "
				+ "where userID = ?";
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
				preparedStatement.setString(1, newUserDto.getLastName());
				preparedStatement.setString(2, newUserDto.getFirstName());
				preparedStatement.setString(3, newUserDto.getEmail());
				preparedStatement.setString(4, newUserDto.getId());
				preparedStatement.executeUpdate();
			}
		}

		return true;
	}

	public boolean addUser(UserDto newUserDto) throws SQLException {
		String insertStatement = "insert into " + DB + ".user " + "(userID, lastname, firstname, email)"
				+ "values (?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertStatement)) {
				preparedStatement.setString(1, newUserDto.getId());
				preparedStatement.setString(2, newUserDto.getLastName());
				preparedStatement.setString(3, newUserDto.getFirstName());
				preparedStatement.setString(4, newUserDto.getEmail());
				
				preparedStatement.executeUpdate();
			}
		}

		return true;
	}

	public boolean deleteUser(String userId) throws SQLException {
		String deleteStatement = "delete from " + DB + ".user " + "where userID = ?";
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement)) {
				preparedStatement.setString(1, userId);

				preparedStatement.executeUpdate();
			}
		}

		return true;
	}

	public RightListDto getAllRights() throws SQLException {
		String selectStatement = "SELECT * FROM " + DB + ".right";
		
		RightListDto rightListDto = new RightListDto();

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				try (ResultSet rs = preparedStatement.executeQuery()) {
					while (rs.next()) {
						rightListDto.addRightDto(new RightDto(rs.getString("rightId"), rs.getString("description")));

						String rightId = rs.getString("rightId");
						String description = rs.getString("description");

						System.out.format("%s, %s\n", rightId, description);
					}
				}
			}
		}

		return rightListDto;
	}

	public RightDto getRight(String rightId) throws SQLException {
		String selectStatement = "Select * from " + DB + ".right where rightID = ?";
		
		RightDto rightDto = new RightDto();
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				preparedStatement.setString(1, rightId);
				
				try (ResultSet rs = preparedStatement.executeQuery()) {
					while (rs.next()) {
						rightDto = new RightDto(rs.getString("rightId"), rs.getString("description"));

						String uId = rs.getString("rightId");
						String description = rs.getString("description");

						System.out.format("%s, %s\n", uId, description);
					}
				}
			}
		}

		return rightDto;
	}

	public RightDto getRightViaUser(String userId) throws SQLException {
		String selectStatement = "select r.*" + " from " + DB + ".right r" + " inner join " + DB
				+ ".user_right ur on r.rightID = ur.rightId" + " where ur.userId = ?";
		
		RightDto rightDto = new RightDto();

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				preparedStatement.setString(1, userId);
				
				try (ResultSet rs = preparedStatement.executeQuery()) {
					while (rs.next()) {
						rightDto = new RightDto(rs.getString("rightId"), rs.getString("description"));

						String uId = rs.getString("rightId");
						String description = rs.getString("description");

						System.out.format("%s, %s\n", uId, description);
					}
				}
			}
		}

		return rightDto;
	}

	public boolean updateRight(RightDto newRightDto) throws SQLException {
		String updateStatement = "update " + DB + ".right " + "set description = ? " + "where rightID = ?";
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
				preparedStatement.setString(1, newRightDto.getDescription());
				preparedStatement.setString(2, newRightDto.getRightId());
				
				preparedStatement.executeUpdate();
			}
		}

		return true;
	}

	public boolean addRight(RightDto newRightDto) throws SQLException {
		String insertStatement = "insert into " + DB + ".right " + "(rightID, description)" + "values (?, ?)";
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertStatement)) {
				preparedStatement.setString(1, newRightDto.getRightId());
				preparedStatement.setString(2, newRightDto.getDescription());
				
				preparedStatement.executeUpdate();
			}
		}
		
		return true;
	}

	public boolean addUserRight(UserRightDto newUserRightDto) throws SQLException {
		String insertStatement = "insert into " + DB + ".user_right " + "(userId, rightId)" + "values (?, ?)";

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertStatement)) {
				preparedStatement.setString(1, newUserRightDto.getUserId());
				preparedStatement.setString(2, newUserRightDto.getRightId());
				
				preparedStatement.executeUpdate();
			}
		}

		return true;
	}

	public boolean deleteRight(String rightId) throws SQLException {
		String deleteStatement = "delete from " + DB + ".right " + "where rightID = ?";
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement)) {
				preparedStatement.setString(1, rightId);

				preparedStatement.executeUpdate();
			}
		}

		return true;
	}

	public boolean deleteUserRight(String userId, String rightId) throws SQLException {
		String deleteStatement = "delete from " + DB + ".user_right " + "where userId = ? and rightId = ?";
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement)) {
				preparedStatement.setString(1, userId);
				preparedStatement.setString(2, rightId);

				preparedStatement.executeUpdate();
			}
		}
		
		return true;
	}

	public GroupListDto getAllGroups() throws SQLException {
		String selectStatement = "SELECT * FROM " + DB + ".group";
		
		GroupListDto groupListDto = new GroupListDto();

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				try (ResultSet rs = preparedStatement.executeQuery()) {
					while (rs.next()) {
						groupListDto.addGroupDto(new GroupDto(rs.getString("groupId"), rs.getString("description")));

						String groupId = rs.getString("groupId");
						String description = rs.getString("description");

						System.out.format("%s, %s\n", groupId, description);
					}
				}
			}
		}

		return groupListDto;
	}

	public GroupDto getGroup(String groupId) throws SQLException {
		String selectStatement = "Select * from " + DB + ".group where groupID = ?";

		GroupDto groupDto = new GroupDto();
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
				preparedStatement.setString(1, groupId);
				try (ResultSet rs = preparedStatement.executeQuery()) {
					while (rs.next()) {
						groupDto = new GroupDto(rs.getString("groupId"), rs.getString("description"));

						String uId = rs.getString("groupId");
						String description = rs.getString("description");

						System.out.format("%s, %s\n", uId, description);
					}
				}
			}
		}
		
		return groupDto;
	}

	public boolean updateGroup(GroupDto newGroupDto) throws SQLException {
		String updateStatement = "update " + DB + ".group " + "set description = ? " + "where groupID = ?";
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
				preparedStatement.setString(1, newGroupDto.getDescription());
				preparedStatement.setString(2, newGroupDto.getGroupId());

				preparedStatement.executeUpdate();
			}
		}

		return true;
	}

	public boolean addGroup(GroupDto newGroupDto) throws SQLException {
		String insertStatement = "insert into " + DB + ".group " + "(groupID, description)" + "values (?, ?)";

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertStatement)) {
				preparedStatement.setString(1, newGroupDto.getGroupId());
				preparedStatement.setString(2, newGroupDto.getDescription());

				preparedStatement.executeUpdate();
			}
		}
		
		return true;
	}

	public boolean deleteGroup(String groupId) throws SQLException {
		String deleteStatement = "delete from " + DB + ".group " + "where groupID = ?";
		
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement)) {
				preparedStatement.setString(1, groupId);

				preparedStatement.executeUpdate();
			}
		}

		return true;
	}

	private static void log(String string) {
		System.out.println(string);

	}
}
