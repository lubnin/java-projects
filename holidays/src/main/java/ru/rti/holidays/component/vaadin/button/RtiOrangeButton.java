package ru.rti.holidays.component.vaadin.button;

import com.vaadin.icons.VaadinIcons;
import ru.rti.holidays.utility.GlobalConstants;

public class RtiOrangeButton extends BaseRtiButton implements RtiButton {
    public RtiOrangeButton() {
        super();
    }

    public RtiOrangeButton(String caption) {
        super(caption);
    }

    @Override
    public RtiOrangeButton build() {
        super.build();
        addStyleName(GlobalConstants.CSS_RTI_BUTTON_ORANGE);
        return this;
    }
}
