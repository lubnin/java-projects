package ru.rti.holidays.utility;

/**
 * Common Utilities, containing methods most widely used in the Application
 */
public class CommonUtils {

    public static <T> String getValueOrEmptyString(T object) {
        return object == null ? "" : object.toString();
    }
    public static boolean checkIfAnyIsNull(String ... args) {
        if (args == null) {
            return true;
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                return true;
            }
        }

        return false;
    }
}
