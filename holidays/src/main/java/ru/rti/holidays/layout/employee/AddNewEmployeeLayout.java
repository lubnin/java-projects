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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AddNewEmployeeLayout extends BaseVerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(AddNewEmployeeLayout.class);
    private Binder<Employee> employeeBinder = new Binder<Employee>();
    private Employee newEmployee = new Employee();
    private List<ProjectRole> projectRoles;
    private List<Team> allTeams;
    private Button btnRemoveSelectedEmployees = new Button("Удалить выбранных сотрудников");
    private PasswordField txtPasswordCheck = new PasswordField("Повтор пароля:");
    private ComboBox<ProjectRole> cboProjectRole;
    private ComboBox<Team> cboTeam;
    private Label lblTeamsCheckboxes = new Label("Команды в управлении:");
    private Team[] teamsArray = null;
    /**
     * CheckBoxGroup in Vaadin is unfortunately buggy, so I finally replaced it with simple array of CheckBox type.
     */
    private CheckBox[] teamCheckboxes;

    public void setAllTeams(List<Team> allTeams) {  this.allTeams = allTeams; }
    public void setProjectRoles(List<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }
    public void setButtonRemoveSelectedEnabled(boolean isEnabled) { btnRemoveSelectedEmployees.setEnabled(isEnabled);}

    @Override
    public void setNewBeanValue(DBEntity newBeanValue) {
        if (newBeanValue instanceof Employee) {
            this.newEmployee = (Employee) newBeanValue;
        }
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
                .bind(Employee::getEmptyPassword, Employee::setPassword);


        //employeeBinder.forField(txtPassword)
        //        .asRequired("Необходимо ввести пароль повторно")
        //        .bind(Employee::getPassword, Employee::setPassword);

        cboProjectRole = new ComboBox<>("Проектная роль:");
        cboTeam = new ComboBox<>("Команда:");

        if (allTeams != null && allTeams.size() > 0) {
            teamCheckboxes = new CheckBox[allTeams.size()];
            teamsArray = allTeams.toArray(new Team[allTeams.size()]);

            for (int i = 0; i < teamCheckboxes.length; i++) {
                teamCheckboxes[i] = new CheckBox();
                teamCheckboxes[i].setCaption(teamsArray[i].getTeamName());
            }
        }

        cboProjectRole.setEmptySelectionAllowed(true);
        cboProjectRole.setEmptySelectionCaption("Роль не выбрана");
        cboProjectRole.setTextInputAllowed(false);
        cboProjectRole.setItems(projectRoles);
        cboProjectRole.setItemCaptionGenerator(ProjectRole::getRoleName);
        cboProjectRole.addValueChangeListener(valueChangeEvent -> {
           if (valueChangeEvent.isUserOriginated()) {
               ProjectRole selectedProjectRole = valueChangeEvent.getValue();
               cboTeam.setEnabled(selectedProjectRole != null);
               if (selectedProjectRole != null) {
                   if (ProjectRole.ProjectRoleSpecialType.getRolesWithTeamManagementAbility().contains(selectedProjectRole.getProjectRoleSpecialType())) {
                       triggerControlsVisibility(true);
                   } else {
                       triggerControlsVisibility(false);
                   }
               } else {
                   triggerControlsVisibility(false);
               }
           }

        });

        employeeBinder.forField(cboProjectRole)
                .bind(Employee::getProjectRole, Employee::setProjectRole);

        cboTeam.setEmptySelectionAllowed(true);
        cboTeam.setEmptySelectionCaption("Команда не выбрана");
        cboTeam.setTextInputAllowed(false);
        cboTeam.setItems(allTeams);
        cboTeam.setItemCaptionGenerator(Team::getTeamName);
        cboTeam.setEnabled(cboProjectRole.getValue() != null);

        employeeBinder.forField(cboTeam).bind(Employee::getTeam, Employee::setTeam);

        Button btnSaveEmployee = new Button("Сохранить", event -> {
            try {
                employeeBinder.writeBean(newEmployee);

                if (newEmployee.isManager()) {
                    Set<Team> managedTeams = newEmployee.getManagedTeams();
                    if (managedTeams != null) {
                        log.info("Managed teams size: " + managedTeams.size());
                        managedTeams.clear();
                        for (int i = 0; i < teamCheckboxes.length; i++) {
                            if (teamCheckboxes[i].getValue() != null && teamCheckboxes[i].getValue()) {
                                managedTeams.add(teamsArray[i]);
                            }
                        }
                    }
                }

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

        int teamsRowsIncrement = allTeams != null && allTeams.size() > 0 ? allTeams.size() + 1 : 0;
        GridLayout addEmployeeGridLayout = new GridLayout(5, 5 + teamsRowsIncrement);

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

        if (allTeams != null && allTeams.size() > 0) {
            lblTeamsCheckboxes.addStyleName(ValoTheme.LABEL_SMALL);
            addEmployeeGridLayout.addComponent(lblTeamsCheckboxes, 0, 4);
            for (int i = 0; i < teamCheckboxes.length; i++) {
                addEmployeeGridLayout.addComponent(teamCheckboxes[i], 0, 4 + i + 1);
            }
        }

        addEmployeeGridLayout.addComponent(btnSaveEmployee, 0,4 + teamsRowsIncrement);
        addEmployeeGridLayout.addComponent(btnRemoveSelectedEmployees, 1,4 + teamsRowsIncrement,2,4 + teamsRowsIncrement);

        addComponents(addEmployeeGridLayout);
    }

    @Override
    public void updateControlsFromBeanState() {
        employeeBinder.readBean(newEmployee);
        updateControlsVisibility();
        updateControlsEnabledState();
    }

    @Override
    public void clearAllControls() {
        setNewBeanValue(new Employee());
        updateControlsFromBeanState();
        txtPasswordCheck.setValue("");
    }

    public void updateControlsEnabledState() {
        if (cboProjectRole.getSelectedItem() != null) {
            cboTeam.setEnabled(true);
        } else {
            cboTeam.setEnabled(false);
        }
    }

    public void updateControlsVisibility() {
        if (newEmployee == null) {
            return;
        }

        if (teamCheckboxes == null && cboTeam == null) {
            log.error("Error: at least one of controls 'teamCheckboxes', 'cboTeam' must not be null!");
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
        if (teamCheckboxes == null && cboTeam == null) {
            log.error("Error: at least one of controls 'teamCheckboxes', 'cboTeam' must not be null!");
            return;
        }

        lblTeamsCheckboxes.setVisible(isManagerRole);
        if (allTeams != null && allTeams.size() > 0) {
            for (int i = 0; i < teamCheckboxes.length; i++) {
                teamCheckboxes[i].setVisible(isManagerRole);
            }
        }

        if (isManagerRole) {
            Set<Team> managedTeams = newEmployee.getManagedTeams();
            for (int i = 0; i < teamCheckboxes.length; i++) {
                Team currentTeam = teamsArray[i];
                boolean isTeamSelected = false;
                for (Team managedTeam : managedTeams) {
                    if (managedTeam.getId().equals(currentTeam.getId())) {
                        isTeamSelected = true;
                        break;
                    }
                }
                teamCheckboxes[i].setValue(isTeamSelected);
            }
        }
        //cboTeam.setVisible(!isManagerRole);
        cboTeam.setVisible(true);
    }
}
