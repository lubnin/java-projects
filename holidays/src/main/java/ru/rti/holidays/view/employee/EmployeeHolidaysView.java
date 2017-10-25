package ru.rti.holidays.view.employee;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.adapter.EmployeeToEmployeeHolidayPeriodAdapter;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.layout.base.StandardBaseLayoutDrawer;
import ru.rti.holidays.layout.base.behaviour.ButtonClickResult;
import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;
import ru.rti.holidays.service.EmailService;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.service.HolidayPeriodService;
import ru.rti.holidays.utility.*;
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
    private Map<Team, Collection<EmployeeHolidayPeriod>> teamMembersHolidayPeriods = new HashMap<>();

    @Override
    protected Label getPageTitleLabel() {
        return new Label("Мои отпуска");
    }


    private void checkCrossDates(EmployeeHolidaysLayout layoutInstance, boolean checkAllPeriods) {
        if (employee.getTeam() != null) {
            Long teamId = employee.getTeam().getId();

            if (checkAllPeriods) {
                List<HolidayPeriod> allPeriods = holidayPeriodServiceImpl.getHolidayPeriodsForEmployee(employee);
                if (allPeriods == null || allPeriods.size() == 0) {
                    return;
                }
                boolean isNoCrossedFound = true;
                StringBuilder sbAllPeriodsCheck = new StringBuilder();
                for (HolidayPeriod hp : allPeriods) {
                    if (HolidayPeriodUtils.isHolidayPeriodInOkStatus(hp) ||
                        HolidayPeriodUtils.isHolidayPeriodInRejectedStatus(hp)) {
                        continue;
                    }
                    Set<Employee> employeesWithCrossingDates = employeeServiceImpl.getEmployeesWithCrossingHolidayPeriods(employee.getId(), teamId, DateUtils.asDate(hp.getDateStart()), hp.getNumDays());
                    if (employeesWithCrossingDates.size() > 0) {
                        if (sbAllPeriodsCheck.length() > 0) {
                            sbAllPeriodsCheck.append("<br><br>");
                        }
                        isNoCrossedFound = false;
                        StringBuilder sb = new StringBuilder();
                        for (Employee emp : employeesWithCrossingDates) {
                            if (sb.length() > 0) {
                                sb.append("<br><br>");
                            }
                            List<HolidayPeriod> crossingHolidayPeriods = emp.getHolidayPeriods();
                            StringBuilder sbPeriods = new StringBuilder();
                            for (HolidayPeriod hpInTeam : crossingHolidayPeriods) {
                                if (DateUtils.isIntersectionBetweenDates(
                                        DateUtils.asDate(hp.getDateStart()),
                                        DateUtils.addDays(DateUtils.asDate(hp.getDateStart()), hp.getNumDays()),
                                        DateUtils.asDate(hpInTeam.getDateStart()),
                                        DateUtils.addDays(DateUtils.asDate(hpInTeam.getDateStart()), hpInTeam.getNumDays()))
                                ) {
                                    if (sbPeriods.length() > 0) {
                                        sbPeriods.append("<br>");
                                    }
                                    sbPeriods.append(String.format("<span class='%s'>Ваш отпуск с %s (дней: %s) пересекается с отпуском сотрудника %s:"+
                                                    " дата начала: %s (дней: %s)!</span>",
                                            GlobalConstants.CSS_HOLIDAY_PERIOD_CROSSING_MESSAGE,
                                            hp.getDateStartAsString(),
                                            hp.getNumDaysAsString(),
                                            emp.getFullName(),
                                            hpInTeam.getDateStartAsString(),
                                            hpInTeam.getNumDaysAsString()));
                                }
                            }
                            sb.append(sbPeriods.toString());
                        }
                        sbAllPeriodsCheck.append(sb.toString());
                    }
                }
                if (isNoCrossedFound) {
                    layoutInstance.setCrossingDatesListValue("", true);
                } else {
                    layoutInstance.setCrossingDatesListValue(sbAllPeriodsCheck.toString(), false);
                }
            } else {
                Set<Employee> employeesWithCrossingDates = employeeServiceImpl.getEmployeesWithCrossingHolidayPeriods(employee.getId(), teamId, DateUtils.asDate(newHolidayPeriod.getDateStart()), newHolidayPeriod.getNumDays());
                if (employeesWithCrossingDates.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (Employee emp : employeesWithCrossingDates) {
                        if (sb.length() > 0) {
                            sb.append("<br><br>");
                        }
                        List<HolidayPeriod> crossingHolidayPeriods = emp.getHolidayPeriods();
                        StringBuilder sbPeriods = new StringBuilder();
                        for (HolidayPeriod hpInTeam : crossingHolidayPeriods) {
                            if (DateUtils.isIntersectionBetweenDates(
                                    DateUtils.asDate(newHolidayPeriod.getDateStart()),
                                    DateUtils.addDays(DateUtils.asDate(newHolidayPeriod.getDateStart()), newHolidayPeriod.getNumDays()),
                                    DateUtils.asDate(hpInTeam.getDateStart()),
                                    DateUtils.addDays(DateUtils.asDate(hpInTeam.getDateStart()), hpInTeam.getNumDays()))
                                    ) {
                                if (sbPeriods.length() > 0) {
                                    sbPeriods.append("<br>");
                                }
                                sbPeriods.append(String.format("<span class='%s'>Ваш отпуск с %s (дней: %s) пересекается с отпуском сотрудника %s:"+
                                                " дата начала: %s (дней: %s)!</span>",
                                        GlobalConstants.CSS_HOLIDAY_PERIOD_CROSSING_MESSAGE,
                                        newHolidayPeriod.getDateStartAsString(),
                                        newHolidayPeriod.getNumDaysAsString(),
                                        emp.getFullName(),
                                        hpInTeam.getDateStartAsString(),
                                        hpInTeam.getNumDaysAsString()));
                            }
                        }
                        sb.append(sbPeriods.toString());
                    }
                    layoutInstance.setCrossingDatesListValue(sb.toString(), false);
                } else {
                    layoutInstance.setCrossingDatesListValue("", true);
                }
            }
        }
    }
    private void checkCrossDates(EmployeeHolidaysLayout layoutInstance) {
        checkCrossDates(layoutInstance, false);
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
        employeeHolidaysLayout.setNegotiateSelectedPeriodsClickListener((hpNegotiationStatus, setEmployeeHolPeriods) -> {
            holidayPeriodServiceImpl.setNegotiationStatusForEmployeeHolidayPeriods(setEmployeeHolPeriods, hpNegotiationStatus);
            emailServiceImpl.sendMailHolidayPeriodsNegotiated(setEmployeeHolPeriods, employee);
            //TODO: for now the whole page is reloaded. It is not an optimal way to refresh data in the grids with holiday periods. Need refactoring later.
            Page.getCurrent().reload();
        });

        employeeHolidaysLayout.setRejectSelectedPeriodsClickListener((hpNegotiationStatus, setEmployeeHolPeriods) -> {
            holidayPeriodServiceImpl.setNegotiationStatusForEmployeeHolidayPeriods(setEmployeeHolPeriods, hpNegotiationStatus);
            emailServiceImpl.sendMailHolidayPeriodsRejected(setEmployeeHolPeriods, employee);
            //TODO: for now the whole page is reloaded. It is not an optimal way to refresh data in the grids with holiday periods. Need refactoring later.
            Page.getCurrent().reload();
        });

        employeeHolidaysLayout.addMainButtonClickListener(layoutInstance -> {
            newHolidayPeriod.setEmployee(employee);
            HolidayPeriod addedToDBHolidayPeriod = holidayPeriodServiceImpl.saveHolidayPeriod(newHolidayPeriod);
            checkCrossDates(layoutInstance);
            return addedToDBHolidayPeriod != null;
        });

        employeeHolidaysLayout.setCheckCrossDatesButtonClickListener((layout, params) -> {
            checkCrossDates((EmployeeHolidaysLayout)layout, true);
            return new ButtonClickResult(true);
        });

        employeeHolidaysLayout.setRefreshGridDataListener(layoutInstance -> {
            employeeHolidayPeriods = holidayPeriodServiceImpl.getHolidayPeriodsForEmployee(employee);
            //HolidayPeriodUtils.checkAndMarkEmployeeHolidayPeriodsForCrossingDates(employee, employeeHolidayPeriods);
            ((EmployeeHolidaysLayout)layoutInstance).setEmployeeHolidayPeriods(employeeHolidayPeriods);
        });

        employeeHolidaysLayout.setSendForNegotiationButtonClickListener((layoutInstance, selectedPeriods, negotiationStatus) -> {
            Set<Employee> managers = employeeServiceImpl.getAllManagersForEmployee(employee);
            if (emailServiceImpl.sendMailHolidayPeriodSubmitted(selectedPeriods, employee, managers)) {
                // if successful mail, change periods statuses in db
                holidayPeriodServiceImpl.setNegotiationStatusForHolidayPeriods(selectedPeriods, negotiationStatus);
            }
            UIHelper.showNotification("Выбранные периоды отпуска успешно отправлены на согласование.");
        });

        employeeHolidaysLayout.addDeleteButtonClickListener((layoutInstance, selectedPeriods) -> {
            if (selectedPeriods != null && selectedPeriods.size() > 0) {
                holidayPeriodServiceImpl.deleteHolidayPeriods(selectedPeriods);
            }
        });


        new StandardBaseLayoutDrawer(this, employeeHolidaysLayout).drawLayout();
        //employeeHolidaysLayout.constructLayout();
        //addComponent(employeeHolidaysLayout);
    }

    @Override
    protected boolean prepareViewData() {
        employee = SessionUtils.getCurrentUser();
        employeeHolidayPeriods = holidayPeriodServiceImpl.getHolidayPeriodsForEmployee(employee);
        allNegotiationStatuses = holidayPeriodServiceImpl.getAllHolidayPeriodNegotiationStatuses();

        if (employee.isManager()) {
            Set<Team> managedTeams = employee.getManagedTeams();
            if (managedTeams != null && managedTeams.size() > 0) {
                managedTeams.forEach(team -> {
                    Set<Employee> teamEmployees = employeeServiceImpl.getByTeamId(team.getId());

                    EmployeeToEmployeeHolidayPeriodAdapter<EmployeeHolidayPeriod> adapter =
                            new EmployeeToEmployeeHolidayPeriodAdapter<EmployeeHolidayPeriod>(EmployeeHolidayPeriod::new, holidayPeriodServiceImpl);

                    Collection<EmployeeHolidayPeriod> empHolidayPeriods = adapter.convert(teamEmployees);

                    //TODO: remove refactored block later
                    /*Set<EmployeeHolidayPeriod> empHolidayPeriods = new HashSet<>();
                    if (teamEmployees != null && teamEmployees.size() > 0) {
                        for(Employee emp : teamEmployees) {
                            List<HolidayPeriod> holidayPeriodsForEmployee = holidayPeriodServiceImpl.getHolidayPeriodsForEmployee(emp);
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
                    }*/
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
    }
}
