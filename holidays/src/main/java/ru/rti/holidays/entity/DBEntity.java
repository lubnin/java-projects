package ru.rti.holidays.entity;

/**
 * Common interface-marker to be implemented by all Entity-classes of the Application
 */
public interface DBEntity {
    Long getId();
    void onCreate();
    void onUpdate();
    DBEntity construct();
}
