package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entity which describes the Employee, working in the company.
 */
@SpringComponent
@Entity
@Table(name = "employee")
public class Employee implements DBEntity {
    /**
     * The primary key for the table holding Employee instances
     */

    //TODO: fix 9 to 1 before going to production mode

    @GenericGenerator(
            name = "employeeSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_emp_id"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "9"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "employeeSequenceGenerator")
    @Column(name = "emp_id", unique = true, nullable = false)
    private Long id;

    /**
     * The first name of this Employee
     */
    @Column(name = "firstName", nullable = false)
    private String firstName;

    /**
     * The last name of this Employee
     */
    @Column(name = "lastName", nullable = false)
    private String lastName;

    /**
     * The middle name of this Employee
     */
    @Column(name = "middleName", nullable = true)
    private String middleName;

    /**
     * E-Mail in GD.RT.RU or other domain of Active Directory which is used for mailing purposes
     */
    @Column(name = "email", nullable = true)
    private String email;

    /**
     * Login name of the Employee newEntity. This is used for logging in to the Application
     */
    @Column(name = "loginName", nullable = false)
    private String loginName;

    /**
     * Password of the Employee. This is used for logging in to the Application
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * The list of holiday periods which this Employee submitted for negotiation for the working year
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<HolidayPeriod> holidayPeriods;

    /**
     * The reference to the ProjectRole newEntity. Describes the project role of the Employee in the company.
     */
    @ManyToOne()
    @JoinColumn(name = "project_role_id")
    private ProjectRole projectRole;

    /**
     * The reference to a Team for all employees. All employees belong to some Team
     */
    @ManyToOne()
    @JoinColumn(name = "team_id")
    private Team team;

    /**
     * The reference to a Team if this Employee newEntity is a ProjectManager employee newEntity
     */
    @OneToOne(mappedBy="projectManager")
    private Team projectManagerTeam;

    /**
     * The reference to a Team if this Employee newEntity is a TeamLead employee newEntity
     */
    @OneToOne(mappedBy="teamLead")
    private Team teamLeadTeam;

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

    public Employee() {

    }

    public Employee(String firstName, String lastName, String middleName, String loginName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.loginName = loginName;
    }
    @PrePersist
    public void onCreate() {
        created = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        updated = new Date();
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (lastName != null) sb.append(getLastName());
        if (firstName != null) sb.append(" ").append(getFirstName());
        if (middleName != null) sb.append(" ").append(getMiddleName());
        return sb.toString();
    }

    public List<HolidayPeriod> getHolidayPeriods() {
        return holidayPeriods;
    }

    public void setHolidayPeriods(List<HolidayPeriod> holidayPeriods) {
        this.holidayPeriods = holidayPeriods;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public void setLoginName(String loginName) { this.loginName = loginName; }

    public ProjectRole getProjectRole() {
        return projectRole;
    }

    public String getProjectRoleAsString() {
        return projectRole == null ? "" : projectRole.getRoleName();
    }

    public String getTeamNameAsString() { return team == null ? "" : team.getTeamName(); }
    public void setProjectRole(ProjectRole projectRole) {
        this.projectRole = projectRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getProjectManagerTeam() {
        return projectManagerTeam;
    }

    public void setProjectManagerTeam(Team projectManagerTeam) {
        this.projectManagerTeam = projectManagerTeam;
    }

    public Team getTeamLeadTeam() {
        return teamLeadTeam;
    }

    public void setTeamLeadTeam(Team teamLeadTeam) {
        this.teamLeadTeam = teamLeadTeam;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("Employee[id=%d, firstName='%s', lastName='%s', middleName='%s', loginName='%s', email='%s']",
                id,
                firstName,
                lastName,
                middleName,
                loginName,
                email);
    }

    @Override
    public DBEntity construct() {
        return new Employee();
    }


}
