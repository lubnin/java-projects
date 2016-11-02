package ru.rti.query.xml;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import ru.rti.query.Query;
import ru.rti.query.QueryManager;
import ru.rti.query.QueryParameter;

/**
 * Event Handler for SAX parser to read XML file with queries
 * @author Maksim.Abramkin
 *
 */
public class QueryHandler extends DefaultHandler {
	private static Logger log = Logger.getLogger(QueryHandler.class);
	private static QueryManager qMgr = QueryManager.getInstance();

	Query currentQuery = null;
	QueryParameter currentQueryParameter = null;
	String currentQueryBody = null;
	
	private enum EQueryCurrentNode {
		NONE, QUERIES, QUERY, PARAMETERS, PARAMETER, BODY 
	};
	
	private EQueryCurrentNode currentNode = EQueryCurrentNode.NONE;
	
	@Override
	public void startDocument() throws SAXException {
		log.debug("Start XML Document");
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		log.debug("End XML Document");
		qMgr.logQueries();
		super.endDocument();
	}

	
	/**
	 * Gets value for the attribute of XML node
	 * @param attributes array of all atributes of XML node
	 * @param attrSearchableName name of attribute which value we want to get
	 * @return value of attribute
	 */
	private String getValForAttribute(Attributes attributes, String attrSearchableName) {
		for (int i = 0; i < attributes.getLength(); i++) {
			String attrName = attributes.getLocalName(i);
			String attrValue = attributes.getValue(i);

			if (attrSearchableName.equals(attrName)) {
				return attrValue;
			}
		}
		return "";
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("queries".equals(qName)) {
			currentNode = EQueryCurrentNode.QUERIES;
		} else if ("parameters".equals(qName)) {
			currentNode = EQueryCurrentNode.PARAMETERS;
			if (currentQuery == null) {
				throw new SAXException("currentQuery is null");
			}
		} else if ("parameter".equals(qName)) {
			currentNode = EQueryCurrentNode.PARAMETER;
			if (attributes == null) {
				throw new SAXException("Query parameter must have a 'name' attribute!");
			}

			currentQueryParameter = new QueryParameter();

			currentQueryParameter.setName(getValForAttribute(attributes, "name"));
			currentQueryParameter.setValue(getValForAttribute(attributes, "value"));
			
		} else if ("body".equals(qName)) {	
			currentNode = EQueryCurrentNode.BODY;
		} else if ("query".equals(qName)) {
			currentNode = EQueryCurrentNode.QUERY;
			currentQuery = new Query();
			if (attributes == null) {
				throw new SAXException("Query must have a 'name' attribute!");
			}

			currentQuery.setName(getValForAttribute(attributes, "name"));
			currentQuery.setDescription(getValForAttribute(attributes, "description"));
			currentQuery.setSchema(getValForAttribute(attributes, "schema"));
			
		} else {
			currentNode = EQueryCurrentNode.NONE;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		currentNode = EQueryCurrentNode.NONE;
		if ("queries".equals(qName)) {			
		} else if ("parameters".equals(qName)) {
			if (currentQuery == null) {
				throw new SAXException("currentQuery is null");
			}
		} else if ("parameter".equals(qName)) {
			currentQuery.addParam(currentQueryParameter);						
		} else if ("query".equals(qName)) {
			qMgr.addQuery(currentQuery);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = String.valueOf(ch, start, length);
		value = value.trim();
		if (value != null && value.length() > 0) {
			if (currentNode == EQueryCurrentNode.BODY) {
				currentQuery.setSqlText(value);
				log.debug("Got value for Query body: " + value);
			}
		}
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		super.error(e);
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		super.fatalError(e);
	}
}
