package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;
import ru.rti.holidays.utility.GlobalConstants;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Entity which describes the holiday period for a particular Employee in the company.
 * Each Employee can have multiple holiday periods (planned) in the working year.
 * And each HolidayPeriod is connected with only one particular Employee.
 */
@SpringComponent
@Entity
@Table(name = "holiday_period")
@SuppressWarnings("unused")
public class HolidayPeriod implements DBEntity {
    /**
     * The primary key for the table holding HolidayPeriod instances
     */
    //TODO: fix 4 to 1 sequence start value before going to production
    @GenericGenerator(
            name = "holidayPeriodSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_hol_period_id"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "4"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "holidayPeriodSequenceGenerator")
    @Column(name = "hol_period_id", unique = true, nullable = false)
    private Long id;

    /**
     * The staring date when the Employee is going on vacation (holidays).
     * It is a date-time value of the first day when Employee is out of office on a planned
     * and negotiated by managers basis.
     */
    @Column(name = "dateStart", nullable = false)
    private LocalDate dateStart;

    /**
     * The number of days in the holiday period. It is the amount of 'out-of-office' days,
     * while the Employee is on vacation (on holidays)
     */
    @Column(name = "numDays", nullable = false)
    private Long numDays;

    /**
     * Foreign key to the Employee table. The Employee instance for which this holiday period
     * is used.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "hp_negotiation_status_id")
    private HolidayPeriodNegotiationStatus negotiationStatus;

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

    public HolidayPeriod() {

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
    public Long getId() {
        return id;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public String getDateStartAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.DATE_FORMAT);

        if (dateStart == null) return GlobalConstants.EMPTY_STRING;

        try {
            String dateStartAsString = this.dateStart.format(formatter);
            return dateStartAsString;
        } catch (IllegalArgumentException e) {
            return GlobalConstants.EMPTY_STRING;
        }
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public String getNumDaysAsString() {
        return String.valueOf(getNumDays());
    }
    public void setNumDaysAsString(String strNumDays) {
        try {
            numDays = Long.parseLong(strNumDays);
        } catch (NumberFormatException e) {
            numDays = 0L;
        }
    }

    public Long getNumDays() {
        return numDays;
    }

    public void setNumDays(Long numDays) {
        this.numDays = numDays;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public HolidayPeriodNegotiationStatus getNegotiationStatus() {
        return negotiationStatus;
    }

    public String getNegotiationStatusAsString() {
        if (negotiationStatus == null) {
            return GlobalConstants.EMPTY_STRING;
        }
        return negotiationStatus.getStatusName();
    }

    public void setNegotiationStatus(HolidayPeriodNegotiationStatus negotiationStatus) {
        this.negotiationStatus = negotiationStatus;
    }

    @Override
    public String toString() {
        return String.format("HolidayPeriod[id=%d, dateStart='%s', numDays='%s', employee='%s', created='%s', updated='%s']",
                id,
                dateStart,
                numDays,
                employee,
                created, updated);
    }

    @Override
    public DBEntity construct() {
        return new HolidayPeriod();
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
