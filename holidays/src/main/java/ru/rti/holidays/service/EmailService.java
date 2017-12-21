package ru.rti.holidays.service;

import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;

import java.util.Set;

public interface EmailService {
    boolean sendMailHolidayPeriodSubmitted(Iterable<HolidayPeriod> holidayPeriods, Employee employee, Set<Employee> managers);
    boolean sendMailHolidayPeriodsNegotiated(Iterable<EmployeeHolidayPeriod> holidayPeriods, Employee manager);
    boolean sendMailHolidayPeriodsRejected(Iterable<EmployeeHolidayPeriod> holidayPeriods, Employee manager);
    boolean sendMail(String to, String messageBody, String subject);
}
