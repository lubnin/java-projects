package ru.rti.holidays.view.old;

import com.vaadin.navigator.Navigator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.service.EmployeeService;

import java.util.List;

@ViewScope
@SpringComponent
public class EmployeeView extends BaseView {
    private static final Logger log = LoggerFactory.getLogger(EmployeeView.class);

    Grid<HolidayPeriod> grid;
    VerticalLayout mainLayout;
    //List<HolidayPeriod> holidayPeriods;

    @Autowired
    EmployeeService employeeServiceImpl;


    public EmployeeView(Navigator navigator) {
        super(navigator);


    }

    @Override
    public Component getViewComponent() {
        mainLayout = new VerticalLayout();

        grid = new Grid<>(HolidayPeriod.class);

        try {
            if (employeeServiceImpl == null) {
                log.error("EmployeeService is NULL!");
            }

            List<Employee> employees = employeeServiceImpl.getByLastName("Abramkin");

            if (employees != null && employees.size() > 0) {
                log.info("The list of Employees is not empty: size=%s", employees.size());
                employees.forEach(employee -> {
                    Label lblFIO = new Label(employee.getLastName() + " " + employee.getFirstName() + " / " + employee.getLoginName());
                    mainLayout.addComponent(lblFIO);
                });
            } else {
                log.error("The employee was not found!");
            }
            //holidayPeriods = repository.findByEmployeeId(new Long(1));
            //grid.setItems(holidayPeriods);
            //mainLayout.addComponent(grid);
        } catch (NullPointerException npe) {
            Label lblNoData = new Label("Данные не найдены.");
            mainLayout.addComponent(lblNoData);
            log.error("NullPointerException detected while trying to find Employee by ID");
        }



/*
        MenuBar mainMenu = new MenuBar();
        MenuBar.MenuItem menuItem1 = mainMenu.addItem("Menu1", null, null);
        MenuBar.MenuItem menuItem2 = mainMenu.addItem("Menu2", null, null);
        MenuBar.MenuItem menuItem3 = mainMenu.addItem("Menu3", null, null);

        mainLayout.addComponent(mainMenu);
*/
        return mainLayout;
    }


}
