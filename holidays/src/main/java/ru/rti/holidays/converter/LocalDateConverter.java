package ru.rti.holidays.converter;

import ru.rti.holidays.utility.DateUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

/**
 * The converter for storing LocalDate values in the database as a Date objects
 * and vice versa: for converting Date values from DB to LocalDate attributes of
 * Entity-classes
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        return DateUtils.asDate(date);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date value) {
        return DateUtils.asLocalDate(value);
    }
}
