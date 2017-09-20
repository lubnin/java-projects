package ru.rti.holidays.layout.base.behaviour;

import ru.rti.holidays.layout.base.BaseLayout;
import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;

@FunctionalInterface
public interface RefreshGridDataListener {
    void onRefreshGridData(BaseLayout layout);
}