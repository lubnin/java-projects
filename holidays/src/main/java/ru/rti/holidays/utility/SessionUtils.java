package ru.rti.holidays.utility;

import com.vaadin.server.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.rti.holidays.entity.Employee;

public class SessionUtils {
    public static final boolean logout() {
        Page.getCurrent().setLocation(GlobalConstants.URL_PATH_LOGOUT);
        return true;
    }

    public static final boolean isAuthenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return false;
            }
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof String && GlobalConstants.SPRING_SECURITY_ANONYMOUS_USER.equals(principal)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static String getCurrentUserSpecialCode() {
        try {
            Employee emp = getCurrentUser();
            String specCode = emp.getSpecialCode();
            return CommonUtils.getValueOrEmptyString(specCode);
        } catch (Exception e) {
            e.printStackTrace();
            return GlobalConstants.EMPTY_STRING;
        }
    }

    public static boolean isCurrentUserB2CManager() {
        return getCurrentUserSpecialCode().equals(GlobalConstants.EMPLOYEE_SPECIAL_CODE_B2C_MANAGER);
    }

    public static boolean isCurrentUserDevManager() {
        return getCurrentUserSpecialCode().equals(GlobalConstants.EMPLOYEE_SPECIAL_CODE_DEV_MANAGER);
    }

    public static boolean isCurrentUserTestManager() {
        return getCurrentUserSpecialCode().equals(GlobalConstants.EMPLOYEE_SPECIAL_CODE_TEST_MANAGER);
    }

    public static boolean isCurrentUserBAManager() {
        return getCurrentUserSpecialCode().equals(GlobalConstants.EMPLOYEE_SPECIAL_CODE_BA_MANAGER);
    }

    public static boolean isCurrentUserTeamLead() {
        return getCurrentUser().isTeamLead();
    }

    public static boolean isCurrentUserLineManager() {
        return getCurrentUser().isLineManager();
    }

    public static boolean isCurrentUserSupervisor() {
        return getCurrentUser().isSupervisor();
    }

    public static boolean isCurrentUserProjectManager() {
        return getCurrentUser().isProjectManager();
    }

    public static final Employee getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails && principal instanceof Employee) {
                    return (Employee) principal;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Employee();
    }
}
