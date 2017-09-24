package ru.rti.holidays.service;

import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.entity.ProjectRole;

import java.util.List;
import java.util.Set;

public interface HolidayPeriodService {
    List<HolidayPeriodNegotiationStatus> getAllHolidayPeriodNegotiationStatuses();
    HolidayPeriodNegotiationStatus saveHolidayPeriodNegotiationStatus(HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus);
    boolean deleteHolidayPeriodNegotiationStatuses(Set<HolidayPeriodNegotiationStatus> holidayPeriodNegotiationStatuses);
}
