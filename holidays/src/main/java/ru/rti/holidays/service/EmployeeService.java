package ru.rti.holidays.service;

import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

import java.util.List;
import java.util.Set;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    void delete(Long id);
    List<Employee> getByLastName(String lastName);
    Employee getByLoginName(String loginName);
    List<Employee> getAllEmployees();
    List<HolidayPeriod> getHolidayPeriodsForEmployee(Employee employee);
    HolidayPeriod saveHolidayPeriod(HolidayPeriod holidayPeriod);
    boolean deleteHolidayPeriods(Set<HolidayPeriod> holidayPeriods);
    boolean deleteEmployees(Set<Employee> employees);
}
