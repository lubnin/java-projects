package ru.rti.holidays.utility;

import com.vaadin.server.Page;
import ru.rti.holidays.beans.session.User;

public class SessionUtils {
    public static final boolean logout(User currentUser) {
        if (currentUser != null) {
            currentUser.setCurrentView(null);
            currentUser.setEmployeeLoginName(null);
            currentUser.setEmployeePassword(null);
            Page.getCurrent().reload();
            return true;
        }
        return false;
    }
}
