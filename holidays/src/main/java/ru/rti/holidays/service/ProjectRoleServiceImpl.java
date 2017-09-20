package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.repository.ProjectRoleRepository;

import java.util.List;

@Service
@UIScope
@SpringComponent
public class ProjectRoleServiceImpl implements ProjectRoleService {

    @Autowired
    private ProjectRoleRepository projectRoleRepository;

    @Override
    public ProjectRole addProjectRole(ProjectRole projectRole) {
        return projectRoleRepository.saveAndFlush(projectRole);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ProjectRole> getAllProjectRoles() {
        return projectRoleRepository.findAll();
    }
}
