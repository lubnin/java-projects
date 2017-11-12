package ru.rti.holidays.layout.projectrole;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.common.ProjectRoleSpecialTypeCaptionGenerator;

public class AddNewProjectRoleLayout extends BaseVerticalLayout {
    private Binder<ProjectRole> projectRoleBinder = new Binder<ProjectRole>();
    private ProjectRole newProjectRole = new ProjectRole();
    private Button btnRemoveSelectedProjectRoles = new Button("Удалить выбранные роли");

    public void setButtonRemoveSelectedEnabled(boolean isEnabled) { btnRemoveSelectedProjectRoles.setEnabled(isEnabled);}

    @Override
    public void setNewBeanValue(DBEntity newBeanValue) {
        if (newBeanValue instanceof ProjectRole) {
            newProjectRole = (ProjectRole)newBeanValue;
        }
    }

    @Override
    public void constructLayout() {
        TextField txtProjectRoleName = new TextField("Название роли:");
        projectRoleBinder.forField(txtProjectRoleName)
                .asRequired("Необходимо ввести название роли")
                .bind(ProjectRole::getRoleName, ProjectRole::setRoleName);

        TextArea txtProjectRoleDescription = new TextArea("Описание роли:");
        projectRoleBinder.forField(txtProjectRoleDescription)
                .asRequired("Необходимо ввести описание роли")
                .bind(ProjectRole::getRoleDescription, ProjectRole::setRoleDescription);

        RadioButtonGroup<ProjectRole.ProjectRoleSpecialType> radioProjectRoleSpecialType = new RadioButtonGroup<>("Специальный тип проектной роли:");
        radioProjectRoleSpecialType.setItemCaptionGenerator(new ProjectRoleSpecialTypeCaptionGenerator());
        radioProjectRoleSpecialType.setItems(ProjectRole.ProjectRoleSpecialType.values());
        radioProjectRoleSpecialType.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        radioProjectRoleSpecialType.setSelectedItem(ProjectRole.ProjectRoleSpecialType.PROJECT_ROLE_SPECIAL_TYPE_REGULAR);

        projectRoleBinder.forField(radioProjectRoleSpecialType)
                .bind(ProjectRole::getProjectRoleSpecialType, ProjectRole::setProjectRoleSpecialType);

        Button btnSaveRole= new Button("Сохранить", event -> {
            try {
                projectRoleBinder.writeBean(newProjectRole);
                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newProjectRole);
                }

                clearAllControls();

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить проектную роль. Пожалуйста, проверьте корректность заполнения полей.");
            }
        });

        btnSaveRole.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSaveRole.setIcon(VaadinIcons.CHECK);

        btnRemoveSelectedProjectRoles.addStyleName(ValoTheme.BUTTON_DANGER);
        btnRemoveSelectedProjectRoles.setIcon(VaadinIcons.DEL);
        btnRemoveSelectedProjectRoles.setEnabled(false);
        btnRemoveSelectedProjectRoles.addClickListener(event -> {
            if (removeSelectedItemsClickListener != null) {
                removeSelectedItemsClickListener.onRemoveSelectedItems(null, null);
            }
        });

        txtProjectRoleDescription.setWidth("100%");
        txtProjectRoleName.setWidth("100%");
        radioProjectRoleSpecialType.setWidth("100%");
        btnRemoveSelectedProjectRoles.setWidth("100%");
        btnSaveRole.setWidth("100%");

        setMargin(false);

        addComponent(radioProjectRoleSpecialType);

        GridLayout addProjectRoleGridLayout = new GridLayout(3, 3);

        addProjectRoleGridLayout.setSpacing(true);
        addProjectRoleGridLayout.setSizeFull();
        addProjectRoleGridLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        addProjectRoleGridLayout.setColumnExpandRatio(0,1);
        addProjectRoleGridLayout.setColumnExpandRatio(1,1);
        addProjectRoleGridLayout.setColumnExpandRatio(2,2);
        addProjectRoleGridLayout.addComponent(txtProjectRoleName, 0,0,2,0);
        addProjectRoleGridLayout.addComponent(txtProjectRoleDescription, 0,1,2,1);
        addProjectRoleGridLayout.addComponent(btnSaveRole, 0,2);
        addProjectRoleGridLayout.addComponent(btnRemoveSelectedProjectRoles, 1,2);

        addComponents(addProjectRoleGridLayout);
    }

    @Override
    public void updateControlsFromBeanState() {
        projectRoleBinder.readBean(newProjectRole);
    }

    @Override
    public void clearAllControls() {
        setNewBeanValue(new ProjectRole());
        updateControlsFromBeanState();
    }
}
