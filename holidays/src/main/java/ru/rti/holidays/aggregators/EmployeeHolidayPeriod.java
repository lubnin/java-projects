package ru.rti.holidays.aggregators;

import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.utility.DateUtils;

import java.time.LocalDate;

/**
 * Class aggregator for displaying Employee's holiday periods in grids.
 */
public class EmployeeHolidayPeriod {
    private String employeeFullName;
    private String employeeEmail;
    private String employeeDepartmentName;
    private String employeeDepartmentCode;
    private String employeeSpecialCode;
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

        return employeeFullName == employeeHolidayPeriod.getEmployeeFullName() &&
                dateStart == employeeHolidayPeriod.getDateStart() &&
                numDays == employeeHolidayPeriod.getNumDays() &&
                holidayPeriodNegotiationStatus == employeeHolidayPeriod.getHolidayPeriodNegotiationStatus() &&
                holidayPeriodHistoryComment == employeeHolidayPeriod.getHolidayPeriodHistoryComment() &&
                employeeRoleName == employeeHolidayPeriod.getEmployeeRoleName() &&
                employeeEmail == employeeHolidayPeriod.getEmployeeEmail() &&
                teamName == employeeHolidayPeriod.getTeamName() &&
                employeeDepartmentName == employeeHolidayPeriod.getEmployeeDepartmentName() &&
                employeeDepartmentCode == employeeHolidayPeriod.getEmployeeDepartmentCode() &&
                employeeRoleName == employeeHolidayPeriod.getEmployeeRoleName() &&
                isEmployeeRegularRole == employeeHolidayPeriod.isEmployeeRegularRole();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((employeeFullName == null) ? 0 : employeeFullName.hashCode());
        result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
        result = prime * result + ((numDays == null) ? 0 : numDays.hashCode());
        result = prime * result + ((holidayPeriodNegotiationStatus == null) ? 0 : holidayPeriodNegotiationStatus.hashCode());
        result = prime * result + ((holidayPeriodHistoryComment == null) ? 0 : holidayPeriodHistoryComment.hashCode());
        result = prime * result + ((employeeRoleName == null) ? 0 : employeeRoleName.hashCode());
        result = prime * result + ((employeeEmail == null) ? 0 : employeeEmail.hashCode());
        result = prime * result + ((teamName == null) ? 0 : teamName.hashCode());
        result = prime * result + ((employeeDepartmentName == null) ? 0 : employeeDepartmentName.hashCode());
        result = prime * result + ((employeeDepartmentCode == null) ? 0 : employeeDepartmentCode.hashCode());
        result = prime * result + ((employeeRoleName == null) ? 0 : employeeRoleName.hashCode());
        result = prime * result + Boolean.valueOf(isEmployeeRegularRole).hashCode();
        return result;
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
}
