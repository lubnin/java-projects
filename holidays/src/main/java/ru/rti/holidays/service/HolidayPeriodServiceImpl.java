package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.repository.HolidayPeriodNegotiationStatusRepository;
import ru.rti.holidays.repository.HolidayPeriodRepository;

import java.util.List;
import java.util.Set;

@Service
@UIScope
@SpringComponent
@SuppressWarnings("unused")
public class HolidayPeriodServiceImpl implements HolidayPeriodService {
    @Autowired
    private HolidayPeriodNegotiationStatusRepository holidayPeriodNegotiationStatusRepository;

    @Autowired
    private HolidayPeriodRepository holidayPeriodRepository;

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

    public List<HolidayPeriod> getHolidayPeriodsForEmployee(Employee employee) {
        return holidayPeriodRepository.findByEmployeeId(employee.getId());
    }
    @Override
    public boolean deleteHolidayPeriods(Iterable<HolidayPeriod> holidayPeriods) {
        holidayPeriodRepository.delete(holidayPeriods);
        return true;
    }

    @Override
    public HolidayPeriod saveHolidayPeriod(HolidayPeriod holidayPeriod) {
        return holidayPeriodRepository.saveAndFlush(holidayPeriod);
    }

    @Override
    public boolean setNegotiationStatusForEmployeeHolidayPeriods(Iterable<EmployeeHolidayPeriod> holidayPeriods, HolidayPeriodNegotiationStatus negotiationStatus) {
        if (holidayPeriods == null || negotiationStatus == null) {
            return false;
        }

        for (EmployeeHolidayPeriod ehp : holidayPeriods) {
            HolidayPeriod hp = ehp.getHolidayPeriod();
            hp.setNegotiationStatus(negotiationStatus);
            holidayPeriodRepository.saveAndFlush(hp);
        }
        return false;
    }

    @Override
    public boolean setNegotiationStatusForHolidayPeriods(Iterable<HolidayPeriod> holidayPeriods, HolidayPeriodNegotiationStatus negotiationStatus) {
        if (holidayPeriods == null || negotiationStatus == null) {
            return false;
        }

        for (HolidayPeriod hp : holidayPeriods) {
            hp.setNegotiationStatus(negotiationStatus);
            holidayPeriodRepository.saveAndFlush(hp);
        }
        return false;
    }
}
