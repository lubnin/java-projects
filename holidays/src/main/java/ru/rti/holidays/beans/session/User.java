package ru.rti.holidays.beans.session;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;

@SpringComponent
@VaadinSessionScope
/**
 * Session-scope class for currently logged-in User
 */
public class User {
    private String employeeLoginName;
    private String employeePassword;
    private String currentView;

    public String getCurrentView() {
        return currentView;
    }

    public void setCurrentView(String currentView) {
        this.currentView = currentView;
    }

    public String getEmployeeLoginName() {
        return employeeLoginName;
    }

    public void setEmployeeLoginName(String employeeLoginName) {
        this.employeeLoginName = employeeLoginName;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }
}
