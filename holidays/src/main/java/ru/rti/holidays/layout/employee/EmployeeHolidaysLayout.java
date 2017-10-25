package ru.rti.holidays.layout.employee;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.component.security.User;
import ru.rti.holidays.entity.*;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.base.StandardBaseLayoutDrawer;
import ru.rti.holidays.layout.base.behaviour.ButtonClickListener;
import ru.rti.holidays.layout.behaviour.*;
import ru.rti.holidays.style.GridEmployeeHolidayPeriodCellStyleGenerator;
import ru.rti.holidays.style.GridHolidayPeriodCellStyleGenerator;
import ru.rti.holidays.utility.*;
import ru.rti.holidays.validator.HolidayPeriodDateValidator;
import ru.rti.holidays.validator.HolidayPeriodDayNumValidator;
import ru.rti.holidays.view.employee.EmployeeSettingsView;

import java.time.LocalDate;
import java.util.*;

public class EmployeeHolidaysLayout extends BaseVerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(EmployeeHolidaysLayout.class);
    /**
     * Logged in Employee. This value is passed to the layout from the view layer
     */
    private Employee employee;
    private String panelName;
    private Grid<HolidayPeriod> grdHolidayPeriods = new Grid<>();
    private Map<Team, Collection<EmployeeHolidayPeriod>> managedTeamMembersHolidays = null;
    private Map<Long, Button> negotiateSelectedPeriodsButtonsMap = new HashMap<>();
    private Map<Long, Button> rejectSelectedPeriodsButtonsMap = new HashMap<>();
    private Map<Long, Set<EmployeeHolidayPeriod>> periodsForNegotiationMap = new HashMap<>();
    private Map<Long, Set<EmployeeHolidayPeriod>> periodsForRejectionMap = new HashMap<>();
    private List<HolidayPeriodNegotiationStatus> allNegotiationStatuses;
    private Button btnRemoveHolidayPeriods = new Button("Удалить выбранные", VaadinIcons.DEL);
    private Button btnSendForNegotiation = new Button("Отправить на согласование", VaadinIcons.USERS);
    private List<HolidayPeriod> employeeHolidayPeriods;
    private DateField datePeriod = new DateField();
    private TextField txtNumDays = new TextField();
    private EmployeeHolidaysLayoutMainButtonClickListener mainButtonClickListener;
    //private RefreshGridDataListener refreshGridDataListener;
    private EmployeeHolidaysLayoutDeleteButtonClickListener deleteButtonClickListener;
    private EmployeeHolidaysLayoutNegotiateSelectedPeriodsClickListener negotiateSelectedPeriodsClickListener;
    private EmployeeHolidaysLayoutRejectSelectedPeriodsClickListener rejectSelectedPeriodsClickListener;
    private EmployeeHolidaysLayoutSendForNegotiationButtonClickListener sendForNegotiationButtonClickListener;
    //private EmployeeHolidaysLayoutCheckCrossDatesButtonClickListener checkCrossDatesButtonClickListener;
    private ButtonClickListener checkCrossDatesButtonClickListener;

    private HolidayPeriod newHolidayPeriod;
    private Binder<HolidayPeriod> holidayPeriodBinder = new Binder<>();
    //private User currentUser;
    public static final String NO_CROSSING_DATES_MESSAGE = "Пересечений на текущий момент не обнаружено.";
    private Label lblCrossingDatesList = new Label(NO_CROSSING_DATES_MESSAGE);
    private boolean isCrossingDates = false;

    public EmployeeHolidaysLayout(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee instance cannot be null for layout class " + this.getClass().toString());
        }
        this.employee = employee;
    }

    public EmployeeHolidaysLayout(Employee employee, String panelName) {
        this(employee);
        this.panelName = panelName;
    }

    /**
     * If the current Employee has a teamlead/line manager/project manager role, display the holiday periods of his/her managed Employees.
     */
    private void addTeamMembersHolidaysTables() {
        Panel pnlPanelTeamMembersHolidays = new Panel("Отпуска Ваших сотрудников:");
        pnlPanelTeamMembersHolidays.setSizeFull();

        VerticalLayout teamMembersHolidaysLayout = new VerticalLayout();

        Set<Team> managedTeams = employee.getManagedTeams();
        if (managedTeams != null && managedTeams.size() > 0) {
            managedTeams.forEach(team -> {
                Label lblCurrentTeamName = new Label(team.getTeamName());
                Grid<EmployeeHolidayPeriod> grdTeamMembersHolidayPeriods = new Grid<>();

                Button btnNegotiateSelPeriods = new Button("Согласовать выбранные");
                btnNegotiateSelPeriods.addStyleName(ValoTheme.BUTTON_FRIENDLY);
                btnNegotiateSelPeriods.setIcon(VaadinIcons.CHECK);
                btnNegotiateSelPeriods.setEnabled(false);
                btnNegotiateSelPeriods.setId("btnNegotiateSelPeriods_" + team.getId());
                btnNegotiateSelPeriods.setWidth("300px");

                Button btnRejectSelPeriods = new Button("Отклонить выбранные");
                btnRejectSelPeriods.addStyleName(ValoTheme.BUTTON_DANGER);
                btnRejectSelPeriods.setIcon(VaadinIcons.STOP);
                btnRejectSelPeriods.setEnabled(false);
                btnRejectSelPeriods.setId("btnRejectSelPeriods_" + team.getId());
                btnRejectSelPeriods.setWidth("300px");

                btnNegotiateSelPeriods.addClickListener(clickEvent -> {
                    String buttonId = clickEvent.getButton().getId();

                    try {
                        String teamId = buttonId.substring("btnNegotiateSelPeriods_".length());
                        Long nTeamId = Long.parseLong(teamId);
                        Set<EmployeeHolidayPeriod> setEmplHolPeriods = periodsForNegotiationMap.get(nTeamId);
                        if (negotiateSelectedPeriodsClickListener != null) {
                            negotiateSelectedPeriodsClickListener.onNegotiateSelectedPeriods(
                                    HolidayPeriodNegotiationStatusUtils.getOkStatusFromList(allNegotiationStatuses),
                                    setEmplHolPeriods);
                        }
                    } catch (NumberFormatException nfe) {
                    }
                });

                btnRejectSelPeriods.addClickListener(clickEvent -> {
                    String buttonId = clickEvent.getButton().getId();

                    try {
                        String teamId = buttonId.substring("btnRejectSelPeriods_".length());
                        Long nTeamId = Long.parseLong(teamId);
                        Set<EmployeeHolidayPeriod> setEmplHolPeriods = periodsForRejectionMap.get(nTeamId);
                        if (rejectSelectedPeriodsClickListener != null) {
                            rejectSelectedPeriodsClickListener.onRejectSelectedPeriods(
                                    HolidayPeriodNegotiationStatusUtils.getRejectedStatusFromList(allNegotiationStatuses),
                                    setEmplHolPeriods);
                        }
                    } catch (NumberFormatException nfe) {
                    }
                });

                negotiateSelectedPeriodsButtonsMap.put(team.getId(), btnNegotiateSelPeriods);
                rejectSelectedPeriodsButtonsMap.put(team.getId(), btnRejectSelPeriods);

                Collection<EmployeeHolidayPeriod> teamMembersHolidayPeriods = null;
                if(managedTeamMembersHolidays != null && managedTeamMembersHolidays.size() > 0) {
                    teamMembersHolidayPeriods = managedTeamMembersHolidays.get(team);
                } else {
                    //TODO: throw exception here
                    teamMembersHolidayPeriods = new HashSet<>();
                }

                MultiSelectionModel<EmployeeHolidayPeriod> selectionModel =
                        (MultiSelectionModel<EmployeeHolidayPeriod>)grdTeamMembersHolidayPeriods.setSelectionMode(Grid.SelectionMode.MULTI);

                grdTeamMembersHolidayPeriods.setItems(teamMembersHolidayPeriods);
                grdTeamMembersHolidayPeriods.setHeightByRows(5);
                grdTeamMembersHolidayPeriods.setWidth("100%");
                grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getEmployeeFullName).setCaption("ФИО сотрудника");
                grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getEmployeeRoleName).setCaption("Роль на проекте");
                grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getDateStartAsString).setCaption("Дата начала отпуска");
                grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getNumDays).setCaption("Количество дней отпуска");
                grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getHolidayPeriodNegotiationStatus).setCaption("Статус согласования").setStyleGenerator(new GridEmployeeHolidayPeriodCellStyleGenerator());

                selectionModel.addMultiSelectionListener(event -> {
                    for (Button btnNegotiateSelectedPeriods : negotiateSelectedPeriodsButtonsMap.values()) {
                        btnNegotiateSelectedPeriods.setEnabled(false);
                    }
                    for (Button btnRejectSelectedPeriods : rejectSelectedPeriodsButtonsMap.values()) {
                        btnRejectSelectedPeriods.setEnabled(false);
                    }

                    Set<EmployeeHolidayPeriod> selectedItems = event.getAllSelectedItems();


                    if (selectedItems != null && selectedItems.size() > 0) {
                        Long selTeamId = -1L;

                        boolean hasSelectedPeriodsForRejection = false;
                        boolean hasSelectedPeriodsForNegotiation = false;

                        for (EmployeeHolidayPeriod empHolPeriod : selectedItems) {
                            if (empHolPeriod.getNegotiationStatus() != null && empHolPeriod.getNegotiationStatus().getNegotiationStatusType() != null) {
                                HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType selPeriodNegStatusType = empHolPeriod.getNegotiationStatus().getNegotiationStatusType();
                                if (selPeriodNegStatusType == HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_OK ||
                                        selPeriodNegStatusType == HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_NEGOTIATING) {
                                    hasSelectedPeriodsForRejection = true;
                                }
                                if (selPeriodNegStatusType == HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_REJECTED ||
                                        selPeriodNegStatusType == HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_NEGOTIATING) {
                                    hasSelectedPeriodsForNegotiation = true;
                                }
                            }

                            if (selTeamId < 0) {
                                selTeamId = empHolPeriod.getTeamId();
                            }
                        }

                        if (hasSelectedPeriodsForNegotiation || hasSelectedPeriodsForRejection) {
                            if (hasSelectedPeriodsForNegotiation && hasSelectedPeriodsForRejection && selectedItems.size() > 1) {
                                // Don't enable any buttons at all, because we cannot negotiate and reject holidays periods simultaneously
                                hasSelectedPeriodsForNegotiation = false;
                                hasSelectedPeriodsForRejection = false;
                            }
                            if (hasSelectedPeriodsForNegotiation) {
                                Button btnNegotiateSelectedPeriods = negotiateSelectedPeriodsButtonsMap.get(selTeamId);
                                btnNegotiateSelectedPeriods.setEnabled(true);
                                periodsForNegotiationMap.remove(selTeamId);
                                periodsForNegotiationMap.put(selTeamId, selectedItems);
                            }
                            if (hasSelectedPeriodsForRejection) {
                                Button btnRejectSelectedPeriods = rejectSelectedPeriodsButtonsMap.get(selTeamId);
                                btnRejectSelectedPeriods.setEnabled(true);
                                periodsForRejectionMap.remove(selTeamId);
                                periodsForRejectionMap.put(selTeamId, selectedItems);
                            }
                        }
                    }
                });

                teamMembersHolidaysLayout.addComponent(lblCurrentTeamName);
                teamMembersHolidaysLayout.addComponent(grdTeamMembersHolidayPeriods);
                teamMembersHolidaysLayout.addComponent(negotiateSelectedPeriodsButtonsMap.get(team.getId()));
                teamMembersHolidaysLayout.addComponent(rejectSelectedPeriodsButtonsMap.get(team.getId()));
            });
        }

        teamMembersHolidaysLayout.setSizeFull();
        pnlPanelTeamMembersHolidays.setContent(teamMembersHolidaysLayout);
        addComponent(pnlPanelTeamMembersHolidays);
    }

    public void constructLayout() {
        try {
            Label lblEmployeeName = new Label("ФИО: <b>" + employee.getFullName() + "</b>", ContentMode.HTML);
            Label lblProjectRole = new Label("Проектная роль: <b>" + employee.getProjectRoleAsString() + "</b>", ContentMode.HTML);
            Label lblProjectRoleType = new Label("Тип проектной роли: <b>" + employee.getProjectRoleSpecialTypeAsString() + "</b>", ContentMode.HTML);

            Label lblTeam = null;

            if (employee.getProjectRole() != null) {
                if (ProjectRole.ProjectRoleSpecialType.getRolesWithTeamManagementAbility().contains(employee.getProjectRole().getProjectRoleSpecialType())) {
                    Set<Team> managedTeams = employee.getManagedTeams();
                    lblTeam = new Label("Команды под руководством: <b>" + TeamUtils.getDelimitedTeamsString(managedTeams, ", ") + "</b>", ContentMode.HTML);
                } else {
                    lblTeam = new Label("Ваша команда: <b>" + employee.getTeamNameAsString() + "</b>", ContentMode.HTML);
                }
            }

            Panel pnlPanelHolidays = new Panel(getPanelName());
            pnlPanelHolidays.setSizeFull();

            VerticalLayout pnlHolidaysLayout = new VerticalLayout();

            grdHolidayPeriods.setItems(employeeHolidayPeriods);
            StyleGenerator<HolidayPeriod> styleGenerator = new GridHolidayPeriodCellStyleGenerator();
            grdHolidayPeriods.addColumn(HolidayPeriod::getDateStartAsString).setCaption("Дата начала отпуска").setStyleGenerator(styleGenerator);
            grdHolidayPeriods.addColumn(HolidayPeriod::getNumDays).setCaption("Количество дней отпуска").setStyleGenerator(styleGenerator);
            grdHolidayPeriods.addColumn(HolidayPeriod::getNegotiationStatusAsString).setCaption("Статус согласования").setStyleGenerator(styleGenerator);
            //grdHolidayPeriods.addColumn(HolidayPeriod::isCrossingDatesAsString).setCaption("Пересечения").setStyleGenerator(styleGenerator);
            grdHolidayPeriods.setHeightByRows(5);
            grdHolidayPeriods.setWidth("100%");

            MultiSelectionModel<HolidayPeriod> selectionModel = (MultiSelectionModel<HolidayPeriod>)grdHolidayPeriods.setSelectionMode(Grid.SelectionMode.MULTI);

            selectionModel.addMultiSelectionListener(event -> {
                Set<HolidayPeriod> selectedItems = event.getAllSelectedItems();

                boolean isBtnRemoveSelectedHolidayPeriodsEnabled = false;
                boolean isBtnSendForNegotiationEnabled = false;

                isBtnRemoveSelectedHolidayPeriodsEnabled = selectedItems != null && selectedItems.size() > 0;
                isBtnSendForNegotiationEnabled = selectedItems != null && selectedItems.size() > 0;

                flags_check:
                for (HolidayPeriod hp : selectedItems) {
                    HolidayPeriodNegotiationStatus hpNegStatus = hp.getNegotiationStatus();
                    if (hpNegStatus != null) {
                        HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType hpNegStatusType = hpNegStatus.getNegotiationStatusType();
                        if (hpNegStatusType != null) {
                            switch (hpNegStatusType) {
                                case NEGOTIATION_STATUS_TYPE_OK:
                                    isBtnRemoveSelectedHolidayPeriodsEnabled = false;
                                    isBtnSendForNegotiationEnabled = false;
                                    break flags_check;
                                case NEGOTIATION_STATUS_TYPE_NEGOTIATING:
                                case NEGOTIATION_STATUS_TYPE_PARTLY_NEGOTIATED:
                                    isBtnRemoveSelectedHolidayPeriodsEnabled = false;
                                    isBtnSendForNegotiationEnabled = false;
                                    break flags_check;
                            }
                        }
                    }
                }
                btnRemoveHolidayPeriods.setEnabled(isBtnRemoveSelectedHolidayPeriodsEnabled);
                btnSendForNegotiation.setEnabled(isBtnSendForNegotiationEnabled);
            });

            pnlHolidaysLayout.addComponent(grdHolidayPeriods);
            pnlHolidaysLayout.addComponent(addControlsPanel());
            pnlHolidaysLayout.setSizeFull();
            pnlPanelHolidays.setContent(pnlHolidaysLayout);


            EmployeeMenuBarLayout employeeMenuBarLayout = new EmployeeMenuBarLayout(false, false);
            employeeMenuBarLayout.setMainMenuItemVisible(false);
            new StandardBaseLayoutDrawer(this, employeeMenuBarLayout).drawLayout();

            addComponent(lblEmployeeName);
            addComponent(lblProjectRole);
            addComponent(lblProjectRoleType);
            addComponent(lblTeam);
            addComponent(pnlPanelHolidays);

            if (employee.isManager()) {
                addTeamMembersHolidaysTables();
            }

            setSizeFull();
        } catch (Exception e) {
            handleException(e, e.getMessage());
        }

    }

    protected GridLayout addControlsPanel() {
        GridLayout addHolidayPeriodLayout = new GridLayout(5, 6);
        addHolidayPeriodLayout.setSizeFull();
        addHolidayPeriodLayout.setSpacing(true);
        addHolidayPeriodLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        //addHolidayPeriodLayout.addStyleName(GlobalConstants.CSS_DEBUG_BORDER);

        Button btnAddHolidayPeriod = new Button("Добавить период отпуска", VaadinIcons.CHECK);
        btnAddHolidayPeriod.addClickListener(event -> {
            try {
                holidayPeriodBinder.writeBean(newHolidayPeriod);
                if (newHolidayPeriod == null || newHolidayPeriod.getDateStart() == null || newHolidayPeriod.getNumDays() == 0) {
                    UIHelper.showError("Поля для отпуска должны быть заполнены.");
                }
                else {
                    if (allNegotiationStatuses != null && allNegotiationStatuses.size() > 0) {
                        newHolidayPeriod.setNegotiationStatus(HolidayPeriodNegotiationStatusUtils.getNewStatusFromList(allNegotiationStatuses));
                    }
                    if (mainButtonClickListener != null) {
                        mainButtonClickListener.onAddSelectedPeriods(this);
                    }
                    newHolidayPeriod = new HolidayPeriod();
                    setNewHolidayPeriod(newHolidayPeriod);
                    //txtNumDays.clear();
                    //datePeriod.clear();
                    holidayPeriodBinder.readBean(newHolidayPeriod);
                    refreshDataGrid();
                    UIHelper.showNotification("Период отпуска успешно сохранен.");
                }
            } catch (ValidationException e) {
                UIHelper.showError("Невозможно сохранить период отпуска. Проверьте заполненность полей ввода, а также наличие сообщений об ошибках для полей.");
            }
        });

        addHolidayPeriodLayout.setColumnExpandRatio(0, 3);
        addHolidayPeriodLayout.setColumnExpandRatio(1, 1);
        addHolidayPeriodLayout.setColumnExpandRatio(2, 1);
        addHolidayPeriodLayout.setColumnExpandRatio(3, 1);
        addHolidayPeriodLayout.setColumnExpandRatio(4, 1);

        btnAddHolidayPeriod.setWidth("300px");
        btnAddHolidayPeriod.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        btnRemoveHolidayPeriods.addStyleName(ValoTheme.BUTTON_DANGER);
        btnRemoveHolidayPeriods.addClickListener(event -> {
            if (deleteButtonClickListener != null) {
                deleteButtonClickListener.onDeleteSelectedPeriods(this, grdHolidayPeriods.getSelectedItems());
                refreshDataGrid();
            }
            UIHelper.showNotification("Выбранные периоды отпуска успешно удалены.");
        });
        btnRemoveHolidayPeriods.setWidth("300px");
        btnRemoveHolidayPeriods.setEnabled(false);

        Label lblDatePeriod = new Label("Период отпуска:");

        datePeriod.addValueChangeListener(new EmployeeHolidayPeriodValueChangeListener());
        Label lblNumDays = new Label("Количество дней отпуска:");
        Button btnCheckCrossingDates = new Button("Проверить пересечения");
        btnCheckCrossingDates.setIcon(VaadinIcons.CALENDAR);
        btnCheckCrossingDates.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnCheckCrossingDates.setWidth("300px");
        btnCheckCrossingDates.addClickListener(clickEvent -> {
            if (checkCrossDatesButtonClickListener != null) {
                checkCrossDatesButtonClickListener.onClick(this);
            }
        });

        Label lblCrossingDates = new Label("Пересечения по датам отпусков:");
        //Label lblCrossingDatesList = new Label("Пересечений на текущий момент не обнаружено");
        lblCrossingDatesList.setContentMode(ContentMode.HTML);
        lblCrossingDatesList.setSizeFull();

        bindDatePeriodControlFields();

        btnSendForNegotiation.addClickListener(event -> {
            if (sendForNegotiationButtonClickListener != null) {
                sendForNegotiationButtonClickListener.onSubmitSelectedPeriodsForNegotiation(this, grdHolidayPeriods.getSelectedItems(), HolidayPeriodNegotiationStatusUtils.getNegotiatingStatusFromList(allNegotiationStatuses));
                refreshDataGrid();
            }
        });
        btnSendForNegotiation.setWidth("300px");
        btnSendForNegotiation.setEnabled(false);


        addHolidayPeriodLayout.addComponent(btnAddHolidayPeriod,0,0);
        addHolidayPeriodLayout.addComponent(lblDatePeriod, 1,0);
        addHolidayPeriodLayout.addComponent(datePeriod, 2,0);
        addHolidayPeriodLayout.addComponent(lblNumDays, 3,0);
        addHolidayPeriodLayout.addComponent(txtNumDays, 4,0);
        addHolidayPeriodLayout.addComponent(btnRemoveHolidayPeriods, 0,1);
        addHolidayPeriodLayout.addComponent(btnSendForNegotiation, 0,2);
        addHolidayPeriodLayout.addComponent(btnCheckCrossingDates, 0,3);
        addHolidayPeriodLayout.addComponent(lblCrossingDates, 0, 4, 4, 4);
        addHolidayPeriodLayout.addComponent(lblCrossingDatesList, 0, 5, 4, 5);
        return addHolidayPeriodLayout;
    }

    private void bindDatePeriodControlFields() {
        holidayPeriodBinder.forField(datePeriod)
                .withValidator(new HolidayPeriodDateValidator())
                .bind(HolidayPeriod::getDateStart, HolidayPeriod::setDateStart);

        holidayPeriodBinder.forField(txtNumDays)
                .withNullRepresentation("")
                .withValidator(new HolidayPeriodDayNumValidator())
                .bind(HolidayPeriod::getNumDaysAsString, HolidayPeriod::setNumDaysAsString);

    }

    public void addDeleteButtonClickListener(EmployeeHolidaysLayoutDeleteButtonClickListener deleteButtonClickListener) {
        this.deleteButtonClickListener = deleteButtonClickListener;
    }

    public EmployeeHolidaysLayoutDeleteButtonClickListener getDeleteButtonClickListener() {
        return this.deleteButtonClickListener;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<HolidayPeriodNegotiationStatus> getAllNegotiationStatuses() {
        return allNegotiationStatuses;
    }

    public void setAllNegotiationStatuses(List<HolidayPeriodNegotiationStatus> allNegotiationStatuses) {
        this.allNegotiationStatuses = allNegotiationStatuses;
    }

    public EmployeeHolidaysLayoutNegotiateSelectedPeriodsClickListener getNegotiateSelectedPeriodsClickListener() {
        return negotiateSelectedPeriodsClickListener;
    }

    public void setNegotiateSelectedPeriodsClickListener(EmployeeHolidaysLayoutNegotiateSelectedPeriodsClickListener negotiateSelectedPeriodsClickListener) {
        this.negotiateSelectedPeriodsClickListener = negotiateSelectedPeriodsClickListener;
    }

    public Map<Long, Button> getRejectSelectedPeriodsButtonsMap() {
        return rejectSelectedPeriodsButtonsMap;
    }

    public void setRejectSelectedPeriodsButtonsMap(Map<Long, Button> rejectSelectedPeriodsButtonsMap) {
        this.rejectSelectedPeriodsButtonsMap = rejectSelectedPeriodsButtonsMap;
    }

    public EmployeeHolidaysLayoutRejectSelectedPeriodsClickListener getRejectSelectedPeriodsClickListener() {
        return rejectSelectedPeriodsClickListener;
    }

    public void setRejectSelectedPeriodsClickListener(EmployeeHolidaysLayoutRejectSelectedPeriodsClickListener rejectSelectedPeriodsClickListener) {
        this.rejectSelectedPeriodsClickListener = rejectSelectedPeriodsClickListener;
    }

    public Map<Long, Set<EmployeeHolidayPeriod>> getPeriodsForRejectionMap() {
        return periodsForRejectionMap;
    }

    public void setPeriodsForRejectionMap(Map<Long, Set<EmployeeHolidayPeriod>> periodsForRejectionMap) {
        this.periodsForRejectionMap = periodsForRejectionMap;
    }

    public EmployeeHolidaysLayoutSendForNegotiationButtonClickListener getSendForNegotiationButtonClickListener() {
        return sendForNegotiationButtonClickListener;
    }

    public void setSendForNegotiationButtonClickListener(EmployeeHolidaysLayoutSendForNegotiationButtonClickListener sendForNegotiationButtonClickListener) {
        this.sendForNegotiationButtonClickListener = sendForNegotiationButtonClickListener;
    }

/*    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }*/

    class EmployeeHolidayPeriodValueChangeListener implements HasValue.ValueChangeListener<LocalDate> {
        @Override
        public void valueChange(HasValue.ValueChangeEvent<LocalDate> event) {
            if (event.isUserOriginated()) {
                //SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
                //String oldValueStr = event.getOldValue() != null ? sdf.format(event.getOldValue()) : "";
                //String newValueStr = event.getValue() != null ? sdf.format(event.getValue()) : "";
                //if (event != null && event.getOldValue() != null && event.getValue() != null) {
                //}
                //Notification.show("Value changed!");
                //Notification.show( String.format("Value changed! Old value: %s, New value: %s", oldValueStr, newValueStr));
            } else {
                //Notification.show("Application originated");
            }
        }
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public List<HolidayPeriod> getEmployeeHolidayPeriods() {
        return employeeHolidayPeriods;
    }

    public void setEmployeeHolidayPeriods(List<HolidayPeriod> employeeHolidayPeriods) {
        this.employeeHolidayPeriods = employeeHolidayPeriods;
    }

    public EmployeeHolidaysLayoutMainButtonClickListener getMainButtonClickListener() {
        return mainButtonClickListener;
    }

    public void addMainButtonClickListener(EmployeeHolidaysLayoutMainButtonClickListener mainButtonClickListener) {
        this.mainButtonClickListener = mainButtonClickListener;
    }

    public ButtonClickListener getCheckCrossDatesButtonClickListener() {
        return checkCrossDatesButtonClickListener;
    }

    public void setCheckCrossDatesButtonClickListener(ButtonClickListener checkCrossDatesButtonClickListener) {
        this.checkCrossDatesButtonClickListener = checkCrossDatesButtonClickListener;
    }

    public HolidayPeriod getNewHolidayPeriod() {
        return newHolidayPeriod;
    }

    public void setNewHolidayPeriod(HolidayPeriod newHolidayPeriod) {
        this.newHolidayPeriod = newHolidayPeriod;
        if (this.newHolidayPeriod != null && this.newHolidayPeriod.getNegotiationStatus() == null) {
            this.newHolidayPeriod.setNegotiationStatus(HolidayPeriodNegotiationStatusUtils.getNewStatusFromList(allNegotiationStatuses));
        }
    }

    @Override
    public void refreshDataGrid() {
        super.refreshDataGrid();

        //TODO: customize your logic here
        grdHolidayPeriods.setItems(employeeHolidayPeriods);
    }

    @Override
    public void postConstructLayout() {

    }

    public Map<Team, Collection<EmployeeHolidayPeriod>> getManagedTeamMembersHolidays() {
        return managedTeamMembersHolidays;
    }

    public void setManagedTeamMembersHolidays(Map<Team, Collection<EmployeeHolidayPeriod>> managedTeamMembersHolidays) {
        this.managedTeamMembersHolidays = managedTeamMembersHolidays;
    }

    public void setCrossingDatesListValue(String message, boolean isCrossingDatesFixed) {
        if (isCrossingDatesFixed) {
            lblCrossingDatesList.setValue(NO_CROSSING_DATES_MESSAGE);
        } else {
            lblCrossingDatesList.setValue(message);
        }
/*        if (!isCrossingDates && !isCrossingDatesFixed) {
            lblCrossingDatesList.setValue(message);
            isCrossingDates = true;
        } else {
            if (!isCrossingDatesFixed) {
                String currentValue = lblCrossingDatesList.getValue();
                String newValue = currentValue + "<br>" + message;
                lblCrossingDatesList.setValue(newValue);
            } else {
                lblCrossingDatesList.setValue(NO_CROSSING_DATES_MESSAGE);
            }
        }*/

    }
}
