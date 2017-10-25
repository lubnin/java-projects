package ru.rti.holidays.utility;

import com.vaadin.ui.UI;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.NavigationContextHolder;
import ru.rti.holidays.view.admin.AdminMainView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;

public class NavigationUtils {

    public static String navigateToView(String viewName) {
        if (viewName == null) {
            throw new IllegalArgumentException("`viewName` parameter is null");
        }

        Employee currentUser = SessionUtils.getCurrentUser();
        NavigationContextHolder navCtxHolder = (NavigationContextHolder)currentUser;
        String oldView = navCtxHolder.getCurrentView();
        ((NavigationContextHolder)currentUser).setCurrentView(viewName);
        UI.getCurrent().getNavigator().navigateTo(viewName);
        return oldView;
    }

    public static void navigateToCurrentView(NavigationContextHolder navigationContextHolder) {
        if (navigationContextHolder == null) {
            throw new IllegalArgumentException("`navigationContextHolder` parameter is null");
        }

        if (navigationContextHolder.getCurrentView() == null) {
            navigateToDefaultView();
        } else {
            navigateToView(navigationContextHolder.getCurrentView());
        }
    }

    public static void navigateToDefaultView() {
        Employee currentUser = SessionUtils.getCurrentUser();
        if (currentUser.isAdmin()) {
            navigateToView(AdminMainView.VIEW_NAME);
        } else {
            navigateToView(EmployeeHolidaysView.VIEW_NAME);
        }
    }
}
