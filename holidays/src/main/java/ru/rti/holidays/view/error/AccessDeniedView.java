package ru.rti.holidays.view.error;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.view.base.AbstractBaseView;

@SpringView(name = AccessDeniedView.VIEW_NAME)
public class AccessDeniedView extends AbstractBaseView {
    public static final String VIEW_NAME = "AccessDenied";

    @Override
    protected Label getPageTitleLabel() {
        return null;
    }

    @Override
    protected void addCustomComponents() {
        Label lblError = new Label("Доступ к данной странице запрещён.");
        addComponent(lblError);

    }

    @Override
    protected boolean prepareViewData() {
        return false;
    }
}
