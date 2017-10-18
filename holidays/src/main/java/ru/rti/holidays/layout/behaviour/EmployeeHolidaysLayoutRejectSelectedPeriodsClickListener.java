package ru.rti.holidays.layout.behaviour;

import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

import java.util.Set;

public interface EmployeeHolidaysLayoutRejectSelectedPeriodsClickListener {
    void onRejectSelectedPeriods(HolidayPeriodNegotiationStatus hpNegotiationStatus, Set<EmployeeHolidayPeriod> setEmployeeHolPeriods);
}
