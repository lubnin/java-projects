package ru.rti.holidays.view.employee;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.adapter.EmployeeToEmployeeHolidayPeriodAdapter;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.layout.base.StandardBaseLayoutDrawer;
import ru.rti.holidays.layout.employee.EmployeeAllHolidaysLayout;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.service.HolidayPeriodService;
import ru.rti.holidays.utility.GlobalConstants;
import ru.rti.holidays.view.base.AbstractBaseView;

import java.util.Collection;
import java.util.Set;

@SpringView(name = EmployeeAllHolidaysView.VIEW_NAME)
@UIScope
public class EmployeeAllHolidaysView extends AbstractBaseView {
    public static final String VIEW_NAME = "EmployeeAllHolidays";
    protected Collection<Employee> allEmployeesExcludingAdmin;
    protected Collection<EmployeeHolidayPeriod> allEmployeeHolidayPeriodsExcludingAdmin;

    @Autowired
    private EmployeeService employeeServiceImpl;

    @Autowired
    private HolidayPeriodService holidayPeriodServiceImpl;

    @Override
    protected Label getPageTitleLabel() {
        return new Label("Все отпуска");
    }

    @Override
    protected void addCustomComponents() {
        EmployeeAllHolidaysLayout employeeAllHolidaysLayout = new EmployeeAllHolidaysLayout();
        employeeAllHolidaysLayout.setEmployeeHolidayPeriods(allEmployeeHolidayPeriodsExcludingAdmin);

        new StandardBaseLayoutDrawer(this, employeeAllHolidaysLayout).drawLayout();
    }

    @Override
    protected boolean prepareViewData() {
        allEmployeesExcludingAdmin = employeeServiceImpl.getAllEmployeesExcludingLoginName(GlobalConstants.ADMIN_USER_LOGIN_NAME);
        EmployeeToEmployeeHolidayPeriodAdapter<EmployeeHolidayPeriod> adapter =
                new EmployeeToEmployeeHolidayPeriodAdapter<EmployeeHolidayPeriod>(EmployeeHolidayPeriod::new, holidayPeriodServiceImpl);
        allEmployeeHolidayPeriodsExcludingAdmin = adapter.convert(allEmployeesExcludingAdmin);

        return true;
    }
}
