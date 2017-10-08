package ru.rti.holidays.component;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Custom Vaadin Component, simple class extending Vaadin Label.
 * Represents the View Page title
 */
public class PageTitle extends Label {

    public PageTitle() {
        this("");

    }
    public PageTitle(String text) {
        this(text, ContentMode.TEXT);
    }
    public PageTitle(String text, ContentMode contentMode) {
        setValue(text);
        setContentMode(contentMode);

        //TODO: customization goes here
        addStyleName(ValoTheme.LABEL_H1);
    }
}
