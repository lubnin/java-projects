package ru.rti.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import ru.rti.property.entity.DatabaseEnvManager;

/**
 * Class for managing application properties
 * @author Maksim.Abramkin
 *
 */
public class AppPropertyManager {
	private static AppPropertyManager instance = null;
	private static Properties appProperties;
	private static DatabaseEnvManager dbEnvManager = DatabaseEnvManager.getInstance();
	private static Logger log = Logger.getLogger(AppPropertyManager.class);
	
	private AppPropertyManager() {	
		// restrict direct creation of Singleton class instances
	}
	
	/**
	 * Bill Pugh Singleton solution pattern
	 * @author Maksim.Abramkin
	 */
//	private static class AppPropertyManagerHelper {
//		private static final AppPropertyManager INSTANCE = new AppPropertyManager();
//		
//		static {
//			INSTANCE.load();
//		}
//	}
	
	/**
	 * Gets the instance of AppPropertyManager
	 * @return
	 */
	public static AppPropertyManager getInstance() {
		if (instance == null) {
			synchronized(AppPropertyManager.class) {
				if (instance == null) {
					instance = new AppPropertyManager();
					instance.load();
				}
			}
		}
		return instance;
	}
	
	/**
	 * Gets all loaded application properties available
	 * @return
	 */
	public Properties getAppProperties() {
		return appProperties;
	}
	
	/**
	 * Gets application property by key
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return instance.getAppProperties().getProperty(key);
	}
	
	/**
	 * Loads particular environment
	 * @param envName
	 */
	private void loadDatabaseEnvironment(String envName) {
		String envConnection = getProperty(String.format(AppProperties.DB_ENV_CONNECTION, envName));
		String envSaUsername = getProperty(String.format(AppProperties.DB_ENV_SA_USERNAME, envName));
		String envSaPassword = getProperty(String.format(AppProperties.DB_ENV_SA_PASSWORD, envName));
		String envOmsUsername = getProperty(String.format(AppProperties.DB_ENV_OMS_USERNAME, envName));
		String envOmsPassword = getProperty(String.format(AppProperties.DB_ENV_OMS_PASSWORD, envName));		
		
		dbEnvManager.addDatabaseEnv(envName, envConnection, envSaUsername, envSaPassword, envOmsUsername, envOmsPassword);
	}
	
	/**
	 * Loads available environments and stores them in EPCDatabaseEnvManager
	 */
	private void loadDatabaseEnvironments() {
		String dbEnvs = getProperty(AppProperties.DB_ENVS);
		
		List<String> envs = Arrays.asList(dbEnvs.split(","));
		for (String env : envs) {
			loadDatabaseEnvironment(env);
		}
		dbEnvManager.logEnvironments();
	}
	
	/**
	 * Loads all the application properties, initializing the application.
	 */
	private void load() {
		log.info("Loading application properties");
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("app.properties");
		if (inputStream != null) {
			appProperties = new Properties();
			try {
				appProperties.load(inputStream);
				loadDatabaseEnvironments();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
