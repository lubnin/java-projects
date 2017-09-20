package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@SpringComponent
@Entity
@Table(name = "holiday_period_neg_status")
public class HolidayPeriodNegotiationStatus implements DBEntity {
    //TODO: fix 9 to 1 before going to production mode
    @GenericGenerator(
            name = "holidayPeriodNegStatusSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_hol_period_neg_status_id"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "9"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "holidayPeriodNegStatusSequenceGenerator")
    @Column(name = "hol_period_neg_status_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "statusName")
    private String statusName;

    @PrePersist
    public void onCreate() {
        created = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        updated = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return String.format("HolidayPeriodNegotiationStatus[id=%d, statusName='%s', created='%s', updated='%s']",
                id,
                statusName,
                created,
                updated);
    }

    @Override
    public DBEntity construct() {
        return new HolidayPeriodNegotiationStatus();
    }
}
