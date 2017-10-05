package ru.rti.holidays.view.base;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.beans.session.User;
import ru.rti.holidays.exception.NotAuthorizedAccessException;
import ru.rti.holidays.exception.handler.DoNothingExceptionHandler;
import ru.rti.holidays.exception.handler.ExceptionHandler;
import ru.rti.holidays.exception.handler.StandardViewExceptionHandler;
import ru.rti.holidays.layout.base.BaseVerticalLayout;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * This is the base abstract class for every view of the Application
 */
abstract public class AbstractBaseView extends VerticalLayout implements View {
    protected Map<String, String> parameterMap;
    protected String parameters;
    private static final Logger log = LoggerFactory.getLogger(AbstractBaseView.class);
    protected ExceptionHandler exceptionHandler;

    @Autowired
    protected User currentUser;

    public AbstractBaseView() {
        exceptionHandler = new StandardViewExceptionHandler();
    }
    /**
     * Gets the logger instance for this view
     * @return
     */
    public Logger getLogger() {
        return log;
    }

    /**
     * Sets the ExceptionHandler for this view. It will handle all the exceptions which are thrown in the view instance.
     * You can write your own implementation of ExceptionHandler interface or use one of the predefined Exception handlers:
     * @see StandardViewExceptionHandler
     * @see DoNothingExceptionHandler
     *
     * @param exceptionHandler
     */
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Gets the ExceptionHandler for this view. This Exception handles all the exceptions which are thrown in the view
     * instance.
     * @return
     */
    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    /**
     * Handles the exception, which occured during layout construction
     * @param e
     * @param errorMessage
     */
    public void handleException(Exception e, String errorMessage) {
        if (exceptionHandler != null) {
            exceptionHandler.handle(e, errorMessage);
        }
    }


    /**
     * Gets the currently logged in user
     * @return
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * This method must be overridden in child subclasses of AbstractBaseView.
     * Returns the Label control that will be used in base view to draw a page title for a particular view;
     * In your custom view you return the instance of Label component, customized the way you want it to look
     * @return an instance of Label component to be used as a page title for this View
     */
    protected abstract Label getPageTitleLabel();

    /**
     * This method must be overridden in child subclasses of AbstractBaseView.
     * The method is used for adding any extra and special components to your custom view.
     * All these components follow the base page title component, to ensure that the UI
     * looks same for the different views of the application.
     */
    protected abstract void addCustomComponents();


    /**
     * This method must be overridden in child subclasses of AbstractBaseView.
     * The purpose of the method is to use a persistence layer services for fetching
     * needed data to be represented by the view
     * @return true if data fetching from the persistence layer completed successfully
     */
    protected abstract boolean prepareViewData();

    @PostConstruct
    void init() {
        setMargin(true);
        setSpacing(true);
        //setMargin(true);
        //setSpacing(true);
        //addComponent(addSpecialStyling(getPageTitleLabel()));
    }

    private Label addSpecialStyling(Label lblTitle) {
        Label pageTitle = getPageTitleLabel();
        if (pageTitle == null) {
            throw new IllegalArgumentException("getPageTitleLabel() returned `null` in class AbstractBaseView. " +
                    "That means that you MUST return non-null value from the method getPageTitleLabel() in your views");
        }

        pageTitle.addStyleName(ValoTheme.LABEL_H1);
        return pageTitle;
    }

    /**
     * Checks for a valid user session. If someone tries to access the view and didn't perform login to
     * the system, the method returns false;
     * @return false if the user accessing this view is not authorized in the Application.
     */
    protected boolean checkUserSession() {
        if (currentUser == null || currentUser.getEmployeeLoginName() == null || currentUser.getEmployeePassword() == null) {
            return false;
        }
        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            parameterMap = event.getParameterMap();
            parameters = event.getParameters();

            if (!checkUserSession()) {
                throw new NotAuthorizedAccessException("Вы не авторизованы для просмотра данной страницы.");
            }

            // the view is constructed in the init() method()
            prepareViewData();
            addCustomComponents();
        } catch (Exception e) {
            handleException(e, e.getMessage());
        }
    }
}
