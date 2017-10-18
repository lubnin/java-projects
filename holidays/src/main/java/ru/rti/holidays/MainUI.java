package ru.rti.holidays;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.utility.GlobalConstants;
import ru.rti.holidays.utility.SessionUtils;
import ru.rti.holidays.view.admin.AdminMainView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;
import ru.rti.holidays.view.error.AccessDeniedView;
import ru.rti.holidays.view.error.ErrorDefaultView;

import java.util.Locale;

@SpringUI(path = GlobalConstants.URL_PATH_MAIN_PAGE)
@Title("MainPage")
@SpringViewDisplay
@Theme(GlobalConstants.THEME_NAME)
public class MainUI extends UI {
    @Autowired
    private SpringViewProvider viewProvider;

    //@Autowired
    //private User currentUser;

    @Override
    protected void init(VaadinRequest request) {
        UI.getCurrent().setLocale(new Locale("ru"));

        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);

        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(new ErrorDefaultView());

        if (SessionUtils.isAuthenticated()) {
            Employee currentUser = SessionUtils.getCurrentUser();
            if (currentUser.isAdmin()) {
                navigator.navigateTo(AdminMainView.VIEW_NAME);
            } else {
                navigator.navigateTo(EmployeeHolidaysView.VIEW_NAME);
            }
        }

        UI.getCurrent().setNavigator(navigator);
    }
}
