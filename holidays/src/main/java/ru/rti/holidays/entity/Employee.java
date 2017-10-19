package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.rti.holidays.utility.GlobalConstants;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Entity which describes the Employee, working in the company.
 */
@SpringComponent
@Entity
@Table(name = "employee")
@SuppressWarnings("unused")
public class Employee implements DBEntity, UserDetails {
    /**
     * The primary key for the table holding Employee instances
     */

    //TODO: fix 9 to 1 sequence start value before going to production mode
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
    @Column(name = "middleName")
    private String middleName;

    /**
     * E-Mail in GD.RT.RU or other domain of Active Directory which is used for mailing purposes
     */
    @Column(name = "email")
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
    //cascade = { CascadeType.REMOVE, CascadeType.MERGE }
    @ManyToOne(cascade = {
        CascadeType.MERGE
    })
    @JoinColumn(name = "project_role_id")
    private ProjectRole projectRole;

    /**
     * The reference to a Team for all employees. All employees belong to some Team
     */
    @ManyToOne(cascade = {
        CascadeType.MERGE
    })
    @JoinColumn(name = "team_id")
    private Team team;

    /**
     * The reference to a Set of teams, managed by this Employee.
     */
    @ManyToMany(cascade = {
        CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "managed_teams",
            joinColumns = @JoinColumn(name = "manager_id", referencedColumnName = "emp_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "team_id"))
    private Set<Team> managedTeams;
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
        return projectRole == null ? GlobalConstants.EMPTY_STRING : projectRole.getRoleName();
    }

    public String getProjectRoleSpecialTypeAsString() {
        if (projectRole == null) {
            return GlobalConstants.EMPTY_STRING;
        }
        ProjectRole.ProjectRoleSpecialType specType = projectRole.getProjectRoleSpecialType();
        if (specType == null) {
            return GlobalConstants.EMPTY_STRING;
        }

        return specType.getDescription();
    }

    /**
     * Returns the name of a Team for The Employee. This is a Team this Employee belongs to.
     * If the 'team' reference is null, this method returns an empty String.
     * @return Team name or empty string if no team is connected with this Employee
     */
    public String getTeamNameAsString() { return team == null ? GlobalConstants.EMPTY_STRING : team.getTeamName(); }

    public String getAllManagedTeamsAsString() {
        return getAllManagedTeamsAsString(", ");
    }

    /**
     * Returns the String representing all managed by this Employee teams. The method is useful for
     * Employees who have manager role. For particular Employee this method returnes an empty string value.
     * @param separator the separator for delimiting the Team names.
     * @return String containing all the teams, managed by this Employee and delimited with 'separator' value. For non-manager Employees method returns an empty stirng.
     */
    public String getAllManagedTeamsAsString(String separator) {
        if (separator == null) {
            throw new IllegalArgumentException("Separator cannot be null. Please, provide the valid string value.");
        }
        StringBuilder sbAllTeams = new StringBuilder();
        if (projectRole != null) {
            switch (projectRole.getProjectRoleSpecialType()) {
                case PROJECT_ROLE_SPECIAL_TYPE_REGULAR:
                    return GlobalConstants.EMPTY_STRING;
                case PROJECT_ROLE_SPECIAL_TYPE_TEAM_LEAD:
                case PROJECT_ROLE_SPECIAL_TYPE_LINE_MANAGER:
                case PROJECT_ROLE_SPECIAL_TYPE_PROJECT_MANAGER:
                    if (managedTeams != null) {
                        int i = 0;
                        for (Team currentManagedTeam: managedTeams) {
                            i++;
                            sbAllTeams.append(currentManagedTeam.getTeamName());
                            if (i < managedTeams.size()) {
                                sbAllTeams.append(separator);
                            }
                        }
                    }
                    break;
            }
        } else {
            return team == null ? GlobalConstants.EMPTY_STRING : team.getTeamName();
        }
        return sbAllTeams.toString();
    }

    /**
     * Returns true if this Employee instance is an Employee with the manager role capabilities.
     * @return
     */
    public boolean isManager() {
        if (projectRole != null) {
            switch (projectRole.getProjectRoleSpecialType()) {
                case PROJECT_ROLE_SPECIAL_TYPE_LINE_MANAGER:
                case PROJECT_ROLE_SPECIAL_TYPE_PROJECT_MANAGER:
                case PROJECT_ROLE_SPECIAL_TYPE_TEAM_LEAD:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    public boolean isAdmin() {
        return GlobalConstants.ADMIN_USER_LOGIN_NAME.equals(loginName);
    }

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

    /**
     * Gets the hashed value of the initial Employee password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the empty string for GUI. The method is needed for Binder classes of Vaading to
     * restrict binding of hashed values to text field controls.
     * @return
     */
    public String getEmptyPassword() {
        return GlobalConstants.EMPTY_STRING;
    }

    /**
     * Sets the password value for this Employee instance. Takes the plain password value and
     * performs the encryption of the plain password before persisting to the database.
     * @param password
     */
    public void setPassword(String password) {
        String encodedPassword = "";
        if (passwordEncoder != null) {
            encodedPassword = passwordEncoder.encode(password);
        } else {
            encodedPassword = new BCryptPasswordEncoder().encode(password);
        }
        this.password = encodedPassword;
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

    public Set<Team> getManagedTeams() {
        return managedTeams;
    }

    public void setManagedTeams(Set<Team> managedTeams) {
        this.managedTeams = managedTeams;
    }

    @Override
    public Date getCreatedDate() {
        return created;
    }

    @Override
    public Date getUpdatedDate() {
        return updated;
    }

    /**
     * ============================================================================================================
     * Spring Security overridden methods and fields needed for authorization of the Employee in the Application
     * ============================================================================================================
     */

    /**
     * The password encoder, provided by the Spring Security library
     */
    @Autowired
    @Transient
    BCryptPasswordEncoder passwordEncoder;

    /**
     * The set of authorities (Roles) for this Employee. Defines the access level to different Application areas.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Authority> authorities;

    /**
     * Returns the collection of GrantedAuthority class instances for this Employee. These are
     * the roles in the System that allow/restrict particular operations.
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Overridden method of UserDetails interface that returns the login name of the user as a username for
     * loggin-in to the Application.
     * @return
     */
    @Override
    public String getUsername() {
        return loginName;
    }

    /**
     * For now, always return true, that means that Account is never expired.
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * For now, always return true, that means that Account is never locked.
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * For now, always return true, that means that Account credentials are never expired.
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * For now, always return true, that means that Employee is always enabled to login to the Application.
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
