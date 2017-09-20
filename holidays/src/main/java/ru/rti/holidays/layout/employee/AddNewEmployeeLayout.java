package ru.rti.holidays.layout.employee;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.base.behaviour.SaveButtonClickListener;
import ru.rti.holidays.view.admin.AddEmployeeView;

import java.util.List;

public class AddNewEmployeeLayout extends BaseVerticalLayout {
    Binder<Employee> employeeBinder = new Binder<Employee>();
    Employee newEmployee = new Employee();
    List<ProjectRole> projectRoles;


    public void setProjectRoles(List<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    @Override
    public void constructLayout() {
        TextField txtLastName = new TextField("Фамилия:");
        employeeBinder.forField(txtLastName)
                .asRequired("Необходимо ввести фамилию сотрудника")
                .bind(Employee::getLastName, Employee::setLastName);

        TextField txtFirstName = new TextField("Имя:");
        employeeBinder.forField(txtFirstName)
                .asRequired("Необходимо ввести имя сотрудника")
                .bind(Employee::getFirstName, Employee::setFirstName);

        TextField txtMiddleName = new TextField("Отчество:");
        employeeBinder.forField(txtMiddleName)
                .asRequired("Необходимо ввести отчество сотрудника")
                .bind(Employee::getMiddleName, Employee::setMiddleName);

        TextField txtLoginName = new TextField("Логин:");
        employeeBinder.forField(txtLoginName)
                .asRequired("Необходимо ввести логин сотрудника")
                .bind(Employee::getLoginName, Employee::setLoginName);

        TextField txtEmail = new TextField("E-Mail:");
        employeeBinder.forField(txtEmail)
                .asRequired("Необходимо ввести E-Mail сотрудника")
                .bind(Employee::getEmail, Employee::setEmail);

        ComboBox<ProjectRole> cboProjectRole = new ComboBox<>("Проектная роль:");

        cboProjectRole.setEmptySelectionAllowed(true);
        cboProjectRole.setEmptySelectionCaption("Роль не выбрана");
        cboProjectRole.setTextInputAllowed(false);
        cboProjectRole.setItems(projectRoles);
        cboProjectRole.setItemCaptionGenerator(ProjectRole::getRoleName);

        employeeBinder.forField(cboProjectRole)
                .bind(Employee::getProjectRole, Employee::setProjectRole);

        Button btnSaveEmployee = new Button("Сохранить", event -> {
            try {
                employeeBinder.writeBean(newEmployee);
                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newEmployee);
                }

                newEmployee = new Employee();
                employeeBinder.readBean(newEmployee);

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить сотрудника. Пожалуйста, проверьте корректность заполнения полей.");
            }
        });

        btnSaveEmployee.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSaveEmployee.setIcon(VaadinIcons.CHECK);

        this.addStyleName("debug_border");
        setMargin(false);

        GridLayout addEmployeeGridLayout = new GridLayout(5, 2);
        addEmployeeGridLayout.addStyleName("debug_border");
        addEmployeeGridLayout.setSpacing(true);
        addEmployeeGridLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        addEmployeeGridLayout.addComponent(txtLastName, 0,0);
        addEmployeeGridLayout.addComponent(txtFirstName, 1,0);
        addEmployeeGridLayout.addComponent(txtMiddleName, 2,0);
        addEmployeeGridLayout.addComponent(txtLoginName, 3,0);
        addEmployeeGridLayout.addComponent(txtEmail, 4,0);
        addEmployeeGridLayout.addComponent(cboProjectRole, 0,1);
        addEmployeeGridLayout.addComponent(btnSaveEmployee, 1,1);

        addComponents(addEmployeeGridLayout);
    }
}
