package ru.rti.holidays.layout.holidayperiod;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.projectrole.AddNewProjectRoleLayout;

public class AddNewHolidayPeriodNegotiationStatusLayout extends BaseVerticalLayout {
    private Binder<HolidayPeriodNegotiationStatus> holidayPeriodNegotiationStatusBinder = new Binder<HolidayPeriodNegotiationStatus>();
    private HolidayPeriodNegotiationStatus newHolidayPeriodNegotiationStatus = new HolidayPeriodNegotiationStatus();
    private Button btnRemoveSelectedStatuses = new Button("Удалить выбранные статусы");
    public void setButtonRemoveSelectedEnabled(boolean isEnabled) { btnRemoveSelectedStatuses.setEnabled(isEnabled);}

    @Override
    public void setNewBeanValue(DBEntity newBeanValue) {
        if (newBeanValue instanceof HolidayPeriodNegotiationStatus)
            this.newHolidayPeriodNegotiationStatus = (HolidayPeriodNegotiationStatus)newBeanValue;
    }

    public Binder<HolidayPeriodNegotiationStatus> getBinder() {
        return holidayPeriodNegotiationStatusBinder;
    }

    private class NegotiationStatusTypeCaptionGenerator implements ItemCaptionGenerator<HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType> {
        @Override
        public String apply(HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType negotiationStatusType) {
            switch (negotiationStatusType) {
                case NEGOTIATION_STATUS_TYPE_NEGOTIATING:
                    return "На согласовании";
                case NEGOTIATION_STATUS_TYPE_OK:
                    return "Согласован";
                case NEGOTIATION_STATUS_TYPE_REJECTED:
                    return "Отклонён";
                default:
                    return "Неизвестный статус";
            }
        }
    }

    @Override
    public void constructLayout() {
        TextField txtStatusName = new TextField("Название статуса:");
        holidayPeriodNegotiationStatusBinder.forField(txtStatusName)
                .asRequired("Необходимо ввести название статуса")
                .bind(HolidayPeriodNegotiationStatus::getStatusName, HolidayPeriodNegotiationStatus::setStatusName);

        TextArea txtStatusDescription = new TextArea("Описание статуса:");
        holidayPeriodNegotiationStatusBinder.forField(txtStatusDescription)
                .bind(HolidayPeriodNegotiationStatus::getStatusDescription, HolidayPeriodNegotiationStatus::setStatusDescription);

        Button btnSaveNegotiationStatus = new Button("Сохранить", event -> {
            try {
                holidayPeriodNegotiationStatusBinder.writeBean(newHolidayPeriodNegotiationStatus);
                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newHolidayPeriodNegotiationStatus);
                }

                clearAllControls();

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить статус. Пожалуйста, проверьте корректность заполнения полей.");
            }
        });

        RadioButtonGroup<HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType> radioNegotiationStatusType = new RadioButtonGroup<>("Тип статуса согласования:");
        radioNegotiationStatusType.setItemCaptionGenerator(new AddNewHolidayPeriodNegotiationStatusLayout.NegotiationStatusTypeCaptionGenerator());
        radioNegotiationStatusType.setItems(HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.values());
        radioNegotiationStatusType.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        radioNegotiationStatusType.setSelectedItem(HolidayPeriodNegotiationStatus.HolidayPeriodNegotiationStatusType.NEGOTIATION_STATUS_TYPE_NEGOTIATING);
        radioNegotiationStatusType.setWidth("100%");
        holidayPeriodNegotiationStatusBinder.forField(radioNegotiationStatusType)
                .bind(HolidayPeriodNegotiationStatus::getNegotiationStatusType, HolidayPeriodNegotiationStatus::setNegotiationStatusType);


        btnRemoveSelectedStatuses.addStyleName(ValoTheme.BUTTON_DANGER);
        btnRemoveSelectedStatuses.setIcon(VaadinIcons.DEL);
        btnRemoveSelectedStatuses.setEnabled(false);
        btnRemoveSelectedStatuses.addClickListener(event -> {
            if (removeSelectedItemsClickListener != null) {
                removeSelectedItemsClickListener.onRemoveSelectedItems(null, null);
            }
        });
        btnRemoveSelectedStatuses.setWidth("100%");

        btnSaveNegotiationStatus.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSaveNegotiationStatus.setIcon(VaadinIcons.CHECK);
        btnSaveNegotiationStatus.setWidth("100%");

        setMargin(false);

        addComponent(radioNegotiationStatusType);

        txtStatusDescription.setWidth("100%");
        txtStatusName.setWidth("100%");

        GridLayout addHolidayPeriodNegotiationStatusGridLayout = new GridLayout(3, 3);

        addHolidayPeriodNegotiationStatusGridLayout.setSizeFull();
        //addHolidayPeriodNegotiationStatusGridLayout.addStyleName("debug_border");

        addHolidayPeriodNegotiationStatusGridLayout.setSpacing(true);
        addHolidayPeriodNegotiationStatusGridLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);

        addHolidayPeriodNegotiationStatusGridLayout.setColumnExpandRatio(0,1);
        addHolidayPeriodNegotiationStatusGridLayout.setColumnExpandRatio(1,1);
        addHolidayPeriodNegotiationStatusGridLayout.setColumnExpandRatio(2,2);

        addHolidayPeriodNegotiationStatusGridLayout.addComponent(txtStatusName, 0,0,2,0);
        addHolidayPeriodNegotiationStatusGridLayout.addComponent(txtStatusDescription, 0,1,2,1);
        addHolidayPeriodNegotiationStatusGridLayout.addComponent(btnSaveNegotiationStatus, 0,2);
        addHolidayPeriodNegotiationStatusGridLayout.addComponent(btnRemoveSelectedStatuses, 1,2);

        addComponents(addHolidayPeriodNegotiationStatusGridLayout);
    }

    @Override
    public void updateControlsFromBeanState() {
        holidayPeriodNegotiationStatusBinder.readBean(newHolidayPeriodNegotiationStatus);
    }

    @Override
    public void clearAllControls() {
        setNewBeanValue(new HolidayPeriodNegotiationStatus());
        updateControlsFromBeanState();
    }
}
