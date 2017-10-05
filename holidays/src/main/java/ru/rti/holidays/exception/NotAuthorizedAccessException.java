package ru.rti.holidays.exception;

/**
 * This type of exception is thrown from view classes under 'ru.rti.holidays.view' package.
 * Exception is thrown when there is an unauthorized access to the view.
 * That means the view is accessed by the user who didn't perform the login to the Application
 */
public class NotAuthorizedAccessException extends Exception {

    private String detailMessage;

    public NotAuthorizedAccessException(Class cls) {
        detailMessage = String.format("Вы не авторизованы для просмотра данной страницы '%s'", cls.getCanonicalName());
    }

    public NotAuthorizedAccessException(String message) {
        if (message == null) {
            throw new IllegalArgumentException(String.format("Параметр `message` не может быть равен `null` для конструктора класса '%s'", this.getClass().getCanonicalName()));
        }
        detailMessage = message;
    }

    @Override
    public String getMessage() {
        return detailMessage;
    }
}
