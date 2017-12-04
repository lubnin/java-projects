package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.repository.EmployeeRepository;
import ru.rti.holidays.repository.HolidayPeriodRepository;
import ru.rti.holidays.utility.DateUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@UIScope
@SpringComponent
@SuppressWarnings("unused")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HolidayPeriodRepository holidayPeriodRepository;

    /**
     * Saves a single Employee object to the database
     * @param employee The Employee that needs to be saved in the database
     * @return the Employee instance that has been saved to the database
     */
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    /**
     * Saves the Iterable collection of Employees into the database.
     * @param employees The Iterable collection of Employees to save
     * @return <b>true</b> if the save operation is successful
     */
    @Override
    public List<Employee> saveEmployees(Iterable<Employee> employees) {
        List<Employee> result = employeeRepository.save(employees);
        employeeRepository.flush();
        return result;
    }

    /**
     * Removes the Employee from the database by his ID (primary identifier)
     * @param id The primary key of the Employee that needs to be removed from the database
     */
    @Override
    public void delete(Long id) {
        employeeRepository.delete(id);
    }

    /**
     * Gets the list of Employees from the database, searching by last name of the Employee.
     * @param lastName the last name of the Employee, that needs to be found
     * @return the list of Employees with the last name equal to `lastName` input parameter
     */
    @Override
    public List<Employee> getByLastName(String lastName) {
        return employeeRepository.findByLastName(lastName);
    }

    /**
     * Gets the List, containing all the available Employees in the Application
     * @return the list of all Employees in the Application
     */
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Flushes pending changes to the database
     */
    @Override
    public void flush() {
        employeeRepository.flush();
    }

    /**
     * Gets the Employee by his login name
     * @param loginName the login name of the Employee to search in the database
     * @return The Employee instance, fetched by login name provided or `null` value if no Employee is found.
     */
    @Override
    public Employee getByLoginName(String loginName) {
        return employeeRepository.findByLoginName(loginName);
    }

    /**
     * Gets the Employee by his login name using ignore case
     * @param loginName the login name of the Employee to search in the database
     * @return The Employee instance, fetched by login name provided or `null` value if no Employee is found.
     */
    @Override
    public Employee getByLoginNameIgnoreCase(String loginName) {
        return employeeRepository.findByLoginNameIgnoreCase(loginName);
    }


    /**
     * Gets the Employee from the database using his login name and password provided.
     * @param loginName The login name of the Employee
     * @param password The password of the Employee
     * @return The Employee instance, fetched by login name and password provided or `null` value if no Employee is found.
     */
    @Override
    public Employee getByLoginNameAndPassword(String loginName, String password) {
        return employeeRepository.findByLoginNameAndPassword(loginName, password);
    }

    /**
     * Removes the given Employees from the database.
     * @param employees The Iterable collection of Employees that should be removed from the database.
     * @return <b>true</b>, if the operation of deletion is successful
     */
    @Override
    public boolean deleteEmployees(Iterable<Employee> employees) {
        employeeRepository.delete(employees);
        return true;
    }

    /**
     * Gets all the Employees in the given team
     * @param teamId The team ID (primary identifier) for which we are getting all the Employees belonging to this team.
     * @return The Set of Employees belonging to the team, specified by teamId parameter
     */
    @Override
    public Set<Employee> getByTeamId(Long teamId) {
        return employeeRepository.findByTeamId(teamId);
    }

    /**
     * Gets the Set of Employees that are managers for current Employee
     * @param employee The current Employee instance for which we are gettings the Set of manager Employees
     * @return The Set of Employee instances that are managers for current Employee
     */
    @Override
    public Set<Employee> getAllManagersForEmployee(Employee employee) {
        Set<Employee> allManagers = employeeRepository.findAllManagersForEmployee(employee.getId());
        return allManagers;
    }

    /**
     * Gets the Set of Employees in the same team, that have intersections by HolidayPeriods with given Employee (by employeeId)
     *
     * @param employeeId The current Employee primary ID for which we search the intersections of Holiday Periods in the Employee's team
     * @param teamId The team ID to which the Employee belongs to
     * @param dateStart The start day for holiday period which is to be checked for intersections
     * @param numDays The number of days in the holiday period
     * @return The Set of Employee instances in the same team (teamId) that have holiday period with date intersections. The
     * Set is always non-null (if no intersections are found, the empty Set is returned).
     */
    @Override
    public Set<Employee> getEmployeesWithCrossingHolidayPeriods(Long employeeId, Long teamId, Date dateStart, Long numDays) {
        Set<Employee> allEmployeesForTeam = employeeRepository.findByTeamIdAndIdNot(teamId, employeeId);
        Set<Employee> resultEmployeeSet = new HashSet<>();
        if (allEmployeesForTeam != null && allEmployeesForTeam.size() > 0) {
            Date dateEnd = DateUtils.addDays(dateStart, numDays);

            for (Employee emp : allEmployeesForTeam) {
                List<HolidayPeriod> empHolidayPeriods = emp.getHolidayPeriods();
                if (empHolidayPeriods == null || empHolidayPeriods.size() == 0) continue;
                for (HolidayPeriod hp : empHolidayPeriods) {
                    Date curHolidayPeriodDateStart = DateUtils.asDate(hp.getDateStart());
                    Long curNumDays = hp.getNumDays();
                    Date curHolidayPeriodDateEnd = DateUtils.addDays(curHolidayPeriodDateStart, curNumDays);

                    if (DateUtils.isIntersectionBetweenDates(dateStart, dateEnd, curHolidayPeriodDateStart, curHolidayPeriodDateEnd)) {
                        resultEmployeeSet.add(emp);
                    }
                }
            }
        }
        return resultEmployeeSet;
    }

    @Override
    public Set<Employee> getAllEmployeesExcludingLoginName(String loginNameToExclude) {
        return employeeRepository.findByLoginNameNot(loginNameToExclude);
    }
}
