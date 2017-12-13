package ru.rti.holidays.utility;

import org.hibernate.LazyInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.*;

public class HolidayPeriodUtils {
    private static final Logger log = LoggerFactory.getLogger(HolidayPeriodUtils.class);

    public static boolean isVisibleForCurrentUser(EmployeeHolidayPeriod empHolidayPeriod, HolidayPeriod holidayPeriod) {
        if (empHolidayPeriod == null) return false;

        boolean isDepartmentCRM = false;
        boolean isDivisionB2C = false;
        boolean isDivisionCRM = false;
        boolean isRegularRole = false;
        boolean isTester = false;

        String projectRoleName = empHolidayPeriod.getEmployeeRoleName().toLowerCase();

        //TODO: remove hardcode from here later!!!
        isTester = projectRoleName.indexOf("тестир") >= 0;
        isRegularRole = empHolidayPeriod.isEmployeeRegularRole();


        String deptCode = empHolidayPeriod.getEmployeeDepartmentCode();
        if (GlobalConstants.DEPT_DEPARTMENT_CRM.equals(deptCode)) {
            isDepartmentCRM = true;
        } else if (GlobalConstants.DEPT_DIVISION_B2C.equals(deptCode)) {
            isDivisionB2C = true;
        } else if (GlobalConstants.DEPT_DIVISION_CRM.equals(deptCode)) {
            isDivisionCRM = true;
        }

        boolean visibilityMask = true;


        if (SessionUtils.isCurrentUserTeamLead()) {
            //log.info("Current user is TeamLead");
            if (isDivisionB2C) {
                visibilityMask = false; // Don't allow TeamLead to negotiate BA's
            }
            return holidayPeriod.isVisibleForTeamLead() && visibilityMask;
        } else if (SessionUtils.isCurrentUserProjectManager()) {
            //log.info("Current user is ProjectManager");
            return holidayPeriod.isVisibleForProjectManager();
        } else if (SessionUtils.isCurrentUserLineManager()) {
            //log.info("Current user is LineManager");
            if (isDivisionB2C) {
                // SA's, BA's
                if (isRegularRole) {
                    if (SessionUtils.isCurrentUserB2CManager()) {
                        visibilityMask = true;
                    } else if (SessionUtils.isCurrentUserBAManager()) {
                        visibilityMask = true;
                    } else {
                        visibilityMask = false; // Not visible for Ivan Artemyev, for example.
                    }
                } else {
                    // Submitter employee is not of a Regular Role
                    visibilityMask = true; // Apply generic teams linking
                }
                //log.info("visibilityMask = " + visibilityMask);
            } else if (isDivisionCRM) {
                // Testers, EPC Analysts, BPs, Developers, Migration Specialists
                // TestManager
                if (isRegularRole) {
                    if (SessionUtils.isCurrentUserBAManager()) {
                        visibilityMask = false;
                    } else if (SessionUtils.isCurrentUserB2CManager()) {
                        visibilityMask = false;
                    } else if (SessionUtils.isCurrentUserDevManager()) {
                        visibilityMask = true;
                    } else if (SessionUtils.isCurrentUserTestManager()) {
                        // Only testers visible
                        visibilityMask = isTester;
                    } else {
                        log.warn("Falled in else block. Unexpected.");
                    }
                } else {
                    visibilityMask = true;
                }
            } else {
                visibilityMask = false;
            }
            return holidayPeriod.isVisibleForLineManager() && visibilityMask;
        } else if (SessionUtils.isCurrentUserSupervisor()) {
            //log.info("Current user is Supervisor");
            visibilityMask = false;
            if (isDivisionCRM) {
                if (SessionUtils.isCurrentUserDevManager()) {
                    visibilityMask = true;
                } else if (SessionUtils.isCurrentUserB2CManager()) {
                    visibilityMask = false;
                } else {
                    visibilityMask = true;
                }
            } else if (isDivisionB2C) {
                // Farkhad negotiates all BA's and SA's
                if (SessionUtils.isCurrentUserB2CManager()) {
                    visibilityMask = true;
                } else if (SessionUtils.isCurrentUserDevManager()) {
                    visibilityMask = false;
                } else {
                    visibilityMask = true;
                }
            } else if (isDepartmentCRM) {
                if (!SessionUtils.isCurrentUserB2CManager() && !SessionUtils.isCurrentUserDevManager()) {
                    visibilityMask = true;
                }
            } else {
                visibilityMask = false;
            }
            return holidayPeriod.isVisibleForSupervisor() && visibilityMask;
        }
        return false;
    }

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
