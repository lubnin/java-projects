package ru.rti.holidays.service;

import ru.rti.holidays.entity.Department;
import ru.rti.holidays.entity.SystemParameter;

import java.util.List;

@SuppressWarnings("unused")
public interface SystemParameterService {
    SystemParameter saveSystemParameter(SystemParameter systemParameter);
    SystemParameter getSystemParameterByCaption(String caption);
    SystemParameter getSystemParameterByInnerName(String innerName);
    List<SystemParameter> getAllSystemParametersSortedByInnerNameAsc();
    boolean deleteSystemParameters(Iterable<SystemParameter> systemParameters);
}
