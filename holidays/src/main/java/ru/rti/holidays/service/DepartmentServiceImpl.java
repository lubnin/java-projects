package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.Department;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.repository.DepartmentRepository;

import java.util.List;

@Service
@UIScope
@SpringComponent
@SuppressWarnings("unused")
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void delete(Long id) {
        departmentRepository.delete(id);
    }

    @Override
    public boolean deleteDepartments(Iterable<Department> departments) {
        departmentRepository.deleteInBatch(departments);
        return true;
    }

    @Override
    public List<Department> getAllDepartmentsSortedByNameAsc() {
        List<Department> allDepartments = departmentRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
        return allDepartments;
    }
}
