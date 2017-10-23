package ru.rti.holidays.utility;

public class GlobalConstants {
    /**
     * Empty string constant
     */
    public static final String EMPTY_STRING = "";

    /**
     * Date format which is used in calendars in UI Views
     */
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String YES_VALUE = "Да";
    public static final String NO_VALUE = "Нет";
    /**
     *  URL paths for pages
     */
    public static final String URL_PATH_MAIN_PAGE = "/main";
    public static final String URL_PATH_LOGOUT = "/logout";
    public static final String URL_PATH_LOGIN = "/login";
    /**
     * Administrator of the system
     */
    public static final String ADMIN_USER_LOGIN_NAME = "admin";
    /**
     * ================================================================================================
     * CSS Style Constants.
     * ATTENTION: if you want to change the value of these constants you
     * must change appropriate CSS-styles in '/resources/VAADIN.themes.rti/rti.scss' file!
     * ================================================================================================
     */

    /**
     * CSS Theme name
     */
    public static final String THEME_NAME = "rti";

    public static final String CSS_DEBUG_BORDER = "debug_border";

    /**
     * Style for Grid cells that contain 'New' status for HolidayPeriod
     */
    public static final String CSS_NEGOTIATION_STATUS_TYPE_NEW = "neg_status_new";

    /**
     * Style for Grid cells that contain 'Negotiating' status for HolidayPeriod
     */
    public static final String CSS_NEGOTIATION_STATUS_TYPE_NEGOTIATING = "neg_status_negotiating";

    /**
     * Style for Grid cells that contain 'Partly Negotiated' status for HolidayPeriod
     */
    public static final String CSS_NEGOTIATION_STATUS_TYPE_PARTLY_NEGOTIATED= "neg_status_partly_negotiated";

    /**
     * Style for Grid cells that contain 'OK' status for HolidayPeriod
     */
    public static final String CSS_NEGOTIATION_STATUS_TYPE_OK = "neg_status_ok";

    /**
     * Style for Grid cells that contain 'Rejected' status for HolidayPeriod
     */
    public static final String CSS_NEGOTIATION_STATUS_TYPE_REJECTED = "neg_status_rejected";

    /**
     * Style for Grid cells that have holiday periods having crossings by dates with other employees
     */
    public static final String CSS_HOLIDAY_PERIOD_CROSSING_DATES = "hp_crossing_dates";

    /**
     * Style for warning message when the Employees holiday period is crossing with other Employees dates
     */
    public static final String CSS_HOLIDAY_PERIOD_CROSSING_MESSAGE = "hp_crossing_message";

}
