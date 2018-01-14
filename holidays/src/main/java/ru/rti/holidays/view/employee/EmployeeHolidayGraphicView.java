package ru.rti.holidays.view.employee;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import ru.rti.holidays.layout.base.StandardBaseLayoutDrawer;
import ru.rti.holidays.layout.employee.EmployeeHolidayGraphicLayout;
import ru.rti.holidays.view.base.AbstractBaseView;

@SpringView(name = EmployeeHolidayGraphicView.VIEW_NAME)
@UIScope
public class EmployeeHolidayGraphicView extends AbstractBaseView {
    public static final String VIEW_NAME = "EmployeeHolidayGraphic";

    @Override
    protected Label getPageTitleLabel() {
        return new Label("График отпусков");
    }

    @Override
    protected void addCustomComponents() {
        EmployeeHolidayGraphicLayout employeeHolidayGraphicLayout = new EmployeeHolidayGraphicLayout();
        new StandardBaseLayoutDrawer(this, employeeHolidayGraphicLayout).drawLayout();
    }

    @Override
    protected boolean prepareViewData() {
        return false;
    }
}
