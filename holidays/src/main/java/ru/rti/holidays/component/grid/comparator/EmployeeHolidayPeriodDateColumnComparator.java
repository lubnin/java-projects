package ru.rti.holidays.component.grid.comparator;

import com.vaadin.server.SerializableComparator;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;

import java.time.LocalDate;

public class EmployeeHolidayPeriodDateColumnComparator implements SerializableComparator<EmployeeHolidayPeriod> {
    @Override
    public int compare(EmployeeHolidayPeriod ehp1, EmployeeHolidayPeriod ehp2) {
        if (ehp1 == null && ehp2 == null) {
            return 0;
        }
        LocalDate dateStart1 = ehp1.getDateStart();
        LocalDate dateStart2 = ehp2.getDateStart();

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
