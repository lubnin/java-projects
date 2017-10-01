package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.repository.HolidayPeriodNegotiationStatusRepository;

import java.util.List;
import java.util.Set;

@Service
@UIScope
@SpringComponent
@SuppressWarnings("unused")
public class HolidayPeriodServiceImpl implements HolidayPeriodService {
    @Autowired
    private HolidayPeriodNegotiationStatusRepository holidayPeriodNegotiationStatusRepository;

    @Override
    public List<HolidayPeriodNegotiationStatus> getAllHolidayPeriodNegotiationStatuses() {
        return holidayPeriodNegotiationStatusRepository.findAll();
    }

    @Override
    public HolidayPeriodNegotiationStatus saveHolidayPeriodNegotiationStatus(HolidayPeriodNegotiationStatus holidayPeriodNegotiationStatus) {
        return holidayPeriodNegotiationStatusRepository.saveAndFlush(holidayPeriodNegotiationStatus);
    }

    @Override
    public boolean deleteHolidayPeriodNegotiationStatuses(Iterable<HolidayPeriodNegotiationStatus> holidayPeriodNegotiationStatuses) {
        holidayPeriodNegotiationStatusRepository.delete(holidayPeriodNegotiationStatuses);
        return true;
    }
}
