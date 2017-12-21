package ru.rti.holidays.aggregators;

import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.utility.DateUtils;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class aggregator for displaying Employee's holiday periods in grids.
 */
public class EmployeeHolidayPeriod {
    private Long employeeId;
    private String employeeFullName;
    private String employeeEmail;
    private String employeeDepartmentName;
    private String employeeDepartmentCode;
    private String employeeSpecialCode;
    private Employee employee;
    private boolean isEmployeeRegularRole;
    private Long teamId;
    private String teamName;
    private LocalDate dateStart;
    private Long numDays;
    private String holidayPeriodNegotiationStatus;
    private String holidayPeriodHistoryComment;
    private String employeeRoleName;
    private HolidayPeriodNegotiationStatus negotiationStatus;
    private HolidayPeriod holidayPeriod;

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public Long getNumDays() {
        return numDays;
    }

    public void setNumDays(Long numDays) {
        this.numDays = numDays;
    }

    public String getNumDaysAsString() {
        return String.valueOf(getNumDays());
    }

    public String getDateStartAsString() {
        return DateUtils.getDateAsString(this.dateStart);
    }

    public String getHolidayPeriodNegotiationStatus() {
        return holidayPeriodNegotiationStatus;
    }

    public void setHolidayPeriodNegotiationStatus(String holidayPeriodNegotiationStatus) {
        this.holidayPeriodNegotiationStatus = holidayPeriodNegotiationStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        EmployeeHolidayPeriod employeeHolidayPeriod = (EmployeeHolidayPeriod)obj;

        return Objects.equals(employeeFullName, employeeHolidayPeriod.getEmployeeFullName()) &&
                Objects.equals(dateStart, employeeHolidayPeriod.getDateStart()) &&
                Objects.equals(numDays, employeeHolidayPeriod.getNumDays()) &&
                Objects.equals(holidayPeriodNegotiationStatus, employeeHolidayPeriod.getHolidayPeriodNegotiationStatus()) &&
                Objects.equals(holidayPeriodHistoryComment, employeeHolidayPeriod.getHolidayPeriodHistoryComment()) &&
                Objects.equals(employeeRoleName, employeeHolidayPeriod.getEmployeeRoleName()) &&
                Objects.equals(employeeEmail, employeeHolidayPeriod.getEmployeeEmail()) &&
                Objects.equals(teamName, employeeHolidayPeriod.getTeamName()) &&
                Objects.equals(employeeDepartmentName, employeeHolidayPeriod.getEmployeeDepartmentName()) &&
                Objects.equals(employeeDepartmentCode, employeeHolidayPeriod.getEmployeeDepartmentCode()) &&
                Objects.equals(employeeRoleName, employeeHolidayPeriod.getEmployeeRoleName()) &&
                isEmployeeRegularRole == employeeHolidayPeriod.isEmployeeRegularRole() &&
                Objects.equals(employeeId, employeeHolidayPeriod.getEmployeeId()) &&
                Objects.equals(employee, employeeHolidayPeriod.getEmployee());
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeFullName, dateStart, numDays, holidayPeriodNegotiationStatus, holidayPeriodHistoryComment,
                employeeRoleName, employeeEmail, teamName, employeeDepartmentCode,
                employeeDepartmentName, employeeRoleName, isEmployeeRegularRole, employeeId, employee);
    }

    public String getEmployeeRoleName() {
        return employeeRoleName;
    }

    public void setEmployeeRoleName(String employeeRoleName) {
        this.employeeRoleName = employeeRoleName;
    }

    public HolidayPeriodNegotiationStatus getNegotiationStatus() {
        return negotiationStatus;
    }

    public void setNegotiationStatus(HolidayPeriodNegotiationStatus negotiationStatus) {
        this.negotiationStatus = negotiationStatus;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public HolidayPeriod getHolidayPeriod() {
        return holidayPeriod;
    }

    public void setHolidayPeriod(HolidayPeriod holidayPeriod) {
        this.holidayPeriod = holidayPeriod;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getEmployeeDepartmentName() {
        return employeeDepartmentName;
    }

    public void setEmployeeDepartmentName(String employeeDepartmentName) {
        this.employeeDepartmentName = employeeDepartmentName;
    }

    public String getEmployeeSpecialCode() {
        return employeeSpecialCode;
    }

    public void setEmployeeSpecialCode(String employeeSpecialCode) {
        this.employeeSpecialCode = employeeSpecialCode;
    }

    public boolean isEmployeeRegularRole() {
        return isEmployeeRegularRole;
    }

    public void setEmployeeRegularRole(boolean employeeRegularRole) {
        isEmployeeRegularRole = employeeRegularRole;
    }

    public String getEmployeeDepartmentCode() {
        return employeeDepartmentCode;
    }

    public void setEmployeeDepartmentCode(String employeeDepartmentCode) {
        this.employeeDepartmentCode = employeeDepartmentCode;
    }

    public String getHolidayPeriodHistoryComment() {
        return holidayPeriodHistoryComment;
    }

    public void setHolidayPeriodHistoryComment(String holidayPeriodHistoryComment) {
        this.holidayPeriodHistoryComment = holidayPeriodHistoryComment;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
