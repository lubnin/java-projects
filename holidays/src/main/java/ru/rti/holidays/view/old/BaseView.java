package ru.rti.holidays.view.old;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class BaseView implements View {
    //TODO: add new constant here, when you decide to create a new view for Application
    public static final String VIEW_MAIN = "Main";
    public static final String VIEW_LOGIN = "Login";
    public static final String VIEW_EMPLOYEE_LIST = "EmployeeList";
    public static final String VIEW_EMPLOYEE = "Employee";

    protected Navigator navigator;

    // Lazy initialization
    protected AbstractOrderedLayout layout = null;

    protected enum ViewType {
        HORIZONTAL, VERTICAL
    }

    // Sets the view layout behavior
    public BaseView(ViewType viewType, Navigator navigator) {
        if (navigator == null) {
            throw new IllegalArgumentException("Navigator instance must not be null. Please, provide valid argument when creating the BaseView instance.");
        }

        switch (viewType) {
            case HORIZONTAL:
                layout = new HorizontalLayout();
                break;
            case VERTICAL:
                layout = new VerticalLayout();
                break;
        }
    }

    public BaseView(Navigator navigator) {
        // Set Vertical layout behavior for this view by default
        this(ViewType.VERTICAL, navigator);
    }

    public Navigator getNavigator() {
        return navigator;
    }
}
