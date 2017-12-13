package ru.rti.holidays.utility;

import java.text.SimpleDateFormat;
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
        if (days <= 1) {
            return date;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days - 1);
        return calendar.getTime();
    }

    public static String getDateAsString(Date date, String format) {
        if (format == null) {
            format = GlobalConstants.DATE_FORMAT;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        if (date == null) return GlobalConstants.EMPTY_STRING;

        try {
            String dateAsString = sdf.format(date);
            return dateAsString;
        } catch (Exception e) {
            return GlobalConstants.EMPTY_STRING;
        }
    }

    public static String getDateAsString(LocalDate date, String format) {
        if (format == null) {
            format = GlobalConstants.DATE_FORMAT;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        if (date == null) return GlobalConstants.EMPTY_STRING;

        try {
            String dateAsString = date.format(formatter);
            return dateAsString;
        } catch (IllegalArgumentException e) {
            return GlobalConstants.EMPTY_STRING;
        }
    }
    public static String getDateAsString(LocalDate date) {
        return getDateAsString(date, GlobalConstants.DATE_FORMAT);
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
        if (CommonUtils.checkIfAnyIsNull(dateStart1, dateEnd1, dateStart2, dateEnd2)) {
            return false;
        }
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
