package ru.rti.holidays.layout.employee;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import java.util.List;
import java.util.Set;

public class AddNewEmployeeLayout extends BaseVerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(AddNewEmployeeLayout.class);
    private Binder<Employee> employeeBinder = new Binder<Employee>();
    private Employee newEmployee = new Employee();
    private List<ProjectRole> projectRoles;
    private Set<Team> teams;
    private Button btnRemoveSelectedEmployees = new Button("Удалить выбранных сотрудников");
    private PasswordField txtPasswordCheck = new PasswordField("Повтор пароля:");
    private ComboBox<ProjectRole> cboProjectRole;
    private ComboBox<Team> cboTeam;
    private CheckBoxGroup<Team> chkGroupTeams;

    public void setTeams(Set<Team> teams) {  this.teams = teams; }
    public void setProjectRoles(List<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }
    public void setButtonRemoveSelectedEnabled(boolean isEnabled) { btnRemoveSelectedEmployees.setEnabled(isEnabled);}

    @Override
    public void setNewBeanValue(DBEntity newBeanValue) {
        if (newBeanValue instanceof Employee)
            this.newEmployee = (Employee)newBeanValue;
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

        PasswordField txtPassword = new PasswordField("Пароль:");
        employeeBinder.forField(txtPassword)
                .asRequired("Необходимо ввести пароль")
                .bind(Employee::getPassword, Employee::setPassword);


        //employeeBinder.forField(txtPassword)
        //        .asRequired("Необходимо ввести пароль повторно")
        //        .bind(Employee::getPassword, Employee::setPassword);

        cboProjectRole = new ComboBox<>("Проектная роль:");
        cboTeam = new ComboBox<>("Команда:");
        chkGroupTeams = new CheckBoxGroup<>("Команды в управлении:");


        chkGroupTeams.setItems(teams);
        chkGroupTeams.setItemCaptionGenerator(Team::getTeamName);
        chkGroupTeams.setVisible(false);
        //TODO: experimental code block
        //chkGroupTeams.addSelectionListener(multiSelectionEvent -> {

            //Set<Employee> managers = new HashSet<>();
            //managers.add(newEmployee);
            //Set<Team> selectedTeams = multiSelectionEvent.getAllSelectedItems();
            //selectedTeams.forEach(team -> {
                //team.setManagers(managers);
            //});
        //});
        employeeBinder.forField(chkGroupTeams).bind(Employee::getManagedTeams, Employee::setManagedTeams);

        cboProjectRole.setEmptySelectionAllowed(true);
        cboProjectRole.setEmptySelectionCaption("Роль не выбрана");
        cboProjectRole.setTextInputAllowed(false);
        cboProjectRole.setItems(projectRoles);
        cboProjectRole.setItemCaptionGenerator(ProjectRole::getRoleName);
        cboProjectRole.addValueChangeListener(valueChangeEvent -> {
           if (valueChangeEvent.isUserOriginated()) {
               ProjectRole selectedProjectRole = valueChangeEvent.getValue();
               cboTeam.setEnabled(selectedProjectRole != null);
               chkGroupTeams.setVisible(selectedProjectRole != null);
               if (selectedProjectRole != null) {
                   if (ProjectRole.ProjectRoleSpecialType.getRolesWithTeamManagementAbility().contains(selectedProjectRole.getProjectRoleSpecialType())) {
                       triggerControlsVisibility(true);
                   } else {
                       triggerControlsVisibility(false);
                   }
               }
           }

        });

        employeeBinder.forField(cboProjectRole)
                .bind(Employee::getProjectRole, Employee::setProjectRole);

        cboTeam.setEmptySelectionAllowed(true);
        cboTeam.setEmptySelectionCaption("Команда не выбрана");
        cboTeam.setTextInputAllowed(false);
        cboTeam.setItems(teams);
        cboTeam.setItemCaptionGenerator(Team::getTeamName);
        cboTeam.setEnabled(cboProjectRole.getValue() != null);

        employeeBinder.forField(cboTeam).bind(Employee::getTeam, Employee::setTeam);

        Button btnSaveEmployee = new Button("Сохранить", event -> {
            try {
                employeeBinder.writeBean(newEmployee);
                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newEmployee);
                }

                clearAllControls();

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить сотрудника. Пожалуйста, проверьте корректность заполнения полей.");
            }
        });

        btnSaveEmployee.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSaveEmployee.setIcon(VaadinIcons.CHECK);

        btnRemoveSelectedEmployees.addStyleName(ValoTheme.BUTTON_DANGER);
        btnRemoveSelectedEmployees.setIcon(VaadinIcons.DEL);
        btnRemoveSelectedEmployees.setEnabled(false);
        btnRemoveSelectedEmployees.addClickListener(event -> {
            if (removeSelectedItemsClickListener != null) {
                log.info("Calling onRemoveSelectedItems from AddNewEmployeeLayout class.");
                removeSelectedItemsClickListener.onRemoveSelectedItems(null, null);
            }
        });

        txtPassword.setWidth("100%");
        txtPasswordCheck.setWidth("100%");

        //this.addStyleName("debug_border");
        setMargin(false);

        txtEmail.setWidth("100%");
        btnSaveEmployee.setWidth("100%");
        btnRemoveSelectedEmployees.setWidth("100%");
        cboProjectRole.setWidth("100%");
        cboTeam.setWidth("100%");

        GridLayout addEmployeeGridLayout = new GridLayout(5, 5);

        addEmployeeGridLayout.setSizeFull();
        //addEmployeeGridLayout.addStyleName("debug_border");
        addEmployeeGridLayout.setSpacing(true);
        addEmployeeGridLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        addEmployeeGridLayout.setColumnExpandRatio(4,0.2f);
        addEmployeeGridLayout.addComponent(txtLastName, 0,0);
        addEmployeeGridLayout.addComponent(txtFirstName, 1,0);
        addEmployeeGridLayout.addComponent(txtMiddleName, 2,0);
        addEmployeeGridLayout.addComponent(txtLoginName, 3,0);
        addEmployeeGridLayout.addComponent(txtEmail, 4,0);
        addEmployeeGridLayout.addComponent(txtPassword, 0,1, 1,1);
        addEmployeeGridLayout.addComponent(txtPasswordCheck, 2,1, 3,1);
        addEmployeeGridLayout.addComponent(cboProjectRole, 0,2, 4,2);
        addEmployeeGridLayout.addComponent(cboTeam, 0,3,4,3);
        addEmployeeGridLayout.setRowExpandRatio(1,1);
        addEmployeeGridLayout.setRowExpandRatio(2,1);
        addEmployeeGridLayout.addComponent(btnSaveEmployee, 0,4);
        addEmployeeGridLayout.addComponent(btnRemoveSelectedEmployees, 1,4,2,4);

        addComponents(addEmployeeGridLayout);
        addComponent(chkGroupTeams);
    }

    @Override
    public void updateControlsFromBeanState() {
        updateControlsVisibility();
        employeeBinder.readBean(newEmployee);
        Set<Team> teams = chkGroupTeams.getSelectedItems();

        //chkGroupTeams.deselectAll();

        if (teams != null && teams.size() > 0) {
            teams.forEach(team -> log.info(String.format("Selected team: '%s'", team)));
            //chkGroupTeams.select(teams.toArray(new Team[teams.size()]));
        }
    }

    @Override
    public void clearAllControls() {
        setNewBeanValue(new Employee());
        updateControlsFromBeanState();
        txtPasswordCheck.setValue("");
    }

    public void updateControlsVisibility() {
        if (newEmployee == null) {
            return;
        }

        if (chkGroupTeams == null || cboTeam == null) {
            log.error("Error: at least one of controls 'chkGroupTeams', 'cboTeam' is null!");
            return;
        }

        if (newEmployee.getProjectRole() != null &&
                ProjectRole.ProjectRoleSpecialType
                        .getRolesWithTeamManagementAbility()
                        .contains(newEmployee .getProjectRole().getProjectRoleSpecialType())) {
            triggerControlsVisibility(true);
        } else {
            triggerControlsVisibility(false);
        }
    }

    private void triggerControlsVisibility(boolean isManagerRole) {
        if (chkGroupTeams == null || cboTeam == null) {
            log.error("Error: at least one of controls 'chkGroupTeams', 'cboTeam' is null!");
            return;
        }

        chkGroupTeams.setVisible(isManagerRole);
        cboTeam.setVisible(!isManagerRole);
    }
}
