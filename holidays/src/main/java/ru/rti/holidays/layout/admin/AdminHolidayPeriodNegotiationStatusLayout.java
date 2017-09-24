package ru.rti.holidays.layout.admin;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.component.BoldLabel;
import ru.rti.holidays.component.PageTitle;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.holidayperiod.AddNewHolidayPeriodNegotiationStatusLayout;

import java.util.List;
import java.util.Set;

@SpringComponent
public class AdminHolidayPeriodNegotiationStatusLayout extends BaseVerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(AdminHolidayPeriodNegotiationStatusLayout.class);
    private Grid<HolidayPeriodNegotiationStatus> grdHolidayPeriodNegotiationStatuses = new Grid<>();
    private List<HolidayPeriodNegotiationStatus> holidayPeriodNegotiationStatuses;

    @Override
    public void constructLayout() {
        try {
            addComponent(new PageTitle("Управление статусами согласования периода отпусков"));
            addComponent(new BoldLabel("Все статусы"));

            MultiSelectionModel<HolidayPeriodNegotiationStatus> selectionModel =
                    (MultiSelectionModel<HolidayPeriodNegotiationStatus>)grdHolidayPeriodNegotiationStatuses.setSelectionMode(Grid.SelectionMode.MULTI);

            grdHolidayPeriodNegotiationStatuses.addColumn(HolidayPeriodNegotiationStatus::getStatusName).setCaption("Название статуса");
            grdHolidayPeriodNegotiationStatuses.addColumn(HolidayPeriodNegotiationStatus::getStatusDescription).setCaption("Описание статуса");
            grdHolidayPeriodNegotiationStatuses.setHeightByRows(5);
            grdHolidayPeriodNegotiationStatuses.setWidth("100%");

            addComponent(grdHolidayPeriodNegotiationStatuses);
            addComponent(new BoldLabel("Добавить новый статус"));

            //TODO change this
            AddNewHolidayPeriodNegotiationStatusLayout addNewHolidayPeriodNegotiationStatusLayout = new AddNewHolidayPeriodNegotiationStatusLayout();
            addNewHolidayPeriodNegotiationStatusLayout.setWidth("100%");
            addNewHolidayPeriodNegotiationStatusLayout.setParentLayout(this);
            addNewHolidayPeriodNegotiationStatusLayout.setExceptionHandler(getExceptionHandler());
            addNewHolidayPeriodNegotiationStatusLayout.setSaveButtonClickListener(saveButtonClickListener);
            addNewHolidayPeriodNegotiationStatusLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                if (removeSelectedItemsClickListener != null) {
                    removeSelectedItemsClickListener.onRemoveSelectedItems(this, grdHolidayPeriodNegotiationStatuses.getSelectedItems());
                    refreshDataGrid();
                }
            });

            addNewHolidayPeriodNegotiationStatusLayout.constructLayout();

            selectionModel.addMultiSelectionListener(event -> {
                Set<HolidayPeriodNegotiationStatus> selectedItems = event.getAllSelectedItems();
                addNewHolidayPeriodNegotiationStatusLayout.setButtonRemoveSelectedEnabled(selectedItems != null && selectedItems.size() > 0);
                if (selectedItems.size() == 1) {
                    HolidayPeriodNegotiationStatus selectedStatus = event.getFirstSelectedItem().get();
                    addNewHolidayPeriodNegotiationStatusLayout.setNewBeanValue(selectedStatus);
                    addNewHolidayPeriodNegotiationStatusLayout.updateControlsFromBeanState();
                } else {
                    addNewHolidayPeriodNegotiationStatusLayout.clearAllControls();
                }
            });


            addComponent(addNewHolidayPeriodNegotiationStatusLayout);
        }
        catch (Exception e) {
            handleException(e, e.getMessage());
        }
    }

    @Override
    public void postConstructLayout() {
        try {
            refreshDataGrid();
        } catch (Exception e) {
            handleException(e, e.getMessage());
        }
    }

    @Override
    public void refreshDataGrid() {
        super.refreshDataGrid();

        //TODO: customize your logic here
        grdHolidayPeriodNegotiationStatuses.setItems(holidayPeriodNegotiationStatuses);
    }


    public List<HolidayPeriodNegotiationStatus> getHolidayPeriodNegotiationStatuses() {
        return holidayPeriodNegotiationStatuses;
    }

    public void setHolidayPeriodNegotiationStatuses(List<HolidayPeriodNegotiationStatus> holidayPeriodNegotiationStatuses) {
        this.holidayPeriodNegotiationStatuses = holidayPeriodNegotiationStatuses;
    }
}
