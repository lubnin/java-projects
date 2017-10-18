package ru.rti.holidays.exception.handler;

/**
 * Common interface for implementation Exception handling logic in Vaadin Views
 */
public interface ExceptionHandler {
    void handle(Exception e, String errorMessage);
    void setNextHandler(ExceptionHandler nextHandler);
}
