package ru.rti.holidays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rti.holidays.entity.HolidayPeriod;

import java.util.List;

@Repository
@SuppressWarnings("unused")
public interface HolidayPeriodRepository extends JpaRepository<HolidayPeriod, Long> {
    List<HolidayPeriod> findByEmployeeId(Long employeeId);
    HolidayPeriod findById(Long id);
}
