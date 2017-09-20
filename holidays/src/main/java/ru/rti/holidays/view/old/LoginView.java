package ru.rti.holidays.view.old;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import ru.rti.holidays.layout.LoginFormLayout;

public class LoginView extends BaseView {
    LoginFormLayout loginFormLayout = new LoginFormLayout();

    @Override
    public Component getViewComponent() {
        return loginFormLayout;
    }

    public LoginView(Navigator navigator) {
        super(navigator);
        loginFormLayout.addLoginButtonClickListener(event -> {
            Notification.show("Входим в систему...");
            navigator.navigateTo(BaseView.VIEW_EMPLOYEE);
        });

        loginFormLayout.addRegisterButtonClickListener(event -> {
            Notification.show("Регистрируем пользователя...");
            navigator.navigateTo(BaseView.VIEW_EMPLOYEE_LIST);
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification notification = new Notification("Добро пожаловать!", Notification.TYPE_TRAY_NOTIFICATION);
        notification.setDescription("Система \"RTI Holidays\" позволяет сотрудникам департамента внедрения и развития CRM решений ООО \"Ростелеком-Интеграция\" регистрировать и управлять датами своих отпусков.");
        notification.setDelayMsec(8000);
        notification.show(Page.getCurrent());
    }
}
