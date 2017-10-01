package ru.rti.holidays.view.employee;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.view.base.AbstractBaseView;

import java.util.*;

@SpringView(name = EmployeeHolidaysView.VIEW_NAME)
public class EmployeeHolidaysView extends AbstractBaseView {
    public static final String VIEW_NAME = "EmployeeHolidays";

    @Autowired
    EmployeeService employeeServiceImpl;

    private Employee employee;
    private List<HolidayPeriod> employeeHolidayPeriods;
    private HolidayPeriod newHolidayPeriod;

    private Map<Team, Set<EmployeeHolidayPeriod>> teamMembersHolidayPeriods = new HashMap<Team, Set<EmployeeHolidayPeriod>>();
    //@Autowired


    @Override
    protected Label getPageTitleLabel() {
        return new Label("Мои отпуска");
    }



    @Override
    protected void addCustomComponents() {
        EmployeeHolidaysLayout employeeHolidaysLayout = new EmployeeHolidaysLayout(
            employee,
            "Периоды моего отпуска"
        );

        employeeHolidaysLayout.setEmployeeHolidayPeriods(employeeHolidayPeriods);
        employeeHolidaysLayout.setNewHolidayPeriod(newHolidayPeriod);
        employeeHolidaysLayout.setManagedTeamMembersHolidays(teamMembersHolidayPeriods);
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
        String loginName = parameterMap.get("loginName");
        String password = parameterMap.get("password");

        if (loginName == null || password == null) {
            throw new IllegalArgumentException("Login name and password cannot be null!");
        }

        employee = employeeServiceImpl.getByLoginNameAndPassword(loginName, password);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found in the database by login and password provided!");
        }

        employeeHolidayPeriods = employeeServiceImpl.getHolidayPeriodsForEmployee(employee);



        if (employee.isManager()) {
            Set<Team> managedTeams = employee.getManagedTeams();
            if (managedTeams != null && managedTeams.size() > 0) {
                managedTeams.forEach(team -> {
                    Set<Employee> teamEmployees = employeeServiceImpl.getByTeamId(team.getId());
                    Set<EmployeeHolidayPeriod> empHolidayPeriods = new HashSet<>();
                    if (teamEmployees != null && teamEmployees.size() > 0) {
                        for(Employee emp : teamEmployees) {
                            List<HolidayPeriod> holidayPeriodsForEmployee = employeeServiceImpl.getHolidayPeriodsForEmployee(emp);
                            if (holidayPeriodsForEmployee != null && holidayPeriodsForEmployee.size() > 0) {
                                holidayPeriodsForEmployee.forEach(holidayPeriod -> {
                                    EmployeeHolidayPeriod empHolidayPeriod = new EmployeeHolidayPeriod();
                                    empHolidayPeriod.setEmployeeFullName(emp.getFullName());
                                    empHolidayPeriod.setDateStart(holidayPeriod.getDateStart());
                                    empHolidayPeriod.setNumDays(holidayPeriod.getNumDays());
                                    empHolidayPeriod.setEmployeeRoleName(emp.getProjectRoleAsString());
                                    //TODO: upgrade here, remove hardcode
                                    empHolidayPeriod.setHolidayPeriodNegotiationStatus("TEST");
                                    empHolidayPeriods.add(empHolidayPeriod);
                                });
                            }

                        }
                    }

                    teamMembersHolidayPeriods.put(team, empHolidayPeriods);
                });
            }
        }
        newHolidayPeriod = new HolidayPeriod();

        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        if (parameters == null && parameterMap == null) {
            throw new IllegalArgumentException("EmployeeHolidaysView must be called with initialized parameter map. Something went wrong if you see this message.");
        }
    }
}
