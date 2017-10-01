package ru.rti.holidays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rti.holidays.entity.ProjectRole;

import java.util.List;

@Repository
@SuppressWarnings("unused")
public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {
    List<ProjectRole> findById(Long id);
}
