package ru.rti.holidays.component;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class BoldLabel extends Label {

    public BoldLabel() {
        this("");
    }

    public BoldLabel(String text) {
        this(text, ContentMode.TEXT);
    }

    public BoldLabel(String text, ContentMode contentMode) {
        setValue(text);
        setContentMode(contentMode);

        //TODO: customization goes here
        addStyleName(ValoTheme.LABEL_BOLD);
    }
}
