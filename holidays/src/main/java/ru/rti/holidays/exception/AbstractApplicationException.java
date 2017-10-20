package ru.rti.holidays.exception;

public abstract class AbstractApplicationException extends Exception {
    private String detailMessage;

    public AbstractApplicationException() {
        setDetailMessage("");
    }

    public AbstractApplicationException(Class cls) {
        setDetailMessage(String.format("An error occured in class '%s'", cls.getCanonicalName()));
    }

    public AbstractApplicationException(String message) {
        if (message == null) {
            throw new IllegalArgumentException(String.format("A message cannot be `null` for constructor of '%s' class", this.getClass().getCanonicalName()));
        }
        setDetailMessage(message);
    }

    @Override
    public String getMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
