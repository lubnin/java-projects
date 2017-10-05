package ru.rti.holidays;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.beans.session.User;
import ru.rti.holidays.utility.GlobalConstants;
import ru.rti.holidays.view.LoginPageView;
import ru.rti.holidays.view.error.AccessDeniedView;
import ru.rti.holidays.view.error.ErrorDefaultView;

import java.util.Locale;

@SpringUI
@SpringViewDisplay
@Theme(GlobalConstants.THEME_NAME)
public class NavigatorUI extends UI {
    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private User currentUser;

    @Override
    protected void init(VaadinRequest request) {
        UI.getCurrent().setLocale(new Locale("ru"));

        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);
/*
        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("Перейти на ScopedView", ViewScopedView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Перейти на DefaultView", DefaultView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Список сотрудников", NewEmployeesListView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("UI Scoped View", UIScopedView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Добавить сотрудника", AddEmployeeView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Добавить проектную роль", AddProjectRoleView.VIEW_NAME));
        root.addComponent(navigationBar);
*/
        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        //viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);

        //SpringNavigator navigator = new SpringNavigator(this, viewContainer);
        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(new ErrorDefaultView());

        if (currentUser != null && currentUser.getEmployeeLoginName() != null && currentUser.getCurrentView() != null) {
            navigator.navigateTo(currentUser.getCurrentView());
        } else {
            navigator.navigateTo(LoginPageView.VIEW_NAME);
        }
        UI.getCurrent().setNavigator(navigator);
    }

    /*
    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        // If you didn't choose Java 8 when creating the project, convert this to an anonymous listener class
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    /////// THIS IS OLD CODE - used with combination with view.old.* classes
    private Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        UI.getCurrent().setLocale(new Locale("ru"));
        getPage().setTitle("Система отпусков. ООО \"Ростелеком-Интеграция\"");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);
        navigator.setErrorView(new ErrorView(navigator));

        // Create and register the views
        navigator.addView(BaseView.VIEW_LOGIN, new LoginView(navigator));
        navigator.addView(BaseView.VIEW_EMPLOYEE_LIST, new EmployeesListView(navigator));
        navigator.addView(BaseView.VIEW_EMPLOYEE, new EmployeeView(navigator));

        navigator.navigateTo(BaseView.VIEW_LOGIN);

    }
*/

}
