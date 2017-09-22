package ru.rti.holidays.view.admin;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemusa.sidemenu.SideMenu;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.exception.StandardViewExceptionHandler;
import ru.rti.holidays.layout.admin.AdminHolidayPeriodNegotiationStatusLayout;
import ru.rti.holidays.layout.admin.AdminTeamManagementLayout;
import ru.rti.holidays.layout.admin.AdminUserManagementLayout;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.service.HolidayPeriodService;
import ru.rti.holidays.service.ProjectRoleService;
import ru.rti.holidays.service.TeamService;
import ru.rti.holidays.service.config.ConfigurationService;
import ru.rti.holidays.service.localization.LocalizationService;
import ru.rti.holidays.view.base.AbstractBaseView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;
import com.vaadin.ui.Notification.Type;

import java.util.List;
import java.util.Set;


@SpringView(name = AdminMainView.VIEW_NAME)
public class AdminMainView extends AbstractBaseView {
    public static final String VIEW_NAME = "AdminMain";

    @Autowired
    EmployeeService employeeServiceImpl;

    @Autowired
    ProjectRoleService projectRoleServiceImpl;

    @Autowired
    HolidayPeriodService holidayPeriodServiceImpl;

    @Autowired
    TeamService teamServiceImpl;

    //@Autowired
    //ConfigurationService configurationServiceImpl;
    @Autowired
    LocalizationService localizationServiceImpl;


    @Override
    protected Label getPageTitleLabel() {
        return null;
    }

    @Override
    protected void addCustomComponents() {
        SideMenu sideMenu = new SideMenu();

        sideMenu.setMenuCaption("Администрирование");
        sideMenu.setSizeFull();

        MarginInfo marginInfo = new MarginInfo(false, false, false, false);
        sideMenu.setMargin(marginInfo);

        addUserMenu(sideMenu);
        sideMenu.addNavigation("Представление по умолчанию", "");
        sideMenu.addNavigation("Отпуска сотрудников", VaadinIcons.CALENDAR_CLOCK, EmployeeHolidaysView.VIEW_NAME);

        addHolidayPeriodNegotiationStatusManagementMenuItem(sideMenu);
        addUserManagementMenuItem(sideMenu);
        addTeamManagementMenuItem(sideMenu);

        sideMenu.addMenuItem("Встроенное меню", () -> {
            VerticalLayout content = new VerticalLayout();
            content.addComponent(new Label("Тестовый текст"));
            sideMenu.setContent(content);
        });

        addComponent(sideMenu);

        /*HybridMenu hybridMenu = HybridMenuBuilder.get()
                .setContent(new VerticalLayout())
                .setMenuComponent(EMenuComponents.LEFT_WITH_TOP)
                .build();

        hybridMenu.setCaption("Администрирование");
        hybridMenu.setSizeFull();

        MenuButton btnHomePage = LeftMenuButtonBuilder.get()
                .setCaption("Главная страница")
                .setIcon(VaadinIcons.HOME)
                .navigateTo(EmployeeHolidaysView.VIEW_NAME)
                .build();

        MenuButton btnUserManagement = LeftMenuButtonBuilder.get()
                .setCaption("Пользователи")
                .setIcon(VaadinIcons.USERS)
                .navigateTo(EmployeeHolidaysView.VIEW_NAME)
                .build();

        MenuSubMenu subMenuReferences = LeftMenuSubMenuBuilder.get()
                .setCaption("Справочники")
                .setIcon(VaadinIcons.BOOK)
                .setConfig(hybridMenu.getConfig())
                .build(hybridMenu);


        MenuItem lblMenuItem = new LabelMenuItem();

        TopMenuButtonBuilder.get()
                .setCaption("Главная")
                .setIcon(VaadinIcons.HOME)
                .setAlignment(Alignment.MIDDLE_RIGHT)
                .setNavigateToName(EmployeeHolidaysView.VIEW_NAME)
                .build(hybridMenu);

        UI.getCurrent().getNavigator().setErrorView(new ErrorDefaultView());

        hybridMenu.addLeftMenuButton(btnHomePage);
        subMenuReferences.addLeftMenuButton(btnUserManagement);
        hybridMenu.addLeftMenuSubMenu(subMenuReferences);
        hybridMenu.addMenuItem(lblMenuItem);
        hybridMenu.switchTheme(EMenuDesign.DARK);
        addComponent(hybridMenu);*/
    }

    private void addUserMenu(SideMenu sideMenu) {
        sideMenu.setUserName("admin");
        sideMenu.setUserMenuVisible(true);
        sideMenu.clearUserMenu();
        sideMenu.addUserMenuItem(localizationServiceImpl.getMessageAdminUserMenuSettings(), VaadinIcons.WRENCH, () -> {
            Notification.show("Отображение настроек", Type.TRAY_NOTIFICATION);
        });
        sideMenu.addUserMenuItem(localizationServiceImpl.getMessageAdminUserMenuExit(), VaadinIcons.EXIT, () -> {
            Notification.show("Выход из приложения", Type.TRAY_NOTIFICATION);
        });
    }
                holidayPeriodServiceImpl.addHolidayPeriodNegotiationStatus((HolidayPeriodNegotiationStatus)objectForSave);

                Notification.show("Статус согласования отпуска успешно сохранён в базу!");

            });


            adminHolidayPeriodNegotiationStatusLayout.setRefreshGridDataListener(layout -> {
                List<HolidayPeriodNegotiationStatus> allHolidayPeriodNegotiationStatuses = holidayPeriodServiceImpl.getAllHolidayPeriodNegotiationStatuses();
                ((AdminHolidayPeriodNegotiationStatusLayout)layout).setHolidayPeriodNegotiationStatuses(allHolidayPeriodNegotiationStatuses);
            });

            adminHolidayPeriodNegotiationStatusLayout.constructLayout();
            adminHolidayPeriodNegotiationStatusLayout.postConstructLayout();

            sideMenu.setContent(adminHolidayPeriodNegotiationStatusLayout);
        });
    }

    private void addUserManagementMenuItem(SideMenu sideMenu) {
        sideMenu.addMenuItem("Управление пользователями", VaadinIcons.USER, () -> {
            AdminUserManagementLayout adminUserManagementLayout = new AdminUserManagementLayout();
            MarginInfo marginInfoLayout = new MarginInfo(false, true, true, true);

            adminUserManagementLayout.setMargin(marginInfoLayout);

            adminUserManagementLayout.setExceptionHandler(new StandardViewExceptionHandler());
            adminUserManagementLayout.setProjectRoles(projectRoleServiceImpl.getAllProjectRoles());
            adminUserManagementLayout.setTeams(teamServiceImpl.getAllTeams());

            adminUserManagementLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
               employeeServiceImpl.deleteEmployees((Set<Employee>)entities);
               //List<Employee> allEmployees = employeeServiceImpl.getAllEmployees();
               //((AdminUserManagementLayout)layout).setEmployees(allEmployees);
               Notification.show("Выбранные сотрудники успешно удалены!");
            });

            adminUserManagementLayout.setSaveButtonClickListener((layout, objectForSave) -> {
                employeeServiceImpl.addEmployee((Employee)objectForSave);
                Notification.show("Сотрудник успешно сохранён в базу!");
            });


            adminUserManagementLayout.setRefreshGridDataListener(layout -> {
                List<Employee> allEmployees = employeeServiceImpl.getAllEmployees();
                ((AdminUserManagementLayout)layout).setEmployees(allEmployees);
            });

            adminUserManagementLayout.constructLayout();
            adminUserManagementLayout.postConstructLayout();

            sideMenu.setContent(adminUserManagementLayout);
        });
    }

    private void addTeamManagementMenuItem(SideMenu sideMenu) {
        sideMenu.addMenuItem("Управление командами", VaadinIcons.USERS, () -> {


            AdminTeamManagementLayout adminTeamManagementLayout = new AdminTeamManagementLayout();
            MarginInfo marginInfoLayout = new MarginInfo(false, true, true, true);
            adminTeamManagementLayout.setMargin(marginInfoLayout);
            adminTeamManagementLayout.setExceptionHandler(new StandardViewExceptionHandler());

            adminTeamManagementLayout.setSaveButtonClickListener((layout, objectForSave) -> {
                teamServiceImpl.addTeam((Team)objectForSave);
                Notification.show("Команда успешно сохранена в базу!");

            });

            adminTeamManagementLayout.setRefreshGridDataListener(layout -> {
                List<Team> allTeams = teamServiceImpl.getAllTeams();
                ((AdminTeamManagementLayout)layout).setTeams(allTeams);
            });

            adminTeamManagementLayout.constructLayout();
            adminTeamManagementLayout.postConstructLayout();

            sideMenu.setContent(adminTeamManagementLayout);
        });
    }

    @Override
    protected boolean prepareViewData() {
        return false;
    }
}
