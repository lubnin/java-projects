package ru.rti.holidays.component.vaadin.label;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.component.vaadin.RtiUIComponent;

/**
 * Custom Vaadin Component, simple class extending Vaadin Label.
 * Represents the View Page title
 */
public class PageTitle extends Label implements RtiUIComponent {

    public PageTitle() {
        this("");
    }

    public PageTitle(String text) {
        this(text, ContentMode.TEXT);
    }

    public PageTitle(String text, ContentMode contentMode) {
        setValue(text);
        setContentMode(contentMode);

    }

    @Override
    public void init() {
        //TODO: customization goes here
        addStyleName(ValoTheme.LABEL_H1);
    }
}
