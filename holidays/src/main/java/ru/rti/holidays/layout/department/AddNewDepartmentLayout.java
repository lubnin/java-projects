package ru.rti.holidays.layout.department;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.entity.Department;
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

        TextField txtDepartmentCode = new TextField("Код подразделения:");
        txtDepartmentCode.setWidth("100%");


        departmentBinder.forField(txtDepartmentName)
                .asRequired("Необходимо ввести название подразделения")
                .withValidator(new StringLengthValidator("Длина названия подразделения должна быть от 1 до 255",1,255))
                .bind(Department::getName, Department::setName);

        departmentBinder.forField(txtDepartmentCode)
                .asRequired("Необходимо ввести код подразделения")
                .withValidator(new StringLengthValidator("Длина кода подразделения должна быть от 1 до 255",1,255))
                .bind(Department::getCode, Department::setCode);

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

        GridLayout addDepartmentGridLayout = new GridLayout(2, 3);
        addDepartmentGridLayout.setSpacing(true);
        addDepartmentGridLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        addDepartmentGridLayout.addComponent(txtDepartmentName, 0,0, 1, 0);
        addDepartmentGridLayout.addComponent(txtDepartmentCode, 0,1, 1, 1);
        addDepartmentGridLayout.addComponent(btnSaveDepartment, 0,2);
        addDepartmentGridLayout.addComponent(btnRemoveSelectedDepartments, 1, 2);

        addComponents(addDepartmentGridLayout);
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
