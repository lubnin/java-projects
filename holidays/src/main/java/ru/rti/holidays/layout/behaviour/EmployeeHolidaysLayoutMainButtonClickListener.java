package ru.rti.holidays.layout.behaviour;

import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;

/**
 * @deprecated
 * TODO: remove later
 */
@FunctionalInterface
public interface EmployeeHolidaysLayoutMainButtonClickListener {
    boolean onAddSelectedPeriods(EmployeeHolidaysLayout layoutInstance);
}
