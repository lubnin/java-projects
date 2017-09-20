package ru.rti.holidays.layout.base;

import ru.rti.holidays.exception.ExceptionHandler;
import ru.rti.holidays.exception.LayoutConstructionException;
import ru.rti.holidays.layout.base.behaviour.RefreshGridDataListener;
import ru.rti.holidays.layout.base.behaviour.SaveButtonClickListener;
import ru.rti.holidays.layout.behaviour.EmployeeHolidaysLayoutRefreshGridDataListener;

public interface BaseLayout {
    // Exception Handling
    void setExceptionHandler(ExceptionHandler exceptionHandler);
    void handleException(Exception e, String errorMessage);

    void setParentLayout(BaseLayout parentLayout);
    BaseLayout getParentLayout();

    // Layout Construction
    void constructLayout();
    void postConstructLayout();

    void refreshDataGrid();

    // Refresh Grid Listener
    RefreshGridDataListener getRefreshGridDataListener();
    void setRefreshGridDataListener(RefreshGridDataListener refreshGridDataListener);

    // Save Button Logic
    SaveButtonClickListener getSaveButtonClickListener();
    void setSaveButtonClickListener(SaveButtonClickListener saveButtonClickListener);
}
