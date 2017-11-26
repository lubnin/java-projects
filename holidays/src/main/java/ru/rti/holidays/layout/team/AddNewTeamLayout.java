package ru.rti.holidays.layout.team;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.layout.base.BaseVerticalLayout;

public class AddNewTeamLayout extends BaseVerticalLayout {
    private Binder<Team> teamBinder = new Binder<Team>();
    private Team newTeam = new Team();
    private Button btnRemoveSelectedTeams = new Button("Удалить выбранные команды");

    @Override
    public void setButtonRemoveSelectedEnabled(boolean isEnabled) {
        btnRemoveSelectedTeams.setEnabled(isEnabled);
    }

    @Override
    public void setNewBeanValue(DBEntity newBeanValue) {
        if (newBeanValue instanceof Team) {
            newTeam = (Team)newBeanValue;
        }
    }

    @Override
    public void constructLayout() {
        TextField txtTeamName = new TextField("Название команды:");
        txtTeamName.setWidth("100%");

        teamBinder.forField(txtTeamName)
                .asRequired("Необходимо ввести название команды")
                .bind(Team::getTeamName, Team::setTeamName);

        Button btnSaveTeam = new Button("Сохранить", event -> {
            try {
                teamBinder.writeBean(newTeam);
                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newTeam);
                }

                clearAllControls();

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить команду. Пожалуйста, проверьте корректность заполнения полей.");
            }
        });


        btnRemoveSelectedTeams.addStyleName(ValoTheme.BUTTON_DANGER);
        btnRemoveSelectedTeams.setIcon(VaadinIcons.DEL);
        btnRemoveSelectedTeams.setEnabled(false);
        btnRemoveSelectedTeams.addClickListener(event -> {
            if (removeSelectedItemsClickListener != null) {
                removeSelectedItemsClickListener.onRemoveSelectedItems(null, null);
            }
        });
        btnRemoveSelectedTeams.setWidth("100%");

        btnSaveTeam.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSaveTeam.setIcon(VaadinIcons.CHECK);

        //this.addStyleName("debug_border");
        setMargin(false);

        GridLayout addTeamGridLayout = new GridLayout(2, 2);
        //addTeamGridLayout.addStyleName("debug_border");
        addTeamGridLayout.setSpacing(true);
        addTeamGridLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        addTeamGridLayout.addComponent(txtTeamName, 0,0, 1, 0);
        addTeamGridLayout.addComponent(btnSaveTeam, 0,1);
        addTeamGridLayout.addComponent(btnRemoveSelectedTeams, 1, 1);

        addComponents(addTeamGridLayout);
    }

    @Override
    public void updateControlsFromBeanState() {
        teamBinder.readBean(newTeam);
    }

    @Override
    public void clearAllControls() {
        setNewBeanValue(new Team());
        updateControlsFromBeanState();
    }
}
