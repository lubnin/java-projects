package ru.rti.holidays.service;

import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;

import java.util.Set;

public interface EmailService {
    public boolean sendMailHolidayPeriodSubmitted(HolidayPeriod holidayPeriod, Employee employee, Set<Employee> managers);
    public boolean sendMailHolidayPeriodsNegotiated(Iterable<EmployeeHolidayPeriod> holidayPeriods, Employee manager);
    public boolean sendMailHolidayPeriodsRejected(Iterable<EmployeeHolidayPeriod> holidayPeriods, Employee manager);
    public boolean sendMail(String to, String messageBody, String subject);
}
