package ru.rti.holidays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rti.holidays.entity.Department;

@Repository
@SuppressWarnings("unused")
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
