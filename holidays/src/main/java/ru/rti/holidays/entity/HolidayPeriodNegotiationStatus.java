package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@SpringComponent
@Entity
@Table(name = "holiday_period_neg_status")
@SuppressWarnings("unused")
public class HolidayPeriodNegotiationStatus implements DBEntity {

    public enum HolidayPeriodNegotiationMode {
        NEGOTIATION,
        REJECTION
    }

    public enum HolidayPeriodNegotiationStatusType {
        NEGOTIATION_STATUS_TYPE_NEW(3L, "Новый", false, true),
        NEGOTIATION_STATUS_TYPE_NEGOTIATING(0L, "На согласовании", true, true),
        NEGOTIATION_STATUS_TYPE_OK(1L, "Согласован", true, true),
        NEGOTIATION_STATUS_TYPE_PARTLY_NEGOTIATED(4L, "Частично согласован", true, true),
        NEGOTIATION_STATUS_TYPE_REJECTED(2L, "Отклонён", false, true),
        NEGOTIATION_STATUS_TYPE_RECALLED(5L, "Отозван", false, false);

        private final Long typeId;
        private final String description;

        /** Flag indicating that this holiday period negotiation status type is participating in crossing algorightm between other Employees **/
        private final boolean isParticipatingInCrossing;

        /** Flag, indicating the status is participating in crossing algorithm of own, Employee's periods **/
        private final boolean isParticipantInOwnCrossing;

        HolidayPeriodNegotiationStatusType(Long typeId, String description, boolean isParticipatingInCrossing, boolean isParticipantInOwnCrossing) {
            this.typeId = typeId;
            this.description = description;
            this.isParticipatingInCrossing = isParticipatingInCrossing;
            this.isParticipantInOwnCrossing = isParticipantInOwnCrossing;
        }

        public static List<HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType> getAllNegotiationStatusTypes() {
            return Arrays.asList(
                    NEGOTIATION_STATUS_TYPE_NEGOTIATING,
                    NEGOTIATION_STATUS_TYPE_OK,
                    NEGOTIATION_STATUS_TYPE_REJECTED,
                    NEGOTIATION_STATUS_TYPE_NEW,
                    NEGOTIATION_STATUS_TYPE_PARTLY_NEGOTIATED,
                    NEGOTIATION_STATUS_TYPE_RECALLED
                    );
        }
        public Long getTypeId() { return this.typeId; }
        public String getDescription() { return this.description; }
        public boolean isParticipatingInCrossing() { return this.isParticipatingInCrossing; }
        public boolean isParticipantInOwnCrossing() { return this.isParticipantInOwnCrossing; }
    }

    public HolidayPeriodNegotiationStatus() {
        negotiationStatusType = HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_NEGOTIATING;
    }

    @GenericGenerator(
            name = "holidayPeriodNegStatusSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_hol_period_neg_status_id"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
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

    @OneToMany(mappedBy = "negotiationStatus", fetch = FetchType.EAGER)
    private Set<HolidayPeriod> holidayPeriods;

    @Column(name = "negotiationStatusType", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType negotiationStatusType;

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

    public String getNegotiationStatusTypeAsString() {
        return getNegotiationStatusType().getDescription();
    }

    public HolidayPeriodNegotiationStatusType getNegotiationStatusType() {
        return negotiationStatusType;
    }

    public void setNegotiationStatusType(HolidayPeriodNegotiationStatusType negotiationStatusType) {
        this.negotiationStatusType = negotiationStatusType;
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
        HolidayPeriodNegotiationStatus status = (HolidayPeriodNegotiationStatus)obj;

        return Objects.equals(id, status.getId()) &&
                Objects.equals(created, status.getCreatedDate()) &&
                Objects.equals(updated, status.getUpdatedDate()) &&
                Objects.equals(statusName, status.getStatusName()) &&
                Objects.equals(statusDescription, status.getStatusDescription());

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, updated, statusName, statusDescription);
    }
    */
}
