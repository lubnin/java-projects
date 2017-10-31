package ru.rti.holidays.layout.employee;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriodCrossing;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.base.behaviour.ButtonClickListener;
import ru.rti.holidays.layout.base.behaviour.ButtonClickResult;
import ru.rti.holidays.utility.GlobalConstants;

import java.util.ArrayList;
import java.util.Collection;

public class EmployeeHolidayPeriodsCrossingDatesLayout extends BaseVerticalLayout {

    protected Grid<EmployeeHolidayPeriodCrossing> grdHolidayCrossings = new Grid<>();
    protected Collection<EmployeeHolidayPeriodCrossing> holidayPeriodCrossings = new ArrayList<>();
    public static double DEFAULT_GRID_HEIGHT_BY_ROWS = 10;
    protected double gridHeightByRows = DEFAULT_GRID_HEIGHT_BY_ROWS;
    protected String panelName = "Пересечения по отпускам";
    protected boolean isSubmitterEmployeeFullNameColumnVisible;
    protected Label lblInformationMessage = new Label("", ContentMode.HTML);
    protected ButtonClickListener<EmployeeHolidayPeriodCrossing> checkCrossingDatesButtonClickListener;

    public EmployeeHolidayPeriodsCrossingDatesLayout() {
        super();
    }

    public EmployeeHolidayPeriodsCrossingDatesLayout(boolean isApplyMargin, boolean isApplySpacing) {
        super(isApplyMargin, isApplySpacing);
    }

    public void setInformationMessageByCheckResult(boolean isNoCrossingDatesFound) {
        lblInformationMessage.removeStyleName(GlobalConstants.CSS_ERROR_MESSAGE);
        lblInformationMessage.removeStyleName(GlobalConstants.CSS_SUCCESS_MESSAGE);

        if (isNoCrossingDatesFound) {
            setInformationMessage("Проверка завершена. Пересечений не найдено.");
            lblInformationMessage.addStyleName(GlobalConstants.CSS_SUCCESS_MESSAGE);
        } else {
            setInformationMessage("Проверка завершена. Обнаружены пересечения отпусков с другими сотрудниками! Пожалуйста, ознакомьтесь с информацией в таблице.");
            lblInformationMessage.addStyleName(GlobalConstants.CSS_ERROR_MESSAGE);
        }
    }

    private void setInformationMessage(String message) {
        lblInformationMessage.setValue(message);
    }

    @Override
    public void constructLayout() {
        Panel pnlPanelHolidaysCrossings = new Panel(getPanelName());
        pnlPanelHolidaysCrossings.setSizeFull();

        VerticalLayout crossingHolidaysLayout = new VerticalLayout();

        grdHolidayCrossings.setItems(holidayPeriodCrossings);
        if (isSubmitterEmployeeFullNameColumnVisible()) {
            grdHolidayCrossings.addColumn(EmployeeHolidayPeriodCrossing::getSubmitterEmployeeFullName).setCaption("ФИО сотрудника");
        }
        grdHolidayCrossings.addColumn(EmployeeHolidayPeriodCrossing::getSubmitterEmployeeDateStartAsString).setCaption("Дата начала отпуска");
        grdHolidayCrossings.addColumn(EmployeeHolidayPeriodCrossing::getSubmitterNumDays).setCaption("Количество дней");
        grdHolidayCrossings.addColumn(EmployeeHolidayPeriodCrossing::getCrossingEmployeeFullName).setCaption("Пересечение с отпуском сотрудника");
        grdHolidayCrossings.addColumn(EmployeeHolidayPeriodCrossing::getCrossingEmployeeDateStartAsString).setCaption("Дата начала отпуска");
        grdHolidayCrossings.addColumn(EmployeeHolidayPeriodCrossing::getCrossingEmployeeNumDays).setCaption("Количество дней");
        grdHolidayCrossings.setWidth("100%");
        grdHolidayCrossings.setHeightByRows(getGridHeightByRows());

        crossingHolidaysLayout.addComponent(grdHolidayCrossings);
        crossingHolidaysLayout.addComponent(getControlPanelLayout());

        pnlPanelHolidaysCrossings.setContent(crossingHolidaysLayout);

        addComponent(pnlPanelHolidaysCrossings);
    }

    private GridLayout getControlPanelLayout() {
        GridLayout controlPanelLayout = new GridLayout(1, 2);
        controlPanelLayout.setSizeFull();
        controlPanelLayout.setSpacing(true);
        controlPanelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        Button btnCheckCrossingDates = new Button("Проверить пересечения");
        btnCheckCrossingDates.setIcon(VaadinIcons.CALENDAR);
        btnCheckCrossingDates.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnCheckCrossingDates.setWidth("300px");
        btnCheckCrossingDates.addClickListener(clickEvent -> {
            if (checkCrossingDatesButtonClickListener != null) {
                ButtonClickResult<EmployeeHolidayPeriodCrossing> buttonClickResult = checkCrossingDatesButtonClickListener.onClick(this);
                if (buttonClickResult.isResult()) {
                    Collection<EmployeeHolidayPeriodCrossing> resultItems = buttonClickResult.getResultItems();
                    grdHolidayCrossings.setItems(resultItems);
                }
            }
        });

        controlPanelLayout.addComponent(btnCheckCrossingDates, 0, 0);
        controlPanelLayout.addComponent(lblInformationMessage, 0, 1);

        return controlPanelLayout;
    }

    @Override
    public void refreshDataGrid() {
        super.refreshDataGrid();

        //TODO: customize your logic here
        grdHolidayCrossings.setItems(holidayPeriodCrossings);
    }

    @Override
    public void postConstructLayout() {

    }

    public Collection<EmployeeHolidayPeriodCrossing> getHolidayPeriodCrossings() {
        return holidayPeriodCrossings;
    }

    public void setHolidayPeriodCrossings(Collection<EmployeeHolidayPeriodCrossing> holidayPeriodCrossings) {
        this.holidayPeriodCrossings = holidayPeriodCrossings;
    }

    public double getGridHeightByRows() {
        return gridHeightByRows;
    }

    public void setGridHeightByRows(double gridHeightByRows) {
        if (gridHeightByRows < 0.0d) {
            this.gridHeightByRows = DEFAULT_GRID_HEIGHT_BY_ROWS;
            return;
        }
        this.gridHeightByRows = gridHeightByRows;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public boolean isSubmitterEmployeeFullNameColumnVisible() {
        return isSubmitterEmployeeFullNameColumnVisible;
    }

    public void setSubmitterEmployeeFullNameColumnVisible(boolean submitterEmployeeFullNameColumnVisible) {
        isSubmitterEmployeeFullNameColumnVisible = submitterEmployeeFullNameColumnVisible;
    }

    public ButtonClickListener<EmployeeHolidayPeriodCrossing> getCheckCrossingDatesButtonClickListener() {
        return checkCrossingDatesButtonClickListener;
    }

    public void setCheckCrossingDatesButtonClickListener(ButtonClickListener<EmployeeHolidayPeriodCrossing> checkCrossingDatesButtonClickListener) {
        this.checkCrossingDatesButtonClickListener = checkCrossingDatesButtonClickListener;
    }
}
