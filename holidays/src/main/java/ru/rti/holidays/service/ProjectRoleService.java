package ru.rti.holidays.service;

import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;

import java.util.List;
import java.util.Set;

public interface ProjectRoleService {
    ProjectRole saveProjectRole(ProjectRole projectRole);
    void delete(Long id);
    List<ProjectRole> getAllProjectRoles();
    boolean deleteProjectRoles(Set<ProjectRole> projectRoles);
}
