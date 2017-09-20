package ru.rti.holidays.utility;

/**
 * Common Utilities, containing methods most widely used in the Application
 */
public class CommonUtils {

    public static <T> String getValueOrEmptyString(T object) {
        return object == null ? "" : object.toString();
    }
}
