package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Date;

@SpringComponent
@Entity
@Table(name = "authority")
public class Authority implements DBEntity, GrantedAuthority {
    @GenericGenerator(
            name = "authoritySequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_authority_id"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "authoritySequenceGenerator")
    @Column(name = "authority_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "authority")
    private String authority;

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
    public String getAuthority() {
        return authority;
    }

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

    @Override
    public DBEntity construct() {
        return new Authority();
    }
}
