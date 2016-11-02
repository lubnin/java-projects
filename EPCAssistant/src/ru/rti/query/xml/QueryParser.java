package ru.rti.query.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


/**
 * SAX Parser for parsing queries stored in application configuration XML file 'queries.xml'
 * @author Maksim.Abramkin
 */
public class QueryParser {
	public QueryParser() {}
	
	/**
	 * Creates SAXParserFactory instance and creates a new SAX parser. 
	 * Reads 'queries.xml' in the InputStream and transfers control to SAX parser event handler
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void parse() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("queries.xml");
		QueryHandler handler = new QueryHandler();
		
		saxParser.parse(inputStream, handler);
		
	}
}
