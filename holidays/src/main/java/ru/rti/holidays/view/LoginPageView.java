package ru.rti.holidays.view;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import ru.rti.holidays.layout.LoginFormLayout;
import ru.rti.holidays.view.admin.AdminMainView;
import ru.rti.holidays.view.admin.AdminPageView;
import ru.rti.holidays.view.base.AbstractBaseView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;

/**
 * This is a view for Login Page. It is the "main entrance" view for the Application.
 * The view constructs controls for entering username and password and manages
 * the process of user login to the system.
 */
@SpringView(name = LoginPageView.VIEW_NAME)
public class LoginPageView extends AbstractBaseView {
    public static final String VIEW_NAME = "LoginPage";
    //private final LoginFormLayout loginFormLayout = new LoginFormLayout();

/*    @PostConstruct
    void init() {
        setMargin(true);
        setSpacing(true);

    }*/

/*    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }*/


    @Override
    protected Label getPageTitleLabel() {
        return new Label("Форма входа");
    }

    @Override
    protected void addCustomComponents() {
        LoginFormLayout loginFormLayout = new LoginFormLayout();

        loginFormLayout.addLoginButtonClickListener(event -> {
            //Notification.show("Входим в систему...");
            getUI().getNavigator().navigateTo(EmployeeHolidaysView.VIEW_NAME);
        });

        loginFormLayout.addRegisterButtonClickListener(event -> {
            getUI().getNavigator().navigateTo(AdminMainView.VIEW_NAME);
            //Notification.show("На текущий момент функция недоступна...");
        });

        addComponent(loginFormLayout);
    }

    @Override
    protected boolean prepareViewData() {
        return false;
    }
}
