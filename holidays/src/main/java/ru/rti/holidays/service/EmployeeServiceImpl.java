package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.repository.EmployeeRepository;
import ru.rti.holidays.repository.HolidayPeriodRepository;

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

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public List<Employee> saveEmployees(Iterable<Employee> employees) {
        List<Employee> result = employeeRepository.save(employees);
        employeeRepository.flush();
        return result;
    }

    @Override
    public void delete(Long id) {
        employeeRepository.delete(id);
    }

    @Override
    public List<Employee> getByLastName(String lastName) {
        return employeeRepository.findByLastName(lastName);
    }

    @Override
    //TODO: remove comment later
    //@Query(value = "SELECT * FROM Employee e LEFT JOIN FETCH Employee.projectRole")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    //@Override
    //public void resetProjectRoleForEmployees(ProjectRole projectRole) {
        //employeeRepository.resetProjectRoleForEmployees(projectRole);
        //employeeRepository.flush();
    //}

    @Override
    public void flush() {
        employeeRepository.flush();
    }

    public List<HolidayPeriod> getHolidayPeriodsForEmployee(Employee employee) {
        return holidayPeriodRepository.findByEmployeeId(employee.getId());
    }

    @Override
    public HolidayPeriod saveHolidayPeriod(HolidayPeriod holidayPeriod) {
        return holidayPeriodRepository.saveAndFlush(holidayPeriod);
    }

    @Override
    public boolean deleteHolidayPeriods(Iterable<HolidayPeriod> holidayPeriods) {
        holidayPeriodRepository.delete(holidayPeriods);
        return true;
    }

    @Override
    public Employee getByLoginName(String loginName) {
        return employeeRepository.findByLoginName(loginName);
    }

    @Override
    public Employee getByLoginNameAndPassword(String loginName, String password) {
        return employeeRepository.findByLoginNameAndPassword(loginName, password);
    }

    @Override
    public boolean deleteEmployees(Iterable<Employee> employees) {
        employeeRepository.delete(employees);
        return true;
    }

    @Override
    public Set<Employee> getByTeamId(Long teamId) {
        return employeeRepository.findByTeamId(teamId);
    }

    @Override
    public Set<Employee> getAllManagersForEmployee(Employee employee) {
        Set<Employee> allManagers = employeeRepository.findAllManagersForEmployee(employee.getId());
        return allManagers;
    }
}
