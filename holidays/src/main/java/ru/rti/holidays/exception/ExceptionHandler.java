package ru.rti.holidays.exception;

/**
 * Common interface for implementation Exception handling logic in Vaadin Views
 */
public interface ExceptionHandler {
    void handle(Exception e, String errorMessage);
}
