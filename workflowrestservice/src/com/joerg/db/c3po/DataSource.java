package com.joerg.db.c3po;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource {
	private static DataSource     datasource;
    private ComboPooledDataSource cpds;

    private DataSource() throws IOException, SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        //cpds.setJdbcUrl("jdbc:mysql://localhost:3306/workflowdb");
        //cpds.setJdbcUrl("jdbc:mysql://localhost:3306/resisdb");
        //cpds.setJdbcUrl("jdbc:mysql://172.17.0.2:3306/workflowdb");
        cpds.setJdbcUrl("jdbc:mysql://172.17.0.2:3306/resisdb");
        cpds.setUser("root");
        cpds.setPassword("12345");

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(180);

    }

    public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
        if (datasource == null) {
            datasource = new DataSource();
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }
}
