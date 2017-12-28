package ru.rti.holidays.layout.admin;

import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.component.vaadin.label.BoldLabel;
import ru.rti.holidays.component.vaadin.label.PageTitle;
import ru.rti.holidays.entity.Department;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.department.AddNewDepartmentLayout;

import java.util.List;
import java.util.Set;

public class AdminDepartmentManagementLayout extends BaseVerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(AdminDepartmentManagementLayout.class);
    private Grid<Department> grdDepartments = new Grid<>();
    private List<Department> departments;

    @Override
    public void constructLayout() {
        try {
            addComponent(new PageTitle("Управление подразделениями"));
            addComponent(new BoldLabel("Все подразделения"));

            MultiSelectionModel<Department> selectionModel =
                    (MultiSelectionModel<Department>)grdDepartments.setSelectionMode(Grid.SelectionMode.MULTI);

            grdDepartments.addColumn(Department::getName).setCaption("Подразделение");
            grdDepartments.addColumn(Department::getCode).setCaption("Код");
            grdDepartments.setHeightByRows(10);
            grdDepartments.setWidth("100%");

            addComponent(grdDepartments);
            addComponent(new BoldLabel("Добавить новое подразделение"));

            AddNewDepartmentLayout addDepartmentLayout = new AddNewDepartmentLayout();
            addDepartmentLayout.setWidth("100%");
            addDepartmentLayout.setParentLayout(this);
            addDepartmentLayout.setExceptionHandler(getExceptionHandler());
            addDepartmentLayout.setSaveButtonClickListener(saveButtonClickListener);
            addDepartmentLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                if (removeSelectedItemsClickListener != null) {
                    removeSelectedItemsClickListener.onRemoveSelectedItems(this, grdDepartments.getSelectedItems());
                    refreshDataGrid();
                }
            });
            addDepartmentLayout.constructLayout();

            selectionModel.addMultiSelectionListener(event -> {
                Set<Department> selectedItems = event.getAllSelectedItems();
                addDepartmentLayout.setButtonRemoveSelectedEnabled(selectedItems != null && selectedItems.size() > 0);
                if (selectedItems.size() == 1) {
                    Department selectedDepartment = event.getFirstSelectedItem().get();
                    addDepartmentLayout.setNewBeanValue(selectedDepartment);
                    addDepartmentLayout.updateControlsFromBeanState();
                } else {
                    addDepartmentLayout.clearAllControls();
                }
            });

            addComponent(addDepartmentLayout);
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
        grdDepartments.setItems(departments);
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
