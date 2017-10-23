package ru.rti.holidays.view.employee;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.layout.base.StandardBaseLayoutDrawer;
import ru.rti.holidays.layout.employee.EmployeeSettingsLayout;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.service.localization.LocalizationService;
import ru.rti.holidays.utility.SessionUtils;
import ru.rti.holidays.utility.UIHelper;
import ru.rti.holidays.view.base.AbstractBaseView;

@SpringView(name = EmployeeSettingsView.VIEW_NAME)
@UIScope
public class EmployeeSettingsView extends AbstractBaseView {
    public static final String VIEW_NAME = "EmployeeSettings";
    private Employee employee;

    @Autowired
    private LocalizationService localizationServiceImpl;

    @Autowired
    private EmployeeService employeeServiceImpl;

    @Override
    protected Label getPageTitleLabel() {
        return new Label("Мои настройки");
    }

    @Override
    protected void addCustomComponents() {
        EmployeeSettingsLayout employeeSettingsLayout = new EmployeeSettingsLayout();
        employeeSettingsLayout.setEmployee(employee);
        employeeSettingsLayout.setLocalizationService(localizationServiceImpl);
        employeeSettingsLayout.setSaveButtonClickListener((layout, objectForSave) -> {
            employeeServiceImpl.saveEmployee(employee);
            UIHelper.showNotification("Ваши настройки были успешно сохранены.");
        });

        new StandardBaseLayoutDrawer(this, employeeSettingsLayout).drawLayout();
    }

    @Override
    protected boolean prepareViewData() {
        employee = SessionUtils.getCurrentUser();
        return true;
    }
}
