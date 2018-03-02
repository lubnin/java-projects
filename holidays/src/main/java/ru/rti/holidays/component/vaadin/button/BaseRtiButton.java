package ru.rti.holidays.component.vaadin.button;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

public class BaseRtiButton extends Button implements RtiButton {
    public BaseRtiButton() {
        super();
    }

    public BaseRtiButton(String caption) {
        super(caption);
    }

//    public BaseRtiButton buildDisabled300px() {
//        setButtonWidth("300px");
//        setButtonEnabled(false);
//        return this;
//    }

    @Override
    public BaseRtiButton build() {
        // TODO: change this settings if you want to apply them by default for all buttons in the application
        setButtonWidth("300px");
        setButtonEnabled(false);
        return this;
    }

    public BaseRtiButton caption(String caption) {
        setButtonCaption(caption);
        return this;
    }

    public BaseRtiButton enabled(boolean enabled) {
        setButtonEnabled(enabled);
        return this;
    }

    public BaseRtiButton id(String id) {
        setButtonId(id);
        return this;
    }

    public BaseRtiButton width(String width) {
        setButtonWidth(width);
        return this;
    }

    public BaseRtiButton icon(Resource icon) {
        setButtonIcon(icon);
        return this;
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

    @Override
    public void setButtonIcon(Resource icon) { setIcon(icon); }
}
