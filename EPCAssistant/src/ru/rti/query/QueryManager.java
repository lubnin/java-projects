package ru.rti.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import ru.rti.query.xml.QueryHandler;
import ru.rti.query.xml.QueryParser;

public class QueryManager {
	private static QueryManager instance = null;
	private List<Query> list = new ArrayList<Query>();
	private static Logger log = Logger.getLogger(QueryHandler.class);
	
	private QueryManager() {		
	}
	
	public List<Query> getQueries() {
		return list;
	}
	
	public void logQueries() {
		if (list != null) {
			for (Query q : list) {
				log.debug(q);
			}
		}
	}
	
	public boolean isQueryHasParamsWithoutValue(Query q) {
		List<QueryParameter> paramsList = q.getParamsList();
		for (QueryParameter qParameter : paramsList) {
			if (!qParameter.hasValue()) {
				return true;
			}
		}
		return false;
	}
	
	public void readQueryParamsFromInput(Query q) {
		String sql = q.getSqlText();
		if (sql == null) {
			return;
		}

		if (isQueryHasParamsWithoutValue(q)) {			
			log.debug(String.format("Query '%s': Reading input for SQL parameter value", q.getName()));
			Scanner scanner = new Scanner(System.in);
			
			List<QueryParameter> paramsList = q.getParamsList();
			
			for (QueryParameter qParameter : paramsList) {
				System.out.println(String.format("Enter value for parameter %s", qParameter.getName()));
				String paramVal = scanner.nextLine();			
				qParameter.setValue(paramVal);	
				
				sql = sql.replaceAll("\\{" + qParameter.getName() + "\\}", qParameter.getValue());
				q.setSqlText(sql);
			}
			
			q.setParamsReplaced(true);
		}

		log.debug(String.format("Query after replacement: %s", sql));		
	}
	
	/**
	 * Replace parameters in SQL of all queries with fact values from user input
	 */
	public void readQueryParamsFromInput() {
		for (Query q : getQueries()) {
			readQueryParamsFromInput(q);
		}
	}
	
	/**
	 * Loads SQL queries configuration from XML 'queries.xml' to memory
	 */
	public void loadQueriesFromXML() {
		QueryParser p = new QueryParser();		
		try {
			log.info("Loading available queries from 'queries.xml'");
			p.parse();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static QueryManager getInstance() {
		if (instance == null) {
			synchronized(QueryManager.class){ 
				if (instance == null) {
					instance = new QueryManager();
				}
			}
		}
		return instance;
	}
	
	public void addQuery(Query query) {
		list.add(query);
	}
	
}
