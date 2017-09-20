package ru.rti.holidays.service;

import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;

import java.util.List;
import java.util.Set;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    void delete(Long id);
    List<Employee> getByLastName(String lastName);
    Employee getByLoginName(String loginName);
    List<Employee> getAllEmployees();
    List<HolidayPeriod> getHolidayPeriodsForEmployee(Employee employee);
    HolidayPeriod addHolidayPeriod(HolidayPeriod holidayPeriod);
    boolean deleteHolidayPeriods(Set<HolidayPeriod> holidayPeriods);
}
