package ru.rti.holidays.aggregators;

import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.utility.GlobalConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeHolidayPeriod {
    private String employeeFullName;
    private Long teamId;
    private LocalDate dateStart;
    private Long numDays;
    private String holidayPeriodNegotiationStatus;
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

    public String getDateStartAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.DATE_FORMAT);

        if (dateStart == null) return GlobalConstants.EMPTY_STRING;

        try {
            String dateStartAsString = this.dateStart.format(formatter);
            return dateStartAsString;
        } catch (IllegalArgumentException e) {
            return GlobalConstants.EMPTY_STRING;
        }
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
                employeeRoleName == employeeHolidayPeriod.getEmployeeRoleName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((employeeFullName == null) ? 0 : employeeFullName.hashCode());
        result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
        result = prime * result + ((numDays == null) ? 0 : numDays.hashCode());
        result = prime * result + ((holidayPeriodNegotiationStatus == null) ? 0 : holidayPeriodNegotiationStatus.hashCode());
        result = prime * result + ((employeeRoleName == null) ? 0 : employeeRoleName.hashCode());
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
}
