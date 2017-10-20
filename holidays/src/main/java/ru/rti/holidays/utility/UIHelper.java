package ru.rti.holidays.utility;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class UIHelper {
    public static final int DEFAULT_DELAY = 5000;

    public static void showError(String errorMessage) {
        showError(errorMessage, DEFAULT_DELAY);
    }

    public static void showError(String errorMessage, int delay) {
        //TODO: remove hardcode later
        Notification notification = new Notification(
                "Ошибка",
                errorMessage,
                Notification.Type.ERROR_MESSAGE);

        notification.setDelayMsec(delay);
        notification.show(UI.getCurrent().getPage());
    }
}
