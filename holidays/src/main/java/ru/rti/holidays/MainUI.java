package ru.rti.holidays;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.exception.handler.CustomVaadinErrorHandler;
import ru.rti.holidays.navigation.CustomNavigationStateManager;
import ru.rti.holidays.utility.CommonUtils;
import ru.rti.holidays.utility.GlobalConstants;
import ru.rti.holidays.utility.NavigationUtils;
import ru.rti.holidays.utility.SessionUtils;
import ru.rti.holidays.view.admin.AdminMainView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;
import ru.rti.holidays.view.error.AccessDeniedView;
import ru.rti.holidays.view.error.ErrorDefaultView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.Locale;

@SpringUI(path = GlobalConstants.URL_PATH_MAIN_PAGE)
@Title("MainPage")
@SpringViewDisplay
@Theme(GlobalConstants.THEME_NAME)
public class MainUI extends UI {
    @Autowired
    protected SpringViewProvider viewProvider;

    @Autowired
    protected SpringViewProvider errorViewProvider;

    @Override
    protected void init(VaadinRequest request) {
        UI.getCurrent().setLocale(new Locale("ru"));

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
        errorViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);

/*        Navigator navigator = new Navigator(this,
                new CustomNavigationStateManager(this.getPage()),
                (ViewDisplay)(new Navigator.SingleComponentContainerViewDisplay(panelViewContainer))
        );*/

        Navigator navigator = new Navigator(this, panelViewContainer);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(new ErrorDefaultView());
        navigator.setErrorProvider(errorViewProvider);
        navigator.addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent viewChangeEvent) {
                String params = viewChangeEvent.getParameters();
                if (!CommonUtils.checkIfEmpty(params)) {
                    return false;
                }
                return true;
            }
        });

        if (SessionUtils.isAuthenticated()) {
            Employee currentUser = SessionUtils.getCurrentUser();
            NavigationUtils.navigateToCurrentView(currentUser);
        }

        UI.getCurrent().setNavigator(navigator);
    }
}
