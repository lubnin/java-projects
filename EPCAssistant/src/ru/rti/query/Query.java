package ru.rti.query;

import java.util.ArrayList;
import java.util.List;

public class Query {
	private boolean isParamsReplaced = false;

	/**
	 * Name for SQL Query
	 */
	private String name;
	
	/**
	 * SQL Text (parametrized) of the query
	 */
	private String sqlText;
	
	/**
	 * Description for a query
	 */
	private String description;
	
	/**
	 * Oracle DB Schema to execute the query
	 */
	private String schema;
	
	/**
	 * List of fact parameters for the query to make it executable
	 */
	private List<QueryParameter> paramsList = new ArrayList<QueryParameter>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	
	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSchema() {
		return schema;
	}

	public List<QueryParameter> getParamsList() {
		return paramsList;
	}

	public void setParamsList(List<QueryParameter> paramsList) {
		this.paramsList = paramsList;
	}
	
	public void addParam(QueryParameter param) {
		getParamsList().add(param);
	}

	
	public boolean isParamsReplaced() {
		return isParamsReplaced;
	}

	public void setParamsReplaced(boolean isParamsReplaced) {
		this.isParamsReplaced = isParamsReplaced;
	}
	


	@Override
	public String toString() {
		return this.getClass() + "@" + hashCode() + " {\r\n\t" +
		getName() + "\r\n}\r\n";
	}
	
	
	
	
}
