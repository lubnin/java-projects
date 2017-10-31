package ru.rti.holidays.utility;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class UIHelper {
    public static final int DEFAULT_DELAY = 5000;

    public static void showError(String errorMessage) {
        showError(errorMessage, DEFAULT_DELAY);
    }

    public static void showError(String errorMessage, int delay) {
        Notification notification = new Notification(
                "Ошибка",
                errorMessage,
                Notification.Type.ERROR_MESSAGE);

        notification.setDelayMsec(delay);
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.show(Page.getCurrent());
    }

    public static void showNotification(String message) {
        showNotification(message, DEFAULT_DELAY);
    }

    public static void showNotification(String message, int delay) {
        Notification notification = new Notification(
                "Сообщение",
                message,
                Notification.Type.HUMANIZED_MESSAGE);

        notification.setDelayMsec(delay);
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.show(Page.getCurrent());
    }
}
