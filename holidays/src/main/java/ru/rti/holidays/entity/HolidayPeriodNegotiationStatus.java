package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringComponent
@Entity
@Table(name = "holiday_period_neg_status")
@SuppressWarnings("unused")
public class HolidayPeriodNegotiationStatus implements DBEntity {
    //TODO: fix 9 to 1 sequence start value before going to production mode
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

    @Column(name = "statusName", nullable = false)
    private String statusName;

    @Column(name = "statusDescription")
    private String statusDescription;

    @OneToMany(mappedBy = "negotiationStatus", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    private Set<HolidayPeriod> holidayPeriods;

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

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Set<HolidayPeriod> getHolidayPeriods() {
        return holidayPeriods;
    }

    public void setHolidayPeriods(Set<HolidayPeriod> holidayPeriods) {
        this.holidayPeriods = holidayPeriods;
    }

    @Override
    public String toString() {
        return String.format("HolidayPeriodNegotiationStatus[id=%d, statusName='%s', statusDescription='%s', created='%s', updated='%s']",
                id,
                statusName,
                statusDescription,
                created,
                updated);
    }

    @Override
    public DBEntity construct() {
        return new HolidayPeriodNegotiationStatus();
    }

    @Override
    public Date getCreatedDate() {
        return created;
    }

    @Override
    public Date getUpdatedDate() {
        return updated;
    }
}
