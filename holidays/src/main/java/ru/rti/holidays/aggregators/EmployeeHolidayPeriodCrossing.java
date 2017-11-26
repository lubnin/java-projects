package ru.rti.holidays.aggregators;

import ru.rti.holidays.utility.DateUtils;
import ru.rti.holidays.utility.GlobalConstants;

import java.time.LocalDate;

public class EmployeeHolidayPeriodCrossing {

    private String submitterEmployeeFullName = GlobalConstants.EMPTY_STRING;
    private LocalDate submitterEmployeeDateStart;
    private Long submitterNumDays;
    private String crossingEmployeeFullName = GlobalConstants.EMPTY_STRING;
    private LocalDate crossingEmployeeDateStart;
    private Long crossingEmployeeNumDays;

    public EmployeeHolidayPeriodCrossing() {

    }

    public EmployeeHolidayPeriodCrossing(String submitterEmployeeFullName,
                                         LocalDate submitterEmployeeDateStart,
                                         Long submitterNumDays,
                                         String crossingEmployeeFullName,
                                         LocalDate crossingEmployeeDateStart,
                                         Long crossingEmployeeNumDays) {

        this.submitterEmployeeFullName = submitterEmployeeFullName;
        this.submitterEmployeeDateStart = submitterEmployeeDateStart;
        this.submitterNumDays = submitterNumDays;
        this.crossingEmployeeFullName = crossingEmployeeFullName;
        this.crossingEmployeeDateStart = crossingEmployeeDateStart;
        this.crossingEmployeeNumDays = crossingEmployeeNumDays;
    }

    public String getSubmitterEmployeeFullName() {
        return submitterEmployeeFullName;
    }

    public void setSubmitterEmployeeFullName(String submitterEmployeeFullName) {
        this.submitterEmployeeFullName = submitterEmployeeFullName;
    }

    public LocalDate getSubmitterEmployeeDateStart() {
        return submitterEmployeeDateStart;
    }

    public String getSubmitterEmployeeDateStartAsString() {
        return DateUtils.getDateAsString(submitterEmployeeDateStart);
    }

    public String getCrossingEmployeeDateStartAsString() {
        return DateUtils.getDateAsString(crossingEmployeeDateStart);
    }

    public void setSubmitterEmployeeDateStart(LocalDate submitterEmployeeDateStart) {
        this.submitterEmployeeDateStart = submitterEmployeeDateStart;
    }

    public Long getSubmitterNumDays() {
        return submitterNumDays;
    }

    public void setSubmitterNumDays(Long submitterNumDays) {
        this.submitterNumDays = submitterNumDays;
    }

    public String getCrossingEmployeeFullName() {
        return crossingEmployeeFullName;
    }

    public void setCrossingEmployeeFullName(String crossingEmployeeFullName) {
        this.crossingEmployeeFullName = crossingEmployeeFullName;
    }

    public LocalDate getCrossingEmployeeDateStart() {
        return crossingEmployeeDateStart;
    }

    public void setCrossingEmployeeDateStart(LocalDate crossingEmployeeDateStart) {
        this.crossingEmployeeDateStart = crossingEmployeeDateStart;
    }

    public Long getCrossingEmployeeNumDays() {
        return crossingEmployeeNumDays;
    }

    public void setCrossingEmployeeNumDays(Long crossingEmployeeNumDays) {
        this.crossingEmployeeNumDays = crossingEmployeeNumDays;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        EmployeeHolidayPeriodCrossing employeeHolidayPeriodCrossing = (EmployeeHolidayPeriodCrossing)obj;

        return submitterEmployeeFullName == employeeHolidayPeriodCrossing.getSubmitterEmployeeFullName() &&
                submitterEmployeeDateStart == employeeHolidayPeriodCrossing.getSubmitterEmployeeDateStart() &&
                submitterNumDays == employeeHolidayPeriodCrossing.getSubmitterNumDays() &&
                crossingEmployeeFullName == employeeHolidayPeriodCrossing.getCrossingEmployeeFullName() &&
                crossingEmployeeDateStart == employeeHolidayPeriodCrossing.getCrossingEmployeeDateStart() &&
                crossingEmployeeNumDays == employeeHolidayPeriodCrossing.getCrossingEmployeeNumDays();

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((submitterEmployeeFullName == null) ? 0 : submitterEmployeeFullName.hashCode());
        result = prime * result + ((submitterEmployeeDateStart == null) ? 0 : submitterEmployeeDateStart.hashCode());
        result = prime * result + ((submitterNumDays == null) ? 0 : submitterNumDays.hashCode());
        result = prime * result + ((crossingEmployeeFullName == null) ? 0 : crossingEmployeeFullName.hashCode());
        result = prime * result + ((crossingEmployeeDateStart == null) ? 0 : crossingEmployeeDateStart.hashCode());
        result = prime * result + ((crossingEmployeeNumDays == null) ? 0 : crossingEmployeeNumDays.hashCode());
        return result;
    }
}
