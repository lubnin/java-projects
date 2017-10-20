package ru.rti.holidays.utility;

import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

import java.util.Iterator;

public class HolidayPeriodNegotiationStatusUtils {
    /**
     * Gets the 'NEGOTIATION_STATUS_TYPE_NEGOTIATING' value from the list of all available statuses
     * @param allStatuses the list of all available statuses
     * @return
     */
    public static HolidayPeriodNegotiationStatus getNegotiatingStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses) {
        return getStatusFromList(allStatuses, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_NEGOTIATING);
    }

    /**
     * Gets the 'NEGOTIATION_STATUS_TYPE_NEW' value from the list of all available statuses
     * @param allStatuses the list of all available statuses
     * @return
     */
    public static HolidayPeriodNegotiationStatus getNewStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses) {
        return getStatusFromList(allStatuses, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_NEW);
    }

    /**
     * Gets the 'NEGOTIATION_STATUS_TYPE_OK' value from the list of all available statuses
     * @param allStatuses the list of all available statuses
     * @return
     */
    public static HolidayPeriodNegotiationStatus getOkStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses) {
        return getStatusFromList(allStatuses, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_OK);
    }

    /**
     * Gets the 'NEGOTIATION_STATUS_TYPE_OK' value from the list of all available statuses
     * @param allStatuses the list of all available statuses
     * @return
     */
    public static HolidayPeriodNegotiationStatus getRejectedStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses) {
        return getStatusFromList(allStatuses, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_REJECTED);
    }


    /**
     * Gets the status value from the list of all available statuses
     * @param allStatuses the list of all available statuses
     * @param status the status to get from the list
     * @return returns the status reference if found in the list of statuses, otherwise returns null. If any of the input parameters is null, returns null
     */
    public static HolidayPeriodNegotiationStatus getStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType status) {
        if (allStatuses == null || status == null) {
            return null;
        }

        for (HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus : allStatuses) {
            if (status.equals(holidayPeriodNegotiationStatus.getNegotiationStatusType())) {
                return holidayPeriodNegotiationStatus;
            }
        }

        return null;
    }
}
