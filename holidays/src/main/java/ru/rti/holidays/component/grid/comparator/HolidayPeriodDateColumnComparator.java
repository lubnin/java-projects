package ru.rti.holidays.component.grid.comparator;

import com.vaadin.server.SerializableComparator;
import ru.rti.holidays.entity.HolidayPeriod;

import java.time.LocalDate;

public class HolidayPeriodDateColumnComparator implements SerializableComparator<HolidayPeriod> {
    @Override
    public int compare(HolidayPeriod hp1, HolidayPeriod hp2) {
        if (hp1 == null && hp2 == null) {
            return 0;
        }
        LocalDate dateStart1 = hp1.getDateStart();
        LocalDate dateStart2 = hp2.getDateStart();

        if (dateStart1 == null || dateStart2 == null) {
            return 0;
        }

        if (dateStart1.isBefore(dateStart2)) {
            return -1;
        } else if (dateStart2.isBefore(dateStart1)) {
            return 1;
        }

        return 0;
    }
}
