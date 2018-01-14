package ru.rti.holidays.layout.common;

import com.vaadin.ui.ItemCaptionGenerator;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.entity.SystemParameter;

public class SystemParameterInnerTypeCaptionGenerator implements ItemCaptionGenerator<SystemParameter.SystemParameterType> {
    @Override
    public String apply(SystemParameter.SystemParameterType systemParameterInnerType) {
        switch (systemParameterInnerType) {
            case SETTING_TYPE_BOOLEAN:
                return "Логический тип (Boolean)";
            case SETTING_TYPE_DATE:
                return "Дата без времени (Date)";
            case SETTING_TYPE_DATETIME:
                return "Дата и время (DateTime)";
            case SETTING_TYPE_TIME:
                return "Время (Time)";
            case SETTING_TYPE_STRING:
                return "Строка (String)";
            case SETTING_TYPE_INTEGER:
                return "Целое число (Integer)";
            default:
                return "Неизвестный тип";
        }
    }
}
