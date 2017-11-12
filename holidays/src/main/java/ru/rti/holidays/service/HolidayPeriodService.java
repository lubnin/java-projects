package ru.rti.holidays.service;

import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.entity.ProjectRole;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public interface HolidayPeriodService {
    List<HolidayPeriodNegotiationStatus> getAllHolidayPeriodNegotiationStatuses();
    HolidayPeriodNegotiationStatus saveHolidayPeriodNegotiationStatus(HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus);
    boolean deleteHolidayPeriodNegotiationStatuses(Iterable<HolidayPeriodNegotiationStatus> holidayPeriodNegotiationStatuses);
    boolean setNegotiationStatusForEmployeeHolidayPeriods(Employee currentManager, Iterable<EmployeeHolidayPeriod> holidayPeriods, Collection<HolidayPeriodNegotiationStatus> allStatuses);
    boolean setNegotiationStatusForEmployeeHolidayPeriods(Iterable<EmployeeHolidayPeriod> holidayPeriods, HolidayPeriodNegotiationStatus negotiationStatus);
    boolean setNegotiationStatusForHolidayPeriods(Iterable<HolidayPeriod> holidayPeriods, HolidayPeriodNegotiationStatus negotiationStatus);
    boolean deleteHolidayPeriods(Iterable<HolidayPeriod> holidayPeriods);
    HolidayPeriod saveHolidayPeriod(HolidayPeriod holidayPeriod);
    List<HolidayPeriod> getHolidayPeriodsForEmployee(Employee employee);
}
