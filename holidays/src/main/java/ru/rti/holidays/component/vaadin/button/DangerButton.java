package ru.rti.holidays.component.vaadin.button;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.themes.ValoTheme;

public class DangerButton extends BaseRtiButton implements RtiButton {
    public DangerButton() {
        super();
    }

    public DangerButton(String caption) {
        super(caption);
    }

    @Override
    public void init() {
        addStyleName(ValoTheme.BUTTON_DANGER);
        setIcon(VaadinIcons.STOP);
    }
}
