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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Object principal = authentication.getPrincipal();
        if (principal != null && principal instanceof String && GlobalConstants.SPRING_SECURITY_ANONYMOUS_USER.equals(principal)) {
            return false;
        }
        return true;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails && principal instanceof Employee) {
                return (Employee) principal;
            }
        }
        return new Employee();
    }
}
