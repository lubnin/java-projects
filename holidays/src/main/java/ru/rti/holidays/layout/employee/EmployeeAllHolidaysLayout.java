package ru.rti.holidays.layout.employee;

import com.vaadin.ui.Grid;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.base.StandardBaseLayoutDrawer;
import ru.rti.holidays.style.GridEmployeeHolidayPeriodCellStyleGenerator;

import java.util.Collection;

public class EmployeeAllHolidaysLayout extends BaseVerticalLayout {
    private Grid<EmployeeHolidayPeriod> grdHolidayPeriods = new Grid<>();
    private Collection<EmployeeHolidayPeriod> employeeHolidayPeriods;
    public static final double DEFAULT_HEIGHT_BY_ROWS = 20d;
    private double gridHeightByRows;

    @Override
    public void constructLayout() {
        grdHolidayPeriods.setItems(employeeHolidayPeriods);
        grdHolidayPeriods.setSizeFull();
        if (gridHeightByRows > 0d) {
            grdHolidayPeriods.setHeightByRows(gridHeightByRows);
        }
        grdHolidayPeriods.addColumn(EmployeeHolidayPeriod::getEmployeeFullName).setCaption("ФИО сотрудника");
        grdHolidayPeriods.addColumn(EmployeeHolidayPeriod::getTeamName).setCaption("Команда");
        grdHolidayPeriods.addColumn(EmployeeHolidayPeriod::getEmployeeRoleName).setCaption("Роль на проекте");
        grdHolidayPeriods.addColumn(EmployeeHolidayPeriod::getDateStartAsString).setCaption("Дата начала отпуска");
        grdHolidayPeriods.addColumn(EmployeeHolidayPeriod::getNumDays).setCaption("Количество дней отпуска");
        grdHolidayPeriods.addColumn(EmployeeHolidayPeriod::getHolidayPeriodNegotiationStatus).setCaption("Статус согласования").setStyleGenerator(new GridEmployeeHolidayPeriodCellStyleGenerator());

        EmployeeMenuBarLayout employeeMenuBarLayout = new EmployeeMenuBarLayout(false, false);
        employeeMenuBarLayout.setHolidaysViewAllMenuItemVisible(false);
        new StandardBaseLayoutDrawer(this, employeeMenuBarLayout).drawLayout();

        addComponent(grdHolidayPeriods);
    }

    @Override
    public void postConstructLayout() {

    }

    public Collection<EmployeeHolidayPeriod> getEmployeeHolidayPeriods() {
        return employeeHolidayPeriods;
    }

    public void setEmployeeHolidayPeriods(Collection<EmployeeHolidayPeriod> employeeHolidayPeriods) {
        this.employeeHolidayPeriods = employeeHolidayPeriods;
    }

    public double getGridHeightByRows() {
        return gridHeightByRows;
    }

    public void setGridHeightByRows(double gridHeightByRows) {
        this.gridHeightByRows = gridHeightByRows;
    }
}
