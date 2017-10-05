package ru.rti.holidays.style;

import com.vaadin.ui.StyleGenerator;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.utility.GlobalConstants;

/**
 * The class that is used in Grid controls that are based on 'HolidayPeriod' Entities for CSS-styling of separate cells
 */
public class GridHolidayPeriodCellStyleGenerator implements StyleGenerator<HolidayPeriod> {
    @Override
    public String apply(HolidayPeriod holidayPeriod) {
        if (holidayPeriod == null) {
            return GlobalConstants.EMPTY_STRING;
        }

        if (holidayPeriod.getNegotiationStatus() != null && holidayPeriod.getNegotiationStatus().getNegotiationStatusType() != null) {
            switch (holidayPeriod.getNegotiationStatus().getNegotiationStatusType()) {
                case NEGOTIATION_STATUS_TYPE_NEGOTIATING:
                    return GlobalConstants.CSS_NEGOTIATION_STATUS_TYPE_NEGOTIATING;
                case NEGOTIATION_STATUS_TYPE_OK:
                    return GlobalConstants.CSS_NEGOTIATION_STATUS_TYPE_OK;
                case NEGOTIATION_STATUS_TYPE_REJECTED:
                    return GlobalConstants.CSS_NEGOTIATION_STATUS_TYPE_REJECTED;
            }
        }
        return GlobalConstants.EMPTY_STRING;
    }
}
