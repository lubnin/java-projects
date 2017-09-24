package ru.rti.holidays.view.admin;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.service.ProjectRoleService;

import javax.annotation.PostConstruct;

@SpringView(name = AddProjectRoleView.VIEW_NAME)
public class AddProjectRoleView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "AddProjectRole";

    @Autowired
    ProjectRole newProjectRole;

    @Autowired
    ProjectRoleService projectRoleServiceImpl;

    @PostConstruct
    void init() {
        addComponent(new Label("Страница добавления роли на проекте."));

        Binder<ProjectRole> projectRoleBinder = new Binder<ProjectRole>();

        Label lblSaveStatus = new Label();

        TextField txtProjectRoleName = new TextField("Название роли:");
        projectRoleBinder.forField(txtProjectRoleName)
                .asRequired("Необходимо ввести название роли")
                .withStatusLabel(lblSaveStatus)
                .bind(ProjectRole::getRoleName, ProjectRole::setRoleName);

        TextArea txtProjectRoleDescription = new TextArea("Описание роли:");
        projectRoleBinder.forField(txtProjectRoleDescription)
                .asRequired("Необходимо ввести описание роли")
                .withStatusLabel(lblSaveStatus)
                .bind(ProjectRole::getRoleDescription, ProjectRole::setRoleDescription);

        Button btnSaveProjectRole = new Button("Сохранить", event -> {
            try {
                projectRoleBinder.writeBean(newProjectRole);
                projectRoleServiceImpl.saveProjectRole(newProjectRole);
                Notification.show("Проектная роль успешно сохранена в базу!");
                getUI().getNavigator().navigateTo(AddProjectRoleView.VIEW_NAME);
            } catch (ValidationException e) {
                Notification.show("Невозможно сохранить проектную роль, " +
                        "пожалуйста, проверьте сообщения об ошибках для каждого из полей.");

            }
        });

        addComponents(txtProjectRoleName, txtProjectRoleDescription, btnSaveProjectRole, lblSaveStatus);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}
