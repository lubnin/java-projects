package ru.rti.holidays.layout.base.behaviour;

import ru.rti.holidays.layout.base.BaseLayout;
import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;

/**
 * This functional interface is used for passing the logic of refreshing the grid from
 * view layer to the underlying layout layer.
 */
@FunctionalInterface
public interface RefreshGridDataListener {
    void onRefreshGridData(BaseLayout layout);
}