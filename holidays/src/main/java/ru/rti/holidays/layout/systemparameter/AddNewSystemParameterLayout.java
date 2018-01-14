package ru.rti.holidays.layout.systemparameter;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.entity.SystemParameter;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.common.SystemParameterInnerTypeCaptionGenerator;

public class AddNewSystemParameterLayout extends BaseVerticalLayout {
    private Binder<SystemParameter> systemParameterBinder = new Binder<>();
    private SystemParameter newSystemParameter = new SystemParameter();
    private Button btnRemoveSelectedSystemParameters = new Button("Удалить выбранные параметры");

    @Override
    public void setNewBeanValue(DBEntity newBeanValue) {
        if (newBeanValue instanceof SystemParameter) {
            newSystemParameter = (SystemParameter)newBeanValue;
        }
    }

    @Override
    public void setButtonRemoveSelectedEnabled(boolean isEnabled) {
        btnRemoveSelectedSystemParameters.setEnabled(isEnabled);
    }

    @Override
    public void constructLayout() {
        TextField txtCaption = new TextField("Название системного параметра:");
        txtCaption.setWidth("100%");

        TextField txtInnerName = new TextField("Внутреннее имя параметра:");
        txtInnerName.setWidth("100%");

        RadioButtonGroup<SystemParameter.SystemParameterType> radioInnerType = new RadioButtonGroup<>("Внутренний тип параметра:");
        radioInnerType.setItemCaptionGenerator(new SystemParameterInnerTypeCaptionGenerator());
        radioInnerType.setItems(SystemParameter.SystemParameterType.values());
        radioInnerType.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        radioInnerType.setSelectedItem(SystemParameter.SystemParameterType.SETTING_TYPE_INTEGER);

        TextField txtValue = new TextField("Значение параметра:");
        txtValue.setWidth("100%");

        systemParameterBinder.forField(txtCaption)
                .asRequired("Необходимо ввести название системного параметра")
                .withValidator(new StringLengthValidator("Длина названия системного параметра должна быть от 1 до 255",1,255))
                .bind(SystemParameter::getCaption, SystemParameter::setCaption);

        systemParameterBinder.forField(txtInnerName)
                .asRequired("Необходимо ввести внутреннее имя системного параметра")
                .withValidator(new StringLengthValidator("Длина внутреннего имени системного параметра должна быть от 1 до 255",1,255))
                .bind(SystemParameter::getInnerName, SystemParameter::setInnerName);

        systemParameterBinder.forField(txtValue)
                .withValidator(new StringLengthValidator("Длина значения системного параметра должна быть от 0 до 255",0,255))
                .bind(SystemParameter::getValue, SystemParameter::setValue);

        systemParameterBinder.forField(radioInnerType)
                .bind(SystemParameter::getInnerType, SystemParameter::setInnerType);

        Button btnSaveSystemParameter = new Button("Сохранить", event -> {
            try {
                systemParameterBinder.writeBean(newSystemParameter);
                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newSystemParameter);
                }

                clearAllControls();

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить сисемтный параметр. Пожалуйста, проверьте корректность заполнения полей.");
            }
        });

        btnRemoveSelectedSystemParameters.addStyleName(ValoTheme.BUTTON_DANGER);
        btnRemoveSelectedSystemParameters.setIcon(VaadinIcons.DEL);
        btnRemoveSelectedSystemParameters.setEnabled(false);
        btnRemoveSelectedSystemParameters.addClickListener(event -> {
            if (removeSelectedItemsClickListener != null) {
                removeSelectedItemsClickListener.onRemoveSelectedItems(null, null);
            }
        });
        btnRemoveSelectedSystemParameters.setWidth("100%");

        btnSaveSystemParameter.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSaveSystemParameter.setIcon(VaadinIcons.CHECK);

        setMargin(false);

        addComponent(radioInnerType);

        txtCaption.setWidth("100%");
        txtInnerName.setWidth("100%");
        txtValue.setWidth("100%");
        btnSaveSystemParameter.setWidth("100%");
        btnRemoveSelectedSystemParameters.setWidth("100%");

        GridLayout addSystemParameterGridLayout = new GridLayout(3, 4);
        addSystemParameterGridLayout.setSpacing(true);
        addSystemParameterGridLayout.setSizeFull();
        addSystemParameterGridLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);

        addSystemParameterGridLayout.setColumnExpandRatio(0,1);
        addSystemParameterGridLayout.setColumnExpandRatio(1,1);
        addSystemParameterGridLayout.setColumnExpandRatio(2,2);

        addSystemParameterGridLayout.addComponent(txtCaption, 0,0, 2, 0);
        addSystemParameterGridLayout.addComponent(txtInnerName, 0,1, 2, 1);
        addSystemParameterGridLayout.addComponent(txtValue, 0,2, 2, 2);
        addSystemParameterGridLayout.addComponent(btnSaveSystemParameter, 0,3);
        addSystemParameterGridLayout.addComponent(btnRemoveSelectedSystemParameters, 1, 3);

        addComponents(addSystemParameterGridLayout);
    }

    @Override
    public void updateControlsFromBeanState() {
        systemParameterBinder.readBean(newSystemParameter);
    }

    @Override
    public void clearAllControls() {
        setNewBeanValue(new SystemParameter());
        updateControlsFromBeanState();
    }
}
