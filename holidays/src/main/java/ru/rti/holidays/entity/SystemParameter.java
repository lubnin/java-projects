package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SpringComponent
@Entity
@Table(name = "system_parameter")
@SuppressWarnings("unused")
public class SystemParameter implements DBEntity {

    public enum SystemParameterType {
        SETTING_TYPE_INTEGER(0L, "Целое число (Integer)"),
        SETTING_TYPE_STRING(1L, "Строковое значение (String)"),
        SETTING_TYPE_BOOLEAN(2L, "Логический тип (Boolean)"),
        SETTING_TYPE_DATETIME(3L, "Дата и время (DateTime)"),
        SETTING_TYPE_DATE(4L, "Дата без времени (Date)"),
        SETTING_TYPE_TIME(5L, "Время (Time)");

        private final Long typeId;
        private final String description;


        SystemParameterType(Long typeId, String description) {
            this.typeId = typeId;
            this.description = description;
        }

        public static List<SystemParameterType> getAllSystemParameterTypes() {
            return Arrays.asList(
                    SETTING_TYPE_INTEGER,
                    SETTING_TYPE_STRING,
                    SETTING_TYPE_BOOLEAN,
                    SETTING_TYPE_DATETIME,
                    SETTING_TYPE_DATE,
                    SETTING_TYPE_TIME);
        }
        public Long getTypeId() { return this.typeId; }
        public String getDescription() { return this.description; }

        @Override
        public String toString() {
            return description;
        }
    }

    /**
     * The primary key for the table holding Employee instances
     */
    @GenericGenerator(
            name = "systemParameterSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_sys_param_id"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "systemParameterSequenceGenerator")
    @Column(name = "sys_param_id", unique = true, nullable = false)
    private Long id;

    /**
     * The inner name of the setting
     */
    @Column(name = "innerName", nullable = false)
    private String innerName;

    /**
     * The caption of the setting
     */
    @Column(name = "caption", nullable = false)
    private String caption;

    /**
     * The inner type of the setting
     */
    @Column(name = "innerType", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private SystemParameterType innerType;

    @Column(name = "value")
    private String value;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

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

    public String getInnerName() {
        return innerName;
    }

    public void setInnerName(String innerName) {
        this.innerName = innerName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public SystemParameterType getInnerType() {
        return innerType;
    }

    public void setInnerType(SystemParameterType innerType) {
        this.innerType = innerType;
    }

    public String getInnerTypeAsString() {
        return getInnerType().getDescription();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Date getCreatedDate() { return created; }

    @Override
    public Date getUpdatedDate() { return updated; }

    @Override
    public String toString() {
        return String.format("SystemParameter[id=%d, innerName='%s', caption='%s', innerType='%s', created='%s', updated='%s']",
                id,
                innerName,
                caption,
                innerType,
                created,
                updated
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        SystemParameter settings = (SystemParameter)obj;

        return Objects.equals(id, settings.getId()) &&
                Objects.equals(innerName, settings.getInnerName()) &&
                Objects.equals(caption, settings.getCaption()) &&
                Objects.equals(innerType, settings.getInnerType()) &&
                Objects.equals(created, settings.getCreatedDate()) &&
                Objects.equals(updated, settings.getUpdatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, innerName, caption, innerType, created, updated);
    }
}
