package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;
import ru.rti.holidays.utility.GlobalConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
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
//@SuppressWarnings("unused")
public class ProjectRole implements DBEntity {

    public enum ProjectRoleSpecialType {
        PROJECT_ROLE_SPECIAL_TYPE_REGULAR(0L, "Обычная роль"),
        PROJECT_ROLE_SPECIAL_TYPE_TEAM_LEAD(1L, "Тимлид команды"),
        PROJECT_ROLE_SPECIAL_TYPE_PROJECT_MANAGER(2L, "Руководитель проекта"),
        PROJECT_ROLE_SPECIAL_TYPE_LINE_MANAGER(3L, "Линейный руководитель"),
        PROJECT_ROLE_SPECIAL_TYPE_SUPERVISOR(4L, "Супервизор");

        private final Long typeId;
        private final String description;

        ProjectRoleSpecialType(Long typeId, String description) {
            this.typeId = typeId;
            this.description = description;
        }

        public static List<ProjectRoleSpecialType> getRolesWithTeamManagementAbility() {
            return Arrays.asList(
                    PROJECT_ROLE_SPECIAL_TYPE_TEAM_LEAD,
                    PROJECT_ROLE_SPECIAL_TYPE_PROJECT_MANAGER,
                    PROJECT_ROLE_SPECIAL_TYPE_LINE_MANAGER,
                    PROJECT_ROLE_SPECIAL_TYPE_SUPERVISOR);
        }
        public Long getTypeId() { return this.typeId; }
        public String getDescription() { return this.description; }
    }

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
    private String roleDescription;

    /**
     * The list of Employee entities which have this project role as a primary role
     */
    //TODO: EAGER is BAD, I know... But couldn't do without EAGER load the correct deletion of separate ProjectRole,
    //TODO: not affecting the Employees with the deleted ProjectRole.
    //TODO: Need to add transaction support and LAZY loading at later time!
    @OneToMany(mappedBy = "projectRole", fetch = FetchType.EAGER, cascade = {
        //CascadeType.REFRESH,
        //CascadeType.DETACH,
        //CascadeType.PERSIST,
        //CascadeType.MERGE
        //CascadeType.REMOVE
    })
    private List<Employee> employees = new ArrayList<>();

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

    @Column(name = "specialType", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProjectRoleSpecialType projectRoleSpecialType;

    public ProjectRole() {
        this(GlobalConstants.EMPTY_STRING, GlobalConstants.EMPTY_STRING);
    }

    public ProjectRole(String roleName, String roleDescription) {
        this.projectRoleSpecialType = ProjectRoleSpecialType.PROJECT_ROLE_SPECIAL_TYPE_REGULAR;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
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

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }
    //public void setEmployees(List<Employee> employees) {
//        this.employees = employees;
//    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public ProjectRoleSpecialType getProjectRoleSpecialType() {
        return projectRoleSpecialType;
    }

    public void setProjectRoleSpecialType(ProjectRoleSpecialType projectRoleSpecialType) {
        this.projectRoleSpecialType = projectRoleSpecialType;
    }

    public String getProjectRoleSpecialTypeAsString() {
        return getProjectRoleSpecialType().getDescription();
    }

    @Override
    public String toString() {
        return String.format("ProjectRole[id=%d, roleName='%s', roleDescription='%s', created='%s', updated='%s']",
                id,
                roleName,
                roleDescription,
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
}
