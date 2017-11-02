package ru.rti.holidays.layout.base;

import com.vaadin.ui.Layout;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.exception.handler.ExceptionHandler;
import ru.rti.holidays.exception.handler.DoNothingExceptionHandler;
import ru.rti.holidays.exception.handler.ViewErrorMessageExceptionHandler;
import ru.rti.holidays.layout.base.behaviour.RefreshGridDataListener;
import ru.rti.holidays.layout.base.behaviour.RemoveSelectedItemsClickListener;
import ru.rti.holidays.layout.base.behaviour.SaveButtonClickListener;

public interface BaseLayout extends Layout {

    // Exception Handling

    /**
     * Sets the ExceptionHandler for this layout. It will handle all the exceptions which are thrown in the layout instance.
     * You can write your own implementation of ExceptionHandler interface or use one of the predefined Exception handlers:
     * @see ViewErrorMessageExceptionHandler
     * @see DoNothingExceptionHandler
     *
     * @param exceptionHandler
     */
    void setExceptionHandler(ExceptionHandler exceptionHandler);

    /**
     * Gets the ExceptionHandler for this layout. This Exception handles all the exceptions which are thrown in the layout
     * instance.
     * @return
     */
    ExceptionHandler getExceptionHandler();

    /**
     * Handles the exception, which occured during layout construction
     * @param e
     * @param errorMessage
     */
    void handleException(Exception e, String errorMessage);

    /**
     * Sets the parent layout for this layout
     * @param parentLayout
     */
    void setParentLayout(BaseLayout parentLayout);

    /**
     * Gets the parent layout for this layout
     * @return
     */
    BaseLayout getParentLayout();

    // Layout Construction

    /**
     * Constructs the layout. You should place the logic and 'filling' for layout in this method.
     */
    void constructLayout();

    /**
     * Method is called when the construction of the layout is finished. Place the logic
     * that should be executed right after the construction of layout is done.
     */
    void postConstructLayout();

    void refreshDataGrid();

    void fireSaveButtonClickedEvent(Object objectForSave);

    // Refresh Grid Listener
    RefreshGridDataListener getRefreshGridDataListener();
    void setRefreshGridDataListener(RefreshGridDataListener refreshGridDataListener);

    // Save Button Logic
    SaveButtonClickListener getSaveButtonClickListener();
    void setSaveButtonClickListener(SaveButtonClickListener saveButtonClickListener);

    // Remove Selected Items Logic
    RemoveSelectedItemsClickListener getRemoveSelectedItemsClickListener();
    void setRemoveSelectedItemsClickListener(RemoveSelectedItemsClickListener removeSelectedItemsClickListener);

    /**
     * Returns the Page title for this layout
     * @return
     */
    String getPageTitle();

    /**
     * Sets the Page title for this layout
     * @param pageTitle
     */
    void setPageTitle(String pageTitle);


    /**
     * Enables/disables 'Remove Selected' button. You should override this method in classes which are
     * implementing BaseLayout interface or are subclasses of BaseVerticalLayout class.
     * @param isEnabled
     */
    public void setButtonRemoveSelectedEnabled(boolean isEnabled);

    /**
     * This method is used for updating UI state of controls controlled by the main Entity bean.
     * Implement your logic of this method in order to update controls state with the values of
     * main Entity bean fields
     */
    public void updateControlsFromBeanState();


    /**
     * This method is used for clearing all the fields of controls controlled by the main Entity bean.
     * Implement your logic of this method in order to clear the controls state, regardless of
     * the state of the main Entity bean field values.
     * The method is usually used when the user has just saved the form and it needs to be repainted allowing
     * to enter new values once again.
     */
    public void clearAllControls();


    /**
     * The method is used for passing a new value for the main Entity bean contained in the layout.
     * According to the logic of the layout and the strict type of the Entity contained the the layout
     * you should use casting to your Bean type when setting a value
     * @param newBeanValue
     */
    public void setNewBeanValue(DBEntity newBeanValue);
}
