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
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.behaviour.EmployeeHolidaysLayoutDeleteButtonClickListener;
import ru.rti.holidays.layout.behaviour.EmployeeHolidaysLayoutMainButtonClickListener;
import ru.rti.holidays.validator.HolidayPeriodDateValidator;
import ru.rti.holidays.validator.HolidayPeriodDayNumValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class EmployeeHolidaysLayout extends BaseVerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(EmployeeHolidaysLayout.class);

    private String projectRoleName;
    private String employeeFullName;
    private String panelName;

    Grid<HolidayPeriod> grdHolidayPeriods = new Grid<>();
    Button btnRemoveHolidayPeriods = new Button("Удалить выбранные", VaadinIcons.DEL);

    private List<HolidayPeriod> employeeHolidayPeriods;
    private EmployeeHolidaysLayoutMainButtonClickListener mainButtonClickListener;
    //private RefreshGridDataListener refreshGridDataListener;
    private EmployeeHolidaysLayoutDeleteButtonClickListener deleteButtonClickListener;
    private HolidayPeriod newHolidayPeriod;
    private Binder<HolidayPeriod> holidayPeriodBinder = new Binder<>();

    public EmployeeHolidaysLayout(String employeeFullName, String projectRoleName) {
        this.setEmployeeFullName(employeeFullName);
        this.setProjectRoleName(projectRoleName);
    }

    public EmployeeHolidaysLayout(String employeeFullName, String projectRoleName, String panelName) {
        this(employeeFullName, projectRoleName);
        this.panelName = panelName;
    }


    public void constructLayout() {
        try {
            Label lblEmployeeName = new Label("ФИО: <b>" + getEmployeeFullName() + "</b>", ContentMode.HTML);
            Label lblProjectRole = new Label("Проектная роль: <b>" + getProjectRoleName() + "</b>", ContentMode.HTML);

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

            /*grdHolidayPeriods.addSelectionListener(event -> {

            });*/


            //MultiSelectionModel<HolidayPeriod> selectionModel = (MultiSelectionModel<HolidayPeriod>) grdHolidayPeriods.setSelectionMode(Grid.SelectionMode.MULTI);

            pnlHolidaysLayout.addComponent(grdHolidayPeriods);
            pnlHolidaysLayout.addComponent(addControlsPanel());

            //pnlHolidaysLayout.setMargin(true);
            pnlHolidaysLayout.setSizeFull();
            pnlPanelHolidays.setContent(pnlHolidaysLayout);

            addComponent(lblEmployeeName);
            addComponent(lblProjectRole);
            addComponent(pnlPanelHolidays);

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

    public String getProjectRoleName() {
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


}
