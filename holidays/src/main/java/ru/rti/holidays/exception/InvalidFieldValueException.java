package ru.rti.holidays.exception;

public class InvalidFieldValueException extends AbstractApplicationException {
    public InvalidFieldValueException(String message) {
        setDetailMessage(message);
    }
}
