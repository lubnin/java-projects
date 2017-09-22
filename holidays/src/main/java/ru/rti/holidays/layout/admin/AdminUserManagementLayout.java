package ru.rti.holidays.layout.admin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.component.BoldLabel;
import ru.rti.holidays.component.PageTitle;
import ru.rti.holidays.entity.*;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.base.behaviour.RefreshGridDataListener;
import ru.rti.holidays.layout.base.behaviour.SaveButtonClickListener;
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


            AddNewEmployeeLayout addEmployeeLayout = new AddNewEmployeeLayout();
            addEmployeeLayout.setWidth("100%");
            addEmployeeLayout.setParentLayout(this);
            addEmployeeLayout.setProjectRoles(projectRoles);
            addEmployeeLayout.setTeams(teams);
            addEmployeeLayout.setSaveButtonClickListener(saveButtonClickListener);
            addEmployeeLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                if (removeSelectedItemsClickListener != null) {
                    removeSelectedItemsClickListener.onRemoveSelectedItems(this, grdEmployees.getSelectedItems());
                    refreshDataGrid();
                }
            });
            addEmployeeLayout.constructLayout();

            selectionModel.addMultiSelectionListener(event -> {
                Set<Employee> selectedItems = event.getAllSelectedItems();
                addEmployeeLayout.setButtonRemoveSelectedEnabled(selectedItems != null && selectedItems.size() > 0);
            });

            grdEmployees.addColumn(Employee::getLastName).setCaption("Фамилия");
            grdEmployees.addColumn(Employee::getFirstName).setCaption("Имя");
            grdEmployees.addColumn(Employee::getMiddleName).setCaption("Отчество");
            grdEmployees.addColumn(Employee::getEmail).setCaption("E-Mail");
            grdEmployees.addColumn(Employee::getProjectRoleAsString).setCaption("Проектная роль");
            grdEmployees.addColumn(Employee::getTeamNameAsString).setCaption("Команда");
            grdEmployees.setHeightByRows(5);
            grdEmployees.setWidth("100%");

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
}
