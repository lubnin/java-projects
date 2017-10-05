package ru.rti.holidays.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.service.EmployeeService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @deprecated
 * //TODO: remove this later.
 *
 */
@SpringView(name = NewEmployeesListView.VIEW_NAME)
public class NewEmployeesListView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "NewEmployeesList";

    @Autowired
    EmployeeService employeeServiceImpl;

    @PostConstruct
    void init() {
        Label lblPageTitle = new Label("Список сотрудников");
        lblPageTitle.addStyleName(ValoTheme.LABEL_H1);

        addComponent(lblPageTitle);



        if (employeeServiceImpl != null) {
            List<Employee> allEmployees = employeeServiceImpl.getAllEmployees();
            allEmployees.forEach(employee -> {
                HorizontalLayout employeeInfo = new HorizontalLayout();
                employeeInfo.addComponent(new Label(employee.getFullName()));
                if (employee.getProjectRole() != null) {
                    employeeInfo.addComponent(new Label(employee.getProjectRole().getRoleName()));
                }
                //employeeInfo.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
                //employeeInfo.setResponsive(true);
                addComponent(employeeInfo);
            });
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}
