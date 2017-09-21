package ru.rti.holidays.service;

import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

import java.util.List;

public interface HolidayPeriodService {
    List<HolidayPeriodNegotiationStatus> getAllHolidayPeriodNegotiationStatuses();
    HolidayPeriodNegotiationStatus addHolidayPeriodNegotiationStatus(HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus);
}
