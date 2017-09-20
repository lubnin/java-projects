package ru.rti.holidays.service;

import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.ProjectRole;

import java.util.List;

public interface ProjectRoleService {
    ProjectRole addProjectRole(ProjectRole projectRole);
    void delete(Long id);
    List<ProjectRole> getAllProjectRoles();
}
