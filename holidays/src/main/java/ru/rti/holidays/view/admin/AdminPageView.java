package ru.rti.holidays.view.admin;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import ru.rti.holidays.design.admin.AdminPageDesign;
import ru.rti.holidays.view.base.AbstractBaseView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;

@SpringView(name = AdminPageView.VIEW_NAME)
public class AdminPageView extends AdminPageDesign implements View {
    public static final String VIEW_NAME = "AdminPage";
}
