package ru.rti.holidays.view.admin;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.service.ProjectRoleService;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = AddEmployeeView.VIEW_NAME)
public class AddEmployeeView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "AddEmployee";

    @Autowired
    Employee newEmployee;

    @Autowired
    EmployeeService employeeServiceImpl;

    @Autowired
    ProjectRoleService projectRoleService;

    @PostConstruct
    void init() {
        addComponent(new Label("Страница добавления сотрудника."));

        Binder<Employee> employeeBinder = new Binder<Employee>();

        Label lblSaveStatus = new Label();

        //Label lblLastName = new Label("Фамилия:");
        TextField txtLastName = new TextField("Фамилия:");
        employeeBinder.forField(txtLastName)
                .asRequired("Необходимо ввести фамилию сотрудника")
                .withStatusLabel(lblSaveStatus)
                .bind(Employee::getLastName, Employee::setLastName);

        //Label lblFirstName = new Label("Имя:");
        TextField txtFirstName = new TextField("Имя:");
        employeeBinder.forField(txtFirstName)
                .asRequired("Необходимо ввести имя сотрудника")
                .withStatusLabel(lblSaveStatus)
                .bind(Employee::getFirstName, Employee::setFirstName);

        //Label lblMiddleName = new Label("Отчество:");
        TextField txtMiddleName = new TextField("Отчество:");
        employeeBinder.forField(txtMiddleName)
                .asRequired("Необходимо ввести отчество сотрудника")
                .withStatusLabel(lblSaveStatus)
                .bind(Employee::getMiddleName, Employee::setMiddleName);

        TextField txtLoginName = new TextField("Логин:");
        employeeBinder.forField(txtLoginName)
                .asRequired("Необходимо ввести логин сотрудника")
                .withStatusLabel(lblSaveStatus)
                .bind(Employee::getLoginName, Employee::setLoginName);

        ComboBox<ProjectRole> cboProjectRole = new ComboBox<>("Выберите проектную роль сотрудника:");
        List<ProjectRole> roles = projectRoleService.getAllProjectRoles();
        cboProjectRole.setEmptySelectionAllowed(true);
        cboProjectRole.setEmptySelectionCaption("Роль не выбрана");
        cboProjectRole.setTextInputAllowed(false);
        cboProjectRole.setItems(roles);
        cboProjectRole.setItemCaptionGenerator(ProjectRole::getRoleName);

        employeeBinder.forField(cboProjectRole)
                .bind(Employee::getProjectRole, Employee::setProjectRole);

        Button btnSaveEmployee = new Button("Сохранить", event -> {
            try {
                employeeBinder.writeBean(newEmployee);
                employeeServiceImpl.addEmployee(newEmployee);
                //employeeBinder.
                //employeeBinder.readBean(newEmployee);
                Notification.show("Сотрудник успешно сохранён в базу!");
                getUI().getNavigator().navigateTo(AddEmployeeView.VIEW_NAME);
            } catch (ValidationException e) {
                Notification.show("Невозможно сохранить сотрудника, " +
                        "пожалуйста, проверьте сообщения об ошибках для каждого из полей.");

            }
        });
        addComponents(txtLastName, txtFirstName, txtMiddleName, txtLoginName, cboProjectRole, btnSaveEmployee, lblSaveStatus);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}
