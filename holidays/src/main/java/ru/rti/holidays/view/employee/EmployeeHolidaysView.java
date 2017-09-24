package ru.rti.holidays.view.employee;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.utility.CommonUtils;
import ru.rti.holidays.view.base.AbstractBaseView;

import java.util.List;

@SpringView(name = EmployeeHolidaysView.VIEW_NAME)
public class EmployeeHolidaysView extends AbstractBaseView {
    public static final String VIEW_NAME = "EmployeeHolidays";

    @Autowired
    EmployeeService employeeServiceImpl;

    private Employee employee;
    private List<HolidayPeriod> employeeHolidayPeriods;
    private HolidayPeriod newHolidayPeriod;
    //@Autowired


    @Override
    protected Label getPageTitleLabel() {
        return new Label("Мои отпуска");
    }



    @Override
    protected void addCustomComponents() {
        EmployeeHolidaysLayout employeeHolidaysLayout = new EmployeeHolidaysLayout(
            employee.getFullName(),
            CommonUtils.getValueOrEmptyString(employee.getProjectRole().getRoleName()),
            "Периоды моего отпуска"
        );

        employeeHolidaysLayout.setEmployeeHolidayPeriods(employeeHolidayPeriods);
        employeeHolidaysLayout.setNewHolidayPeriod(newHolidayPeriod);
        employeeHolidaysLayout.addMainButtonClickListener(layoutInstance -> {

            newHolidayPeriod.setEmployee(employee);
            HolidayPeriod addedToDBHolidayPeriod = employeeServiceImpl.saveHolidayPeriod(newHolidayPeriod);

            // re-create the instance
            newHolidayPeriod = new HolidayPeriod();
            layoutInstance.setNewHolidayPeriod(newHolidayPeriod);

            return addedToDBHolidayPeriod != null;
        });

        employeeHolidaysLayout.setRefreshGridDataListener(layoutInstance -> {
            employeeHolidayPeriods = employeeServiceImpl.getHolidayPeriodsForEmployee(employee);
            ((EmployeeHolidaysLayout)layoutInstance).setEmployeeHolidayPeriods(employeeHolidayPeriods);
        });


        employeeHolidaysLayout.addDeleteButtonClickListener((layoutInstance, selectedPeriods) -> {
            if (selectedPeriods != null && selectedPeriods.size() > 0) {
                employeeServiceImpl.deleteHolidayPeriods(selectedPeriods);
                //employeeHolidayPeriods = employeeServiceImpl.getHolidayPeriodsForEmployee(employee);
                //layoutInstance.setEmployeeHolidayPeriods(employeeHolidayPeriods);
            }
        });


        employeeHolidaysLayout.constructLayout();

        addComponent(employeeHolidaysLayout);
    }

    @Override
    protected boolean prepareViewData() {
        //TODO: replace this test fetch by Last name later
        List<Employee> emp = employeeServiceImpl.getByLastName("Абрамкин");
        if (emp.size() == 1) {
            employee = emp.get(0);
        }

        employeeHolidayPeriods = employeeServiceImpl.getHolidayPeriodsForEmployee(employee);

        newHolidayPeriod = new HolidayPeriod();

        return true;
    }
}
