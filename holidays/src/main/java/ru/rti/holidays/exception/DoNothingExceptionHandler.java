package ru.rti.holidays.exception;

/**
 * Default exception handler for disabling exception handling at all and do nothing.
 */
public class DoNothingExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Exception e, String errorMessage) {
        //TODO: do nothing here. This handler is needed to disable exception hanlding at all
    }
}
