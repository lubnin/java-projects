package ru.rti.holidays.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

import java.util.List;
import java.util.Set;

public class HolidayPeriodUtils {
    private static final Logger log = LoggerFactory.getLogger(HolidayPeriodUtils.class);

    public static boolean isHolidayPeriodInOkStatus(HolidayPeriod holidayPeriod) {
        return isHolidayPeriodInSpecifiedStatus(holidayPeriod, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_OK);
    }

    public static boolean isHolidayPeriodInNegotiatingStatus(HolidayPeriod holidayPeriod) {
        return isHolidayPeriodInSpecifiedStatus(holidayPeriod, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_NEGOTIATING);
    }

    public static boolean isHolidayPeriodInRejectedStatus(HolidayPeriod holidayPeriod) {
        return isHolidayPeriodInSpecifiedStatus(holidayPeriod, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_REJECTED);
    }

    public static boolean isHolidayPeriodInPartlyNegotiatedStatus(HolidayPeriod holidayPeriod) {
        return isHolidayPeriodInSpecifiedStatus(holidayPeriod, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_PARTLY_NEGOTIATED);
    }

    public static boolean isHolidayPeriodInNewStatus(HolidayPeriod holidayPeriod) {
        return isHolidayPeriodInSpecifiedStatus(holidayPeriod, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_NEW);
    }

    public static boolean isHolidayPeriodInSpecifiedStatus(HolidayPeriod holidayPeriod, HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType statusType) {
        if (holidayPeriod == null ||
                holidayPeriod.getNegotiationStatus() == null ||
                holidayPeriod.getNegotiationStatus().getNegotiationStatusType() == null ||
                statusType == null) {
            return false;
        }

        return (statusType.equals(holidayPeriod
                .getNegotiationStatus()
                .getNegotiationStatusType()));
    }

    /**
     * @deprecated
     * @param employee
     * @param holidayPeriods
     */
    public static void checkAndMarkEmployeeHolidayPeriodsForCrossingDates(Employee employee, Iterable<HolidayPeriod> holidayPeriods) {
        /*
        if (employee == null || holidayPeriods == null || employee.getTeam() == null) {
            log.error(String.format("Error occured in `checkAndMarkEmployeeHolidayPeriodsForCrossingDates` method: null parameters passed for input: " +
                "employee = %s, holidayPeriods = %s", employee, holidayPeriods
            ));
            return;
        }

        Long teamId = employee.getTeam().getId();
        if (teamId == null) {
            log.error("Error occured in `checkAndMarkEmployeeHolidayPeriodsForCrossingDates` method: teamId is null!");
            return;
        }

        for (HolidayPeriod holidayPeriod : holidayPeriods) {
            Set<Employee> employeesWithCrossingDates = employeeServiceImpl.getEmployeesWithCrossingHolidayPeriods(employee.getId(), teamId, DateUtils.asDate(hp.getDateStart()), hp.getNumDays());
            if (employeesWithCrossingDates.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (Employee emp : employeesWithCrossingDates) {
                    if (sb.length() > 0) {
                        sb.append("<br><br>");
                    }
                    List<HolidayPeriod> crossingHolidayPeriods = emp.getHolidayPeriods();
                    StringBuilder sbPeriods = new StringBuilder();
                    for (HolidayPeriod hpInTeam : crossingHolidayPeriods) {
                        if (sbPeriods.length() > 0) {
                            sbPeriods.append("<br>");
                        }
                        sbPeriods.append(String.format("Дата начала: %s, Дней: %s", hpInTeam.getDateStartAsString(), hpInTeam.getNumDaysAsString()));
                    }
                    sb.append(emp.getFullName() + ":<br>" + sbPeriods.toString());
                }

                layoutInstance.setCrossingDatesListValue(sb.toString(), false);
            } else {
                layoutInstance.setCrossingDatesListValue("", true);
            }
        }


        for (HolidayPeriod hp : allPeriods) {

        }
        */
    }
}
