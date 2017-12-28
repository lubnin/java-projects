package ru.rti.holidays.layout.base;

import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.exception.LayoutConstructionException;
import ru.rti.holidays.exception.handler.ExceptionHandler;
import ru.rti.holidays.layout.base.behaviour.RefreshGridDataListener;
import ru.rti.holidays.layout.base.behaviour.RemoveSelectedItemsClickListener;
import ru.rti.holidays.layout.base.behaviour.SaveButtonClickListener;
import ru.rti.holidays.service.localization.LocalizationService;

/**
 * BaseVerticalLayout is a base layout which extends one of the standard Vaadin layouts (VerticalLayout).
 * This layout is implementing BaseLayout interface and contains the base logic of 'holidays' Application
 * in the sense of constructing the Application pages.
 */
public class BaseVerticalLayout extends VerticalLayout implements BaseLayout {
    private static final Logger log = LoggerFactory.getLogger(BaseVerticalLayout.class);

    protected ExceptionHandler exceptionHandler;
    protected RefreshGridDataListener refreshGridDataListener;
    protected SaveButtonClickListener saveButtonClickListener;
    protected RemoveSelectedItemsClickListener removeSelectedItemsClickListener;
    protected BaseLayout parentLayout;
    protected String pageTitle;
    protected boolean isApplyMargin = true;
    protected boolean isApplySpacing = true;
    protected LocalizationService localizationService;

    public BaseVerticalLayout() {
        setMargin(isApplyMargin);
        setSpacing(isApplySpacing);
    }

    public BaseVerticalLayout(boolean isApplyMargin, boolean isApplySpacing) {
        setApplyMargin(isApplyMargin);
        setApplySpacing(isApplySpacing);
        setMargin(isApplyMargin());
        setSpacing(isApplySpacing());
    }

    public Logger getLogger() {
        return log;
    }

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
    public void fireSaveButtonClickedEvent(Object objectForSave) {
        if (this.saveButtonClickListener != null) {
            this.saveButtonClickListener.onSaveData(this, objectForSave);
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
    public RemoveSelectedItemsClickListener getRemoveSelectedItemsClickListener() {
        return removeSelectedItemsClickListener;
    }

    @Override
    public void setRemoveSelectedItemsClickListener(RemoveSelectedItemsClickListener removeSelectedItemsClickListener) {
        this.removeSelectedItemsClickListener = removeSelectedItemsClickListener;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    /**
     * This method is not implemented in BaseVerticalLayout class
     * @param isEnabled
     */
    @Override
    public void setButtonRemoveSelectedEnabled(boolean isEnabled) {
        handleUnsupportedOperation();
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    private void handleUnsupportedOperation() {
        if (exceptionHandler != null) {
            handleException(new UnsupportedOperationException("This operation is not implemented in BaseVerticalLayout class. You must override it in your class to use it."), "Operation Not Supported");
        } else {
            throw new UnsupportedOperationException("This operation is not implemented in BaseVerticalLayout class. You must override it in your class to use it.");
        }
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
