package ru.rti.holidays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rti.holidays.entity.SystemParameter;

@Repository
@SuppressWarnings("unused")
public interface SystemParameterRepository extends JpaRepository<SystemParameter, Long> {
    SystemParameter findByInnerName(String innerName);
    SystemParameter findByCaption(String caption);
}
