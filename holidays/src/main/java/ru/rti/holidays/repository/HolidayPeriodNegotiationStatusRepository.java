package ru.rti.holidays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

import java.util.List;

@Repository
public interface HolidayPeriodNegotiationStatusRepository extends JpaRepository<HolidayPeriodNegotiationStatus, Long> {
    List<HolidayPeriodNegotiationStatus> findById(Long id);
}
