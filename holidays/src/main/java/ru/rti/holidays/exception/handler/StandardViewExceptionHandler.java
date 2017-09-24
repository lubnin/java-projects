package ru.rti.holidays.exception.handler;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import ru.rti.holidays.exception.handler.ExceptionHandler;

/**
 * Standard exception handler for Views in UI.
 * It shows the Red alert window containing the Error message
 */
public class StandardViewExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Exception e, String errorMessage) {
        //TODO: remove hardcode later
        Notification notification = new Notification(
                "Ошибка",
                errorMessage,
                Notification.Type.ERROR_MESSAGE);

        notification.show(UI.getCurrent().getPage());
    }
}
