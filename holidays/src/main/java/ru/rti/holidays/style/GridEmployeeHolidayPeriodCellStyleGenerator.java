package ru.rti.holidays.style;

import com.vaadin.ui.StyleGenerator;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.utility.GlobalConstants;

public class GridEmployeeHolidayPeriodCellStyleGenerator implements StyleGenerator<EmployeeHolidayPeriod> {
    @Override
    public String apply(EmployeeHolidayPeriod employeeHolidayPeriod) {
        if (employeeHolidayPeriod == null ||
                employeeHolidayPeriod.getNegotiationStatus() == null || employeeHolidayPeriod.getNegotiationStatus().getNegotiationStatusType() == null) {
            return GlobalConstants.EMPTY_STRING;
        }

        switch (employeeHolidayPeriod.getNegotiationStatus().getNegotiationStatusType()) {
            case NEGOTIATION_STATUS_TYPE_NEGOTIATING:
                return GlobalConstants.CSS_NEGOTIATION_STATUS_TYPE_NEGOTIATING;
            case NEGOTIATION_STATUS_TYPE_OK:
                return GlobalConstants.CSS_NEGOTIATION_STATUS_TYPE_OK;
            case NEGOTIATION_STATUS_TYPE_REJECTED:
                return GlobalConstants.CSS_NEGOTIATION_STATUS_TYPE_REJECTED;
        }

        return GlobalConstants.EMPTY_STRING;
    }
}
