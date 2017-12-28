package ru.rti.holidays.component.vaadin.button;

import com.vaadin.icons.VaadinIcons;
import ru.rti.holidays.utility.GlobalConstants;

public class RtiDatesCrossingButton extends BaseRtiButton implements RtiButton {
    public RtiDatesCrossingButton() {
        super();
    }

    public RtiDatesCrossingButton(String caption) {
        super(caption);
    }

    @Override
    public void init() {
        addStyleName(GlobalConstants.CSS_RTI_BUTTON_ORANGE);
        setIcon(VaadinIcons.ARROWS_CROSS);
    }
}
