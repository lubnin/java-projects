package ru.rti.holidays.service;

import ru.rti.holidays.entity.Department;
import ru.rti.holidays.entity.Team;

import java.util.List;

public interface DepartmentService {
    Department saveDepartment(Department department);
    void delete(Long id);
    List<Department> getAllDepartmentsSortedByNameAsc();
    boolean deleteDepartments(Iterable<Department> departments);
}
