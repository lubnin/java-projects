package ru.rti.holidays.layout.behaviour;

import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

import java.util.Set;

@FunctionalInterface
public interface EmployeeHolidaysLayoutNegotiateSelectedPeriodsClickListener {
    void onNegotiateSelectedPeriods(HolidayPeriodNegotiationStatus hpNegotiationStatus, Set<EmployeeHolidayPeriod> setEmployeeHolPeriods);
}
