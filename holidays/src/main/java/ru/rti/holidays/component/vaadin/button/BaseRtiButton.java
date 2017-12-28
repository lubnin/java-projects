package ru.rti.holidays.component.vaadin.button;

import com.vaadin.ui.Button;

public class BaseRtiButton extends Button implements RtiButton {
    public BaseRtiButton() {
        super();
    }

    public BaseRtiButton(String caption) {
        super(caption);
    }

    @Override
    public void init() {
    }

    @Override
    public void setButtonCaption(String caption) {
        setCaption(caption);
    }

    @Override
    public void setButtonEnabled(boolean enabled) {
        setEnabled(enabled);
    }

    @Override
    public void setButtonId(String id) {
        setId(id);
    }

    @Override
    public void setButtonWidth(String width) {
        setWidth(width);
    }
}
