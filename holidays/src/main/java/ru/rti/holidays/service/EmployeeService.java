package ru.rti.holidays.service;

import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.entity.ProjectRole;

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
    //void resetProjectRoleForEmployees(ProjectRole projectRoleId);
    List<HolidayPeriod> getHolidayPeriodsForEmployee(Employee employee);
    HolidayPeriod saveHolidayPeriod(HolidayPeriod holidayPeriod);
    boolean deleteHolidayPeriods(Iterable<HolidayPeriod> holidayPeriods);
    boolean deleteEmployees(Iterable<Employee> employees);
    void flush();
}
