package ru.rti.holidays.layout.department;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.entity.Department;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.layout.base.BaseVerticalLayout;

public class AddNewDepartmentLayout extends BaseVerticalLayout {
    private Binder<Department> departmentBinder = new Binder<>();
    private Department newDepartment = new Department();
    private Button btnRemoveSelectedDepartments = new Button("Удалить выбранные подразделения");

    @Override
    public void setNewBeanValue(DBEntity newBeanValue) {
        if (newBeanValue instanceof Department) {
            newDepartment = (Department)newBeanValue;
        }
    }

    @Override
    public void setButtonRemoveSelectedEnabled(boolean isEnabled) {
        btnRemoveSelectedDepartments.setEnabled(isEnabled);
    }

    @Override
    public void constructLayout() {
        TextField txtDepartmentName = new TextField("Название подразделения:");
        txtDepartmentName.setWidth("100%");

        departmentBinder.forField(txtDepartmentName)
                .asRequired("Необходимо ввести название подразделения")
                .bind(Department::getName, Department::setName);

        Button btnSaveDepartment = new Button("Сохранить", event -> {
            try {
                departmentBinder.writeBean(newDepartment);
                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newDepartment);
                }

                clearAllControls();

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить подразделение. Пожалуйста, проверьте корректность заполнения полей.");
            }
        });


        btnRemoveSelectedDepartments.addStyleName(ValoTheme.BUTTON_DANGER);
        btnRemoveSelectedDepartments.setIcon(VaadinIcons.DEL);
        btnRemoveSelectedDepartments.setEnabled(false);
        btnRemoveSelectedDepartments.addClickListener(event -> {
            if (removeSelectedItemsClickListener != null) {
                removeSelectedItemsClickListener.onRemoveSelectedItems(null, null);
            }
        });
        btnRemoveSelectedDepartments.setWidth("100%");

        btnSaveDepartment.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSaveDepartment.setIcon(VaadinIcons.CHECK);

        setMargin(false);

        GridLayout addTeamGridLayout = new GridLayout(2, 2);
        addTeamGridLayout.setSpacing(true);
        addTeamGridLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        addTeamGridLayout.addComponent(txtDepartmentName, 0,0, 1, 0);
        addTeamGridLayout.addComponent(btnSaveDepartment, 0,1);
        addTeamGridLayout.addComponent(btnRemoveSelectedDepartments, 1, 1);

        addComponents(addTeamGridLayout);
    }

    @Override
    public void updateControlsFromBeanState() {
        departmentBinder.readBean(newDepartment);
    }

    @Override
    public void clearAllControls() {
        setNewBeanValue(new Department());
        updateControlsFromBeanState();
    }
}
