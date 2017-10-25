package ru.rti.holidays.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.service.HolidayPeriodService;
import ru.rti.holidays.utility.GlobalConstants;

import java.util.*;
import java.util.function.Supplier;


public class EmployeeToEmployeeHolidayPeriodAdapter<T extends EmployeeHolidayPeriod> {
    private static final Logger log = LoggerFactory.getLogger(EmployeeToEmployeeHolidayPeriodAdapter.class);

    private HolidayPeriodService holidayPeriodServiceImpl;
    private Supplier<T> supplier;

    public EmployeeToEmployeeHolidayPeriodAdapter(Supplier<T> supplier, HolidayPeriodService holidayPeriodServiceImpl) {
        if (holidayPeriodServiceImpl == null) {
            throw new IllegalArgumentException("HolidayPeriodService instance cannot be null");
        }
        this.supplier = supplier;
        this.holidayPeriodServiceImpl = holidayPeriodServiceImpl;
    }

    private T createInstance() {
        return supplier.get();
    }

    private T convertInner(Employee employee, HolidayPeriod holidayPeriod, Team team) {

        T employeeHolidayPeriod = createInstance();

        employeeHolidayPeriod.setEmployeeEmail(employee.getEmail());
        employeeHolidayPeriod.setEmployeeFullName(employee.getFullName());
        employeeHolidayPeriod.setDateStart(holidayPeriod.getDateStart());
        employeeHolidayPeriod.setNumDays(holidayPeriod.getNumDays());
        employeeHolidayPeriod.setEmployeeRoleName(employee.getProjectRoleAsString());
        employeeHolidayPeriod.setHolidayPeriodNegotiationStatus(holidayPeriod.getNegotiationStatusAsString());
        employeeHolidayPeriod.setNegotiationStatus(holidayPeriod.getNegotiationStatus());
        employeeHolidayPeriod.setTeamId(team != null ? team.getId() : null);
        employeeHolidayPeriod.setTeamName(team != null ? team.getTeamName() : GlobalConstants.EMPTY_STRING);
        employeeHolidayPeriod.setHolidayPeriod(holidayPeriod);

        return employeeHolidayPeriod;
    }

    /**
     * Convert the single Employee instance to a Collection of EmployeeHolidayPeriod objects
     * @param employee
     * @return
     */
    public Collection<T> convert(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("`employee` instance cannot be null");
        }

        //TODO: default implementation is ArrayList
        Collection<T> convertedResult= new ArrayList<>();

        Team team = employee.getTeam();
        List<HolidayPeriod> holidayPeriods = holidayPeriodServiceImpl.getHolidayPeriodsForEmployee(employee);
        for (HolidayPeriod holidayPeriod : holidayPeriods) {
            convertedResult.add(convertInner(employee, holidayPeriod, team));
        }

        return convertedResult;
    }

    /**
     * Convert the Collection of Employee objects to a Collection of EmployeeHolidayPeriod objects
     * @param employees
     * @return
     */
    public Collection<T> convert(Collection<Employee> employees) {
        if (employees == null) {
            return null;
        }

        Collection<T> convertedResult = null;
        try {
            convertedResult = employees.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
        }

        for (Employee employee : employees) {
            Collection<T> convertedEmployee = convert(employee);
            convertedResult.addAll(convertedEmployee);
        }

        return convertedResult;
    }
}
