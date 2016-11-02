package ru.rti.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ru.rti.jdbc.ConnectionFactory;
import ru.rti.jdbc.JDBCManager;
import ru.rti.property.AppProperties;
import ru.rti.property.AppPropertyManager;
import ru.rti.property.entity.DatabaseEnv;
import ru.rti.property.entity.DatabaseEnvManager;
import ru.rti.query.Query;
import ru.rti.query.QueryManager;

public class EPCImporter {
	private static AppPropertyManager propMgr = AppPropertyManager.getInstance();
	private static DatabaseEnvManager dbEnvMgr = DatabaseEnvManager.getInstance();
	private static QueryManager qMgr = QueryManager.getInstance();	
	private static Logger log = Logger.getLogger(EPCImporter.class);

	public static String getProperty(String propertyName) {
		return propMgr.getProperty(propertyName);
	}

	public static Connection getOMSConnection(DatabaseEnv env) {
		try {						
			Connection connection = DriverManager.getConnection(env.getConnection(), env.getOms_username(), env.getOms_password());
			return connection;
		} catch (SQLException e) {
			log.error("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Send SQL queries to existing environments
	 * @throws SQLException 
	 */
	public static void queryEnvironments() throws SQLException {
		List<DatabaseEnv> envs = dbEnvMgr.getEnvironments();
		if (envs == null) {
			log.warn("No environments detected. Nothing to query");
			return;
		}
		
		for (DatabaseEnv env : envs) {
			String envName = env.getEnvName();

			

			for (Query q : qMgr.getQueries()) {	
				Connection connection = ConnectionFactory.getConnection(env, q);
				log.info(String.format("Connected to database %s under user %s ", envName, q.getSchema()));
				try {
					JDBCManager.executeQuery(connection, env, q);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (connection != null) {
						connection.close();
						connection = null;					
					}
				}
			}			
			log.info("DONE!");					
		}
		
	}
	
	/**
	 * Main entry point of the application
	 * @param argv
	 */
	public static void main(String[] argv) {
		log.info(String.format("Started EPCAssistant (v.%s)", getProperty(AppProperties.APP_VERSION)));
		
		JDBCManager.registerOracleDriver();
		
		qMgr.loadQueriesFromXML();		
		qMgr.readQueryParamsFromInput();

		try {
			queryEnvironments();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
