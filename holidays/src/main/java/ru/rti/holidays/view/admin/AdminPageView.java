package ru.rti.holidays.view.admin;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import ru.rti.holidays.design.admin.AdminPageDesign;
import ru.rti.holidays.view.base.AbstractBaseView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;

/**
 * @deprecated
 * The test Spring View for demonstrating usage of Vaadin Designer and extension of class, constructed by Vaadin Designer
 * Not needed anywhere in the app
 * //TODO: remove this class later
 */
@SpringView(name = AdminPageView.VIEW_NAME)
public class AdminPageView extends AdminPageDesign implements View {
    public static final String VIEW_NAME = "AdminPage";
}
