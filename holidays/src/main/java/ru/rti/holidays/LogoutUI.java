package ru.rti.holidays;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import ru.rti.holidays.exception.handler.CustomVaadinErrorHandler;
import ru.rti.holidays.utility.GlobalConstants;

import java.util.Locale;

/**
 * This is the default UI for logging out from Application
 */
@SpringUI(path = GlobalConstants.URL_PATH_LOGOUT)
@Title("LogoutPage")
@Theme(GlobalConstants.THEME_NAME)
public class LogoutUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        UI.getCurrent().setLocale(new Locale("ru"));
        setErrorHandler(new CustomVaadinErrorHandler());
    }
}
