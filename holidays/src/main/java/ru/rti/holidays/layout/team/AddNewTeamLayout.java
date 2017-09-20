package ru.rti.holidays.layout.team;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.layout.base.BaseVerticalLayout;

import java.util.List;

public class AddNewTeamLayout extends BaseVerticalLayout {
    Binder<Team> teamBinder = new Binder<Team>();
    Team newTeam = new Team();

    @Override
    public void constructLayout() {
        TextField txtTeamName = new TextField("Название команды:");
        teamBinder.forField(txtTeamName)
                .asRequired("Необходимо ввести название команды")
                .bind(Team::getTeamName, Team::setTeamName);

        Button btnSaveTeam = new Button("Сохранить", event -> {
            try {
                teamBinder.writeBean(newTeam);
                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newTeam);
                }

                newTeam = new Team();
                teamBinder.readBean(newTeam);

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить команду. Пожалуйста, проверьте корректность заполнения полей.");
            }
        });

        btnSaveTeam.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSaveTeam.setIcon(VaadinIcons.CHECK);

        this.addStyleName("debug_border");
        setMargin(false);

        GridLayout addTeamGridLayout = new GridLayout(2, 1);
        addTeamGridLayout.addStyleName("debug_border");
        addTeamGridLayout.setSpacing(true);
        addTeamGridLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        addTeamGridLayout.addComponent(txtTeamName, 0,0);
        addTeamGridLayout.addComponent(btnSaveTeam, 1,0);

        addComponents(addTeamGridLayout);
    }
}
