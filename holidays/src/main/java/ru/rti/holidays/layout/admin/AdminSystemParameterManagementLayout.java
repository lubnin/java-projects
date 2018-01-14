package ru.rti.holidays.layout.admin;

import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.component.vaadin.label.BoldLabel;
import ru.rti.holidays.component.vaadin.label.PageTitle;
import ru.rti.holidays.entity.SystemParameter;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.systemparameter.AddNewSystemParameterLayout;

import java.util.List;
import java.util.Set;

public class AdminSystemParameterManagementLayout extends BaseVerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(AdminSystemParameterManagementLayout.class);
    private Grid<SystemParameter> grdSystemParameters = new Grid<>();
    private List<SystemParameter> systemParameters;

    @Override
    public void constructLayout() {
        try {
            addComponent(new PageTitle("Управление системными параметрами"));
            addComponent(new BoldLabel("Все системные параметры"));

            MultiSelectionModel<SystemParameter> selectionModel =
                    (MultiSelectionModel<SystemParameter>)grdSystemParameters.setSelectionMode(Grid.SelectionMode.MULTI);

            grdSystemParameters.addColumn(SystemParameter::getCaption).setCaption("Название");
            grdSystemParameters.addColumn(SystemParameter::getInnerName).setCaption("Внутреннее имя");
            grdSystemParameters.addColumn(SystemParameter::getInnerTypeAsString).setCaption("Внутренний тип");
            grdSystemParameters.addColumn(SystemParameter::getValue).setCaption("Значение");
            grdSystemParameters.setHeightByRows(10);
            grdSystemParameters.setWidth("100%");

            addComponent(grdSystemParameters);
            addComponent(new BoldLabel("Добавить новый системный параметр"));

            AddNewSystemParameterLayout addNewSystemParameterLayout = new AddNewSystemParameterLayout();
            addNewSystemParameterLayout.setWidth("100%");
            addNewSystemParameterLayout.setParentLayout(this);
            addNewSystemParameterLayout.setExceptionHandler(getExceptionHandler());
            addNewSystemParameterLayout.setSaveButtonClickListener(saveButtonClickListener);
            addNewSystemParameterLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                if (removeSelectedItemsClickListener != null) {
                    removeSelectedItemsClickListener.onRemoveSelectedItems(this, grdSystemParameters.getSelectedItems());
                    refreshDataGrid();
                }
            });
            addNewSystemParameterLayout.constructLayout();

            selectionModel.addMultiSelectionListener(event -> {
                Set<SystemParameter> selectedItems = event.getAllSelectedItems();
                addNewSystemParameterLayout.setButtonRemoveSelectedEnabled(selectedItems != null && selectedItems.size() > 0);
                if (selectedItems.size() == 1) {
                    SystemParameter selectedSystemParameter = event.getFirstSelectedItem().get();
                    addNewSystemParameterLayout.setNewBeanValue(selectedSystemParameter);
                    addNewSystemParameterLayout.updateControlsFromBeanState();
                } else {
                    addNewSystemParameterLayout.clearAllControls();
                }
            });

            addComponent(addNewSystemParameterLayout);
        } catch (Exception e) {
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
        grdSystemParameters.setItems(systemParameters);
    }

    public List<SystemParameter> getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(List<SystemParameter> systemParameters) {
        this.systemParameters = systemParameters;
    }
}
