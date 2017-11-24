package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationHistory;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.repository.HolidayPeriodNegotiationHistoryRepository;
import ru.rti.holidays.repository.HolidayPeriodNegotiationStatusRepository;
import ru.rti.holidays.repository.HolidayPeriodRepository;
import ru.rti.holidays.utility.HolidayPeriodNegotiationStatusUtils;
import ru.rti.holidays.utility.HolidayPeriodUtils;

import java.util.Collection;
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

    @Autowired
    private HolidayPeriodNegotiationHistoryRepository holidayPeriodNegotiationHistoryRepository;

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
        try {
            if (holidayPeriods != null) {
                for (HolidayPeriod hp : holidayPeriods) {
                    Set<HolidayPeriodNegotiationHistory> histories = hp.getHolidayPeriodNegotiationHistories();
                    if (histories != null) {
                        holidayPeriodNegotiationHistoryRepository.deleteInBatch(histories);
                    }
                }
            }
            holidayPeriodRepository.deleteInBatch(holidayPeriods);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public HolidayPeriod saveHolidayPeriod(HolidayPeriod holidayPeriod) {
        return holidayPeriodRepository.saveAndFlush(holidayPeriod);
    }

    @Override
    public HolidayPeriodNegotiationHistory saveHolidayPeriodNegotiationHistory(HolidayPeriodNegotiationHistory holidayPeriodNegotiationHistory) {
        return holidayPeriodNegotiationHistoryRepository.saveAndFlush(holidayPeriodNegotiationHistory);
    }

    @Override
    public boolean setNegotiationStatusForEmployeeHolidayPeriods(Employee currentManager,
                                                                 Iterable<EmployeeHolidayPeriod> holidayPeriods,
                                                                 Collection<HolidayPeriodNegotiationStatus> allStatuses) {
        if (holidayPeriods == null || allStatuses == null || currentManager == null) {
            return false;
        }

        for (EmployeeHolidayPeriod ehp : holidayPeriods) {
            HolidayPeriod hp = ehp.getHolidayPeriod();
            HolidayPeriodNegotiationStatus currentStatus = hp.getNegotiationStatus();
            hp.setNegotiationMaskByManager(currentManager);
            HolidayPeriodNegotiationStatus nextStatus = HolidayPeriodNegotiationStatusUtils.getNextStatusByNegotiationMask(hp, allStatuses);
            if (nextStatus != null && !nextStatus.equals(currentStatus)) {
                hp.setNegotiationStatus(nextStatus);
            }
            holidayPeriodRepository.saveAndFlush(hp);
        }
        return false;
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
