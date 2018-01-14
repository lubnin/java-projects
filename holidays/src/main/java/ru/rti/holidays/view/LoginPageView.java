package ru.rti.holidays.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.layout.LoginFormLayout;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.utility.GlobalConstants;
import ru.rti.holidays.view.admin.AdminMainView;
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

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

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

            try {
                Authentication auth = new UsernamePasswordAuthenticationToken(loginName, password);
                Authentication authenticated = daoAuthenticationProvider.authenticate(auth);
                SecurityContextHolder.getContext().setAuthentication(authenticated);
                Page.getCurrent().setLocation(GlobalConstants.URL_PATH_MAIN_PAGE);
            } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
                handleException(e, "Введённые имя пользователя и пароль неверны. Попробуйте еще раз.");
            } catch (Exception e) {
                handleException(e, "Введённые имя пользователя и пароль неверны. Попробуйте еще раз.");
            }
        });

        addComponent(loginFormLayout);
    }

    @Override
    protected boolean prepareViewData() {
        return false;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
    }
}
