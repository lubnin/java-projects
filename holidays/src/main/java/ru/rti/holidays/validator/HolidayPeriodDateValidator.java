package ru.rti.holidays.validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

import java.time.LocalDate;

/**
 * Validator for checking & validating the values of holiday period date-time value.
 */
public class HolidayPeriodDateValidator implements Validator<LocalDate> {
    @Override
    public ValidationResult apply(LocalDate value, ValueContext context) {
        if (value == null) {
            return ValidationResult.error("Введите дату начала Вашего отпуска");
        }

        if (value.isBefore(LocalDate.now())) {
            return ValidationResult.error("Дата начала отпуска не может быть в прошлом");
        }

        LocalDate nowPlus14Days = LocalDate.now().plusDays(14);
        if (value.isBefore(nowPlus14Days)) {
            return ValidationResult.error("Дата начала отпуска должна отстоять от текущей даты на 14 дней (2 недели)");
        }

        return ValidationResult.ok();
    }
}
