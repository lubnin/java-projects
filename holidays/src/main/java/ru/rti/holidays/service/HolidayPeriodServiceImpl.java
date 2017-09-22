package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.repository.HolidayPeriodNegotiationStatusRepository;

import java.util.List;

@Service
@UIScope
@SpringComponent
public class HolidayPeriodServiceImpl implements HolidayPeriodService {
    @Autowired
    private HolidayPeriodNegotiationStatusRepository holidayPeriodNegotiationStatusRepository;

    @Override
    public List<HolidayPeriodNegotiationStatus> getAllHolidayPeriodNegotiationStatuses() {
        return holidayPeriodNegotiationStatusRepository.findAll();
    }

    @Override
    public HolidayPeriodNegotiationStatus addHolidayPeriodNegotiationStatus(HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus) {
        return holidayPeriodNegotiationStatusRepository.saveAndFlush(holidayPeriodNegotiationStatus);
    }
}
