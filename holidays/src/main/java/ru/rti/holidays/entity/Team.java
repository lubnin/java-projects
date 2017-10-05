package ru.rti.holidays.entity;

import com.vaadin.spring.annotation.SpringComponent;
import org.hibernate.annotations.GenericGenerator;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Entity which describes the Scrum Team containing multiple Employee entities.
 * The team has a TeamLead (Employee instance) and a ProjectManager (Emploee instance).
 */
@SpringComponent
@Entity
@Table(name = "team")
@SuppressWarnings("unused")
public class Team implements DBEntity {
    @GenericGenerator(
            name = "teamSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_team_id"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "teamSequenceGenerator")
    @Column(name = "team_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "teamName", nullable = false)
    private String teamName;

    //TODO: old cascade was: ALL + FetchType.LAZY, changed to EAGER+MERGE to implement correct deletion of team without
    //TODO: removing employees from the database
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = {
        CascadeType.MERGE
    })
    private Set<Employee> employees;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_lead_id")
    private Employee teamLead;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_manager_id")
    private Employee projectManager;

    @ManyToMany(mappedBy = "managedTeams", cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    private Set<Employee> managers;

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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Employee getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(Employee teamLead) {
        this.teamLead = teamLead;
    }

    public Employee getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(Employee projectManager) {
        this.projectManager = projectManager;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public Set<Employee> getManagers() {
        return managers;
    }

    public void setManagers(Set<Employee> managers) {
        this.managers = managers;
    }

    public Date getCreatedDate() { return created; }
    public Date getUpdatedDate() { return updated; }

    @Override
    public String toString() {
        return String.format("Team[id=%d, teamName='%s', created='%s', updated='%s']",
                id,
                teamName,
                created,
                updated
        );
    }

    @Override
    public DBEntity construct() {
        return new Team();
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Team team = (Team)obj;

        return id == team.getId() &&
                teamName == team.getTeamName() &&
                created == team.getCreatedDate() &&
                updated == team.getUpdatedDate();

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((teamName == null) ? 0 : teamName.hashCode());
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + ((updated == null) ? 0 : updated.hashCode());
        return result;
    }

}
