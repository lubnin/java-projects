package ru.rti.holidays.exception.handler;

/**
 * Abstract Exception Handler, implementing the base logic for all Exception Handlers of the Application.
 * Supports the chaining, if needed.
 */
public class AbstractExceptionHandler implements ExceptionHandler {

    ExceptionHandler nextHandler;

    public AbstractExceptionHandler() {
    }

    public AbstractExceptionHandler(ExceptionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Exception e, String errorMessage) {
        if (nextHandler != null) {
            nextHandler.handle(e, errorMessage);
        }
    }

    @Override
    public void setNextHandler(ExceptionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
