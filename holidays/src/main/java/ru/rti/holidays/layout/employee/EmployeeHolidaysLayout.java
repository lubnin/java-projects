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
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.behaviour.EmployeeHolidaysLayoutDeleteButtonClickListener;
import ru.rti.holidays.layout.behaviour.EmployeeHolidaysLayoutMainButtonClickListener;
import ru.rti.holidays.utility.TeamUtils;
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

    //private String projectRoleName;
    //private String employeeFullName;
    private String panelName;

    Grid<HolidayPeriod> grdHolidayPeriods = new Grid<>();
    Map<Team, Set<EmployeeHolidayPeriod>> managedTeamMembersHolidays = null;

    Button btnRemoveHolidayPeriods = new Button("Удалить выбранные", VaadinIcons.DEL);

    private List<HolidayPeriod> employeeHolidayPeriods;


    private EmployeeHolidaysLayoutMainButtonClickListener mainButtonClickListener;
    //private RefreshGridDataListener refreshGridDataListener;
    private EmployeeHolidaysLayoutDeleteButtonClickListener deleteButtonClickListener;
    private HolidayPeriod newHolidayPeriod;
    private Binder<HolidayPeriod> holidayPeriodBinder = new Binder<>();

    public EmployeeHolidaysLayout(Employee employee) {
        //this.setEmployeeFullName(employeeFullName);
        //this.setProjectRoleName(projectRoleName);
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

                Set<EmployeeHolidayPeriod> teamMembersHolidayPeriods = null;
                if(managedTeamMembersHolidays != null && managedTeamMembersHolidays.size() > 0) {
                    teamMembersHolidayPeriods = managedTeamMembersHolidays.get(team);
                } else {
                    //TODO: throw exception here
                    teamMembersHolidayPeriods = new HashSet<>();
                }

                grdTeamMembersHolidayPeriods.setItems(teamMembersHolidayPeriods);
                grdTeamMembersHolidayPeriods.setHeightByRows(15);
                grdTeamMembersHolidayPeriods.setWidth("100%");
                grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getEmployeeFullName).setCaption("ФИО сотрудника");
                grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getEmployeeRoleName).setCaption("Роль на проекте");
                grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getDateStartAsString).setCaption("Дата начала отпуска");
                grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getNumDays).setCaption("Количество дней отпуска");
                grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getHolidayPeriodNegotiationStatus).setCaption("Статус согласования");
                teamMembersHolidaysLayout.addComponent(lblCurrentTeamName);
                teamMembersHolidaysLayout.addComponent(grdTeamMembersHolidayPeriods);
            });
        }

        //


        teamMembersHolidaysLayout.setSizeFull();
        pnlPanelTeamMembersHolidays.setContent(teamMembersHolidaysLayout);
        addComponent(pnlPanelTeamMembersHolidays);
    }

    public void constructLayout() {
        try {
            Label lblEmployeeName = new Label("ФИО: <b>" + employee.getFullName() + "</b>", ContentMode.HTML);
            Label lblProjectRole = new Label("Проектная роль: <b>" + employee.getProjectRoleAsString() + "</b>", ContentMode.HTML);
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
            grdHolidayPeriods.addColumn(HolidayPeriod::getDateStartAsString).setCaption("Дата начала отпуска");
            grdHolidayPeriods.addColumn(HolidayPeriod::getNumDays).setCaption("Количество дней отпуска");
            grdHolidayPeriods.setHeightByRows(3);
            grdHolidayPeriods.setWidth("100%");

            MultiSelectionModel<HolidayPeriod> selectionModel = (MultiSelectionModel<HolidayPeriod>)grdHolidayPeriods.setSelectionMode(Grid.SelectionMode.MULTI);

            selectionModel.addMultiSelectionListener(event -> {
                Set<HolidayPeriod> selectedItems = event.getAllSelectedItems();
                btnRemoveHolidayPeriods.setEnabled(selectedItems != null && selectedItems.size() > 0);
            });

            pnlHolidaysLayout.addComponent(grdHolidayPeriods);
            pnlHolidaysLayout.addComponent(addControlsPanel());
            pnlHolidaysLayout.setSizeFull();
            pnlPanelHolidays.setContent(pnlHolidaysLayout);

            addComponent(lblEmployeeName);
            addComponent(lblProjectRole);
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
        GridLayout addHolidayPeriodLayout = new GridLayout(5, 2);
        addHolidayPeriodLayout.setSpacing(true);
        addHolidayPeriodLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        Button btnAddHolidayPeriod = new Button("Добавить период отпуска", VaadinIcons.CHECK);
        btnAddHolidayPeriod.addClickListener(event -> {
            try {
                holidayPeriodBinder.writeBean(newHolidayPeriod);

                log.info(String.format("DateStart = %s", newHolidayPeriod.getDateStart()));
                log.info(String.format("Num Days = %s", newHolidayPeriod.getNumDays()));

                if (newHolidayPeriod == null || newHolidayPeriod.getDateStart() == null || newHolidayPeriod.getNumDays() == 0) {
                    Notification errorNotification = new Notification("Ошибка: Вы должны ввести данные в поля для Вашего отпуска!", Notification.Type.ERROR_MESSAGE);
                    errorNotification.setDescription("Поля для отпуска должны быть заполнены");
                    errorNotification.setDelayMsec(5000);
                    errorNotification.setPosition(Position.TOP_RIGHT);
                    errorNotification.show(Page.getCurrent());
                } else {
                    if (mainButtonClickListener != null) {
                        mainButtonClickListener.performClick(this);
                    }
                    //refreshGridData();
                    refreshDataGrid();
                    Notification.show("Период отпуска успешно сохранен");
                }
            } catch (ValidationException e) {
                Notification.show("Невозможно сохранить период отпуска. Проверьте поля ввода на наличие сообщений об ошибках.");
            }


        });

        btnAddHolidayPeriod.addStyleName(ValoTheme.BUTTON_FRIENDLY);


        btnRemoveHolidayPeriods.addStyleName(ValoTheme.BUTTON_DANGER);
        btnRemoveHolidayPeriods.addClickListener(event -> {
            if (deleteButtonClickListener != null) {
                deleteButtonClickListener.performDelete(this, grdHolidayPeriods.getSelectedItems());
                //refreshGridData();
                refreshDataGrid();
            }

            Notification.show("Выбранные периоды отпуска успешно удалены");

        });
        btnRemoveHolidayPeriods.setWidth("100%");
        btnRemoveHolidayPeriods.setEnabled(false);

        Label lblDatePeriod = new Label("Период отпуска:");
        DateField datePeriod = new DateField();

        datePeriod.addValueChangeListener(new EmployeeHolidayPeriodValueChangeListener());

        Label lblNumDays = new Label("Количество дней отпуска:");
        TextField txtNumDays = new TextField();

        bindDatePeriodControlFields(datePeriod, txtNumDays);


        addHolidayPeriodLayout.addComponent(btnAddHolidayPeriod,0,0);
        addHolidayPeriodLayout.addComponent(lblDatePeriod, 1,0);
        addHolidayPeriodLayout.addComponent(datePeriod, 2,0);
        addHolidayPeriodLayout.addComponent(lblNumDays, 3,0);
        addHolidayPeriodLayout.addComponent(txtNumDays, 4,0);
        addHolidayPeriodLayout.addComponent(btnRemoveHolidayPeriods, 0,1);

        return addHolidayPeriodLayout;
    }

    private void bindDatePeriodControlFields(DateField dateStartField, TextField txtNumDaysField) {
        holidayPeriodBinder.forField(dateStartField)
                .withValidator(new HolidayPeriodDateValidator())
                .bind(HolidayPeriod::getDateStart, HolidayPeriod::setDateStart);

        holidayPeriodBinder.forField(txtNumDaysField)
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
                Notification.show("Application originated");
            }
        }
    }

    //protected void refreshGridData() {

        //grdHolidayPeriods
        //replaceComponent(grdHolidayPeriods, grdHolidayPeriods);
    //}

    /*public String getProjectRoleName() {
        return projectRoleName;
    }

    public void setProjectRoleName(String projectRoleName) {
        this.projectRoleName = projectRoleName;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }*/

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

    //public EmployeeHolidaysLayoutRefreshGridDataListener getRefreshGridDataListener() {
    //    return refreshGridDataListener;
    //}

    //public void addRefreshGridDataListener(EmployeeHolidaysLayoutRefreshGridDataListener refreshGridDataListener) {
    //    this.refreshGridDataListener = refreshGridDataListener;
    //}

    public HolidayPeriod getNewHolidayPeriod() {
        return newHolidayPeriod;
    }

    public void setNewHolidayPeriod(HolidayPeriod newHolidayPeriod) {
        this.newHolidayPeriod = newHolidayPeriod;
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

    public Map<Team, Set<EmployeeHolidayPeriod>> getManagedTeamMembersHolidays() {
        return managedTeamMembersHolidays;
    }

    public void setManagedTeamMembersHolidays(Map<Team, Set<EmployeeHolidayPeriod>> managedTeamMembersHolidays) {
        this.managedTeamMembersHolidays = managedTeamMembersHolidays;
    }

}
