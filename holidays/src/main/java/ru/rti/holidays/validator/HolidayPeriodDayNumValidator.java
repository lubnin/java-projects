package ru.rti.holidays.validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

/**
 * Validator for checking & validating the values of holiday period 'number of days' parameter.
 */
public class HolidayPeriodDayNumValidator implements Validator<String> {
    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value == null || value.length() < 1) {
            return ValidationResult.error("Введите значение количества дней отпуска в поле");
        } else {
            try {
                Long daysNum = Long.parseLong(value);

                if (daysNum <= 0 || daysNum > 28) {
                    return ValidationResult.error("Количество дней отпуска должно быть целым числом от 1 до 28");
                }
            } catch (NumberFormatException e) {
                return ValidationResult.error("Количество дней отпуска должно быть целым числом");
            }
        }
        return ValidationResult.ok();
    }
}
