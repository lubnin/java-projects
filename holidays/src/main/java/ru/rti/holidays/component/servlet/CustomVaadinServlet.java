package ru.rti.holidays.component.servlet;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.spring.server.SpringVaadinServlet;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("vaadinServlet")
public class CustomVaadinServlet extends SpringVaadinServlet {
    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().setSystemMessagesProvider(systemMessagesInfo -> {
            CustomizedSystemMessages messages = new CustomizedSystemMessages();
            messages.setAuthenticationErrorCaption("Ошибка аутентификации");
            messages.setAuthenticationErrorMessage("Пожалуйста, запишите все несохранённые на этом экране данные и <u>нажимте сюда</u> или нажмите ESC, чтобы продолжить.");
            messages.setAuthenticationErrorNotificationEnabled(true);

            messages.setCommunicationErrorCaption("Ошибка соединения");
            messages.setCommunicationErrorMessage("Пожалуйста, запишите все несохранённые на этом экране данные и <u>нажимте сюда</u> или нажмите ESC, чтобы продолжить.");
            messages.setCommunicationErrorNotificationEnabled(true);

            messages.setInternalErrorCaption("Внутренняя ошибка");
            messages.setInternalErrorMessage("Произошла внутренняя ошибка на сервере.<br/>Пожалуйста, уведомите администратора системы, запишите все несохранённые на этом экране данные и <u>нажимте сюда</u> или нажмите ESC, чтобы продолжить.");
            messages.setInternalErrorNotificationEnabled(true);

            messages.setCookiesDisabledCaption("Cookies Вашего браузера выключены или недоступны");
            messages.setCookiesDisabledMessage("Это приложение требует для корректного функционирования включенных cookies в настройках Вашего браузера.<br/>Пожалуйста, включите поддержку cookies, после чего <u>нажмите сюда</u> или нажмите ESC, чтобы продолжить.");
            messages.setCookiesDisabledNotificationEnabled(true);

            messages.setSessionExpiredCaption("Сессия истекла");
            messages.setSessionExpiredMessage("Пожалуйста, запишите все несохранённые на этом экране данные и <u>нажимте сюда</u> или нажмите ESC, чтобы продолжить.");
            messages.setSessionExpiredNotificationEnabled(true);

            return messages;
        });
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
    }
}
