package ru.rti.holidays.exception;

/**
 * @deprecated
 */
public class InvalidFieldValueException extends AbstractApplicationException {
    public InvalidFieldValueException(String message) {
        setDetailMessage(message);
    }
}
