package ru.rti.holidays.layout.base;

import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.exception.ExceptionHandler;
import ru.rti.holidays.exception.LayoutConstructionException;
import ru.rti.holidays.layout.EmployeeListLayout;
import ru.rti.holidays.layout.base.behaviour.RefreshGridDataListener;
import ru.rti.holidays.layout.base.behaviour.SaveButtonClickListener;

public class BaseVerticalLayout extends VerticalLayout implements BaseLayout {
    private static final Logger log = LoggerFactory.getLogger(BaseVerticalLayout.class);
    protected ExceptionHandler exceptionHandler;
    protected RefreshGridDataListener refreshGridDataListener;
    protected SaveButtonClickListener saveButtonClickListener;
    protected BaseLayout parentLayout;

    @Override
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void handleException(Exception e, String errorMessage) {
        if (exceptionHandler != null) {
            exceptionHandler.handle(e, errorMessage);
        }
    }

    @Override
    public void constructLayout() {

    }

    @Override
    public void postConstructLayout() {

    }

    @Override
    public void refreshDataGrid() {
        if (refreshGridDataListener != null) {
            refreshGridDataListener.onRefreshGridData(this);
        }
    }

    @Override
    public RefreshGridDataListener getRefreshGridDataListener() {
        return refreshGridDataListener;
    }

    @Override
    public void setRefreshGridDataListener(RefreshGridDataListener refreshGridDataListener) {
        this.refreshGridDataListener = refreshGridDataListener;
    }

    public SaveButtonClickListener getSaveButtonClickListener() {
        return saveButtonClickListener;
    }

    public void setSaveButtonClickListener(SaveButtonClickListener saveButtonClickListener) {
        this.saveButtonClickListener = saveButtonClickListener;
    }

    @Override
    public void setParentLayout(BaseLayout parentLayout) {
        this.parentLayout = parentLayout;
    }

    @Override
    public BaseLayout getParentLayout() {
        return this.parentLayout;
    }
}
