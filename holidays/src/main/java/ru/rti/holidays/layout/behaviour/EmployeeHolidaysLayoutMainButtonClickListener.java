package ru.rti.holidays.layout.behaviour;

import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;

/**
 *
 *
 */
@FunctionalInterface
public interface EmployeeHolidaysLayoutMainButtonClickListener {
    boolean onAddSelectedPeriods(EmployeeHolidaysLayout layoutInstance);
}
