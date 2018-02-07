package ru.rti.holidays.view.employee;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.adapter.EmployeeToEmployeeHolidayPeriodAdapter;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriodCrossing;
import ru.rti.holidays.entity.*;
import ru.rti.holidays.layout.base.StandardBaseLayoutDrawer;
import ru.rti.holidays.layout.base.behaviour.ActionPerformedResult;
import ru.rti.holidays.layout.base.behaviour.ButtonClickResult;
import ru.rti.holidays.layout.employee.EmployeeHolidayPeriodsCrossingDatesLayout;
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
    //private HolidayPeriod newHolidayPeriod;
    private List<HolidayPeriodNegotiationStatus> allNegotiationStatuses;
    private Map<Team, Collection<EmployeeHolidayPeriod>> teamMembersHolidayPeriods = new HashMap<>();

    @Override
    protected Label getPageTitleLabel() {
        return new Label("Мои отпуска");
    }

    private Collection<EmployeeHolidayPeriodCrossing> getCrossingHolidayPeriods(EmployeeHolidayPeriodsCrossingDatesLayout layoutInstance, boolean checkAllEmployeePeriods) {
        Collection<EmployeeHolidayPeriodCrossing> resultItems = new ArrayList<>();

        if (employee.getTeam() == null) {
            return resultItems;
        }

        Long teamId = employee.getTeam().getId();

        if (checkAllEmployeePeriods) {
            List<HolidayPeriod> allPeriods = holidayPeriodServiceImpl.getHolidayPeriodsForEmployee(employee);
            if (allPeriods == null || allPeriods.size() == 0) {
                return resultItems;
            }

            for (HolidayPeriod hp : allPeriods) {
                if (HolidayPeriodUtils.isHolidayPeriodInOkStatus(hp) || HolidayPeriodUtils.isHolidayPeriodInRejectedStatus(hp)) {
                    continue;
                }

                Set<Employee> employeesWithCrossingDates = employeeServiceImpl.getEmployeesWithCrossingHolidayPeriods(employee.getId(), teamId, DateUtils.asDate(hp.getDateStart()), hp.getNumDays());

                if (employeesWithCrossingDates.size() > 0) {
                    for (Employee emp : employeesWithCrossingDates) {
                        List<HolidayPeriod> crossingHolidayPeriods = emp.getHolidayPeriods();

                        for (HolidayPeriod hpInTeam : crossingHolidayPeriods) {
                            if (DateUtils.isIntersectionBetweenDates(
                                    DateUtils.asDate(hp.getDateStart()),
                                    DateUtils.addDays(DateUtils.asDate(hp.getDateStart()), hp.getNumDays()),
                                    DateUtils.asDate(hpInTeam.getDateStart()),
                                    DateUtils.addDays(DateUtils.asDate(hpInTeam.getDateStart()), hpInTeam.getNumDays()))
                                    ) {
                                        resultItems.add(
                                                new EmployeeHolidayPeriodCrossing(employee.getFullName(),
                                                hp.getDateStart(),
                                                hp.getNumDays(),
                                                emp.getFullName(),
                                                hpInTeam.getDateStart(),
                                                hpInTeam.getNumDays())
                                        );
                            }
                        }
                    }
                }
            }
        } else {
           /* Set<Employee> employeesWithCrossingDates = employeeServiceImpl.getEmployeesWithCrossingHolidayPeriods(employee.getId(), teamId, DateUtils.asDate(newHolidayPeriod.getDateStart()), newHolidayPeriod.getNumDays());

            if (employeesWithCrossingDates.size() > 0) {
                for (Employee emp : employeesWithCrossingDates) {
                    List<HolidayPeriod> crossingHolidayPeriods = emp.getHolidayPeriods();
                    for (HolidayPeriod hpInTeam : crossingHolidayPeriods) {
                        if (DateUtils.isIntersectionBetweenDates(
                                DateUtils.asDate(newHolidayPeriod.getDateStart()),
                                DateUtils.addDays(DateUtils.asDate(newHolidayPeriod.getDateStart()), newHolidayPeriod.getNumDays()),
                                DateUtils.asDate(hpInTeam.getDateStart()),
                                DateUtils.addDays(DateUtils.asDate(hpInTeam.getDateStart()), hpInTeam.getNumDays()))
                                ) {
                                    resultItems.add(
                                            new EmployeeHolidayPeriodCrossing(employee.getFullName(),
                                                    newHolidayPeriod.getDateStart(),
                                                    newHolidayPeriod.getNumDays(),
                                                    emp.getFullName(),
                                                    hpInTeam.getDateStart(),
                                                    hpInTeam.getNumDays())
                                    );

                                }
                    }
                }
            }*/
        }

        return resultItems;
    }

    /**
     * Adds Holiday Period History.
     * TODO: move to utilities later
     * @param setEmployeeHolPeriods
     * @param negotiatorPostfixComment
     */
    private void addHolidayPeriodHistory(Set<EmployeeHolidayPeriod> setEmployeeHolPeriods,
                                         HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationMode negotiationMode,
                                         String negotiatorPostfixComment) {
        try {
            boolean isSetNotEmpty = setEmployeeHolPeriods != null && !setEmployeeHolPeriods.isEmpty();
            String negotiatorPostfixCommentNonNull = CommonUtils.getValueOrEmptyString(negotiatorPostfixComment);

            Map<Long, String> oldStatusMap = new HashMap<>();
            Map<Long, String> newStatusMap = new HashMap<>();

            if (isSetNotEmpty) {
                for (EmployeeHolidayPeriod ehp1 : setEmployeeHolPeriods) {
                    HolidayPeriod hp = ehp1.getHolidayPeriod();
                    oldStatusMap.put(hp.getId(), hp.getNegotiationStatusAsString());
                    HolidayPeriodNegotiationStatus futureStatus = null;
                    if (negotiationMode == HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationMode.REJECTION) {
                        futureStatus = HolidayPeriodNegotiationStatusUtils.getRejectedStatusFromList(allNegotiationStatuses);
                    } else {
                        futureStatus = HolidayPeriodNegotiationStatusUtils.calculateNextStatus(SessionUtils.getCurrentUser(), hp, allNegotiationStatuses);
                    }

                    if (futureStatus == null) {
                        newStatusMap.put(hp.getId(), "");
                    } else {
                        newStatusMap.put(hp.getId(), futureStatus.getStatusName());
                    }
                }
            }

            holidayPeriodServiceImpl.setNegotiationStatusForEmployeeHolidayPeriods(
                    SessionUtils.getCurrentUser(),
                    setEmployeeHolPeriods,
                    allNegotiationStatuses,
                    negotiationMode);

            //emailServiceImpl.sendMailHolidayPeriodsNegotiated(setEmployeeHolPeriods, employee);

            if (isSetNotEmpty) {
                for (EmployeeHolidayPeriod ehp : setEmployeeHolPeriods) {
                    HolidayPeriod hp = ehp.getHolidayPeriod();
                    Employee currentUser = SessionUtils.getCurrentUser();

                    String comment = currentUser.getFullName() + negotiatorPostfixCommentNonNull;
    /*                if (isNegotiated) {
                        String comment = currentUser.getFullName() + " согласовал Ваш период отпуска";
                    } else {
                        String comment = currentUser.getFullName() + " согласовал Ваш период отпуска";
                    }*/
                    String oldStatus = oldStatusMap.get(hp.getId());
                    String newStatus = newStatusMap.get(hp.getId());
                    HolidayPeriodNegotiationHistory history = HolidayPeriodNegotiationHistoryUtils
                            .createHolidayPeriodNegotiationHistory(hp, comment, oldStatus, newStatus);
                    holidayPeriodServiceImpl.saveHolidayPeriodNegotiationHistory(history);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Adds Holiday Period History with no status change
     * TODO: move to utilities later
     * @param setHolPeriods
     * @param comment
     */
    private void addHolidayPeriodHistorySimple(Set<HolidayPeriod> setHolPeriods, String comment) {
        try {
            if (setHolPeriods == null || setHolPeriods.isEmpty()) {
                return;
            }

            for (HolidayPeriod hp : setHolPeriods) {
                HolidayPeriodNegotiationStatus newStatus = HolidayPeriodNegotiationStatusUtils.getNewStatusFromList(allNegotiationStatuses);
                HolidayPeriodNegotiationStatus negotiatingStatus = HolidayPeriodNegotiationStatusUtils.getNegotiatingStatusFromList(allNegotiationStatuses);

                HolidayPeriodNegotiationHistory history = HolidayPeriodNegotiationHistoryUtils
                        .createHolidayPeriodNegotiationHistory(hp, comment, newStatus.getStatusName(), negotiatingStatus.getStatusName());

                holidayPeriodServiceImpl.saveHolidayPeriodNegotiationHistory(history);
            }

        } catch (Exception e) {
        }
    }

    @Override
    protected void addCustomComponents() {
        EmployeeHolidaysLayout employeeHolidaysLayout = new EmployeeHolidaysLayout(
            employee,
            "Периоды моего отпуска"
        );

        employeeHolidaysLayout.setEmployeeHolidayPeriods(employeeHolidayPeriods);
        employeeHolidaysLayout.setAllNegotiationStatuses(allNegotiationStatuses);
        //employeeHolidaysLayout.setNewHolidayPeriod(newHolidayPeriod);
        employeeHolidaysLayout.setManagedTeamMembersHolidays(teamMembersHolidayPeriods);
//        employeeHolidaysLayout.setNegotiateSelectedPeriodsClickListener((hpNegotiationStatus, setEmployeeHolPeriods) -> {
//                boolean isSetNotEmpty = !setEmployeeHolPeriods.isEmpty();
//                Map<Long, String> oldStatusMap = new HashMap<>();
//                Map<Long, String> newStatusMap = new HashMap<>();
//                if (isSetNotEmpty) {
//                    for (EmployeeHolidayPeriod ehp1 : setEmployeeHolPeriods) {
//                        HolidayPeriod hp = ehp1.getHolidayPeriod();
//                        oldStatusMap.put(hp.getId(), hp.getNegotiationStatusAsString());
//                        HolidayPeriodNegotiationStatus futureStatus = HolidayPeriodNegotiationStatusUtils.calculateNextStatus(SessionUtils.getCurrentUser(), hp, allNegotiationStatuses);
//                        if (futureStatus == null) {
//                            newStatusMap.put(hp.getId(), "");
//                        } else {
//                            newStatusMap.put(hp.getId(), futureStatus.getStatusName());
//                        }
//                    }
//                }
//
//                holidayPeriodServiceImpl.setNegotiationStatusForEmployeeHolidayPeriods(
//                        SessionUtils.getCurrentUser(),
//                        setEmployeeHolPeriods,
//                        allNegotiationStatuses,
//                        HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationMode.NEGOTIATION);
//
//                //emailServiceImpl.sendMailHolidayPeriodsNegotiated(setEmployeeHolPeriods, employee);
//
//                if (isSetNotEmpty) {
//                    for (EmployeeHolidayPeriod ehp : setEmployeeHolPeriods) {
//                        HolidayPeriod hp = ehp.getHolidayPeriod();
//                        Employee currentUser = SessionUtils.getCurrentUser();
//                        String comment = currentUser.getFullName() + " согласовал Ваш период отпуска";
//                        String oldStatus = oldStatusMap.get(hp.getId());
//                        String newStatus = newStatusMap.get(hp.getId());
//                        HolidayPeriodNegotiationHistory history = HolidayPeriodNegotiationHistoryUtils
//                                .createHolidayPeriodNegotiationHistory(hp, comment, oldStatus, newStatus);
//                        holidayPeriodServiceImpl.saveHolidayPeriodNegotiationHistory(history);
//                    }
//                }
//
//            //addHolidayPeriodHistory(setEmployeeHolPeriods,
//                    //HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationMode.NEGOTIATION,
//                    //" согласовал Ваш период отпуска"
//                    //);
//
//            emailServiceImpl.sendMailHolidayPeriodsNegotiated(setEmployeeHolPeriods, employee);
//
//
//            //TODO: for now the whole page is reloaded. It is not an optimal way to refresh data in the grids with holiday periods. Need refactoring later.
//            Page.getCurrent().reload();
//        });

        employeeHolidaysLayout.setNegotiateSelectedPeriodsClickListener((layout, params) -> {
            if (params != null && params.length > 0) {

                Set<EmployeeHolidayPeriod> selectedHolidayPeriods = (Set<EmployeeHolidayPeriod>)params[0];
                Team team = (Team)params[1];

                boolean isSetNotEmpty = !selectedHolidayPeriods.isEmpty();
                Map<Long, String> oldStatusMap = new HashMap<>();
                Map<Long, String> newStatusMap = new HashMap<>();
                if (isSetNotEmpty) {
                    for (EmployeeHolidayPeriod ehp1 : selectedHolidayPeriods) {
                        HolidayPeriod hp = ehp1.getHolidayPeriod();
                        oldStatusMap.put(hp.getId(), hp.getNegotiationStatusAsString());
                        HolidayPeriodNegotiationStatus futureStatus = HolidayPeriodNegotiationStatusUtils.calculateNextStatus(SessionUtils.getCurrentUser(), hp, allNegotiationStatuses);
                        if (futureStatus == null) {
                            newStatusMap.put(hp.getId(), "");
                        } else {
                            newStatusMap.put(hp.getId(), futureStatus.getStatusName());
                        }
                    }
                }

                holidayPeriodServiceImpl.setNegotiationStatusForEmployeeHolidayPeriods(
                        SessionUtils.getCurrentUser(),
                        selectedHolidayPeriods,
                        allNegotiationStatuses,
                        HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationMode.NEGOTIATION);

                //emailServiceImpl.sendMailHolidayPeriodsNegotiated(setEmployeeHolPeriods, employee);

                if (isSetNotEmpty) {
                    for (EmployeeHolidayPeriod ehp : selectedHolidayPeriods) {
                        HolidayPeriod hp = ehp.getHolidayPeriod();
                        Employee currentUser = SessionUtils.getCurrentUser();
                        String comment = currentUser.getFullName() + " согласовал Ваш период отпуска";
                        String oldStatus = oldStatusMap.get(hp.getId());
                        String newStatus = newStatusMap.get(hp.getId());
                        HolidayPeriodNegotiationHistory history = HolidayPeriodNegotiationHistoryUtils
                                .createHolidayPeriodNegotiationHistory(hp, comment, oldStatus, newStatus);
                        holidayPeriodServiceImpl.saveHolidayPeriodNegotiationHistory(history);
                    }
                }

                //addHolidayPeriodHistory(setEmployeeHolPeriods,
                //HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationMode.NEGOTIATION,
                //" согласовал Ваш период отпуска"
                //);

                emailServiceImpl.sendMailHolidayPeriodsNegotiated(selectedHolidayPeriods, employee);

                ButtonClickResult<EmployeeHolidayPeriod> result = new ButtonClickResult<>(true);
                result.setResultItems(getHolidayPeriodsForTeam(team));
                return result;
            }

            ButtonClickResult<EmployeeHolidayPeriod> result = new ButtonClickResult<>(false);
            return result;
        });

        employeeHolidaysLayout.setRejectSelectedPeriodsClickListener((layout, params) -> {
            if (params != null && params.length > 0) {

                Set<EmployeeHolidayPeriod> selectedHolidayPeriods = (Set<EmployeeHolidayPeriod>)params[0];
                Team team = (Team)params[1];

                addHolidayPeriodHistory(selectedHolidayPeriods,
                    HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationMode.REJECTION,
                    " отклонил Ваш период отпуска");

                emailServiceImpl.sendMailHolidayPeriodsRejected(selectedHolidayPeriods, employee);

                ButtonClickResult<EmployeeHolidayPeriod> result = new ButtonClickResult<>(true);
                result.setResultItems(getHolidayPeriodsForTeam(team));
                return result;
            }

            ButtonClickResult<EmployeeHolidayPeriod> result = new ButtonClickResult<>(false);
            return result;
        });

//        employeeHolidaysLayout.setRejectSelectedPeriodsClickListener((hpNegotiationStatus, setEmployeeHolPeriods) -> {
//            //holidayPeriodServiceImpl.setNegotiationStatusForEmployeeHolidayPeriods(setEmployeeHolPeriods, hpNegotiationStatus);
//
//
//            addHolidayPeriodHistory(setEmployeeHolPeriods,
//                    HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationMode.REJECTION,
//                    " отклонил Ваш период отпуска");
//
//            emailServiceImpl.sendMailHolidayPeriodsRejected(setEmployeeHolPeriods, employee);
//
//            //TODO: for now the whole page is reloaded. It is not an optimal way to refresh data in the grids with holiday periods. Need refactoring later.
//            Page.getCurrent().reload();
//        });

        employeeHolidaysLayout.setSaveButtonClickListener((layout, objectForSave) -> {
            holidayPeriodServiceImpl.saveHolidayPeriod((HolidayPeriod)objectForSave);
            //HolidayPeriod addedToDBHolidayPeriod = holidayPeriodServiceImpl.saveHolidayPeriod(newHolidayPeriod);
            //return addedToDBHolidayPeriod != null;
        });

        employeeHolidaysLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
            if (entities != null && entities.size() > 0) {
                if (employee.getHolidayPeriods() != null) {
                    employee.getHolidayPeriods().removeAll(entities);
                }
                holidayPeriodServiceImpl.deleteHolidayPeriods((Iterable<HolidayPeriod>)entities);
                employeeHolidaysLayout.refreshDataGrid();
            }
        });

        employeeHolidaysLayout.setAddNegotiationHistoryActionListener((layout, params) -> {
            ActionPerformedResult<HolidayPeriodNegotiationHistory> actionPerformedResult = new ActionPerformedResult<>(true);
            EmployeeHolidaysLayout layoutInstance = (EmployeeHolidaysLayout)layout;
            if (params != null && params.length > 0) {
                HolidayPeriodNegotiationHistory holidayPeriodNegotiationHistory = (HolidayPeriodNegotiationHistory)params[0];
                holidayPeriodServiceImpl.saveHolidayPeriodNegotiationHistory(holidayPeriodNegotiationHistory);
            }
            return actionPerformedResult;
        });

        employeeHolidaysLayout.setCheckCrossingDatesButtonClickListener((layout, params) -> {
            //checkCrossDates((EmployeeHolidaysLayout)layout, true);
            ButtonClickResult<EmployeeHolidayPeriodCrossing> buttonClickResult = new ButtonClickResult<>(true);
            EmployeeHolidayPeriodsCrossingDatesLayout layoutInstance = (EmployeeHolidayPeriodsCrossingDatesLayout)layout;
            Collection<EmployeeHolidayPeriodCrossing> crossingPeriodsList = getCrossingHolidayPeriods(layoutInstance, true);
            layoutInstance.setInformationMessageByCheckResult(crossingPeriodsList.isEmpty());
            buttonClickResult.setResultItems(crossingPeriodsList);
            return buttonClickResult;
        });

        employeeHolidaysLayout.setRefreshGridDataListener(layoutInstance -> {
            employeeHolidayPeriods = holidayPeriodServiceImpl.getHolidayPeriodsForEmployee(employee);
            //HolidayPeriodUtils.checkAndMarkEmployeeHolidayPeriodsForCrossingDates(employee, employeeHolidayPeriods);
            ((EmployeeHolidaysLayout)layoutInstance).setEmployeeHolidayPeriods(employeeHolidayPeriods);
        });

        employeeHolidaysLayout.setRecallSelectedPeriodsButtonClickListener((layout, params) -> {
            Set<Employee> managers = employeeServiceImpl.getAllManagersForEmployee(employee);
            if (params != null && params.length > 1) {
                Set<HolidayPeriod> selectedPeriods = (Set<HolidayPeriod>)params[0];
                HolidayPeriodNegotiationStatus recalledStatus = (HolidayPeriodNegotiationStatus)params[1];

                if (emailServiceImpl.sendMailHolidayPeriodsRecalled(selectedPeriods, employee, managers)) {
                    // if successful mail, change periods statuses in db
                    UIHelper.showNotification("Выбранные периоды отпуска успешно отозваны с согласования.");
                } else {
                    UIHelper.showNotification("Выбранные периоды отпуска успешно отозваны с согласования, но не удалось отправить письмо одному или нескольким руководителям.");
                }

                holidayPeriodServiceImpl.setNegotiationStatusForHolidayPeriods(selectedPeriods, recalledStatus);

                addHolidayPeriodHistorySimple(selectedPeriods, "Отпуск был отозван сотрудником.");
            }

            return new ButtonClickResult<>(true);
        });

        employeeHolidaysLayout.setSendForNegotiationButtonClickListener((layout, params) -> {
            Set<Employee> managers = employeeServiceImpl.getAllManagersForEmployee(employee);
            if (params != null && params.length > 1) {
                Set<HolidayPeriod> selectedPeriods = (Set<HolidayPeriod>)params[0];
                HolidayPeriodNegotiationStatus negotiatingStatus = (HolidayPeriodNegotiationStatus)params[1];

                if (emailServiceImpl.sendMailHolidayPeriodSubmitted(selectedPeriods, employee, managers)) {
                    // if successful mail, change periods statuses in db
                    UIHelper.showNotification("Выбранные периоды отпуска успешно отправлены на согласование.");
                } else {
                    UIHelper.showNotification("Выбранные периоды отпуска успешно отправлены на согласование, но не удалось отправить письмо одному или нескольким руководителям.");
                }

                holidayPeriodServiceImpl.setNegotiationStatusForHolidayPeriods(selectedPeriods, negotiatingStatus);

                addHolidayPeriodHistorySimple(selectedPeriods, "Отпуск отправлен на согласование");
            }

            return new ButtonClickResult<>(true);
        });

//        employeeHolidaysLayout.setSendForNegotiationButtonClickListener((layoutInstance, selectedPeriods, negotiationStatus) -> {
//            Set<Employee> managers = employeeServiceImpl.getAllManagersForEmployee(employee);
//            if (emailServiceImpl.sendMailHolidayPeriodSubmitted(selectedPeriods, employee, managers)) {
//                // if successful mail, change periods statuses in db
//                UIHelper.showNotification("Выбранные периоды отпуска успешно отправлены на согласование.");
//            } else {
//                UIHelper.showNotification("Выбранные периоды отпуска успешно отправлены на согласование, но не удалось отправить письмо одному или нескольким руководителям.");
//            }
//            holidayPeriodServiceImpl.setNegotiationStatusForHolidayPeriods(selectedPeriods, negotiationStatus);
//
//            addHolidayPeriodHistorySimple(selectedPeriods, "Отпуск отправлен на согласование");
//        });

        new StandardBaseLayoutDrawer(this, employeeHolidaysLayout).drawLayout();
    }

    Collection<EmployeeHolidayPeriod> getHolidayPeriodsForTeam(Team team) {
        if (team == null) {
            return null;
        }

        Set<Employee> teamEmployees = employeeServiceImpl.getByTeamId(team.getId());

        EmployeeToEmployeeHolidayPeriodAdapter<EmployeeHolidayPeriod> adapter =
                new EmployeeToEmployeeHolidayPeriodAdapter<EmployeeHolidayPeriod>(EmployeeHolidayPeriod::new, holidayPeriodServiceImpl);

        Collection<EmployeeHolidayPeriod> empHolidayPeriods = adapter.convert(teamEmployees);
        Collection<EmployeeHolidayPeriod> periodsToRemoveByNegotiationMaskAndStatus = new ArrayList<>();

        for (EmployeeHolidayPeriod curEmpHolidayPeriod : empHolidayPeriods) {
            HolidayPeriod curHolidayPeriod = curEmpHolidayPeriod.getHolidayPeriod();
            if (!HolidayPeriodUtils.isHolidayPeriodInNewStatus(curHolidayPeriod) &&
                    HolidayPeriodUtils.isVisibleForCurrentUser(curEmpHolidayPeriod, curHolidayPeriod)) {
                continue;
            }

            periodsToRemoveByNegotiationMaskAndStatus.add(curEmpHolidayPeriod);
        }

        // Perform filtering according to project role of current manager
        empHolidayPeriods.removeAll(periodsToRemoveByNegotiationMaskAndStatus);

        return empHolidayPeriods;
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
                    teamMembersHolidayPeriods.put(team, getHolidayPeriodsForTeam(team));
                });
            }
        }

        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
    }
}
