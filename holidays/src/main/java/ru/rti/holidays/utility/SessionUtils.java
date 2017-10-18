package ru.rti.holidays.utility;

import com.vaadin.server.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.rti.holidays.component.security.User;
import ru.rti.holidays.entity.Employee;

public class SessionUtils {
    public static final boolean logout() {
        Page.getCurrent().setLocation("/logout");
        return true;
    }

    public static final boolean logout(User currentUser) {
        if (currentUser != null) {
            currentUser.setCurrentView(null);
            currentUser.setEmployeeLoginName(null);
            currentUser.setEmployeePassword(null);
            Page.getCurrent().reload();
            return true;
        }
        return false;
    }

    public static final boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null);
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
