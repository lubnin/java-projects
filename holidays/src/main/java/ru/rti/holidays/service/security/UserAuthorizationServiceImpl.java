package ru.rti.holidays.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.repository.EmployeeRepository;

@Service("authService")
public class UserAuthorizationServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserAuthorizationServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Main method for logging-in the User.
     * Allows authentication of User using login in the Application or Email address.
     * @param username
     * @return Employee instance if successfully found the Employee in the database
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee loggedInEmployee = employeeRepository.findByLoginNameIgnoreCase(username);
        if (loggedInEmployee != null) {
            log.info(String.format("User successfully logged-in: %s", loggedInEmployee));
        }
        return loggedInEmployee;
        /*Employee loggedInEmployee = employeeRepository.findByLoginNameIgnoreCase(username);
        if (loggedInEmployee != null) {
            return loggedInEmployee;
        }
        loggedInEmployee = employeeRepository.findByEmailIgnoreCase(username);
        return loggedInEmployee;*/
    }
}
