package ru.rti.holidays.service;

import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationHistory;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public interface HolidayPeriodService {
    HolidayPeriod getById(Long id);
    List<HolidayPeriodNegotiationStatus> getAllHolidayPeriodNegotiationStatuses();
    HolidayPeriodNegotiationStatus saveHolidayPeriodNegotiationStatus(HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus);
    HolidayPeriodNegotiationHistory saveHolidayPeriodNegotiationHistory(HolidayPeriodNegotiationHistory holidayPeriodNegotiationHistory);
    boolean deleteHolidayPeriodNegotiationStatuses(Iterable<HolidayPeriodNegotiationStatus> holidayPeriodNegotiationStatuses);
    boolean setNegotiationStatusForEmployeeHolidayPeriods(Employee currentManager,
                                                          Iterable<EmployeeHolidayPeriod> holidayPeriods,
                                                          Collection<HolidayPeriodNegotiationStatus> allStatuses,
                                                          HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationMode negotiationMode);
    boolean setNegotiationStatusForEmployeeHolidayPeriods(Iterable<EmployeeHolidayPeriod> holidayPeriods, HolidayPeriodNegotiationStatus negotiationStatus);
    boolean setNegotiationStatusForHolidayPeriods(Iterable<HolidayPeriod> holidayPeriods, HolidayPeriodNegotiationStatus negotiationStatus);
    boolean deleteHolidayPeriods(Iterable<HolidayPeriod> holidayPeriods);
    HolidayPeriod saveHolidayPeriod(HolidayPeriod holidayPeriod);
    List<HolidayPeriod> getHolidayPeriodsForEmployee(Employee employee);
}
