package ru.rti.holidays.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Common interface-marker to be implemented by all Entity-classes of the Application
 */
@SuppressWarnings("unused")
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
     * This method should be implemented by getting the Entity class property representing the 'created date' value of
     * this Entity. The property value is stored in the DataSource when saving the Entity the very first time.
     * @return Date value representing when the Entity object was first time created
     */
    Date getCreatedDate();

    /**
     * This method should be implemented by getting the Entity class property representing the 'updated date' value of
     * this Entity. The property value is stored in the DataSource when updating the Entity. When the Entity
     * is saved in the DataSource the first time this value is 'null'.
     * @return Date value representing the last time when the Entity object was updated.
     */
    Date getUpdatedDate();
}
