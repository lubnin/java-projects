package ru.rti.holidays.view.error;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import ru.rti.holidays.view.base.AbstractBaseView;

@SpringView(name = ErrorDefaultView.VIEW_NAME)
public class ErrorDefaultView extends AbstractBaseView {
    public static final String VIEW_NAME = "Error";

    @Override
    protected Label getPageTitleLabel() {
        return null;
    }

    @Override
    protected void addCustomComponents() {
        Label lblError = new Label("Ошибка: представление не найдено.");
        addComponent(lblError);
    }

    @Override
    protected boolean prepareViewData() {
        return false;
    }
}
