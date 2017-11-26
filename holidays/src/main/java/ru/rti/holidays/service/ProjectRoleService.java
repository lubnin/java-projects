package ru.rti.holidays.service;

import ru.rti.holidays.entity.ProjectRole;

import java.util.List;

public interface ProjectRoleService {
    ProjectRole saveProjectRole(ProjectRole projectRole);
    void delete(Long id);
    List<ProjectRole> getAllProjectRoles();
    boolean deleteProjectRoles(Iterable<ProjectRole> projectRoles);
}
