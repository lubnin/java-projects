package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@SpringComponent
@Entity
@Table(name = "holiday_period_neg_history")
public class HolidayPeriodNegotiationHistory implements DBEntity {
    /**
     * The primary key for the table holding Holiday Period Negotiation History instances
     */
    @GenericGenerator(
            name = "holidayPeriodNegHistorySequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_hol_period_neg_history_id"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "holidayPeriodNegHistorySequenceGenerator")
    @Column(name = "hol_period_neg_history_id", unique = true, nullable = false)
    private Long id;

    /**
     * The comment for this negotiation history
     */
    @Column(name = "comment", nullable = false)
    private String comment;

    /**
     * Old negotiation status before this negotiation history record appeared (old status can be equal to new if only comment added)
     */
    private String oldStatus;

    /**
     * New negotiation status after this negotiation history record appeared (old status can be equal to new if only comment added)
     */
    private String newStatus;

    /**
     * The reference to a HolidayPeriod instance this negotiation history belongs to
     */
    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "hol_period_id")
    private HolidayPeriod holidayPeriod;

    /**
     * The date when the record was created in DB the very first time
     */
    @Column(name = "created")
    private Date created;

    /**
     * The date when the record was last time updated in DB
     */
    @Column(name = "updated")
    private Date updated;

    @Override
    public Long getId() {
        return id;
    }

    @PrePersist
    public void onCreate() {
        created = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        updated = new Date();
    }

    @Override
    public Date getCreatedDate() {
        return created;
    }

    @Override
    public Date getUpdatedDate() {
        return updated;
    }

    public HolidayPeriod getHolidayPeriod() {
        return holidayPeriod;
    }

    public void setHolidayPeriod(HolidayPeriod holidayPeriod) {
        this.holidayPeriod = holidayPeriod;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return String.format("HolidayPeriodNegotiationHistory[id=%d, comment='%s', oldStatus='%s', newStatus='%s', created='%s', updated='%s']",
                id,
                comment,
                oldStatus,
                newStatus,
                created,
                updated);
    }
}
