package ru.rti.holidays.component.vaadin.button;

import ru.rti.holidays.component.vaadin.RtiUIComponent;

public interface RtiButton extends RtiUIComponent {
    void setButtonCaption(String caption);
    void setButtonEnabled(boolean enabled);
    void setButtonId(String id);
    void setButtonWidth(String width);
}
