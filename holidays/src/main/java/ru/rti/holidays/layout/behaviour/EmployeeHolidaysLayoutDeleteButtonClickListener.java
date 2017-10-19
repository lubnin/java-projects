package ru.rti.holidays.layout.behaviour;

import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;

import java.util.Set;

/**
 *
 *
 */
@FunctionalInterface
public interface EmployeeHolidaysLayoutDeleteButtonClickListener {
    void onDeleteSelectedPeriods(EmployeeHolidaysLayout layoutInstance, Set<HolidayPeriod> selectedPeriods);
}
