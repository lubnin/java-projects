package ru.rti.holidays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
@SuppressWarnings("unused")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByLastNameStartsWithIgnoreCase(String lastName);
    List<Employee> findByLastName(String lastName);
    Employee findByLoginName(String loginName);
    Set<Employee> findByLoginNameNot(String loginName);
    Employee findByLoginNameAndPassword(String loginName, String password);
    Set<Employee> findByTeamId(Long teamId);
    Set<Employee> findByTeamIdAndIdNot(Long teamId, Long employeeId);

    //@Query(value = "SELECT * FROM #{#entityName} mgr " +
    //        "INNER JOIN mgr.managedTeams mt WHERE e.projectRole = :projRole")
    @Query(value = "SELECT mgr.* " +
            "FROM employee mgr " +
            "INNER JOIN managed_teams mt on (mgr.emp_id = mt.manager_id) " +
            "WHERE mt.team_id IN (SELECT team_id FROM employee WHERE emp_id = :employeeId) ", nativeQuery = true)
    Set<Employee> findAllManagersForEmployee(@Param("employeeId")Long employeeId);

/*    @Query(value = "SELECT emp.* FROM employee " +
            "LEFT JOIN holiday_period hp ON (emp.emp_id = hp.emp_id)" +
            "LEFT JOIN holiday_period_neg_status hpns ON (hp.hp_negotiation_status_id = hpns.hol_period_neg_status_id)" +
            "WHERE emp.team_id = :teamId AND hp.date_start >= :dateStart AND (hp.date_start + interval ':numDays' day) <= ")
    Set<Employee> findEmployeesWithCrossingHolidayPeriods(@Param("teamId")Long teamId, @Param("dateStart")Date dateStart, @Param("numDays")Long numDays);*/
    //@Modifying(clearAutomatically = true)
    //@Query(value = "UPDATE Employee e SET e.projectRole = NULL WHERE e.projectRole = :projRole")
    //@Transactional
    //void resetProjectRoleForEmployees(@Param("projRole")ProjectRole projectRole);
}
