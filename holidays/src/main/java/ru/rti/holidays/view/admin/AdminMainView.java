package ru.rti.holidays.view.admin;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemusa.sidemenu.SideMenu;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.HolidayPeriodNegotiationStatus;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.exception.handler.StandardViewExceptionHandler;
import ru.rti.holidays.layout.admin.AdminHolidayPeriodNegotiationStatusLayout;
import ru.rti.holidays.layout.admin.AdminProjectRoleManagementLayout;
import ru.rti.holidays.layout.admin.AdminTeamManagementLayout;
import ru.rti.holidays.layout.admin.AdminUserManagementLayout;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.service.HolidayPeriodService;
import ru.rti.holidays.service.ProjectRoleService;
import ru.rti.holidays.service.TeamService;
import ru.rti.holidays.service.localization.LocalizationService;
import ru.rti.holidays.view.base.AbstractBaseView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;
import com.vaadin.ui.Notification.Type;

import java.util.List;
import java.util.Set;


@SpringView(name = AdminMainView.VIEW_NAME)
public class AdminMainView extends AbstractBaseView {
    private static final Logger log = LoggerFactory.getLogger(AdminMainView.class);
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
        addProjectRoleManagementMenuItem(sideMenu);

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


    private void addProjectRoleManagementMenuItem(SideMenu sideMenu) {
        sideMenu.addMenuItem("Управление проектными ролями", VaadinIcons.USER_STAR, () -> {
            MarginInfo marginInfoLayout = new MarginInfo(false, true, true, true);


/*
            AddNewEntityLayout<ProjectRole> projectRoleAddNewEntityLayout = new AddNewEntityLayout<>(ProjectRole.class);
            MarginInfo marginInfoLayout = new MarginInfo(false, true, true, true);
            projectRoleAddNewEntityLayout.setMargin(marginInfoLayout);
            projectRoleAddNewEntityLayout.setExceptionHandler(new StandardViewExceptionHandler());
            projectRoleAddNewEntityLayout.constructLayout();
            projectRoleAddNewEntityLayout.postConstructLayout();
*/

            AdminProjectRoleManagementLayout adminProjectRoleManagementLayout = new AdminProjectRoleManagementLayout();
            adminProjectRoleManagementLayout.setMargin(marginInfoLayout);
            adminProjectRoleManagementLayout.setPageTitle("Управление проектными ролями");
            adminProjectRoleManagementLayout.setExceptionHandler(new StandardViewExceptionHandler());

            adminProjectRoleManagementLayout.setSaveButtonClickListener((layout, objectForSave) -> {
                projectRoleServiceImpl.saveProjectRole((ProjectRole)objectForSave);
                Notification.show("Проектная роль успешно сохранена!");
            });

            adminProjectRoleManagementLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                Set<ProjectRole> projectRoles = (Set<ProjectRole>)entities;
                //projectRoles.forEach(projectRole -> {

                    //employeeServiceImpl.resetProjectRoleForEmployees(projectRole);
                //});

                projectRoleServiceImpl.deleteProjectRoles((Set<ProjectRole>)entities);
                Notification.show("Выбранные проектные роли успешно удалены!");
            });

            adminProjectRoleManagementLayout.setRefreshGridDataListener(layout -> {
                List<ProjectRole> allProjectRoles = projectRoleServiceImpl.getAllProjectRoles();
                ((AdminProjectRoleManagementLayout)layout).setProjectRoles(allProjectRoles);

            });

            adminProjectRoleManagementLayout.constructLayout();
            adminProjectRoleManagementLayout.postConstructLayout();

            ///Binder<ProjectRole> binder = projectRoleAddNewEntityLayout.getEntityBinder();

            //List<AddNewEntityLayout.NewEntityTextControlBinding> textCtrlBindings = projectRoleAddNewEntityLayout.getTextControlBindings();
            //textCtrlBindings.add(new AddNewEntityLayout.NewEntityTextControlBinding("Название роли", ProjectRole::getRoleName, ProjectRole::setRoleName));

            sideMenu.setContent(adminProjectRoleManagementLayout);
        });
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

    private void addHolidayPeriodNegotiationStatusManagementMenuItem(SideMenu sideMenu) {
        sideMenu.addMenuItem("Управление статусами согласования отпусков", VaadinIcons.CHECK, () -> {
            AdminHolidayPeriodNegotiationStatusLayout adminHolidayPeriodNegotiationStatusLayout = new AdminHolidayPeriodNegotiationStatusLayout();
            MarginInfo marginInfoLayout = new MarginInfo(false, true, true, true);

            adminHolidayPeriodNegotiationStatusLayout.setMargin(marginInfoLayout);
            adminHolidayPeriodNegotiationStatusLayout.setExceptionHandler(new StandardViewExceptionHandler());
            adminHolidayPeriodNegotiationStatusLayout.setHolidayPeriodNegotiationStatuses(holidayPeriodServiceImpl.getAllHolidayPeriodNegotiationStatuses());

            adminHolidayPeriodNegotiationStatusLayout.setSaveButtonClickListener((layout, objectForSave) -> {
                holidayPeriodServiceImpl.saveHolidayPeriodNegotiationStatus((HolidayPeriodNegotiationStatus)objectForSave);
                Notification.show("Статус согласования отпуска успешно сохранён!");
            });

            adminHolidayPeriodNegotiationStatusLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                holidayPeriodServiceImpl.deleteHolidayPeriodNegotiationStatuses((Set<HolidayPeriodNegotiationStatus>)entities);
                Notification.show("Выбранные статусы согласования отпуска успешно удалены!");
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
                log.info("AdminMainView::setRemoveSelectedItemsClickListener() called.");
                //TODO: remove later
                if (entities != null) {
                    entities.forEach(o -> {
                        log.info("Entity to delete: " + o);
                    });
                }
                log.info("Calling employeeServiceImpl.deleteEmployees(...);");
                employeeServiceImpl.deleteEmployees((Set<Employee>)entities);
               //List<Employee> allEmployees = employeeServiceImpl.getAllEmployees();
               //((AdminUserManagementLayout)layout).setEmployees(allEmployees);
                Notification.show("Выбранные сотрудники успешно удалены!");
            });

            adminUserManagementLayout.setSaveButtonClickListener((layout, objectForSave) -> {
                employeeServiceImpl.saveEmployee((Employee)objectForSave);
                Notification.show("Сотрудник успешно сохранён!");
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
                teamServiceImpl.saveTeam((Team)objectForSave);
                Notification.show("Команда успешно сохранена!");

            });

            adminTeamManagementLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                teamServiceImpl.deleteTeams((Set<Team>)entities);
                Notification.show("Выбранные команды успешно удалены!");
            });


            adminTeamManagementLayout.setRefreshGridDataListener(layout -> {
                Set<Team> allTeams = teamServiceImpl.getAllTeams();
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
