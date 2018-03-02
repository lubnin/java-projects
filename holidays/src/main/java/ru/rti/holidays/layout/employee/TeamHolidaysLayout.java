package ru.rti.holidays.layout.employee;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.renderers.ButtonRenderer;
import ru.rti.holidays.aggregators.EmployeeHolidayPeriod;
import ru.rti.holidays.component.grid.filter.EmployeeHolidayPeriodFilteredGrid;
import ru.rti.holidays.component.vaadin.RtiGUIFactory;
import ru.rti.holidays.component.vaadin.button.*;
import ru.rti.holidays.component.vaadin.label.BoldLabel;
import ru.rti.holidays.component.grid.comparator.EmployeeHolidayPeriodDateColumnComparator;
import ru.rti.holidays.entity.HolidayPeriod;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.exception.LayoutConstructionException;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.base.behaviour.ButtonClickListener;
import ru.rti.holidays.layout.base.behaviour.ButtonClickResult;
import ru.rti.holidays.style.GridEmployeeHolidayPeriodCellStyleGenerator;
import ru.rti.holidays.utility.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class TeamHolidaysLayout extends BaseVerticalLayout {
    //private EmployeeHolidayPeriodFilteredGrid<EmployeeHolidayPeriod> filteredGrid = new EmployeeHolidayPeriodFilteredGrid<>();
    private Grid<EmployeeHolidayPeriod> grdTeamMembersHolidayPeriods = new Grid<>();
    private Collection<EmployeeHolidayPeriod> teamMembersHolidayPeriods;
    private BaseRtiButton btnNegotiateSelPeriods; //FriendlyButton
    private BaseRtiButton btnRejectSelPeriods; //DangerButton
    private Button btnShowCrossings;
    private Team team;
    private ButtonClickListener<EmployeeHolidayPeriod> negotiateSelectedPeriodsButtonClickListener;
    private ButtonClickListener<EmployeeHolidayPeriod> rejectSelectedPeriodsButtonClickListener;

    public TeamHolidaysLayout() {
        super();
    }
    public TeamHolidaysLayout(boolean isApplyMargin, boolean isApplySpacing) {
        super(isApplyMargin, isApplySpacing);
    }

    @Override
    public void constructLayout() throws LayoutConstructionException {
        if (team == null) {
            throw new LayoutConstructionException("Невозможно построить TeamHolidaysLayout. Экземпляр `team` не может быть `null`.");
        }

        //grdTeamMembersHolidayPeriods = filteredGrid.getGridControl();

        Label lblTeamName = new BoldLabel(team.getTeamName());

        btnNegotiateSelPeriods = RtiGUIFactory.createButton(FriendlyButton.class)
                .caption("Согласовать выбранные")
                .id("btnNegotiateSelPeriods_" + team.getId())
                .icon(VaadinIcons.CHECK);

        btnNegotiateSelPeriods.addClickListener(clickEvent -> {
            Set<EmployeeHolidayPeriod> setEmplHolPeriods = grdTeamMembersHolidayPeriods.getSelectedItems();

            if (negotiateSelectedPeriodsButtonClickListener != null) {
                ButtonClickResult<EmployeeHolidayPeriod> result = negotiateSelectedPeriodsButtonClickListener.onClick(this, setEmplHolPeriods, team);
                if (result != null && result.isResult()) {
                    UIHelper.showNotification("Операция по согласованию выбранных периодов отпуска успешно завершена.");
                    Collection<EmployeeHolidayPeriod> resultList = result.getResultItems();
                    grdTeamMembersHolidayPeriods.setItems(resultList);
                }
            }
        });


        btnRejectSelPeriods = RtiGUIFactory.createButton(DangerButton.class)
                .caption("Отклонить выбранные")
                .id("btnRejectSelPeriods_" + team.getId())
                .icon(VaadinIcons.STOP);


        btnRejectSelPeriods.addClickListener(clickEvent -> {
            Set<EmployeeHolidayPeriod> setEmplHolPeriods = grdTeamMembersHolidayPeriods.getSelectedItems();
            if (rejectSelectedPeriodsButtonClickListener != null) {
                ButtonClickResult<EmployeeHolidayPeriod> result = rejectSelectedPeriodsButtonClickListener.onClick(this, setEmplHolPeriods, team);
                if (result != null && result.isResult()) {
                    UIHelper.showNotification("Операция по отклонению выбранных периодов отпуска успешно завершена.");
                    Collection<EmployeeHolidayPeriod> resultList = result.getResultItems();
                    grdTeamMembersHolidayPeriods.setItems(resultList);
                }
            }
        });

//        btnShowCrossings = RtiGUIFactory.createButton(RtiOrangeButton.class,
//                "Показать пересечения",
//                true,
//                "btnShowCrossingsPeriods_" + team.getId(),
//                "300px");

        btnShowCrossings = RtiGUIFactory.createButton(RtiOrangeButton.class)
                .caption("Показать пересечения")
                .id("btnShowCrossingsPeriods_" + team.getId())
                .icon(VaadinIcons.ARROWS_CROSS)
                .enabled(true);

        VerticalLayout popupLayout = new VerticalLayout();

        PopupView popup = new PopupView(null, popupLayout);

        btnShowCrossings.addClickListener(clickEvent -> {
            try {
                Collection<EmployeeHolidayPeriod> allHolidays = teamMembersHolidayPeriods;
                Collection<EmployeeHolidayPeriod> allHolidaysCheckCrossings = new ArrayList<>();
                allHolidaysCheckCrossings.addAll(allHolidays);

                StringBuilder sbTotal = new StringBuilder();
                sbTotal.append("<div class='rti_small_text'><b><u>Пересечения отпусков по этой команде:</u></b>");
                for (EmployeeHolidayPeriod ehp : allHolidays) {
                    HolidayPeriod holPeriod = ehp.getHolidayPeriod();
                    if (HolidayPeriodUtils.isHolidayPeriodInRejectedStatus(holPeriod)) {
                        continue;
                    }

                    String empName = ehp.getEmployeeFullName();
                    Date dtStart = DateUtils.asDate(ehp.getDateStart());
                    Long numDays = ehp.getNumDays();
                    Date dtEnd = DateUtils.addDays(dtStart, numDays);

                    boolean isIntersect = false;

                    int c = 0;

                    for (EmployeeHolidayPeriod ehpCross : allHolidaysCheckCrossings) {
                        if (ehpCross.equals(ehp)) {
                            continue;
                        }
                        HolidayPeriod holPeriodCross = ehpCross.getHolidayPeriod();
                        if (HolidayPeriodUtils.isHolidayPeriodInRejectedStatus(holPeriodCross)) {
                            continue;
                        }

                        Date dtStartCross = DateUtils.asDate(ehpCross.getDateStart());
                        Long numDaysCross = ehpCross.getNumDays();
                        Date dtEndCross = DateUtils.addDays(dtStartCross, numDaysCross);

                        if (DateUtils.isIntersectionBetweenDates(dtStart, dtEnd, dtStartCross, dtEndCross)) {
                            int daysInIntersectionTotal = DateUtils.getIntersectionDaysNumber(dtStart, dtEnd, dtStartCross, dtEndCross);
                            if (ehp.getEmployeeRoleName() != null && ehpCross.getEmployeeRoleName() != null) {
                                if (!ehp.getEmployeeRoleName().equals(ehpCross.getEmployeeRoleName())) {
                                    continue;
                                }
                            }
                            if (c >= 0) {
                                sbTotal.append("<br/>");
                            }
                            sbTotal.append("<b>" + empName + "</b>, отпуск с " + ehp.getDateStartAsString() + " на " + ehp.getNumDaysAsString() + " дн.: ");
                            sbTotal.append("<span class='neg_status_new'>есть пересечение ")
                                    .append(" длиной в ")
                                    .append(daysInIntersectionTotal + " дн. ")
                                    .append(" с сотрудником ")
                                    .append(ehpCross.getEmployeeFullName())
                                    .append(", отпуск которого с ")
                                    .append(ehpCross.getDateStartAsString())
                                    .append(" на ")
                                    .append(ehpCross.getNumDays())
                                    .append(" дн.</span>");
                            isIntersect = true;
                            c++;
                        }
                    }
                    //if (!isIntersect) {
                    //    sbTotal.append("<span class='neg_status_ok'>нет пересечений по проектной роли \"" + CommonUtils.getValueOrEmptyString(ehp.getEmployeeRoleName()) + "\"</span>");

                    //}
                    //sbTotal.append("<br/>");
                }

                sbTotal.append("</div>");

                popupLayout.removeAllComponents();
                //VerticalLayout popupLayout = new VerticalLayout();
                popupLayout.addComponent(new Label(sbTotal.toString(), ContentMode.HTML));
                //PopupView popup = new PopupView(null, popupLayout);
                //this.addComponent(popup);
                popup.setPopupVisible(true);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        MultiSelectionModel<EmployeeHolidayPeriod> selectionModel =
                (MultiSelectionModel<EmployeeHolidayPeriod>)grdTeamMembersHolidayPeriods.setSelectionMode(Grid.SelectionMode.MULTI);

        grdTeamMembersHolidayPeriods.setItems(teamMembersHolidayPeriods);
        grdTeamMembersHolidayPeriods.setHeightByRows(15);
        grdTeamMembersHolidayPeriods.setWidth("100%");
        grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getEmployeeFullName).setCaption("ФИО сотрудника");
        grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getEmployeeRoleName).setCaption("Роль на проекте");
        grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getDateStartAsString).setCaption("Дата начала отпуска").setComparator(new EmployeeHolidayPeriodDateColumnComparator());
        grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getNumDays).setCaption("Количество дней отпуска");
        grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getReadableNegotiationMask).setCaption("Согласовавшие");
        grdTeamMembersHolidayPeriods.addColumn(EmployeeHolidayPeriod::getHolidayPeriodNegotiationStatus).setCaption("Статус согласования").setStyleGenerator(new GridEmployeeHolidayPeriodCellStyleGenerator());




        grdTeamMembersHolidayPeriods.addColumn(employeeHolidayPeriod -> "История согласования", new ButtonRenderer<>(rendererClickEvent -> {
            popupLayout.removeAllComponents();
            EmployeeHolidayPeriod ehp = rendererClickEvent.getItem();
            popupLayout.addComponent(new Label("<b>"+ehp.getEmployeeFullName()+"</b><br/>Отпуск с " + ehp.getDateStartAsString() + " на " + ehp.getNumDays() + " дн.<br/>" + ehp.getHolidayPeriodHistoryComment(), ContentMode.HTML));
            popup.setPopupVisible(true);
        })).setCaption("Детали");

        grdTeamMembersHolidayPeriods.setDescriptionGenerator(employeeHolidayPeriod -> {
            String description = GlobalConstants.EMPTY_STRING;
            try {
                HolidayPeriod hp = employeeHolidayPeriod.getHolidayPeriod();
                return "ID: " + hp.getId() + "\r\nДата начала: " + hp.getDateStartAsString() + "\r\n"+ hp.getHolidayPeriodNegotiationHistoryComment(false);
            } catch (Exception e) {
                return description;
            }
        });

        selectionModel.addMultiSelectionListener(event -> {
            Set<EmployeeHolidayPeriod> selectedItems = event.getAllSelectedItems();

            btnNegotiateSelPeriods.setEnabled(false);
            btnRejectSelPeriods.setEnabled(false);

            if (selectedItems != null && selectedItems.size() > 0) {
                boolean hasSelectedPeriodsForRejection = false;
                boolean hasSelectedPeriodsForNegotiation = false;
                boolean isAtLeastOneInRejectedStatus = false;
                boolean isAtLeastOneInOkStatus = false;

                for (EmployeeHolidayPeriod empHolPeriod : selectedItems) {
                    if (empHolPeriod.getNegotiationStatus() != null && empHolPeriod.getNegotiationStatus().getNegotiationStatusType() != null) {

                        HolidayPeriod curHP = empHolPeriod.getHolidayPeriod();
                        if (HolidayPeriodUtils.isHolidayPeriodInPartlyNegotiatedStatus(curHP) ||
                                HolidayPeriodUtils.isHolidayPeriodInOkStatus(curHP) ||
                                HolidayPeriodUtils.isHolidayPeriodInNegotiatingStatus(curHP)) {
                            hasSelectedPeriodsForRejection = true;
                        }

                        if (HolidayPeriodUtils.isHolidayPeriodInPartlyNegotiatedStatus(curHP) ||
                                HolidayPeriodUtils.isHolidayPeriodInRejectedStatus(curHP) ||
                                HolidayPeriodUtils.isHolidayPeriodInNegotiatingStatus(curHP)) {
                            hasSelectedPeriodsForNegotiation = true;
                        }

                        if (HolidayPeriodUtils.isHolidayPeriodInRejectedStatus(curHP)) {
                            isAtLeastOneInRejectedStatus = true;
                        }

                        if (HolidayPeriodUtils.isHolidayPeriodInOkStatus(curHP)) {
                            isAtLeastOneInOkStatus = true;
                        }
                    }
                }

                if (hasSelectedPeriodsForNegotiation || hasSelectedPeriodsForRejection) {
                    if (isAtLeastOneInOkStatus) {
                        hasSelectedPeriodsForNegotiation = false;
                    }
                    if (isAtLeastOneInRejectedStatus) {
                        hasSelectedPeriodsForRejection = false;
                    }
                    if (hasSelectedPeriodsForNegotiation) {
                        btnNegotiateSelPeriods.setEnabled(true);
                    }
                    if (hasSelectedPeriodsForRejection) {
                        btnRejectSelPeriods.setEnabled(true);
                    }
                }
            }
        });

        addComponent(lblTeamName);
        addComponent(grdTeamMembersHolidayPeriods);
        //addComponent(filteredGrid);

        GridLayout buttonsPanelLayout = new GridLayout(4,1);

        buttonsPanelLayout.setSpacing(true);

        buttonsPanelLayout.setColumnExpandRatio(0, 1);
        buttonsPanelLayout.setColumnExpandRatio(1, 1);
        buttonsPanelLayout.setColumnExpandRatio(2, 1);
        buttonsPanelLayout.setColumnExpandRatio(3, 2);

        buttonsPanelLayout.addComponent(btnNegotiateSelPeriods,0,0);
        buttonsPanelLayout.addComponent(btnRejectSelPeriods,1,0);
        buttonsPanelLayout.addComponent(btnShowCrossings,2,0);
        buttonsPanelLayout.addComponent(popup);
        addComponent(buttonsPanelLayout);
    }

    @Override
    public void postConstructLayout() {

    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setTeamMembersHolidayPeriods(Collection<EmployeeHolidayPeriod> teamMembersHolidayPeriods) {
        this.teamMembersHolidayPeriods = teamMembersHolidayPeriods;
    }

    public ButtonClickListener getNegotiateSelectedPeriodsButtonClickListener() {
        return negotiateSelectedPeriodsButtonClickListener;
    }

    public void setNegotiateSelectedPeriodsButtonClickListener(ButtonClickListener negotiateSelectedPeriodsButtonClickListener) {
        this.negotiateSelectedPeriodsButtonClickListener = negotiateSelectedPeriodsButtonClickListener;
    }

    public ButtonClickListener getRejectSelectedPeriodsButtonClickListener() {
        return rejectSelectedPeriodsButtonClickListener;
    }

    public void setRejectSelectedPeriodsButtonClickListener(ButtonClickListener rejectSelectedPeriodsButtonClickListener) {
        this.rejectSelectedPeriodsButtonClickListener = rejectSelectedPeriodsButtonClickListener;
    }
}
