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
import ru.rti.holidays.utility.GlobalConstants;
import ru.rti.holidays.utility.SessionUtils;
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
        //sideMenu.addNavigation("Представление по умолчанию", "");
        //sideMenu.addNavigation("Отпуска сотрудников", VaadinIcons.CALENDAR_CLOCK, EmployeeHolidaysView.VIEW_NAME);

        addHolidayPeriodNegotiationStatusManagementMenuItem(sideMenu);
        addUserManagementMenuItem(sideMenu);
        addTeamManagementMenuItem(sideMenu);
        addProjectRoleManagementMenuItem(sideMenu);

/*        sideMenu.addMenuItem("Встроенное меню", () -> {
            VerticalLayout content = new VerticalLayout();
            content.addComponent(new Label("Тестовый текст"));
            sideMenu.setContent(content);
        });*/

        addComponent(sideMenu);
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
                projectRoleServiceImpl.deleteProjectRoles((Set<ProjectRole>)entities);
                Notification.show("Выбранные проектные роли успешно удалены!");
            });

            adminProjectRoleManagementLayout.setRefreshGridDataListener(layout -> {
                List<ProjectRole> allProjectRoles = projectRoleServiceImpl.getAllProjectRoles();
                ((AdminProjectRoleManagementLayout)layout).setProjectRoles(allProjectRoles);

            });

            adminProjectRoleManagementLayout.constructLayout();
            adminProjectRoleManagementLayout.postConstructLayout();

            sideMenu.setContent(adminProjectRoleManagementLayout);
        });
    }

    private void addUserMenu(SideMenu sideMenu) {
        sideMenu.setUserName(GlobalConstants.ADMIN_USER_LOGIN_NAME);
        sideMenu.setUserMenuVisible(true);
        sideMenu.clearUserMenu();
        //sideMenu.addUserMenuItem(localizationServiceImpl.getMessageAdminUserMenuSettings(), VaadinIcons.WRENCH, () -> {
        //    Notification.show("Отображение настроек", Type.TRAY_NOTIFICATION);
        //});
        sideMenu.addUserMenuItem(localizationServiceImpl.getMessageAdminUserMenuExit(), VaadinIcons.EXIT, () -> {
            //Notification.show("Выход из приложения", Type.TRAY_NOTIFICATION);
            SessionUtils.logout(currentUser);
        });
    }

    /**
     * Method for constructing the view for Holiday Period Negotiation Statuses Management
     * @param sideMenu
     */
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

    /**
     * Method for constructing the view for Users Management
     * @param sideMenu
     */
    private void addUserManagementMenuItem(SideMenu sideMenu) {
        sideMenu.addMenuItem("Управление пользователями", VaadinIcons.USER, () -> {
            AdminUserManagementLayout adminUserManagementLayout = new AdminUserManagementLayout();
            MarginInfo marginInfoLayout = new MarginInfo(false, true, true, true);

            adminUserManagementLayout.setMargin(marginInfoLayout);

            adminUserManagementLayout.setExceptionHandler(new StandardViewExceptionHandler());
            adminUserManagementLayout.setProjectRoles(projectRoleServiceImpl.getAllProjectRoles());
            adminUserManagementLayout.setTeams(teamServiceImpl.getAllTeamsSortedByTeamNameAsc());

            adminUserManagementLayout.setRemoveSelectedItemsClickListener((layout, entities) -> {
                employeeServiceImpl.deleteEmployees((Set<Employee>)entities);
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

    /**
     * Method for constructing the view for Teams Management
     * @param sideMenu
     */
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
                List<Team> allTeams = teamServiceImpl.getAllTeamsSortedByTeamNameAsc();
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

    /**
     * Override the logic of checking user session and additionally check that the user accessing this view is Global Admin
     * @return
     */
    @Override
    protected boolean checkUserSession() {
        boolean isValidUserSession = super.checkUserSession();
        if (isValidUserSession) {
            if (GlobalConstants.ADMIN_USER_LOGIN_NAME.equals(getCurrentUser().getEmployeeLoginName())) {
                return true;
            }
        }
        return false;
    }
}
