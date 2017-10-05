package ru.rti.holidays.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.beans.session.User;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.layout.LoginFormLayout;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.utility.GlobalConstants;
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
@UIScope
public class LoginPageView extends AbstractBaseView {
    public static final String VIEW_NAME = "LoginPage";

    @Autowired
    EmployeeService employeeServiceImpl;

    @Override
    protected Label getPageTitleLabel() {
        return new Label("Форма входа");
    }

    @Override
    protected void addCustomComponents() {
        LoginFormLayout loginFormLayout = new LoginFormLayout();

        loginFormLayout.addLoginButtonClickListener(event -> {
            String loginName = loginFormLayout.getLoginNameValue();
            String password = loginFormLayout.getPasswordValue();

            //TODO: add password encrypt/decrypt logic here, not plain password!
            Employee loggedInEmployee = employeeServiceImpl.getByLoginNameAndPassword(loginName, password);
            if (loggedInEmployee == null) {
                Notification ntfy = new Notification(
                        "Ошибка",
                        "Имя пользователя или пароль неверны. Попробуйте еще раз",
                        Notification.Type.ERROR_MESSAGE);
                ntfy.show(Page.getCurrent());
            } else {
                //currentUser.setLoggedInEmployee(loggedInEmployee);
                getCurrentUser().setEmployeeLoginName(loggedInEmployee.getLoginName());
                getCurrentUser().setEmployeePassword(loggedInEmployee.getPassword());

                //getUI().getNavigator().navigateTo(EmployeeHolidaysView.VIEW_NAME + "/loginName=" + loginName + "&password=" + password);
                if (GlobalConstants.ADMIN_USER_LOGIN_NAME.equals(getCurrentUser().getEmployeeLoginName())) {
                    getUI().getNavigator().navigateTo(AdminMainView.VIEW_NAME);
                    getCurrentUser().setCurrentView(AdminMainView.VIEW_NAME);
                } else {
                    getCurrentUser().setCurrentView(EmployeeHolidaysView.VIEW_NAME);
                    getUI().getNavigator().navigateTo(EmployeeHolidaysView.VIEW_NAME);
                }
            }
        });

        //loginFormLayout.addRegisterButtonClickListener(event -> {
//            getUI().getNavigator().navigateTo(AdminMainView.VIEW_NAME);
            //Notification.show("На текущий момент функция недоступна...");
  //      });

        addComponent(loginFormLayout);
    }

    @Override
    protected boolean prepareViewData() {
        return false;
    }

    /**
     * Override standard behaviour and don't check the User's session here, as it is the very first login page view,
     * where we initialize the user's session data right after the successful login into the System.
     * @return
     */
    @Override
    protected boolean checkUserSession() {
        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
    }
}
