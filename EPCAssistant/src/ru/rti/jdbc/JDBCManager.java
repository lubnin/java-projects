package ru.rti.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ru.rti.excel.ExcelWriter;
import ru.rti.property.entity.DatabaseEnv;
import ru.rti.query.Query;

public class JDBCManager {
	private static Logger log = Logger.getLogger(JDBCManager.class);
	/**
	 * Registers Oracle JDBC Driver
	 */
	public static void registerOracleDriver() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			log.fatal("Oracle JDBC Driver not found.");
			e.printStackTrace();
			return;
		}
		log.info("Oracle JDBC Driver Registered - OK.");
	}
	
	public static void putValueToResultMap(Integer rowNum, String value, Map<Integer, List<String>> queryResultMap) {
		List<String> lst = queryResultMap.get(rowNum);
		if (lst == null) {
			lst = new ArrayList<String>();
		}
		lst.add(String.valueOf(value));
		queryResultMap.put(rowNum, lst);
	}
	
	public static void executeQuery(Connection con, DatabaseEnv env, Query q) throws SQLException {
		Statement stmt = null;
		String query = q.getSqlText();

		
		if (query == null) {
			//log.error("Query must contain valid SQL text. Please verify, that all queries in 'queries.xml' contain valid SQL text (body)");
			//throw new SQLException("Query sqlTest is null");
			return;
		}
		
		//String query = "select * from TBORDER";
		try {
			log.debug("Created statement");
			stmt = con.createStatement();
			
			log.debug("Executing query...");
			ResultSet rs = stmt.executeQuery(query);
			
			log.debug("Getting query metadata...");
			ResultSetMetaData rsmd = rs.getMetaData();
						
			int columnCount = rsmd.getColumnCount();
			log.debug(String.format("Columns count: %s", columnCount));
			
			Map<String, Integer> columns = new HashMap<String, Integer>();
			
			for (int i = 1; i <= columnCount; i++ ) {				
				String columnName = rsmd.getColumnName(i); 
				int columnType = rsmd.getColumnType(i);
				columns.put(columnName, Integer.valueOf(columnType));
			}

			log.debug("Reading query results...");
			
			Map<Integer, List<String>> queryResultMap = new HashMap<Integer, List<String>>();
			int row = 0;
			for (Iterator<String> it = columns.keySet().iterator(); it.hasNext();) {
				String colName = it.next();
				putValueToResultMap(row, colName, queryResultMap);
			}
			
			
			while (rs.next()) {
				row++;
				Iterator<String> it = columns.keySet().iterator();				
				StringBuilder sb = new StringBuilder();				
				while (it.hasNext()) {					
					String colName = it.next();					
					int colType = columns.get(colName);
					sb.append(colName).append(": ");
					switch (colType) {
					case Types.INTEGER: 
					case Types.NUMERIC:
						int iValue = rs.getInt(colName);
						putValueToResultMap(row, String.valueOf(iValue), queryResultMap);
						break;
					case Types.VARCHAR:
					case Types.CHAR:
					case Types.LONGNVARCHAR:
						String sValue = rs.getString(colName);
						putValueToResultMap(row, String.valueOf(sValue), queryResultMap);
						break;
					default:
						String sValueDef = rs.getString(colName);
						//sb.append(sValue).append("\r\n");
						putValueToResultMap(row, String.valueOf(sValueDef), queryResultMap);
						break;							
					}
				}
				
				log.debug(String.format("Processing row %s", row));
			}

			log.info("Saving results to Excel...");
			String fileName = env.getEnvName() + "_" + q.getName() + ".xlsx";
			ExcelWriter.writeToFile(row, fileName, columns, queryResultMap);
			
			log.info("Finished saving results in Excel for query: " + q.getName());			
		} catch (SQLException e) {
			log.error("Error executing the query:");
			e.printStackTrace();			
		} catch (IOException e) {
			log.error("Error writing to Excel file:");
			e.printStackTrace();
		} finally {
			log.debug("Finished quering database");
			if (stmt != null) {
				log.debug("Closing statement");
				stmt.close();
			}
		}
	}
}
