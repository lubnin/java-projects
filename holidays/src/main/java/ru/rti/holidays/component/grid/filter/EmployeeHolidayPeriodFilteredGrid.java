package ru.rti.holidays.component.grid.filter;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;

public class EmployeeHolidayPeriodFilteredGrid<T extends EmployeeHolidayPeriod> extends VerticalLayout {
    private Grid<T> gridControl = new Grid<>();
    private TextField txtName;

    public EmployeeHolidayPeriodFilteredGrid() {
        GridLayout filterLayout = new GridLayout(2,1);
        Label lblFilterByName = new Label("Фильтр по имени:");
        txtName = new TextField();
        txtName.setPlaceholder("Введите имя...");

        filterLayout.addComponent(lblFilterByName, 0, 0);
        filterLayout.addComponent(txtName, 1, 0);
        filterLayout.setMargin(true);
        filterLayout.setSpacing(true);

        setMargin(false);
        setSpacing(false);

        addComponent(filterLayout);
        addComponent(gridControl);

        txtName.addValueChangeListener(valueChangeEvent -> {
            ListDataProvider<T> dataProvider = (ListDataProvider<T>)gridControl.getDataProvider();
            dataProvider.setFilter(T::getEmployeeFullName, s -> filterRecordsByEmployeeFullName(s, valueChangeEvent.getValue()));
        });
    }

    public Boolean filterRecordsByEmployeeFullName(String fullName, String txtChangedValue) {
        if (txtChangedValue == null || txtChangedValue.length() == 0) {
            return true;
        }

        if (fullName != null && txtChangedValue != null && fullName.length() > 0 && txtChangedValue.length() > 0) {
            if (fullName.toLowerCase().indexOf(txtChangedValue.toLowerCase()) >= 0) {
                return true;
            }
        }
        return false;
    }

    public void clearFilter() {
        txtName.setValue("");
    }

    public Grid<T> getGridControl() {
        return gridControl;
    }

    public void setGridControl(Grid<T> gridControl) {
        this.gridControl = gridControl;
    }
}
