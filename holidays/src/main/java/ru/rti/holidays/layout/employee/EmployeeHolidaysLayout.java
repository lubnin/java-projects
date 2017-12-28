package ru.rti.holidays.layout.employee;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.dialogs.ConfirmDialog;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriodCrossing;
import ru.rti.holidays.component.grid.comparator.EmployeeHolidayPeriodDateColumnComparator;
import ru.rti.holidays.component.grid.comparator.HolidayPeriodDateColumnComparator;
import ru.rti.holidays.converter.StringValueProvider;
import ru.rti.holidays.entity.*;
import ru.rti.holidays.layout.base.BaseLayout;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.base.StandardBaseLayoutDrawer;
import ru.rti.holidays.layout.base.behaviour.ActionPerformedListener;
import ru.rti.holidays.layout.base.behaviour.ButtonClickListener;
import ru.rti.holidays.layout.base.behaviour.ButtonClickResult;
import ru.rti.holidays.layout.behaviour.*;
import ru.rti.holidays.style.GridEmployeeHolidayPeriodCellStyleGenerator;
import ru.rti.holidays.style.GridHolidayPeriodCellStyleGenerator;
import ru.rti.holidays.utility.*;
import ru.rti.holidays.validator.HolidayPeriodDateValidator;
import ru.rti.holidays.validator.HolidayPeriodDayNumValidator;

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
    private Map<Long, Button> showTeamCrossingPeriodsButtonsMap = new HashMap<>();
    private Map<Long, Set<EmployeeHolidayPeriod>> periodsForNegotiationMap = new HashMap<>();
    private Map<Long, Set<EmployeeHolidayPeriod>> periodsForRejectionMap = new HashMap<>();
    //private Map<Long, Set<EmployeeHolidayPeriod>> periodsForCrossingsSearchMap = new HashMap<>();

    private ButtonClickListener<EmployeeHolidayPeriodCrossing> periodsForCrossingsSearchClickListener;

    private List<HolidayPeriodNegotiationStatus> allNegotiationStatuses;
    private Button btnRemoveHolidayPeriods = new Button("Удалить выбранные", VaadinIcons.DEL);
    private Button btnSendForNegotiation = new Button("Отправить на согласование", VaadinIcons.USERS);
    private Button btnCheckCrossingDates = new Button("Проверить пересечения", VaadinIcons.ARROWS_CROSS);

    private List<HolidayPeriod> employeeHolidayPeriods;
    private DateField datePeriod = new DateField();
    private TextField txtNumDays = new TextField();
    //private EmployeeHolidaysLayoutMainButtonClickListener mainButtonClickListener;
    private EmployeeHolidaysLayoutDeleteButtonClickListener deleteButtonClickListener;



    //private EmployeeHolidaysLayoutNegotiateSelectedPeriodsClickListener negotiateSelectedPeriodsClickListener;
    //private EmployeeHolidaysLayoutRejectSelectedPeriodsClickListener rejectSelectedPeriodsClickListener;
    private ButtonClickListener<EmployeeHolidayPeriod> negotiateSelectedPeriodsClickListener;
    private ButtonClickListener<EmployeeHolidayPeriod> rejectSelectedPeriodsClickListener;
    private EmployeeHolidaysLayoutSendForNegotiationButtonClickListener sendForNegotiationButtonClickListener;

    private ActionPerformedListener<HolidayPeriodNegotiationHistory> addNegotiationHistoryActionListener;
    private ButtonClickListener<EmployeeHolidayPeriodCrossing> checkCrossingDatesButtonClickListener;
    private HolidayPeriod newHolidayPeriod = new HolidayPeriod();
    private Binder<HolidayPeriod> holidayPeriodBinder = new Binder<>();
    private EmployeeHolidayPeriodsCrossingDatesLayout employeeHolidayPeriodsCrossingDatesLayout =
            new EmployeeHolidayPeriodsCrossingDatesLayout(false, false);

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
        Panel pnlTeamMembersHolidays = new Panel("Отпуска Ваших сотрудников:");
        pnlTeamMembersHolidays.setSizeFull();

        VerticalLayout pnlTeamMembersHolidaysContent = new VerticalLayout();

        BaseVerticalLayout allTeamsHolidaysLayout = new BaseVerticalLayout();
        allTeamsHolidaysLayout.setSizeFull();
        allTeamsHolidaysLayout.setSpacing(false);
        allTeamsHolidaysLayout.setMargin(false);

        ComboBox<Team> cboSelectedManagedTeam = new ComboBox<>("Выберите команду под управлением:");
        cboSelectedManagedTeam.setItems(managedTeamMembersHolidays.keySet());
        cboSelectedManagedTeam.setItemCaptionGenerator(Team::getTeamName);
        cboSelectedManagedTeam.setTextInputAllowed(false);
        cboSelectedManagedTeam.setEmptySelectionCaption("Команда не выбрана");
        cboSelectedManagedTeam.addValueChangeListener(valueChangeEvent -> {
            allTeamsHolidaysLayout.removeAllComponents();
            Team selectedTeam = valueChangeEvent.getValue();
            if (selectedTeam != null) {
                TeamHolidaysLayout teamHolidaysLayout = new TeamHolidaysLayout(false, true);

                teamHolidaysLayout.setNegotiateSelectedPeriodsButtonClickListener(negotiateSelectedPeriodsClickListener);
                teamHolidaysLayout.setRejectSelectedPeriodsButtonClickListener(rejectSelectedPeriodsClickListener);

                Collection<EmployeeHolidayPeriod> teamHolidayPeriods = managedTeamMembersHolidays.get(selectedTeam);
                teamHolidaysLayout.setTeam(selectedTeam);
                teamHolidaysLayout.setTeamMembersHolidayPeriods(teamHolidayPeriods);
                new StandardBaseLayoutDrawer(allTeamsHolidaysLayout, teamHolidaysLayout).drawLayout();
            }
        });

        cboSelectedManagedTeam.setSelectedItem(managedTeamMembersHolidays.keySet().iterator().next());

        pnlTeamMembersHolidaysContent.addComponent(cboSelectedManagedTeam);
        pnlTeamMembersHolidaysContent.addComponent(allTeamsHolidaysLayout);
        pnlTeamMembersHolidays.setContent(pnlTeamMembersHolidaysContent);

        addComponent(pnlTeamMembersHolidays);
    }

    public void constructLayout() {
        try {
            boolean isNoTeam = CommonUtils.checkIfEmpty(employee.getTeam());
            boolean isNoRole = CommonUtils.checkIfEmpty(employee.getProjectRole());

            Label lblEmployeeName = new Label("ФИО: " + HtmlUtils.bold(employee.getFullName()), ContentMode.HTML);


            Label lblProjectRole = new Label("Проектная роль: " + HtmlUtils.newBuilder()
                    .withMessage(isNoTeam ?
                            HtmlUtils.newBuilder()
                                    .withMessage("Роль не назначена, обратитесь к администратору Системы.")
                                    .wrapSpan(GlobalConstants.CSS_NEGOTIATION_STATUS_TYPE_REJECTED)
                                    .build()
                                    .getHTML()
                            :
                            employee.getProjectRoleAsString()
                    ).wrapBold()
                    .build()
                    .getHTML(), ContentMode.HTML);

            //Label lblProjectRoleType = new Label("Тип проектной роли: <b>" + employee.getProjectRoleSpecialTypeAsString() + "</b>", ContentMode.HTML);
            Label lblTeam = null;

            if (!isNoRole) {
                if (ProjectRole.ProjectRoleSpecialType.getRolesWithTeamManagementAbility().contains(employee.getProjectRole().getProjectRoleSpecialType())) {
                    Set<Team> managedTeams = employee.getManagedTeams();
                    lblTeam = new Label("Команды под руководством: <b>" + TeamUtils.getDelimitedTeamsString(managedTeams, ", ") + "</b>", ContentMode.HTML);
                } else {
                    lblTeam = new Label("Ваша команда: <b>" + employee.getTeamNameAsString() + "</b>", ContentMode.HTML);
                }
            } else {
                lblTeam = new Label(HtmlUtils.newBuilder()
                        .withMessage("Ваша роль не определена. Обратитесь к администратору Системы.")
                        .wrapSpan(GlobalConstants.CSS_NEGOTIATION_STATUS_TYPE_REJECTED)
                        .build()
                        .getHTML(), ContentMode.HTML);

            }

            Panel pnlPanelHolidays = new Panel(getPanelName());
            pnlPanelHolidays.setSizeFull();

            VerticalLayout pnlHolidaysLayout = new VerticalLayout();

            grdHolidayPeriods.addStyleName("rti-grid-comments");
            grdHolidayPeriods.setItems(employeeHolidayPeriods);
            StyleGenerator<HolidayPeriod> styleGenerator = new GridHolidayPeriodCellStyleGenerator();
            grdHolidayPeriods.addColumn(HolidayPeriod::getDateStartAsString).setCaption("Дата начала отпуска").setStyleGenerator(styleGenerator).setComparator(new HolidayPeriodDateColumnComparator());
            grdHolidayPeriods.addColumn(HolidayPeriod::getNumDays).setCaption("Количество дней отпуска").setStyleGenerator(styleGenerator);
            grdHolidayPeriods.addColumn(HolidayPeriod::getNegotiationStatusAsString).setCaption("Статус согласования").setStyleGenerator(styleGenerator);
            grdHolidayPeriods.addColumn(HolidayPeriod::getHolidayPeriodNegotiationHistoryComment).setCaption("Комментарий").setStyleGenerator(styleGenerator).setRenderer(new StringValueProvider(), new HtmlRenderer());
            //grdHolidayPeriods.addColumn(HolidayPeriod::isCrossingDatesAsString).setCaption("Пересечения").setStyleGenerator(styleGenerator);
            grdHolidayPeriods.setHeightByRows(5);
            grdHolidayPeriods.setWidth("100%");
            grdHolidayPeriods.setColumnResizeMode(ColumnResizeMode.ANIMATED);
            //grdHolidayPeriods.setDetailsGenerator(holidayPeriod -> {
            //    return "Details</br>Details!";
            //});
            //grdHolidayPeriods.setDe
            grdHolidayPeriods.setDescriptionGenerator(holidayPeriod -> {
                return "ID: " + holidayPeriod.getId() + "\r\nДата начала: " + holidayPeriod.getDateStartAsString() + "\r\n"+ holidayPeriod.getHolidayPeriodNegotiationHistoryComment(false);
            });

            MultiSelectionModel<HolidayPeriod> selectionModel = (MultiSelectionModel<HolidayPeriod>)grdHolidayPeriods.setSelectionMode(Grid.SelectionMode.MULTI);

            selectionModel.addMultiSelectionListener(event -> {
                Set<HolidayPeriod> selectedItems = event.getAllSelectedItems();

                boolean isAtLeastOneItemSelected = selectedItems != null && selectedItems.size() > 0;

                boolean isBtnRemoveSelectedHolidayPeriodsEnabled = false;
                boolean isBtnSendForNegotiationEnabled = false;


                isBtnRemoveSelectedHolidayPeriodsEnabled = isAtLeastOneItemSelected;
                isBtnSendForNegotiationEnabled = isAtLeastOneItemSelected;

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
                //enableCheckCrossingsButton(isAtLeastOneItemSelected);
                btnRemoveHolidayPeriods.setEnabled(isBtnRemoveSelectedHolidayPeriodsEnabled);
                btnSendForNegotiation.setEnabled(isBtnSendForNegotiationEnabled);
            });

            pnlHolidaysLayout.addComponent(getTopButtonsPanelLayout());
            pnlHolidaysLayout.addComponent(grdHolidayPeriods);
            pnlHolidaysLayout.addComponent(getBottomButtonsControlPanelLayout());
            pnlHolidaysLayout.setSizeFull();
            pnlPanelHolidays.setContent(pnlHolidaysLayout);


            //EmployeeMenuBarLayout employeeMenuBarLayout = new EmployeeMenuBarLayout(false, false);
            //employeeMenuBarLayout.setMainMenuItemVisible(false);
            //new StandardBaseLayoutDrawer(this, employeeMenuBarLayout).drawLayout();
            EmployeeHorizontalButtonMenuBarLayout employeeHorizontalButtonMenuBarLayout = new EmployeeHorizontalButtonMenuBarLayout(false, false);
            new StandardBaseLayoutDrawer(this, employeeHorizontalButtonMenuBarLayout).drawLayout();


            addComponent(lblEmployeeName);
            addComponent(lblProjectRole);
            //addComponent(lblProjectRoleType);
            addComponent(lblTeam);
            addComponent(pnlPanelHolidays);

            //TODO: temporary solution
            //employeeHolidayPeriodsCrossingDatesLayout.setCheckCrossingDatesButtonClickListener(checkCrossingDatesButtonClickListener);
            //employeeHolidayPeriodsCrossingDatesLayout.setSubmitterEmployeeFullNameColumnVisible(false);
            //new StandardBaseLayoutDrawer(this, employeeHolidayPeriodsCrossingDatesLayout).drawLayout();

            if (employee.isManager()) {
                addTeamMembersHolidaysTables();
            }

            setSizeFull();
        } catch (Exception e) {
            handleException(e, e.getMessage());
        }

    }

    /**
     * Adds panel with holiday period start, number of holiday period days & button 'Add'
     * @return
     */
    private GridLayout getTopButtonsPanelLayout() {
        GridLayout topButtonsPanelLayout = new GridLayout(5, 1);
        topButtonsPanelLayout.setSizeFull();
        topButtonsPanelLayout.setSpacing(true);
        topButtonsPanelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        topButtonsPanelLayout.setColumnExpandRatio(0, 1);
        topButtonsPanelLayout.setColumnExpandRatio(1,1);
        topButtonsPanelLayout.setColumnExpandRatio(2,1);
        topButtonsPanelLayout.setColumnExpandRatio(3,1);
        topButtonsPanelLayout.setColumnExpandRatio(4,6);

        Label lblDatePeriod = new Label("Введите дату начала отпуска:");

        datePeriod.addValueChangeListener(new EmployeeHolidayPeriodValueChangeListener());
        Label lblNumDays = new Label("Введите количество дней отпуска:");

        bindDatePeriodControlFields();

        Button btnAddHolidayPeriod = new Button("Добавить период отпуска", VaadinIcons.CHECK);
        btnAddHolidayPeriod.addClickListener(event -> {
            try {
                holidayPeriodBinder.writeBean(newHolidayPeriod);
                if (newHolidayPeriod == null || newHolidayPeriod.getDateStart() == null || newHolidayPeriod.getNumDays() == null || newHolidayPeriod.getNumDays() <= 0) {
                    UIHelper.showError("Поля для отпуска должны быть заполнены.");
                } else if (checkForOwnCrossingDates(newHolidayPeriod)) {
                    UIHelper.showError("Добавляемый период отпуска пересекается с Вашими ранее добавленными периодами отпуска! Период не будет добавлен.");
                }
                else {
                    if (allNegotiationStatuses != null && allNegotiationStatuses.size() > 0) {
                        newHolidayPeriod.setNegotiationStatus(HolidayPeriodNegotiationStatusUtils.getNewStatusFromList(allNegotiationStatuses));
                    }


                    LocalDate holidayDateStart = newHolidayPeriod.getDateStart();
                    LocalDate nowPlus14Days = LocalDate.now().plusDays(14);

                    boolean isNowPlus14Days = false;
                    HolidayPeriodNegotiationHistory hpNegHistory = new HolidayPeriodNegotiationHistory();

                    if (holidayDateStart.isBefore(nowPlus14Days)) {
                        isNowPlus14Days = true;
                        ConfirmDialog.show(UI.getCurrent(),
                                "Внимание",
                                "Вы действительно хотите добавить период отпуска, дата начала которого отстоит от текущей менее, чем на 14 дней?",
                                "Да",
                                "Нет",
                                new ConfirmDialog.Listener() {
                                    @Override
                                    public void onClose(ConfirmDialog confirmDialog) {
                                        if (confirmDialog.isConfirmed()) {
                                            //HolidayPeriodNegotiationHistory hpNegHistory = new HolidayPeriodNegotiationHistory();
                                            hpNegHistory.setComment("Дата начала отпуска отстоит от даты добавления отпуска сотрудником менее, чем 14 дней (2 недели).");
                                            hpNegHistory.setOldStatus(newHolidayPeriod.getNegotiationStatusAsString());
                                            hpNegHistory.setNewStatus(newHolidayPeriod.getNegotiationStatusAsString());

                                            newHolidayPeriod.setEmployee(employee);

                                            fireSaveButtonClickedEvent(newHolidayPeriod);
                                            hpNegHistory.setHolidayPeriod(newHolidayPeriod);
                                            fireSaveHolidayPeriodHistoryActionPerformedEvent(hpNegHistory);

                                            //TODO: temporary solution
                                            //fireCheckCrossingDatesButtonClickedEvent();

                                            clearAllControls();

                                            updateControlsFromBeanState();
                                            refreshDataGrid();
                                            UIHelper.showNotification("Период отпуска успешно сохранен.");
                                        }
                                    }
                                });
                    } else {
                        hpNegHistory.setComment("Сотрудник добавил период отпуска.");
                        hpNegHistory.setOldStatus(newHolidayPeriod.getNegotiationStatusAsString());
                        hpNegHistory.setNewStatus(newHolidayPeriod.getNegotiationStatusAsString());
                    }

                    if (!isNowPlus14Days) {
                        newHolidayPeriod.setEmployee(employee);

                        fireSaveButtonClickedEvent(newHolidayPeriod);
                        hpNegHistory.setHolidayPeriod(newHolidayPeriod);
                        fireSaveHolidayPeriodHistoryActionPerformedEvent(hpNegHistory);

                        //TODO: temporary solution
                        //fireCheckCrossingDatesButtonClickedEvent();

                        clearAllControls();

                        updateControlsFromBeanState();
                        refreshDataGrid();
                        UIHelper.showNotification("Период отпуска успешно сохранен.");
                    }
                }
            } catch (ValidationException e) {
                UIHelper.showError("Невозможно сохранить период отпуска. Проверьте заполненность полей ввода, а также наличие сообщений об ошибках для полей.");
            }
        });

        btnAddHolidayPeriod.setWidth("300px");
        btnAddHolidayPeriod.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        topButtonsPanelLayout.addComponent(lblDatePeriod, 0,0);
        topButtonsPanelLayout.addComponent(datePeriod, 1,0);
        topButtonsPanelLayout.addComponent(lblNumDays, 2,0);
        topButtonsPanelLayout.addComponent(txtNumDays, 3,0);
        topButtonsPanelLayout.addComponent(btnAddHolidayPeriod,4,0);

        topButtonsPanelLayout.setComponentAlignment(btnAddHolidayPeriod, Alignment.MIDDLE_LEFT);

        return topButtonsPanelLayout;
    }

    @Override
    public void updateControlsFromBeanState() {
        holidayPeriodBinder.readBean(newHolidayPeriod);
    }

    @Override
    public void setNewBeanValue(DBEntity newBeanValue) {
        if (newBeanValue instanceof HolidayPeriod) {
            this.newHolidayPeriod = (HolidayPeriod)newBeanValue;
            if (this.newHolidayPeriod != null && this.newHolidayPeriod.getNegotiationStatus() == null) {
                this.newHolidayPeriod.setNegotiationStatus(HolidayPeriodNegotiationStatusUtils.getNewStatusFromList(allNegotiationStatuses));
            }
        }
    }

    private void fireSaveHolidayPeriodHistoryActionPerformedEvent(HolidayPeriodNegotiationHistory holidayPeriodNegotiationHistory) {
        if (addNegotiationHistoryActionListener != null) {
            addNegotiationHistoryActionListener.onActionPerformed(this, holidayPeriodNegotiationHistory);
        }
    }
    private void fireCheckCrossingDatesButtonClickedEvent() {
        if (checkCrossingDatesButtonClickListener != null) {
            ButtonClickResult<EmployeeHolidayPeriodCrossing> buttonClickResult = checkCrossingDatesButtonClickListener.onClick(employeeHolidayPeriodsCrossingDatesLayout);
            if (buttonClickResult.isResult()) {
                Collection<EmployeeHolidayPeriodCrossing> resultItems = buttonClickResult.getResultItems();
                employeeHolidayPeriodsCrossingDatesLayout.setHolidayPeriodCrossings(resultItems);
                employeeHolidayPeriodsCrossingDatesLayout.refreshDataGrid();
            }
        }
    }

/*    private void fireMainButtonClickedEvent() {
        if (mainButtonClickListener != null) {
            mainButtonClickListener.onAddSelectedPeriods(this);
        }
    }*/

    private boolean checkForOwnCrossingDates(HolidayPeriod addedHolidayPeriod) {
        Date addedHPDateStart = DateUtils.asDate(addedHolidayPeriod.getDateStart());
        Long addedHPNumDays = addedHolidayPeriod.getNumDays();
        Date addedHPDateEnd = DateUtils.addDays(addedHPDateStart, addedHPNumDays);

        DataProvider<HolidayPeriod, ?> dataProvider = grdHolidayPeriods.getDataProvider();
        if (dataProvider != null && dataProvider instanceof ListDataProvider) {
            ListDataProvider<HolidayPeriod> allItemsDataProvider = (ListDataProvider<HolidayPeriod>)dataProvider;
            Collection<HolidayPeriod> allSavedHolidayPeriods = allItemsDataProvider.getItems();
            if (allSavedHolidayPeriods == null || allSavedHolidayPeriods.size() == 0) {
                return false;
            }

            for (HolidayPeriod hp : allSavedHolidayPeriods) {
                Long curHPNumDays = hp.getNumDays();
                Date curHPDateStart = DateUtils.asDate(hp.getDateStart());
                Date curHPDateEnd = DateUtils.addDays(curHPDateStart, curHPNumDays);

                if (DateUtils.isIntersectionBetweenDates(addedHPDateStart, addedHPDateEnd, curHPDateStart, curHPDateEnd)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Bottom layout with buttons 'Remove', 'Send for Negotiation', 'Check for crossings'
     * @return
     */
    protected GridLayout getBottomButtonsControlPanelLayout() {

        GridLayout bottomButtonsControlPanelLayout = new GridLayout(3, 1);
        //GridLayout bottomButtonsControlPanelLayout = new GridLayout(4, 1);
        //bottomButtonsControlPanelLayout.setSizeFull();
        bottomButtonsControlPanelLayout.setSpacing(true);
        bottomButtonsControlPanelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

/*        bottomButtonsControlPanelLayout.setColumnExpandRatio(0,1);
        bottomButtonsControlPanelLayout.setColumnExpandRatio(1, 1);
        bottomButtonsControlPanelLayout.setColumnExpandRatio(2,1);
        bottomButtonsControlPanelLayout.setColumnExpandRatio(3, 10);*/

        bottomButtonsControlPanelLayout.setColumnExpandRatio(0,1);
        bottomButtonsControlPanelLayout.setColumnExpandRatio(1, 1);
        bottomButtonsControlPanelLayout.setColumnExpandRatio(2,2);


        //enableCheckCrossingsButton(false);

        btnRemoveHolidayPeriods.addStyleName(ValoTheme.BUTTON_DANGER);
        btnRemoveHolidayPeriods.addClickListener(event -> {
            if (deleteButtonClickListener != null) {
                deleteButtonClickListener.onDeleteSelectedPeriods(this, grdHolidayPeriods.getSelectedItems());
                refreshDataGrid();
            }
            //TODO: temporary solution
            //fireCheckCrossingDatesButtonClickedEvent();
            UIHelper.showNotification("Выбранные периоды отпуска успешно удалены.");
        });
        btnRemoveHolidayPeriods.setWidth("300px");
        btnRemoveHolidayPeriods.setEnabled(false);

        btnSendForNegotiation.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSendForNegotiation.addClickListener(event -> {
            if (sendForNegotiationButtonClickListener != null) {
                sendForNegotiationButtonClickListener.onSubmitSelectedPeriodsForNegotiation(this, grdHolidayPeriods.getSelectedItems(), HolidayPeriodNegotiationStatusUtils.getNegotiatingStatusFromList(allNegotiationStatuses));
                refreshDataGrid();
            }
        });
        btnSendForNegotiation.setWidth("300px");
        btnSendForNegotiation.setEnabled(false);

        //TODO: check crossings only for selected items. commented for now
/*      btnCheckCrossingDates = new Button("Проверить пересечения");
        btnCheckCrossingDates.setIcon(VaadinIcons.CALENDAR);
        btnCheckCrossingDates.addStyleName(GlobalConstants.CSS_RTI_BUTTON_ORANGE);
        btnCheckCrossingDates.setWidth("300px");
        btnCheckCrossingDates.addClickListener(clickEvent -> {
            //TODO: check crossings only for selected items. commented for now
            fireCheckCrossingDatesButtonClickedEvent();
        });*/

/*        bottomButtonsControlPanelLayout.addComponent(btnCheckCrossingDates, 0,0);
        bottomButtonsControlPanelLayout.addComponent(btnSendForNegotiation, 1,0);
        bottomButtonsControlPanelLayout.addComponent(btnRemoveHolidayPeriods, 2,0);
        bottomButtonsControlPanelLayout.addComponent(new Label(""), 3,0);*/

        bottomButtonsControlPanelLayout.addComponent(btnSendForNegotiation, 0,0);
        bottomButtonsControlPanelLayout.addComponent(btnRemoveHolidayPeriods, 1,0);
        //bottomButtonsControlPanelLayout.addComponent(new Label(""), 2,0);

        return bottomButtonsControlPanelLayout;
    }

    public void enableCheckCrossingsButton(boolean enable) {
        btnCheckCrossingDates.setEnabled(enable);
    }

    @Override
    public void clearAllControls() {
        setNewBeanValue(new HolidayPeriod());
        updateControlsFromBeanState();
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

//    public EmployeeHolidaysLayoutNegotiateSelectedPeriodsClickListener getNegotiateSelectedPeriodsClickListener() {
//        return negotiateSelectedPeriodsClickListener;
//    }
//
//    public void setNegotiateSelectedPeriodsClickListener(EmployeeHolidaysLayoutNegotiateSelectedPeriodsClickListener negotiateSelectedPeriodsClickListener) {
//        this.negotiateSelectedPeriodsClickListener = negotiateSelectedPeriodsClickListener;
//    }

    public Map<Long, Button> getRejectSelectedPeriodsButtonsMap() {
        return rejectSelectedPeriodsButtonsMap;
    }

    public void setRejectSelectedPeriodsButtonsMap(Map<Long, Button> rejectSelectedPeriodsButtonsMap) {
        this.rejectSelectedPeriodsButtonsMap = rejectSelectedPeriodsButtonsMap;
    }

//    public EmployeeHolidaysLayoutRejectSelectedPeriodsClickListener getRejectSelectedPeriodsClickListener() {
//        return rejectSelectedPeriodsClickListener;
//    }
//
//    public void setRejectSelectedPeriodsClickListener(EmployeeHolidaysLayoutRejectSelectedPeriodsClickListener rejectSelectedPeriodsClickListener) {
//        this.rejectSelectedPeriodsClickListener = rejectSelectedPeriodsClickListener;
//    }

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

    public ActionPerformedListener<HolidayPeriodNegotiationHistory> getAddNegotiationHistoryActionListener() {
        return addNegotiationHistoryActionListener;
    }

    public void setAddNegotiationHistoryActionListener(ActionPerformedListener<HolidayPeriodNegotiationHistory> addNegotiationHistoryActionListener) {
        this.addNegotiationHistoryActionListener = addNegotiationHistoryActionListener;
    }

    public ButtonClickListener<EmployeeHolidayPeriodCrossing> getPeriodsForCrossingsSearchClickListener() {
        return periodsForCrossingsSearchClickListener;
    }

    public void setPeriodsForCrossingsSearchClickListener(ButtonClickListener<EmployeeHolidayPeriodCrossing> periodsForCrossingsSearchClickListener) {
        this.periodsForCrossingsSearchClickListener = periodsForCrossingsSearchClickListener;
    }

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

    public ButtonClickListener<EmployeeHolidayPeriodCrossing> getCheckCrossingDatesButtonClickListener() {
        return checkCrossingDatesButtonClickListener;
    }

    public void setCheckCrossingDatesButtonClickListener(ButtonClickListener<EmployeeHolidayPeriodCrossing> checkCrossingDatesButtonClickListener) {
        this.checkCrossingDatesButtonClickListener = checkCrossingDatesButtonClickListener;
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

    public ButtonClickListener<EmployeeHolidayPeriod> getNegotiateSelectedPeriodsClickListener() {
        return negotiateSelectedPeriodsClickListener;
    }

    public void setNegotiateSelectedPeriodsClickListener(ButtonClickListener<EmployeeHolidayPeriod> negotiateSelectedPeriodsClickListener) {
        this.negotiateSelectedPeriodsClickListener = negotiateSelectedPeriodsClickListener;
    }

    public ButtonClickListener<EmployeeHolidayPeriod> getRejectSelectedPeriodsClickListener() {
        return rejectSelectedPeriodsClickListener;
    }

    public void setRejectSelectedPeriodsClickListener(ButtonClickListener<EmployeeHolidayPeriod> rejectSelectedPeriodsClickListener) {
        this.rejectSelectedPeriodsClickListener = rejectSelectedPeriodsClickListener;
    }
}
