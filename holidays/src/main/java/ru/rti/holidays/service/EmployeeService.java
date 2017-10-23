package ru.rti.holidays.service;

import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.entity.ProjectRole;

import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> saveEmployees(Iterable<Employee> employees);
    void delete(Long id);
    List<Employee> getByLastName(String lastName);
    Employee getByLoginName(String loginName);
    Employee getByLoginNameAndPassword(String loginName, String password);
    Set<Employee> getByTeamId(Long teamId);
    List<Employee> getAllEmployees();
    boolean deleteEmployees(Iterable<Employee> employees);
    Set<Employee> getAllManagersForEmployee(Employee employee);
    Set<Employee> getEmployeesWithCrossingHolidayPeriods(Long employeeId, Long teamId, Date dateStart, Long numDays);
    void flush();
}
