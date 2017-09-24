package ru.rti.holidays.layout.admin;

import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.component.BoldLabel;
import ru.rti.holidays.component.PageTitle;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.projectrole.AddNewProjectRoleLayout;

import java.util.List;
import java.util.Set;

@SpringComponent
public class AdminProjectRoleManagementLayout extends BaseVerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(AdminProjectRoleManagementLayout.class);
    private Grid<ProjectRole> grdProjectRoles = new Grid<>();
    private List<ProjectRole> projectRoles;

    @Override
    public void constructLayout() {
        try {
            addComponent(new PageTitle(getPageTitle()));
            addComponent(new BoldLabel("Все проектные роли"));

            MultiSelectionModel<ProjectRole> selectionModel =
                    (MultiSelectionModel<ProjectRole>)grdProjectRoles.setSelectionMode(Grid.SelectionMode.MULTI);

            selectionModel.addMultiSelectionListener(event -> {
                Set<ProjectRole> selectedItems = event.getAllSelectedItems();
                //btnRemoveHolidayPeriods.setEnabled(selectedItems != null && selectedItems.size() > 0);
            });

            grdProjectRoles.addColumn(ProjectRole::getRoleName).setCaption("Название проектной роли");
            grdProjectRoles.setColumnResizeMode(ColumnResizeMode.ANIMATED);
            grdProjectRoles.addColumn(ProjectRole::getRoleDescription).setCaption("Описание проектной роли");
            grdProjectRoles.addColumn(ProjectRole::getProjectRoleSpecialTypeAsString).setCaption("Тип роли");
            grdProjectRoles.setHeightByRows(5);
            grdProjectRoles.setWidth("100%");

            addComponent(grdProjectRoles);
            addComponent(new BoldLabel("Добавить новую проектную роль"));

            AddNewProjectRoleLayout addNewProjectRoleLayout = new AddNewProjectRoleLayout();
            addNewProjectRoleLayout.setWidth("100%");
            addNewProjectRoleLayout.setSizeFull();
            addNewProjectRoleLayout.setExceptionHandler(getExceptionHandler());
            addNewProjectRoleLayout.setParentLayout(this);
            addNewProjectRoleLayout.setSaveButtonClickListener(saveButtonClickListener);
            addNewProjectRoleLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                if (removeSelectedItemsClickListener != null) {
                    removeSelectedItemsClickListener.onRemoveSelectedItems(this, grdProjectRoles.getSelectedItems());
                    refreshDataGrid();
                }
            });
            selectionModel.addMultiSelectionListener(event -> {
                Set<ProjectRole> selectedItems = event.getAllSelectedItems();
                addNewProjectRoleLayout.setButtonRemoveSelectedEnabled(selectedItems != null && selectedItems.size() > 0);
                if (selectedItems.size() == 1) {
                    ProjectRole selectedProjectRole = event.getFirstSelectedItem().get();
                    addNewProjectRoleLayout.setNewBeanValue(selectedProjectRole);
                    addNewProjectRoleLayout.updateControlsFromBeanState();
                } else {
                    addNewProjectRoleLayout.clearAllControls();
                }
            });

            addNewProjectRoleLayout.constructLayout();



            addComponent(addNewProjectRoleLayout);
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
        grdProjectRoles.setItems(projectRoles);
    }

    public void setProjectRoles(List<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    public List<ProjectRole> getProjectRoles() {
        return projectRoles;
    }
}
