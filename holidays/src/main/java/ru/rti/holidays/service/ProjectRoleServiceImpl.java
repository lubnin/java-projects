package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.repository.ProjectRoleRepository;

import java.util.List;

@Service
@UIScope
@SpringComponent
@SuppressWarnings("unused")
public class ProjectRoleServiceImpl implements ProjectRoleService {
    private static final Logger log = LoggerFactory.getLogger(ProjectRoleServiceImpl.class);

    @Autowired
    private ProjectRoleRepository projectRoleRepository;

    @Autowired
    private EmployeeService employeeServiceImpl1;

    @Override
    public ProjectRole saveProjectRole(ProjectRole projectRole) {
        return projectRoleRepository.saveAndFlush(projectRole);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ProjectRole> getAllProjectRoles() {
        return projectRoleRepository.findAll();
    }

    @Override
    @Transactional
    public boolean deleteProjectRoles(Iterable<ProjectRole> projectRoles) {
        log.debug("ProjectRoleServiceImpl::deleteProjectRoles() called.");
        if (projectRoles != null) {
            for (ProjectRole projectRole : projectRoles) {
                int size = projectRole.getEmployees().size();
                if (size > 0) {
                    for (Employee emp : projectRole.getEmployees()) {
                        emp.setProjectRole(null);
                    }
                }
            }
            projectRoleRepository.delete(projectRoles);
            return true;
        }
        return false;
    }
}
