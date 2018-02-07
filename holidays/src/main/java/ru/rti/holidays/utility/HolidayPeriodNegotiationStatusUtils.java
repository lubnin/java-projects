package ru.rti.holidays.utility;

import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

import java.util.Collection;
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
     * Gets the 'NEGOTIATION_STATUS_TYPE_RECALLED' value from the list of all available statuses
     * @param allStatuses the list of all available statuses
     * @return
     */
    public static HolidayPeriodNegotiationStatus getRecalledStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses) {
        return getStatusFromList(allStatuses, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_RECALLED);
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
     * Gets the 'NEGOTIATION_STATUS_TYPE_PARTLY_NEGOTIATED' value from the list of all available statuses
     * @param allStatuses the list of all available statuses
     * @return
     */
    public static HolidayPeriodNegotiationStatus getPartlyNegotiatedStatusFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses) {
        return getStatusFromList(allStatuses, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_PARTLY_NEGOTIATED);
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
    public static HolidayPeriodNegotiationStatus getStatusBySpecialTypeFromList(Iterable<HolidayPeriodNegotiationStatus> allStatuses, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType statusType) {
        if (statusType != null) {
            return getStatusFromList(allStatuses, statusType);
        }
        return getNewStatusFromList(allStatuses);
    }

    /**
     * Used for calculating the next status of Holiday Period before applying any actions and changes to the period
     * @param manager
     * @param holidayPeriod
     * @param allNegotiationStatuses
     * @return
     */
    public static HolidayPeriodNegotiationStatus calculateNextStatus(Employee manager, HolidayPeriod holidayPeriod, Collection<HolidayPeriodNegotiationStatus> allNegotiationStatuses) {
        if (CommonUtils.checkIfAnyIsNull(manager, holidayPeriod, allNegotiationStatuses)) {
            return null;
        }
        HolidayPeriodNegotiationStatus currentStatus = holidayPeriod.getNegotiationStatus();
        if (currentStatus == null) {
            return null;
        }
        byte futureNegMask = holidayPeriod.getNegotiationMaskByManager(manager);
        if (futureNegMask == 0) {
            return null;
        }

        HolidayPeriodNegotiationStatus futureStatus = HolidayPeriodNegotiationStatusUtils.getNextStatusByNegotiationMask(futureNegMask, allNegotiationStatuses);

        return futureStatus;
    }

    public static HolidayPeriodNegotiationStatus getNextStatusByNegotiationMask(byte negMask, Collection<HolidayPeriodNegotiationStatus> allNegotiationStatuses) {
        if (allNegotiationStatuses == null) {
            return null;
        }

        byte negotiationMask = negMask;

        if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_TEAM_LEAD_ONLY) {
            // TL only
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_TEAM_LEAD_AND_PROJECT_MANAGER) {
            // TL & PM
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_LINE_MANAGER_ONLY) {
            // LM only
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_LINE_MANAGER_AND_PROJECT_MANAGER) {
            // LM & PM
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_LINE_MANAGER_AND_TEAM_LEAD) {
            // LM & TL
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_PROJECT_MANAGER_ONLY) {
            // PM only
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_ALL) {
            // all
            return HolidayPeriodNegotiationStatusUtils.getOkStatusFromList(allNegotiationStatuses);
        }
        else {
            return null;
        }
    }

    public static HolidayPeriodNegotiationStatus getNextStatusByNegotiationMask(HolidayPeriod holidayPeriod, Collection<HolidayPeriodNegotiationStatus> allNegotiationStatuses) {
        if (holidayPeriod == null || allNegotiationStatuses == null) {
            return null;
        }

        byte negotiationMask = 0;
        try {
            Byte negMask = holidayPeriod.getNegotiationMask();
            negotiationMask = negMask.byteValue();
        } catch (Exception e) {
            negotiationMask = holidayPeriod.getSafeNegotiationMask();
        }



        if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_TEAM_LEAD_ONLY) {
            // TL only
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_TEAM_LEAD_AND_PROJECT_MANAGER) {
            // TL & PM
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_LINE_MANAGER_ONLY) {
            // LM only
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_LINE_MANAGER_AND_PROJECT_MANAGER) {
            // LM & PM
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_LINE_MANAGER_AND_TEAM_LEAD) {
            // LM & TL
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_PROJECT_MANAGER_ONLY) {
            // PM only
            return HolidayPeriodNegotiationStatusUtils.getPartlyNegotiatedStatusFromList(allNegotiationStatuses);
        } else if (negotiationMask == HolidayPeriod.NEGOTIATION_MASK_ALL) {
            // all
            return HolidayPeriodNegotiationStatusUtils.getOkStatusFromList(allNegotiationStatuses);
        }
        else {
            // return self
            HolidayPeriodNegotiationStatus selfStatus = holidayPeriod.getNegotiationStatus();
            if (selfStatus != null) {
                HolidayPeriodNegotiationStatusUtils.getStatusBySpecialTypeFromList(allNegotiationStatuses, selfStatus.getNegotiationStatusType());
            }
        }

        return null;
    }
}
