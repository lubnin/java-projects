package ru.rti.holidays.utility;

/**
 * Common Utilities, containing methods most widely used in the Application
 */
public class CommonUtils {

    /**
     * Gets the string representation of given object. Calls 'toString()' if the object is not null.
     * If the reference to object is null an empty string is returned
     * @param object the object which string value is needed
     * @param <T> type of the object
     * @return string value of the object if it is not null or an empty string if the object is null
     */
    public static <T> String getValueOrEmptyString(T object) {
        return object == null ? GlobalConstants.EMPTY_STRING : object.toString();
    }

    public static <T> boolean checkIfEmpty(T object) {
        if (object instanceof String) {
            return object == null || GlobalConstants.EMPTY_STRING.equals(object);
        } else {
            return object == null || GlobalConstants.EMPTY_STRING.equals(object.toString());
        }
    }

    /**
     * Checks if any of the given parameters is null
     * @param args array of values
     * @return true if at least one of the arguments is null
     */
    public static <T> boolean checkIfAnyIsNull(T ... args) {
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

    /**
     * Checks if any of the given parameters is null or empty (for string it is an empty string)
     * @param args array of values
     * @return true if at least one of the arguments is null or an empty string
     */
    public static <T> boolean checkIfAnyIsEmpty(T ... args) {
        if (args == null) {
            return true;
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i] == null || GlobalConstants.EMPTY_STRING.equals(args[i])) {
                return true;
            }
        }

        return false;
    }

    public static <T> boolean checkIfEqual(T object1, T object2) {
        if (object1 == null && object2 == null) {
            return true;
        }
        if (object1 != null) {
            return object1.equals(object2);
        }
        return object2.equals(object1);
    }

    public static <T> boolean checkIfNotEqual(T object1, T object2) {
        return !checkIfEqual(object1, object2);
    }
}
