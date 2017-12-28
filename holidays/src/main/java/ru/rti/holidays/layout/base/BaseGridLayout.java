package ru.rti.holidays.layout.base;

import com.vaadin.ui.GridLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.exception.LayoutConstructionException;
import ru.rti.holidays.exception.handler.ExceptionHandler;
import ru.rti.holidays.layout.base.behaviour.RefreshGridDataListener;
import ru.rti.holidays.layout.base.behaviour.RemoveSelectedItemsClickListener;
import ru.rti.holidays.layout.base.behaviour.SaveButtonClickListener;
import ru.rti.holidays.service.localization.LocalizationService;

public class BaseGridLayout extends GridLayout implements BaseLayout {
    private static final Logger log = LoggerFactory.getLogger(BaseGridLayout.class);

    protected ExceptionHandler exceptionHandler;
    protected RefreshGridDataListener refreshGridDataListener;
    protected SaveButtonClickListener saveButtonClickListener;
    protected RemoveSelectedItemsClickListener removeSelectedItemsClickListener;
    protected BaseLayout parentLayout;
    protected String pageTitle;
    protected boolean isApplyMargin = true;
    protected boolean isApplySpacing = true;
    protected LocalizationService localizationService;

    public BaseGridLayout() {
        setMargin(isApplyMargin);
        setSpacing(isApplySpacing);
    }

    public BaseGridLayout(boolean isApplyMargin, boolean isApplySpacing) {
        setApplyMargin(isApplyMargin);
        setApplySpacing(isApplySpacing);
        setMargin(isApplyMargin());
        setSpacing(isApplySpacing());
    }

    @Override
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public Logger getLogger() {
        return log;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    @Override
    public void handleException(Exception e, String errorMessage) {
        if (exceptionHandler != null) {
            exceptionHandler.handle(e, errorMessage);
        }
    }

    @Override
    public void setParentLayout(BaseLayout parentLayout) {
        this.parentLayout = parentLayout;
    }

    @Override
    public BaseLayout getParentLayout() {
        return this.parentLayout;
    }

    @Override
    public void constructLayout() throws LayoutConstructionException {

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
    public void fireSaveButtonClickedEvent(Object objectForSave) {
        if (this.saveButtonClickListener != null) {
            this.saveButtonClickListener.onSaveData(this, objectForSave);
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

    @Override
    public SaveButtonClickListener getSaveButtonClickListener() {
        return saveButtonClickListener;
    }

    @Override
    public void setSaveButtonClickListener(SaveButtonClickListener saveButtonClickListener) {
        this.saveButtonClickListener = saveButtonClickListener;
    }

    @Override
    public RemoveSelectedItemsClickListener getRemoveSelectedItemsClickListener() {
        return removeSelectedItemsClickListener;
    }

    @Override
    public void setRemoveSelectedItemsClickListener(RemoveSelectedItemsClickListener removeSelectedItemsClickListener) {
        this.removeSelectedItemsClickListener = removeSelectedItemsClickListener;
    }

    @Override
    public String getPageTitle() {
        return pageTitle;
    }

    @Override
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public void setButtonRemoveSelectedEnabled(boolean isEnabled) {
        handleUnsupportedOperation();
    }

    @Override
    public void updateControlsFromBeanState() {
        handleUnsupportedOperation();
    }

    @Override
    public void clearAllControls() {
        handleUnsupportedOperation();
    }

    @Override
    public void setNewBeanValue(DBEntity newBeanValue) {
        handleUnsupportedOperation();
    }

    /**
     * Private non-overriden methods
     */

    private void handleUnsupportedOperation() {
        if (exceptionHandler != null) {
            handleException(new UnsupportedOperationException("This operation is not implemented in BaseVerticalLayout class. You must override it in your class to use it."), "Operation Not Supported");
        } else {
            throw new UnsupportedOperationException("This operation is not implemented in BaseVerticalLayout class. You must override it in your class to use it.");
        }
    }

    public boolean isApplyMargin() {
        return isApplyMargin;
    }

    public void setApplyMargin(boolean applyMargin) {
        isApplyMargin = applyMargin;
    }

    public boolean isApplySpacing() {
        return isApplySpacing;
    }

    public void setApplySpacing(boolean applySpacing) {
        isApplySpacing = applySpacing;
    }

    public LocalizationService getLocalizationService() {
        return localizationService;
    }

    public void setLocalizationService(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }
}
