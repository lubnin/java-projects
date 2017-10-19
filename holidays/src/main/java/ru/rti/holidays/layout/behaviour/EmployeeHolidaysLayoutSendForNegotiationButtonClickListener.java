package ru.rti.holidays.layout.behaviour;

import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.layout.employee.EmployeeHolidaysLayout;

import java.util.Set;

public interface EmployeeHolidaysLayoutSendForNegotiationButtonClickListener {
    void onSubmitSelectedPeriodsForNegotiation(EmployeeHolidaysLayout layoutInstance, Set<HolidayPeriod> selectedPeriods, HolidayPeriodNegotiationStatus negotiatingStatus);
}
