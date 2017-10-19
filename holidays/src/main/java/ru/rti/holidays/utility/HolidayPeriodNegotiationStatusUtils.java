package ru.rti.holidays.utility;

import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

import java.util.Iterator;

public class HolidayPeriodNegotiationStatusUtils {
    public static HolidayPeriodNegotiationStatus getNegotiatingStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses) {
        if (allStatuses == null) {
            return null;
        }

        for (HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus : allStatuses) {
            if (holidayPeriodNegotiationStatus.getNegotiationStatusType() ==
                    HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_NEGOTIATING) {
                return holidayPeriodNegotiationStatus;
            }
        }

        return null;
    }

    public static HolidayPeriodNegotiationStatus getNewStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses) {
        if (allStatuses == null) {
            return null;
        }

        for (HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus : allStatuses) {
            if (holidayPeriodNegotiationStatus.getNegotiationStatusType() ==
                    HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_NEW) {
                return holidayPeriodNegotiationStatus;
            }
        }

        return null;
    }

    public static HolidayPeriodNegotiationStatus getOkStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses) {
        if (allStatuses == null) {
            return null;
        }

        for (HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus : allStatuses) {
            if (holidayPeriodNegotiationStatus.getNegotiationStatusType() ==
                    HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_OK) {
                return holidayPeriodNegotiationStatus;
            }
        }

        return null;
    }

    public static HolidayPeriodNegotiationStatus getRejectedStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses) {
        if (allStatuses == null) {
            return null;
        }

        for (HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus : allStatuses) {
            if (holidayPeriodNegotiationStatus.getNegotiationStatusType() ==
                    HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_REJECTED) {
                return holidayPeriodNegotiationStatus;
            }
        }

        return null;
    }
}
