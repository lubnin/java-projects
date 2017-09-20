package ru.rti.holidays.view.admin;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemusa.sidemenu.SideMenu;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.exception.StandardViewExceptionHandler;
import ru.rti.holidays.layout.admin.AdminTeamManegementLayout;
import ru.rti.holidays.layout.admin.AdminUserManagementLayout;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.service.ProjectRoleService;
import ru.rti.holidays.service.TeamService;
import ru.rti.holidays.view.base.AbstractBaseView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;
import com.vaadin.ui.Notification.Type;

import java.util.List;


@SpringView(name = AdminMainView.VIEW_NAME)
public class AdminMainView extends AbstractBaseView {
    public static final String VIEW_NAME = "AdminMain";

    @Autowired
    EmployeeService employeeServiceImpl;

    @Autowired
    ProjectRoleService projectRoleServiceImpl;

    @Autowired
    TeamService teamServiceImpl;

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


        sideMenu.setUserName("admin");
        sideMenu.setUserMenuVisible(true);
        sideMenu.clearUserMenu();
        sideMenu.addUserMenuItem("Настройки", VaadinIcons.WRENCH, () -> {
            Notification.show("Отображение настроек", Type.TRAY_NOTIFICATION);
        });
        sideMenu.addUserMenuItem("Выход", VaadinIcons.EXIT, () -> {
            Notification.show("Выход из приложения", Type.TRAY_NOTIFICATION);
        });

        sideMenu.addNavigation("Представление по умолчанию", "");
        sideMenu.addNavigation("Отпуска сотрудников", VaadinIcons.CALENDAR_CLOCK, EmployeeHolidaysView.VIEW_NAME);

        sideMenu.addMenuItem("Управление пользователями", VaadinIcons.USER, () -> {
            AdminUserManagementLayout adminUserManagementLayout = new AdminUserManagementLayout();
            MarginInfo marginInfoLayout = new MarginInfo(false, true, true, true);
            //sideMenu.setMargin(marginInfo);
            adminUserManagementLayout.setMargin(marginInfoLayout);

            adminUserManagementLayout.setExceptionHandler(new StandardViewExceptionHandler());
            adminUserManagementLayout.setProjectRoles(projectRoleServiceImpl.getAllProjectRoles());

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

        sideMenu.addMenuItem("Управление командами", VaadinIcons.USERS, () -> {


            AdminTeamManegementLayout adminTeamManegementLayout = new AdminTeamManegementLayout();
            MarginInfo marginInfoLayout = new MarginInfo(false, true, true, true);
            adminTeamManegementLayout.setMargin(marginInfoLayout);
            adminTeamManegementLayout.setExceptionHandler(new StandardViewExceptionHandler());

            adminTeamManegementLayout.setSaveButtonClickListener((layout, objectForSave) -> {
                teamServiceImpl.addTeam((Team)objectForSave);
                Notification.show("Команда успешно сохранена в базу!");

            });

            adminTeamManegementLayout.setRefreshGridDataListener(layout -> {
                List<Team> allTeams = teamServiceImpl.getAllTeams();
                ((AdminTeamManegementLayout)layout).setTeams(allTeams);
            });

            adminTeamManegementLayout.constructLayout();
            adminTeamManegementLayout.postConstructLayout();

            sideMenu.setContent(adminTeamManegementLayout);
        });

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

    @Override
    protected boolean prepareViewData() {
        return false;
    }
}
