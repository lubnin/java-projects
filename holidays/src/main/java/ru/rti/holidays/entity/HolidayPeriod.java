package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.LazyInitializationException;
import org.hibernate.annotations.GenericGenerator;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.utility.CommonUtils;
import ru.rti.holidays.utility.DateUtils;
import ru.rti.holidays.utility.GlobalConstants;
import ru.rti.holidays.utility.HolidayPeriodUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @Transient
    public static final byte NEGOTIATION_MASK_NONE = 0;
    @Transient
    public static final byte NEGOTIATION_MASK_TEAM_LEAD_ONLY = 1;
    @Transient
    public static final byte NEGOTIATION_MASK_PROJECT_MANAGER_ONLY = 2;
    @Transient
    public static final byte NEGOTIATION_MASK_LINE_MANAGER_ONLY = 4;
    /* Combinations */
    @Transient
    public static final byte NEGOTIATION_MASK_TEAM_LEAD_AND_PROJECT_MANAGER = 3;
    @Transient
    public static final byte NEGOTIATION_MASK_LINE_MANAGER_AND_TEAM_LEAD = 5;
    @Transient
    public static final byte NEGOTIATION_MASK_LINE_MANAGER_AND_PROJECT_MANAGER = 6;
    @Transient
    public static final byte NEGOTIATION_MASK_ALL = 7;

    /**
     * The primary key for the table holding HolidayPeriod instances
     */
    @GenericGenerator(
            name = "holidayPeriodSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_hol_period_id"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
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

    @ManyToOne()
    @JoinColumn(name = "hp_negotiation_status_id")
    private HolidayPeriodNegotiationStatus negotiationStatus;

    /**
     * The byte value (mask) which is the negotiation status mask for this holiday period.
     * The abbreviations used below are:
     * LM = Line Manager for the Employee which submitted the holiday period
     * PM = Project Manager for the Employee which submitted the holiday period
     * TL = Team Lead of the Team the Employee which submitted the holiday period belongs to
     *
     * The available final values and meaning of value bits are the following:
     * LM  PM  TL    Final Value    Description
     *  0   0   0     = 0           This holiday period has not been negotiated by any of managers: LM, PM and TL
     *  0   0   1     = 1           This holiday period has been negotiated by TL
     *  0   1   0     = 2           This holiday period has been negotiated by PM
     *  0   1   1     = 3           This holiday period has been negotiated by TL & PM
     *  1   0   0     = 4           This holiday period has been negotiated by LM
     *  1   0   1     = 5           This holiday period has been negotiated by LM & TL
     *  1   1   0     = 6           This holiday period has been negotiated by LM & PM
     *  1   1   1     = 7           This holiday period has been negotiated by All managers : TL, PM & LM or Supervisor (Supervisor sets bit mask to 7)
     */
    @Column(name = "negotiationMask")
    private Byte negotiationMask;

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

    @OneToMany(mappedBy = "holidayPeriod", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    private Set<HolidayPeriodNegotiationHistory> holidayPeriodNegotiationHistories;


    @Transient
    private boolean isCrossingDates;

    public boolean isCrossingDatesDetected() {
        return isCrossingDates;
    }

    public String isCrossingDatesAsString() {
        return isCrossingDates ? GlobalConstants.YES_VALUE : GlobalConstants.NO_VALUE;
    }
    public void setCrossingDates(boolean isCrossingDatesDetected) {
        isCrossingDates = isCrossingDatesDetected;
    }

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
        if (getNumDays() == null) {
            return GlobalConstants.EMPTY_STRING;
        }
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

    public Byte getNegotiationMask() {
        return negotiationMask;
    }

    public void setNegotiationMask(Byte negotiationMask) {
        if (negotiationMask == null) {
            this.negotiationMask = 0;
            return;
        }
        this.negotiationMask = negotiationMask;
    }

    public Byte getSafeNegotiationMask() {
        return negotiationMask == null ? Byte.valueOf((byte)0) : negotiationMask;
    }
    public byte getSafeNegotiationMaskValue() { return getSafeNegotiationMask().byteValue(); }

    public void clearNegotiationMaskByManager(Employee manager, String deptCode) {
        if (manager == null  || !manager.isManager() || deptCode == null) {
            return;
        }

        if (deptCode.equals(GlobalConstants.DEPT_DIVISION_B2C)) {
            // fore TeamLead flag to be set for BA's. As there's no really TL which
            // is negotiating these Employees
            setNegotiationMask((byte)(getSafeNegotiationMask() | NEGOTIATION_MASK_TEAM_LEAD_ONLY));
        }

        if (manager.isTeamLead()) {
            setNegotiationMask((byte)(getSafeNegotiationMask() & NEGOTIATION_MASK_LINE_MANAGER_AND_PROJECT_MANAGER));
        } else if (manager.isProjectManager()) {
            setNegotiationMask((byte)(getSafeNegotiationMask() & NEGOTIATION_MASK_LINE_MANAGER_AND_TEAM_LEAD));
        } else if (manager.isLineManager()) {
            setNegotiationMask((byte)(getSafeNegotiationMask() & NEGOTIATION_MASK_TEAM_LEAD_AND_PROJECT_MANAGER));
        } else if (manager.isSupervisor()) {
            setNegotiationMask((byte)0);
        }
    }

    public String getReadableNegotiationMaskValue() {
        byte currentMask = getSafeNegotiationMask().byteValue();
        switch (currentMask) {
            case NEGOTIATION_MASK_TEAM_LEAD_ONLY:
                return "? / ? / ТЛ";
            case NEGOTIATION_MASK_LINE_MANAGER_ONLY:
                return "ЛР / ? / ?";
            case NEGOTIATION_MASK_PROJECT_MANAGER_ONLY:
                return "? / РП / ?";
            case NEGOTIATION_MASK_LINE_MANAGER_AND_PROJECT_MANAGER:
                return "ЛР / РП / ?";
            case NEGOTIATION_MASK_LINE_MANAGER_AND_TEAM_LEAD:
                return "ЛР / ? / ТЛ";
            case NEGOTIATION_MASK_TEAM_LEAD_AND_PROJECT_MANAGER:
                return "? / РП / ТЛ";
            case NEGOTIATION_MASK_NONE:
                return "? / ? / ?";
            case NEGOTIATION_MASK_ALL:
                return "ЛР / РП / ТЛ";
            default:
                return "? / ? / ?";
        }
    }

    public void setNegotiationMaskByManager(Employee manager, EmployeeHolidayPeriod ehp) {
        if (manager == null  || !manager.isManager()) {
            return;
        }


        if (ehp.getEmployeeDepartmentCode().equals(GlobalConstants.DEPT_DIVISION_B2C)) {
            // fore TeamLead flag to be set for BA's. As there's no really TL which
            // is negotiating these Employees
            setNegotiationMask((byte)(getSafeNegotiationMask() | NEGOTIATION_MASK_TEAM_LEAD_ONLY));
        }

        if (manager.isTeamLead()) {
            setNegotiationMask((byte)(getSafeNegotiationMask() | NEGOTIATION_MASK_TEAM_LEAD_ONLY));
        } else if (manager.isProjectManager()) {
            setNegotiationMask((byte)(getSafeNegotiationMask() | NEGOTIATION_MASK_PROJECT_MANAGER_ONLY));
        } else if (manager.isLineManager()) {
            setNegotiationMask((byte)(getSafeNegotiationMask() | NEGOTIATION_MASK_LINE_MANAGER_ONLY));
        } else if (manager.isSupervisor()) {
            setNegotiationMask(NEGOTIATION_MASK_ALL);
        }
    }

    public byte getNegotiationMaskByManager(Employee manager) {
        if (manager == null || !manager.isManager()) {
            return 0;
        }
        if (manager.isTeamLead()) {
            return ((byte)(getSafeNegotiationMask() | NEGOTIATION_MASK_TEAM_LEAD_ONLY));
        } else if (manager.isProjectManager()) {
            return ((byte)(getSafeNegotiationMask() | NEGOTIATION_MASK_PROJECT_MANAGER_ONLY));
        } else if (manager.isLineManager()) {
            return ((byte)(getSafeNegotiationMask() | NEGOTIATION_MASK_LINE_MANAGER_ONLY));
        } else if (manager.isSupervisor()) {
            return NEGOTIATION_MASK_ALL;
        }
        return 0;
    }

    private boolean isVisibleCommon() {
        if (HolidayPeriodUtils.isHolidayPeriodInNewStatus(this)) {
            return false;
        }
        return true;
    }

    public boolean isVisibleForTeamLead() {
        if (!isVisibleCommon()) {
            return false;
        }

        return true; // Always visible for team lead
    }

    public boolean isVisibleForProjectManager() {
        if (!isVisibleCommon()) {
            return false;
        }

        return true;
        //return getSafeNegotiationMask() >= 1;
    }

    public boolean isVisibleForLineManager() {
        if (!isVisibleCommon()) {
            return false;
        }

        return true;
        //return getSafeNegotiationMask() >= 5;
    }

    public boolean isVisibleForSupervisor() {
        return true;
    }

    public Set<HolidayPeriodNegotiationHistory> getHolidayPeriodNegotiationHistories() {
        return holidayPeriodNegotiationHistories;
    }

    public void setHolidayPeriodNegotiationHistories(Set<HolidayPeriodNegotiationHistory> holidayPeriodNegotiationHistories) {
        this.holidayPeriodNegotiationHistories = holidayPeriodNegotiationHistories;
    }

    public String getHolidayPeriodNegotiationHistoryComment(boolean isHTMLMode) {
        if (!CommonUtils.checkIfEmpty(holidayPeriodNegotiationHistories)) {
            StringBuilder sb = new StringBuilder();

            Set<HolidayPeriodNegotiationHistory> sortedHistories = new TreeSet<HolidayPeriodNegotiationHistory>(new Comparator<HolidayPeriodNegotiationHistory>() {
                @Override
                public int compare(HolidayPeriodNegotiationHistory o1, HolidayPeriodNegotiationHistory o2) {
                    if (o1.getCreatedDate().before(o2.getCreatedDate())) {
                        return 1;
                    } else if (o2.getCreatedDate().before(o1.getCreatedDate())) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });

            sortedHistories.addAll(holidayPeriodNegotiationHistories);

            for (HolidayPeriodNegotiationHistory history : sortedHistories) {
                if (sb.length() > 0) {
                    if (isHTMLMode) {
                        sb.append("<br/>");
                    } else {
                        sb.append("\r\n");
                    }
                }
                String oldStatus = history.getOldStatus();
                String newStatus = history.getNewStatus();
                String fullComment = "";
                if (CommonUtils.checkIfAnyIsEmpty(oldStatus, newStatus) || oldStatus.equals(newStatus)) {
                    fullComment = String.format("[%s] - %s", DateUtils.getDateAsString(history.getCreatedDate(), GlobalConstants.DATETIME_FORMAT), history.getComment());
                } else {
                    fullComment = String.format("[%s] - %s, Статус изменён с \"%s\" на \"%s\"", DateUtils.getDateAsString(history.getCreatedDate(), GlobalConstants.DATETIME_FORMAT), history.getComment(), oldStatus, newStatus);
                }

                sb.append(fullComment);
            }
            return sb.toString();
        } else {
            return GlobalConstants.EMPTY_STRING;
        }
    }
    public String getHolidayPeriodNegotiationHistoryComment() {
        return getHolidayPeriodNegotiationHistoryComment(true);
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
    public Date getCreatedDate() {
        return created;
    }

    @Override
    public Date getUpdatedDate() {
        return updated;
    }
/*
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        HolidayPeriod holidayPeriod = (HolidayPeriod)obj;

        return Objects.equals(id, holidayPeriod.getId()) &&
                Objects.equals(created, holidayPeriod.getCreatedDate()) &&
                Objects.equals(updated, holidayPeriod.getUpdatedDate()) &&
                Objects.equals(dateStart, holidayPeriod.getDateStart()) &&
                Objects.equals(numDays, holidayPeriod.getNumDays()) &&
                Objects.equals(employee, holidayPeriod.getEmployee()) &&
                Objects.equals(negotiationMask, holidayPeriod.getNegotiationMask()) &&
                Objects.equals(negotiationStatus, holidayPeriod.getNegotiationStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, updated, dateStart, numDays, employee, negotiationMask, negotiationStatus);
    }
*/
}
