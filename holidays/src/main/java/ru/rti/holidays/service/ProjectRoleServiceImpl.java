package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.repository.ProjectRoleRepository;

import java.util.List;
import java.util.Set;

@Service
@UIScope
@SpringComponent
public class ProjectRoleServiceImpl implements ProjectRoleService {

    @Autowired
    private ProjectRoleRepository projectRoleRepository;

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
    public boolean deleteProjectRoles(Set<ProjectRole> projectRoles) {
        projectRoleRepository.delete(projectRoles);
        return true;
    }
}
