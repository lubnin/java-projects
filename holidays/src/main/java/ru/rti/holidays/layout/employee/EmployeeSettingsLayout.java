package ru.rti.holidays.layout.employee;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.utility.CommonUtils;
import ru.rti.holidays.utility.UIHelper;

public class EmployeeSettingsLayout extends BaseVerticalLayout {
    private Binder<Employee> employeeBinder = new Binder<Employee>();
    private Employee employee;

    @Override
    public void constructLayout() {
        Label lblChangePassword = new Label(getLocalizationService().getMessageEmployeeSettingsLayoutPageTitle());

        PasswordField txtNewPassword = new PasswordField(getLocalizationService().getMessageEmployeeSettingsLayoutPasswordFieldNew());
        employeeBinder.forField(txtNewPassword)
                .asRequired(getLocalizationService().getMessageEmployeeSettingsLayoutPasswordFieldNewRequired())
                .bind(Employee::getEmptyPassword, Employee::setPassword);

        PasswordField txtPasswordRepeat = new PasswordField(getLocalizationService().getMessageEmployeeSettingsLayoutPasswordFieldRepeat());
        txtPasswordRepeat.setRequiredIndicatorVisible(true);

        Button btnSaveSettings = new Button(getLocalizationService().getMessageControlsCommonButtonSaveSettings(), event -> {
            if (CommonUtils.checkIfAnyIsEmpty(txtNewPassword.getValue(), txtPasswordRepeat.getValue())) {
                UIHelper.showError(getLocalizationService().getMessageCommonValidationErrorPasswordFieldsAreRequired());
            } else if (CommonUtils.checkIfNotEqual(txtNewPassword.getValue(), txtPasswordRepeat.getValue())) {
                UIHelper.showError(getLocalizationService().getMessageCommonValidationErrorPasswordPasswordsMustBeEqual());
            } else {

                try {
                    employeeBinder.writeBean(employee);
                    if (getSaveButtonClickListener() != null) {
                        getSaveButtonClickListener().onSaveData(this, getEmployee());
                    }
                } catch (ValidationException e) {
                    UIHelper.showError(getLocalizationService().getMessageCommonValidationErrorPasswordFieldsAreRequired());
                }
            }
        });

        btnSaveSettings.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSaveSettings.setIcon(VaadinIcons.CHECK);

        EmployeeMenuBarLayout employeeMenuBarLayout = new EmployeeMenuBarLayout(false, false);
        employeeMenuBarLayout.setSettingsMenutItemVisible(false);
        employeeMenuBarLayout.constructLayout();

        addComponent(employeeMenuBarLayout);
        addComponent(lblChangePassword);
        addComponent(txtNewPassword);
        addComponent(txtPasswordRepeat);
        addComponent(btnSaveSettings);
    }

    @Override
    public void postConstructLayout() {

    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
