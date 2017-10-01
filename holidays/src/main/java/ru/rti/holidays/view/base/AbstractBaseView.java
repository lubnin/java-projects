package ru.rti.holidays.view.base;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;
import java.util.Map;

abstract public class AbstractBaseView extends VerticalLayout implements View {
    protected Map<String, String> parameterMap;
    protected String parameters;

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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        parameterMap = event.getParameterMap();
        parameters = event.getParameters();
        // the view is constructed in the init() method()
        prepareViewData();
        addCustomComponents();
    }
}
