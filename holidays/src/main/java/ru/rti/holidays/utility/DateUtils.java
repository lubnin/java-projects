package ru.rti.holidays.utility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class to convert between new Java 1.8 LocalDate & LocalDateTime classes
 * and Date class
 */
public class DateUtils {

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static String getDateAsString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.DATE_FORMAT);

        if (date == null) return GlobalConstants.EMPTY_STRING;

        try {
            String dateStartAsString = date.format(formatter);
            return dateStartAsString;
        } catch (IllegalArgumentException e) {
            return GlobalConstants.EMPTY_STRING;
        }
    }

    public static Date addDays(Date date, Long days) {
        return addDays(date, days.intValue());
    }

    /**
     * Checks the intersection between two date intervals - (dateStart1; dateEnd1) and (dateStart2; dateEnd2).
     * Returns true in the following cases:
     * 1) Date period (dateStart2; dateEnd2) is fully contained in the period (dateStart1; dateEnd1)
     * 2) There is an intersection of periods (dateStart1; dateEnd1) and (dateStart2; dateEnd2) of any kind. This
     * means that these date periods intersect at least by 1 day
     * @param dateStart1
     * @param dateEnd1
     * @param dateStart2
     * @param dateEnd2
     * @return
     */
    public static boolean isIntersectionBetweenDates(Date dateStart1, Date dateEnd1, Date dateStart2, Date dateEnd2) {
        boolean isIntersectionBetweenDates = false;
        if (dateStart2.after(dateStart1) && dateEnd2.before(dateEnd1)) {
            isIntersectionBetweenDates = true; // full containment of (dateStart2; dateEnd2) in period (dateStart1; dateEnd1)
        } else if ((dateStart2.after(dateStart1) || dateStart2.equals(dateStart1)) && (dateStart2.before(dateEnd1) || dateStart2.equals(dateEnd1))) {
            isIntersectionBetweenDates = true; // the period (dateStart2; dateEnd2) goes after period (dateStart1; dateEnd1) and intersects with it
        } else if ((dateStart2.before(dateStart1) || dateStart2.equals(dateStart1)) && (dateEnd2.after(dateStart1) || dateEnd2.equals(dateStart1))) {
            isIntersectionBetweenDates =  true; // the period (dateStart2; dateEnd2) goes before period (dateStart1; dateEnd1) and intersects with it
        }
        return isIntersectionBetweenDates;
    }
}
