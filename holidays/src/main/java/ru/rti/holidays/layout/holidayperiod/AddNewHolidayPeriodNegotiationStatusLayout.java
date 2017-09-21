package ru.rti.holidays.layout.holidayperiod;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.layout.base.BaseVerticalLayout;

public class AddNewHolidayPeriodNegotiationStatusLayout extends BaseVerticalLayout {
    Binder<HolidayPeriodNegotiationStatus> holidayPeriodNegotiationStatusBinder = new Binder<HolidayPeriodNegotiationStatus>();
    HolidayPeriodNegotiationStatus newHolidayPeriodNegotiationStatus = new HolidayPeriodNegotiationStatus();

    @Override
    public void constructLayout() {
        TextField txtStatusName = new TextField("Название статуса:");
        holidayPeriodNegotiationStatusBinder.forField(txtStatusName)
                .asRequired("Необходимо ввести название статуса")
                .bind(HolidayPeriodNegotiationStatus::getStatusName, HolidayPeriodNegotiationStatus::setStatusName);

        Button btnSaveNegotiationStatus = new Button("Сохранить", event -> {
            try {
                holidayPeriodNegotiationStatusBinder.writeBean(newHolidayPeriodNegotiationStatus);
                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newHolidayPeriodNegotiationStatus);
                }

                newHolidayPeriodNegotiationStatus = new HolidayPeriodNegotiationStatus();
                holidayPeriodNegotiationStatusBinder.readBean(newHolidayPeriodNegotiationStatus);

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить статус. Пожалуйста, проверьте корректность заполнения полей.");
            }
        });

        btnSaveNegotiationStatus.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSaveNegotiationStatus.setIcon(VaadinIcons.CHECK);

        setMargin(false);

        GridLayout addHolidayPeriodNegotiationStatusGridLayout = new GridLayout(2, 1);
        addHolidayPeriodNegotiationStatusGridLayout.setSpacing(true);
        addHolidayPeriodNegotiationStatusGridLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        addHolidayPeriodNegotiationStatusGridLayout.addComponent(txtStatusName, 0,0);
        addHolidayPeriodNegotiationStatusGridLayout.addComponent(btnSaveNegotiationStatus, 1,0);

        addComponents(addHolidayPeriodNegotiationStatusGridLayout);
    }
}
