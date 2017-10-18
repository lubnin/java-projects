package ru.rti.holidays;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
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
import ru.rti.holidays.utility.GlobalConstants;
import ru.rti.holidays.view.LoginPageView;
import ru.rti.holidays.view.error.AccessDeniedView;
import ru.rti.holidays.view.error.ErrorDefaultView;

import java.util.Locale;

/**
 * This is the default UI for logging in to the Application
 */
@SpringUI(path = GlobalConstants.URL_PATH_LOGIN)
@Title("LoginPage")
@Theme(GlobalConstants.THEME_NAME)
public class LoginUI extends UI {
    private static final Logger log = LoggerFactory.getLogger(LoginUI.class);

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        UI.getCurrent().setLocale(new Locale("ru"));

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

        //TODO: remove for production mode
        String encodedAdminPwd = passwordEncoder.encode("B09108b198613");
        log.info("Encoded admin password: " + encodedAdminPwd);

        //TODO: remove for production mode
        String encodedTestPwd = passwordEncoder.encode("Qwerty123");
        log.info("Encoded test password: " + encodedTestPwd);

        Navigator navigator = new Navigator(this, panelViewContainer);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(new ErrorDefaultView());
        navigator.navigateTo(LoginPageView.VIEW_NAME);

        UI.getCurrent().setNavigator(navigator);
    }
}
