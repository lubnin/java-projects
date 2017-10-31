package ru.rti.holidays;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import ru.rti.holidays.exception.handler.CustomVaadinErrorHandler;
import ru.rti.holidays.utility.GlobalConstants;

@SpringUI(path = "/")
@Title("Root")
@Theme(GlobalConstants.THEME_NAME)
public class RootUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Page.getCurrent().setLocation(GlobalConstants.URL_PATH_LOGIN);
        setErrorHandler(new CustomVaadinErrorHandler());
    }
}
