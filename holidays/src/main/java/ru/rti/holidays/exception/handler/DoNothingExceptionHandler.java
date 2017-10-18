package ru.rti.holidays.exception.handler;

/**
 * Default exception handler for disabling exception handling at all and do nothing.
 */
public class DoNothingExceptionHandler extends AbstractExceptionHandler {
    @Override
    public void handle(Exception e, String errorMessage) {
        // Do nothing here. This handler is needed to disable exception handling at all
    }

    @Override
    public void setNextHandler(ExceptionHandler nextHandler) {
        // Do nothing
    }
}
