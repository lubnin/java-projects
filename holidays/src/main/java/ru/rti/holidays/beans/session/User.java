package ru.rti.holidays.beans.session;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import ru.rti.holidays.entity.Employee;

@SpringComponent
@VaadinSessionScope
public class User {
    //private Employee loggedInEmployee;
    private String employeeLoginName;
    private String employeePassword;
    private String currentView;

/*    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public void setLoggedInEmployee(Employee loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
    }*/

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
