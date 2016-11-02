package ru.rti.property.exception;

public class AppPropertyParseException extends Exception {
	private static final long serialVersionUID = 7425442099636173364L;
	public AppPropertyParseException() { super(); }
	  public AppPropertyParseException(String message) { super(message); }
	  public AppPropertyParseException(String message, Throwable cause) { super(message, cause); }
	  public AppPropertyParseException(Throwable cause) { super(cause); }
}
