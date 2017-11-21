package ru.rti.holidays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rti.holidays.entity.HolidayPeriodNegotiationHistory;

import java.util.List;

@Repository
public interface HolidayPeriodNegotiationHistoryRepository extends JpaRepository<HolidayPeriodNegotiationHistory, Long> {
    List<HolidayPeriodNegotiationHistory> findById(Long id);
}
