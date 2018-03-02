package ru.rti.holidays.component.vaadin.label;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.component.vaadin.RtiUIComponent;

/**
 * Custom Vaadin Component for displaying Label in Bold style
 */
public class BoldLabel extends Label implements RtiUIComponent {

    public BoldLabel() {
        this("");
    }

    public BoldLabel(String text) {
        this(text, ContentMode.TEXT);
    }

    public BoldLabel(String text, ContentMode contentMode) {
        setValue(text);
        setContentMode(contentMode);

        //TODO: customization of the component goes here
        addStyleName(ValoTheme.LABEL_BOLD);
    }

    @Override
    public BoldLabel build() {
        //TODO: customization of the component goes here
        addStyleName(ValoTheme.LABEL_BOLD);
        return this;
    }
}
