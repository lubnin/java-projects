package ru.rti.holidays.view.employee;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;
import ru.rti.holidays.service.EmailService;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.service.HolidayPeriodService;
import ru.rti.holidays.utility.SessionUtils;
import ru.rti.holidays.view.base.AbstractBaseView;

import java.util.*;

@SpringView(name = EmployeeHolidaysView.VIEW_NAME)
@UIScope
public class EmployeeHolidaysView extends AbstractBaseView {
    public static final String VIEW_NAME = "EmployeeHolidays";

    @Autowired
    private EmployeeService employeeServiceImpl;

    @Autowired
    private HolidayPeriodService holidayPeriodServiceImpl;

    @Autowired
    private EmailService emailServiceImpl;

    private Employee employee;
    private List<HolidayPeriod> employeeHolidayPeriods;
    private HolidayPeriod newHolidayPeriod;
    private List<HolidayPeriodNegotiationStatus> allNegotiationStatuses;
    private Map<Team, Set<EmployeeHolidayPeriod>> teamMembersHolidayPeriods = new HashMap<Team, Set<EmployeeHolidayPeriod>>();


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
        employeeHolidaysLayout.setAllNegotiationStatuses(allNegotiationStatuses);
        employeeHolidaysLayout.setNewHolidayPeriod(newHolidayPeriod);
        employeeHolidaysLayout.setManagedTeamMembersHolidays(teamMembersHolidayPeriods);
        //employeeHolidaysLayout.setCurrentUser(currentUser);
        employeeHolidaysLayout.setNegotiateSelectedPeriodsClickListener((hpNegotiationStatus, setEmployeeHolPeriods) -> {
            holidayPeriodServiceImpl.setNegotiationStatusForHolidayPeriods(setEmployeeHolPeriods, hpNegotiationStatus);
            emailServiceImpl.sendMailHolidayPeriodsNegotiated(setEmployeeHolPeriods, employee);
            //TODO: for now the whole page is reloaded. It is not an optimal way to refresh data in the grids with holiday periods. Need refactoring later.
            Page.getCurrent().reload();
        });

        employeeHolidaysLayout.setRejectSelectedPeriodsClickListener((hpNegotiationStatus, setEmployeeHolPeriods) -> {
            holidayPeriodServiceImpl.setNegotiationStatusForHolidayPeriods(setEmployeeHolPeriods, hpNegotiationStatus);
            emailServiceImpl.sendMailHolidayPeriodsRejected(setEmployeeHolPeriods, employee);
            //TODO: for now the whole page is reloaded. It is not an optimal way to refresh data in the grids with holiday periods. Need refactoring later.
            Page.getCurrent().reload();
        });

        employeeHolidaysLayout.addMainButtonClickListener(layoutInstance -> {
            newHolidayPeriod.setEmployee(employee);
            HolidayPeriod addedToDBHolidayPeriod = employeeServiceImpl.saveHolidayPeriod(newHolidayPeriod);

            Set<Employee> managers = employeeServiceImpl.getAllManagersForEmployee(employee);
            emailServiceImpl.sendMailHolidayPeriodSubmitted(newHolidayPeriod, employee, managers);

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
        /*
        String loginName = parameterMap.get("loginName");
        String password = parameterMap.get("password");

        if (loginName == null || password == null) {
            throw new IllegalArgumentException("Login name and password cannot be null!");
        }*/



        //employee = currentUser.getLoggedInEmployee();
        employee = SessionUtils.getCurrentUser();
        //employee = employeeServiceImpl.getByLoginNameAndPassword(
        //        getCurrentUser().getEmployeeLoginName(),
        //        getCurrentUser().getEmployeePassword());


        //if (employee == null) {
        //    throw new IllegalArgumentException("Employee not found in the database by login and password provided!");
        //}

        employeeHolidayPeriods = employeeServiceImpl.getHolidayPeriodsForEmployee(employee);
        allNegotiationStatuses = holidayPeriodServiceImpl.getAllHolidayPeriodNegotiationStatuses();

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
                                    empHolidayPeriod.setEmployeeEmail(emp.getEmail());
                                    empHolidayPeriod.setEmployeeFullName(emp.getFullName());
                                    empHolidayPeriod.setDateStart(holidayPeriod.getDateStart());
                                    empHolidayPeriod.setNumDays(holidayPeriod.getNumDays());
                                    empHolidayPeriod.setEmployeeRoleName(emp.getProjectRoleAsString());
                                    empHolidayPeriod.setHolidayPeriodNegotiationStatus(holidayPeriod.getNegotiationStatusAsString());
                                    empHolidayPeriod.setNegotiationStatus(holidayPeriod.getNegotiationStatus());
                                    empHolidayPeriod.setTeamId(team.getId());
                                    empHolidayPeriod.setHolidayPeriod(holidayPeriod);
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
        /*
        if (parameters == null && parameterMap == null) {
            throw new IllegalArgumentException("EmployeeHolidaysView must be called with initialized parameter map. Something went wrong if you see this message.");
        }
        */
    }
}
