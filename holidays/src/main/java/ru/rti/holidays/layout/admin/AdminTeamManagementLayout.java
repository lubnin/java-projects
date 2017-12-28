package ru.rti.holidays.layout.admin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.component.vaadin.label.BoldLabel;
import ru.rti.holidays.component.vaadin.label.PageTitle;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.team.AddNewTeamLayout;

import java.util.List;
import java.util.Set;

@SpringComponent
public class AdminTeamManagementLayout extends BaseVerticalLayout {
    private static final Logger log = LoggerFactory.getLogger(AdminTeamManagementLayout.class);
    private Grid<Team> grdTeams = new Grid<>();
    private List<Team> teams;

    @Override
    public void constructLayout() {
        try {
            addComponent(new PageTitle("Управление командами"));
            addComponent(new BoldLabel("Все команды"));

            MultiSelectionModel<Team> selectionModel =
                    (MultiSelectionModel<Team>)grdTeams.setSelectionMode(Grid.SelectionMode.MULTI);

            //selectionModel.addMultiSelectionListener(event -> {
            //    Set<Team> selectedItems = event.getAllSelectedItems();
                //btnRemoveHolidayPeriods.setEnabled(selectedItems != null && selectedItems.size() > 0);
            //});

            grdTeams.addColumn(Team::getTeamName).setCaption("Команда");
            grdTeams.setHeightByRows(5);
            grdTeams.setWidth("100%");


            addComponent(grdTeams);
            addComponent(new BoldLabel("Добавить новую команду"));

            //AddNewEntityLayout<Team> addTeamLayout = new AddNewEntityLayout<>(Team.class);
            //addTeamLayout.getTextControlBindings().add(
            //        new AddNewEntityLayout<Team>.NewEntityTextControlBinding<String, ValueProvider, Setter>("text", Team::getTeamName, Team::setTeamName));
            //AddNewEntityLayout.AddNewEntityLayoutBuilder layoutBuilder  = addTeamLayout.getLayoutBuilder();
            //layoutBuilder.appendTextField("text1", Team::getTeamName, Team::setTeamName );
            //addTeamLayout.addTextFieldAndBind("Команда").bind(Team::getTeamName, Team::setTeamName);

            //addTeamLayout.setParentLayout(this);
            //addTeamLayout.setWidth("100%");
            //addTeamLayout.setSaveButtonClickListener(saveButtonClickListener);

            AddNewTeamLayout addTeamLayout = new AddNewTeamLayout();
            addTeamLayout.setWidth("100%");
            addTeamLayout.setParentLayout(this);
            addTeamLayout.setExceptionHandler(getExceptionHandler());
            addTeamLayout.setSaveButtonClickListener(saveButtonClickListener);
            addTeamLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                if (removeSelectedItemsClickListener != null) {
                    removeSelectedItemsClickListener.onRemoveSelectedItems(this, grdTeams.getSelectedItems());
                    refreshDataGrid();
                }
            });
            addTeamLayout.constructLayout();

            selectionModel.addMultiSelectionListener(event -> {
                Set<Team> selectedItems = event.getAllSelectedItems();
                addTeamLayout.setButtonRemoveSelectedEnabled(selectedItems != null && selectedItems.size() > 0);
                if (selectedItems.size() == 1) {
                    Team selectedTeam = event.getFirstSelectedItem().get();
                    addTeamLayout.setNewBeanValue(selectedTeam);
                    addTeamLayout.updateControlsFromBeanState();
                } else {
                    addTeamLayout.clearAllControls();
                }
            });

            addComponent(addTeamLayout);
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
        grdTeams.setItems(teams);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
