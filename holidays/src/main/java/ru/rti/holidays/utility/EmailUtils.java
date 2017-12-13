package ru.rti.holidays.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.entity.Department;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailUtils {
    private static final Logger log = LoggerFactory.getLogger(HolidayPeriodUtils.class);

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public static boolean isNeedToSendEmailForManager(Employee employee, Employee currentManager) {
        if (employee == null || currentManager == null) return false;

        try {
            boolean isDepartmentCRM = false;
            boolean isDivisionB2C = false;
            boolean isDivisionCRM = false;

            boolean isRegularRole = false;
            boolean isTester = false;

            log.info("isVisibleForCurrentUser() called.");

            ProjectRole projectRole = employee.getProjectRole();
            Department dept = employee.getDepartment();

            if (projectRole == null || projectRole.getRoleName() == null) return false;
            if (dept == null || dept.getCode() == null) return false;

            String projectRoleName = projectRole.getRoleName().toLowerCase();

            log.info("roleName = " + projectRoleName);

            isTester = projectRoleName.indexOf("тестир") >= 0;
            log.info("isTester flag: " + String.valueOf(isTester));

            isRegularRole = !employee.isManager();
            log.info("isRegularRole flag: " + String.valueOf(isRegularRole));

            String deptCode = dept.getCode();
            if (GlobalConstants.DEPT_DEPARTMENT_CRM.equals(deptCode)) {
                isDepartmentCRM = true;
            } else if (GlobalConstants.DEPT_DIVISION_B2C.equals(deptCode)) {
                isDivisionB2C = true;
            } else if (GlobalConstants.DEPT_DIVISION_CRM.equals(deptCode)) {
                isDivisionCRM = true;
            }
            log.info("deptCode = " + deptCode);

            boolean isNeedToSendEmailToManagerFlag = true;

            log.info(String.format("Flags: isDepartmentCRM = %s, isDivisionB2C = %s, isDivisionCRM = %s",
                    String.valueOf(isDepartmentCRM),
                    String.valueOf(isDivisionB2C),
                    String.valueOf(isDivisionCRM)));

            //byte negotiationMask = holidayPeriod.getNegotiationMask();
            if (currentManager.isTeamLead()) {
                if (isDivisionB2C) {
                    isNeedToSendEmailToManagerFlag = false; // Don't allow TeamLead to negotiate BA's
                }
                return isNeedToSendEmailToManagerFlag;
            } else if (currentManager.isProjectManager()) {
                return true;
            } else if (currentManager.isLineManager()) {
                if (isDivisionB2C) {
                    // SA's, BA's
                    if (isRegularRole) {
                        if (currentManager.isB2CManager()) {
                            isNeedToSendEmailToManagerFlag = true;
                        } else if (currentManager.isBAManager()) {
                            isNeedToSendEmailToManagerFlag = true;
                        } else if (currentManager.isTestManager()) {
                            isNeedToSendEmailToManagerFlag = false; // Not visible for Ivan Artemyev, for example.
                        } else {
                            isNeedToSendEmailToManagerFlag = false;
                        }
                    } else {
                        // Submitter employee is not of a Regular Role
                        isNeedToSendEmailToManagerFlag = true; // Apply generic teams linking
                    }
                    log.info("visibilityMask = " + isNeedToSendEmailToManagerFlag);
                } else if (isDivisionCRM) {
                    // Testers, EPC Analysts, BPs, Developers, Migration Specialists
                    // TestManager
                    if (isRegularRole) {
                        if (currentManager.isBAManager()) {
                            isNeedToSendEmailToManagerFlag = false;
                        } else if (currentManager.isB2CManager()) {
                            isNeedToSendEmailToManagerFlag = false;
                        } else if (currentManager.isDevManager()) {
                            isNeedToSendEmailToManagerFlag = true;
                        } else if (currentManager.isTestManager()) {
                            // Only testers visible
                            isNeedToSendEmailToManagerFlag = isTester;
                        } else {
                            log.warn("Falled in else block. Unexpected.");
                        }
                    } else {
                        isNeedToSendEmailToManagerFlag = true;
                    }
                    log.info("visibilityMask = " + isNeedToSendEmailToManagerFlag);
                } else {
                    isNeedToSendEmailToManagerFlag = false;
                }
                return isNeedToSendEmailToManagerFlag;
            } else if (currentManager.isSupervisor()) {
                log.info("Current user is Supervisor");
                isNeedToSendEmailToManagerFlag = false;
                if (isDivisionCRM) {
                    if (currentManager.isDevManager()) {
                        isNeedToSendEmailToManagerFlag = true;
                    } else if (currentManager.isB2CManager()) {
                        isNeedToSendEmailToManagerFlag = false;
                    }
                    else {
                        isNeedToSendEmailToManagerFlag = true;
                    }
                } else if (isDivisionB2C) {
                    // Farkhad negotiates all BA's and SA's
                    if (currentManager.isB2CManager()) {
                        isNeedToSendEmailToManagerFlag = true;
                    } else if (currentManager.isDevManager()) {
                        isNeedToSendEmailToManagerFlag = false;
                    } else {
                        isNeedToSendEmailToManagerFlag = true;
                    }
                } else if (isDepartmentCRM) {
                    if (!currentManager.isB2CManager() && !currentManager.isDevManager()) {
                        isNeedToSendEmailToManagerFlag = true;
                    }
                } else {
                    isNeedToSendEmailToManagerFlag = false;
                }
                return isNeedToSendEmailToManagerFlag;
            }
            return false;
        } catch (Exception e) {
            log.error("Exception occured in EmailUtils");
            e.printStackTrace();
        }

        return false;
    }
}
