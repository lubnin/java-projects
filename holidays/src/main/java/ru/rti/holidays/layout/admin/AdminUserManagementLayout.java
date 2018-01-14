package ru.rti.holidays.layout.admin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.component.vaadin.label.BoldLabel;
import ru.rti.holidays.component.vaadin.label.PageTitle;
import ru.rti.holidays.entity.Department;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.employee.AddNewEmployeeLayout;

import java.util.List;
import java.util.Set;

@SpringComponent
public class AdminUserManagementLayout extends BaseVerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(AdminUserManagementLayout.class);
    private Grid<Employee> grdEmployees = new Grid<>();
    private List<Employee> employees;
    private List<ProjectRole> projectRoles;
    private List<Team> teams;
    private List<Department> departments;

    public void setTeams(List<Team> teams) { this.teams = teams; }
    public void setProjectRoles(List<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    @Override
    public void constructLayout() {
        try {
            addComponent(new PageTitle("Управление пользователями"));
            addComponent(new BoldLabel("Все пользователи"));

            MultiSelectionModel<Employee> selectionModel =
                    (MultiSelectionModel<Employee>)grdEmployees.setSelectionMode(Grid.SelectionMode.MULTI);


            grdEmployees.addColumn(Employee::getLastName).setCaption("Фамилия");
            grdEmployees.addColumn(Employee::getFirstName).setCaption("Имя");
            grdEmployees.addColumn(Employee::getMiddleName).setCaption("Отчество");
            grdEmployees.addColumn(Employee::getEmail).setCaption("E-Mail");
            grdEmployees.addColumn(Employee::getProjectRoleAsString).setCaption("Проектная роль");
            grdEmployees.addColumn(Employee::getLoginName).setCaption("Логин");
            grdEmployees.addColumn(Employee::getTeamNameAsString).setCaption("Команда");
            grdEmployees.addColumn(Employee::getAllManagedTeamsAsString).setCaption("Команды под управлением");
            grdEmployees.addColumn(Employee::getDepartmentAsString).setCaption("Подразделение");
            grdEmployees.addColumn(Employee::getSpecialCode).setCaption("Спецкод");

            grdEmployees.setHeightByRows(5);
            grdEmployees.setWidth("100%");

            AddNewEmployeeLayout addEmployeeLayout = new AddNewEmployeeLayout();
            addEmployeeLayout.setWidth("100%");
            addEmployeeLayout.setParentLayout(this);
            addEmployeeLayout.setProjectRoles(projectRoles);
            addEmployeeLayout.setAllTeams(teams);
            addEmployeeLayout.setDepartments(departments);
            addEmployeeLayout.setExceptionHandler(getExceptionHandler());
            addEmployeeLayout.setSaveButtonClickListener(saveButtonClickListener);
            addEmployeeLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                if (removeSelectedItemsClickListener != null) {
                    //log.info("AdminUserManagementLayout::setRemoveSelectedItemsClickListener() called. removeSelectedItemsClickListener != null - OK");
                    removeSelectedItemsClickListener.onRemoveSelectedItems(this, grdEmployees.getSelectedItems());
                    //log.info("Calling refreshDataGrid()");
                    refreshDataGrid();
                }
            });

            selectionModel.addMultiSelectionListener(event -> {
                Set<Employee> selectedItems = event.getAllSelectedItems();
                addEmployeeLayout.setButtonRemoveSelectedEnabled(selectedItems != null && selectedItems.size() > 0);
                if (selectedItems.size() == 1) {
                    Employee selectedEmployee = event.getFirstSelectedItem().get();
                    addEmployeeLayout.setNewBeanValue(selectedEmployee);
                    addEmployeeLayout.updateControlsFromBeanState();
                    //addEmployeeLayout.updateControlsVisibility();
                } else {
                    addEmployeeLayout.clearAllControls();
                }
            });

            addEmployeeLayout.constructLayout();

            addComponent(grdEmployees);

            addComponent(new BoldLabel("Добавить нового пользователя"));
            addComponent(addEmployeeLayout);
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
        grdEmployees.setItems(employees);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
