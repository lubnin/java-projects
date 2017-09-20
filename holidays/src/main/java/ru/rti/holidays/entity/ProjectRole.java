package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entity which describes the project role of a particular Employee in the company.
 * Project role can be one of the following examples: 'manager', 'programmer', 'tester', 'business analyst' an so on.
 * Each ProjectRole instance holds the array of Employees which have this project role as a primary role in
 * the company
 */
@SpringComponent
@Entity
@Table(name = "project_role")
public class ProjectRole implements DBEntity {
    /**
     * The primary key for the table holding ProjectRole instances
     */
    @GenericGenerator(
            name = "projectRoleSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_proj_role_id"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "7"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "projectRoleSequenceGenerator")
    @Column(name = "project_role_id", unique = true, nullable = false)
    private Long id;

    /**
     * The name of this project role instance in a human-readable format
     */
    @Column(name = "roleName", nullable = false)
    private String roleName;

    /**
     * The description of this project role instance. Contains details for this project role
     * in a human-readable format
     */
    @Column(name = "roleDescription", nullable = false)
    private String roleDescripion;

    /**
     * The list of Employee entities which have this project role as a primary role
     */
    @OneToMany(mappedBy = "projectRole", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;

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

    public ProjectRole() {

    }

    public ProjectRole(String roleName, String roleDescription) {
        this.roleName = roleName;
        this.roleDescripion = roleDescription;
    }

    @PrePersist
    public void onCreate() {
        created = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        updated = new Date();
    }

    public Long getId() {
        return id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescripion() {
        return roleDescripion;
    }

    public void setRoleDescripion(String roleDescripion) {
        this.roleDescripion = roleDescripion;
    }



    @Override
    public String toString() {
        return String.format("ProjectRole[id=%d, roleName='%s', roleDescription='%s', created='%s', updated='%s']",
                id,
                roleName,
                roleDescripion,
                created,
                updated);
    }

    @Override
    public DBEntity construct() {
        return new ProjectRole();
    }
}
