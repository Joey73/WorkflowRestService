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

//http://crunchify.com/java-mysql-jdbc-hello-world-tutorial-create-connection-insert-data-and-retrieve-data-from-mysql/
public class WorkflowDb {
	public static final String CONNECTION_STRING = "jdbc:mysql://172.17.0.2:3306/workflowdb";
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return processDataDto;
	}

	private static void log(String string) {
		System.out.println(string);
 
	}	
}
