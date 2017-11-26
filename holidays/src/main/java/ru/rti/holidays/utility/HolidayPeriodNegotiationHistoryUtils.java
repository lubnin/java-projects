package ru.rti.holidays.utility;

import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationHistory;

public class HolidayPeriodNegotiationHistoryUtils {

    public static HolidayPeriodNegotiationHistory createHolidayPeriodNegotiationHistory(HolidayPeriod holidayPeriod, String comment, String oldStatus, String newStatus) {
        if (CommonUtils.checkIfAnyIsEmpty(holidayPeriod, comment, oldStatus, newStatus)) {
            return null;
        }

        HolidayPeriodNegotiationHistory hpNegHistory = new HolidayPeriodNegotiationHistory();

        hpNegHistory.setComment(comment);
        hpNegHistory.setOldStatus(oldStatus);
        hpNegHistory.setNewStatus(newStatus);
        hpNegHistory.setHolidayPeriod(holidayPeriod);

        return hpNegHistory;
    }
}
