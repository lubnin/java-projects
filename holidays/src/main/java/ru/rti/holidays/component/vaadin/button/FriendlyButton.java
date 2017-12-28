package ru.rti.holidays.component.vaadin.button;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.themes.ValoTheme;

public class FriendlyButton extends BaseRtiButton implements RtiButton {
    public FriendlyButton() {
        super();
    }

    public FriendlyButton(String caption) {
        super(caption);
    }

    @Override
    public void init() {
        addStyleName(ValoTheme.BUTTON_FRIENDLY);
        setIcon(VaadinIcons.CHECK);
    }

    @Override
    public void setButtonCaption(String caption) {
        setCaption(caption);
    }
}
