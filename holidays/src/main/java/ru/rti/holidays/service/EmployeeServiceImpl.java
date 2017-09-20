package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.repository.EmployeeRepository;
import ru.rti.holidays.repository.HolidayPeriodRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@UIScope
@SpringComponent
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HolidayPeriodRepository holidayPeriodRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
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
    //@Query(value = "SELECT * FROM Employee e LEFT JOIN FETCH Employee.projectRole")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<HolidayPeriod> getHolidayPeriodsForEmployee(Employee employee) {
        return holidayPeriodRepository.findByEmployeeId(employee.getId());
    }

    @Override
    public HolidayPeriod addHolidayPeriod(HolidayPeriod holidayPeriod) {
        return holidayPeriodRepository.saveAndFlush(holidayPeriod);
    }

    @Override
    public boolean deleteHolidayPeriods(Set<HolidayPeriod> holidayPeriods) {
        holidayPeriodRepository.delete(holidayPeriods);
        return true;
    }

    @Override
    public Employee getByLoginName(String loginName) {
        return employeeRepository.findByLoginName(loginName);
    }
}
