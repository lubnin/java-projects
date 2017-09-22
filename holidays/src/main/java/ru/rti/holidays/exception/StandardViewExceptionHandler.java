package ru.rti.holidays.exception;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * Standard exception handler for Views in UI.
 */
public class StandardViewExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Exception e, String errorMessage) {
        Notification notification = new Notification(
                "Ошибка",
                errorMessage,
                Notification.Type.ERROR_MESSAGE);

        notification.show(UI.getCurrent().getPage());
    }
}
