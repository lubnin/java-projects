package ru.rti.holidays.servlet;

import com.vaadin.server.*;

import javax.servlet.ServletException;

/*
@Component("vaadinServlet")
@WebServlet(value = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = MainUI.class)
*/
public class CustomVaadinServlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener{
    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();

        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);

        getService().setSystemMessagesProvider(systemMessagesInfo -> {
            CustomizedSystemMessages messages = new CustomizedSystemMessages();
            messages.setCommunicationErrorCaption("Ошибка связи");
            messages.setCommunicationErrorMessage("Это плохо.");
            messages.setCommunicationErrorNotificationEnabled(true);
            //TODO: remove hardcode here!
            messages.setCommunicationErrorURL("http://localhost:8080");
            messages.setSessionExpiredCaption("Сессия истекла");
            messages.setSessionExpiredMessage("Пожалуйста, запишите все несохранённые на этом экране данные и нажимте сюда или нажмите ESC, чтобы продолжить.");
            messages.setSessionExpiredNotificationEnabled(true);

            return messages;
        });
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {

    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {

    }
}
