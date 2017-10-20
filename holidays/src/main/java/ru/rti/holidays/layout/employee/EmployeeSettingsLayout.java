package ru.rti.holidays.layout.employee;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.exception.InvalidFieldValueException;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.utility.CommonUtils;
import ru.rti.holidays.utility.UIHelper;

public class EmployeeSettingsLayout extends BaseVerticalLayout {
    private Binder<Employee> employeeBinder = new Binder<Employee>();
    private Employee employee;

    @Override
    public void constructLayout() {
        try {
            Label lblChangePassword = new Label("Смена пароля:");

            PasswordField txtNewPassword = new PasswordField("Новый пароль:");
            employeeBinder.forField(txtNewPassword)
                    .asRequired("Необходимо ввести значение для нового пароля")
                    .bind(Employee::getEmptyPassword, Employee::setPassword);

            PasswordField txtPasswordRepeat = new PasswordField("Повтор пароля:");
            //txtPasswordRepeat.setRequiredIndicatorVisible(true);
            employeeBinder.forField(txtPasswordRepeat)
                    .asRequired("Необходимо ввести пароль повторно")
                    .bind(null, null);

            Button btnSaveSettings = new Button("Сохранить настройки", event -> {
                if (CommonUtils.checkIfAnyIsNull(txtNewPassword, txtPasswordRepeat)) {
                    UIHelper.showError("Поля ввода пароля не должны быть пустыми");
                } else if (CommonUtils.checkIfNotEqual(txtNewPassword.getValue(), txtPasswordRepeat.getValue())) {
                    UIHelper.showError("Пароли должны совпадать");
                } else {
                    if (getSaveButtonClickListener() != null) {
                        getSaveButtonClickListener().onSaveData(this, getEmployee());
                    }
                }
            });


            btnSaveSettings.addStyleName(ValoTheme.BUTTON_FRIENDLY);
            btnSaveSettings.setIcon(VaadinIcons.CHECK);

            addComponent(lblChangePassword);
            addComponent(txtNewPassword);
            addComponent(txtPasswordRepeat);
            addComponent(btnSaveSettings);
        } catch (Exception e) {
            handleException(e, e.getMessage());
        }
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
