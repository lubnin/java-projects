package ru.rti.holidays.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.repository.EmployeeRepository;

@Service("authService")
public class UserAuthorizationServiceImpl implements UserDetailsService {
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
        return employeeRepository.findByLoginNameIgnoreCase(username);
        /*Employee loggedInEmployee = employeeRepository.findByLoginNameIgnoreCase(username);
        if (loggedInEmployee != null) {
            return loggedInEmployee;
        }
        loggedInEmployee = employeeRepository.findByEmailIgnoreCase(username);
        return loggedInEmployee;*/
    }
}
