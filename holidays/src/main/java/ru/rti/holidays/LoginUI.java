package ru.rti.holidays;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.rti.holidays.exception.handler.CustomVaadinErrorHandler;
import ru.rti.holidays.utility.GlobalConstants;
import ru.rti.holidays.utility.SessionUtils;
import ru.rti.holidays.view.LoginPageView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;
import ru.rti.holidays.view.error.AccessDeniedView;
import ru.rti.holidays.view.error.ErrorDefaultView;

import java.util.Locale;

/**
 * This is the default UI for logging in to the Application
 */
@SpringUI(path = GlobalConstants.URL_PATH_LOGIN)
@Title("Вход в cистему отпусков РТИ")
@Theme(GlobalConstants.THEME_NAME)
public class LoginUI extends UI {
    private static final Logger log = LoggerFactory.getLogger(LoginUI.class);

    @Autowired
    protected SpringViewProvider viewProvider;

    @Autowired
    protected SpringViewProvider errorViewProvider;

    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setErrorHandler(new CustomVaadinErrorHandler());
        final VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        rootLayout.setMargin(true);
        rootLayout.setSpacing(true);

        setContent(rootLayout);

        final Panel panelViewContainer = new Panel();
        panelViewContainer.setSizeFull();
        rootLayout.addComponent(panelViewContainer);
        rootLayout.setExpandRatio(panelViewContainer, 1.0f);

        viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);

        Navigator navigator = new Navigator(this, panelViewContainer);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(new ErrorDefaultView());
        if (SessionUtils.isAuthenticated()) {
            Page.getCurrent().setLocation(GlobalConstants.URL_PATH_MAIN_PAGE);
        } else {
            navigator.navigateTo(LoginPageView.VIEW_NAME);
        }

        UI.getCurrent().setNavigator(navigator);
    }
}
