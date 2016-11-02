package ru.rti.property.entity;


/**
 * Entity describing one of Amdocs CRM 8.1 database environments
 * @author Maksim.Abramkin
 *
 */
public class DatabaseEnv {
	/**
	 * Database instance name
	 */
	private String envName;
	
	/**
	 * Database username for schema SA
	 */	
	private String sa_username;
	/**
	 * Database username password for schema SA
	 */
	private String sa_password;

	/**
	 * Database username for schema OMS
	 */
	
	private String oms_username;
	/**
	 * Database username password for schema OMS
	 */
	private String oms_password;	
	
	
	/**
	 * JDBC connection string for this db environment
	 */
	private String connection;


	public DatabaseEnv(String envName, String connection, String saUsername, String saPassword, String omsUsername, String omsPassword) {
		setEnvName(envName);
		setConnection(connection);
		setSa_username(saUsername);
		setSa_password(saPassword);
		setOms_username(omsUsername);
		setOms_password(omsPassword);
	}
	
	public String getEnvName() {
		return envName;
	}


	public void setEnvName(String envName) {
		this.envName = envName;
	}


	public String getSa_username() {
		return sa_username;
	}


	public void setSa_username(String sa_username) {
		this.sa_username = sa_username;
	}


	public String getSa_password() {
		return sa_password;
	}


	public void setSa_password(String sa_password) {
		this.sa_password = sa_password;
	}


	public String getOms_username() {
		return oms_username;
	}


	public void setOms_username(String oms_username) {
		this.oms_username = oms_username;
	}


	public String getOms_password() {
		return oms_password;
	}


	public void setOms_password(String oms_password) {
		this.oms_password = oms_password;
	}


	public String getConnection() {
		return connection;
	}


	public void setConnection(String connection) {
		this.connection = connection;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass());
		builder.append("@" + hashCode() + " {\r\n");
    	builder.append(String.format("\tenvName : %s\n", getEnvName()));
    	builder.append(String.format("\tconnection : %s\n", getConnection()));
    	builder.append(String.format("\tsa_username : %s\n", getSa_username()));
    	builder.append(String.format("\tsa_password : %s\n", getSa_password()));
    	builder.append(String.format("\toms_username : %s\n", getOms_username()));
    	builder.append(String.format("\toms_password : %s\n", getOms_password()));
    	builder.append("}\r\n");
		
		return builder.toString();
	}
	
	
	

	
	
}
