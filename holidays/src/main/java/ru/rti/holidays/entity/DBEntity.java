package ru.rti.holidays.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Common interface-marker to be implemented by all Entity-classes of the Application
 */
public interface DBEntity {
    /**
     * This method should return the Primary Key field value for the particular {@link DBEntity} instance
     * @return the numeric value of the Primary Key of the instance
     */
    Long getId();

    /**
     * This method should be implemented by setting the field in the table with the value of current Date and Time
     * when the record is created in the appropriate DB table
     */
    @PrePersist
    void onCreate();

    /**
     * This method should be implemented by setting the field in the table with the value of current Date and Time
     * when the record is updated in the appropriate DB table
     */
    @PreUpdate
    void onUpdate();

    /**
     * Constructs the instance of particular class implementing the {@link DBEntity} interface
     * @return
     */
    DBEntity construct();
}
