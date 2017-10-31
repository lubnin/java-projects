package ru.rti.holidays.exception.handler;

import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class CustomVaadinErrorHandler extends DefaultErrorHandler {
    @Override
    public void error(ErrorEvent event) {
        super.error(event);
        // Find the final cause
        String cause = "<b>Произошла ошибка по следующей причине:</b><br/><br/>";
        for (Throwable t = event.getThrowable(); t != null;
             t = t.getCause())
            if (t.getCause() == null) // We're at final cause
                cause += t.getClass().getName() + "<br/>";

        // Display the error message in a custom fashion
        if (Page.getCurrent() != null) {
            Notification errorNotification = new Notification("Ошибка", cause, Notification.Type.ERROR_MESSAGE);
            errorNotification.setDelayMsec(5000);
            errorNotification.setPosition(Position.MIDDLE_CENTER);
            errorNotification.setHtmlContentAllowed(true);
            errorNotification.show(Page.getCurrent());
        }
        // Do the default error handling (optional)
        doDefault(event);
    }
}
