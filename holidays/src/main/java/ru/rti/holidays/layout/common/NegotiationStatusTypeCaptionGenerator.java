package ru.rti.holidays.layout.common;

import com.vaadin.ui.ItemCaptionGenerator;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;

public class NegotiationStatusTypeCaptionGenerator implements ItemCaptionGenerator<HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType> {
    @Override
    public String apply(HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType negotiationStatusType) {
        if (negotiationStatusType != null) {
            return negotiationStatusType.getDescription();
        } else {
            return "Неизвестный статус";
        }
//        switch (negotiationStatusType) {
//            case NEGOTIATION_STATUS_TYPE_NEGOTIATING:
//                return "На согласовании";
//            case NEGOTIATION_STATUS_TYPE_OK:
//                return "Согласован";
//            case NEGOTIATION_STATUS_TYPE_REJECTED:
//                return "Отклонён";
//            case NEGOTIATION_STATUS_TYPE_NEW:
//                return "Новый";
//            case NEGOTIATION_STATUS_TYPE_PARTLY_NEGOTIATED:
//                return "Частично согласован";
//            case NEGOTIATION_STATUS_TYPE_RECALLED:
//                return
//            default:
//                return "Неизвестный статус";
//        }
    }
}
