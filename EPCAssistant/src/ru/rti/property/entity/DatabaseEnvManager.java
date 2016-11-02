package ru.rti.property.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * DatabaseEnvManager class stores available database environments
 * @author Maksim.Abramkin
 *
 */
public class DatabaseEnvManager {
	private static Logger log = Logger.getLogger(DatabaseEnvManager.class);
	private static DatabaseEnvManager instance = null;
	private static List<DatabaseEnv> list = new ArrayList<DatabaseEnv>();
	
	/**
	 * Private constructor
	 */
	private DatabaseEnvManager() {
		// restrict direct creation of class instances
	}
	
	/**
	 * Gets the instance of DatabaseEnvManager class
	 * @return
	 */
	public static DatabaseEnvManager getInstance() {
		if (instance == null) {
			synchronized(DatabaseEnvManager.class) {
				log.debug("Initializing instance of DatabaseEnvManager class");
				if (instance == null) {
					log.debug("Creating new instance of DatabaseEnvManager class");
					instance = new DatabaseEnvManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * Adds new database environment to the list of available environments
	 * @param envName Database environment name
	 * @param connection JDBC connection string
	 * @param saUsername SA username
	 * @param saPassword SA password
	 * @param omsUsername OMS username
	 * @param omsPassword OMS password
	 */
	public void addDatabaseEnv(String envName, String connection, String saUsername, String saPassword, String omsUsername, String omsPassword) {
		log.debug("Adding new database instance in list");
		list.add(new DatabaseEnv(envName, connection, saUsername, saPassword, omsUsername, omsPassword));
	}
	
	/**
	 * Adds database environment to the list of available environments
	 * @param instance
	 */
	public void addDatabaseEnv(DatabaseEnv instance) {
		log.debug("Adding new database instance in list");
		list.add(instance);
	}
	
	/**
	 * Logs available environments
	 */
	public void logEnvironments() {
		log.debug("Current environments in list:");
		for (DatabaseEnv env : list) {
			log.debug(env);
		}
	}
	
	/**
	 * Gets the list of all available database environments
	 * @return
	 */
	public List<DatabaseEnv> getEnvironments() {
		return list;
	}
	
	/**
	 * Gets Database environment instance by environment name
	 * @param envName
	 * @return
	 */
	public DatabaseEnv getDatabaseEnv(String envName) {
		log.debug("Getting database instance from list");
		if (envName == null || envName.length() == 0) {
			throw new IllegalArgumentException("Argument envName must not be null");
		}
		
		Iterator<DatabaseEnv> it = list.iterator();
		while (it.hasNext()) {
			DatabaseEnv env = it.next();
			if (envName.equalsIgnoreCase(env.getEnvName())) {
				return env;
			}
			
		}
		return null;
		
	}
}
