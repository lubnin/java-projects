package ru.rti.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ru.rti.property.entity.DatabaseEnv;
import ru.rti.query.Query;

public class ConnectionFactory {
	private static Logger log = Logger.getLogger(ConnectionFactory.class);
	
	private static Connection getOMSConnection(DatabaseEnv env) {
		try {						
			Connection connection = DriverManager.getConnection(env.getConnection(), env.getOms_username(), env.getOms_password());
			return connection;
		} catch (SQLException e) {
			log.error("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	}
	
	private static Connection getSAConnection(DatabaseEnv env) {
		try {						
			Connection connection = DriverManager.getConnection(env.getConnection(), env.getSa_username(), env.getSa_password());
			return connection;
		} catch (SQLException e) {
			log.error("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	}
	
	public static Connection getConnection(DatabaseEnv env, Query q) {
		if ("oms".equalsIgnoreCase(q.getSchema())) {
			return getOMSConnection(env);
		} else {
			return getSAConnection(env);
		}
	}
}
