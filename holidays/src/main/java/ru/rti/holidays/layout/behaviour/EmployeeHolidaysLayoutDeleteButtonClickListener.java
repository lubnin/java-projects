package ru.rti.holidays.layout.behaviour;

import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;

import java.util.Set;

/**
 * @deprecated
 * TODO: remove later
 */
@FunctionalInterface
public interface EmployeeHolidaysLayoutDeleteButtonClickListener {
    void performDelete(EmployeeHolidaysLayout layoutInstance, Set<HolidayPeriod> selectedPeriods);
}
