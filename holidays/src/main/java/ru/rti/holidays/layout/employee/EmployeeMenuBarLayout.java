package ru.rti.holidays.layout.employee;

import com.vaadin.ui.MenuBar;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.utility.NavigationUtils;
import ru.rti.holidays.utility.SessionUtils;
import ru.rti.holidays.view.employee.EmployeeAllHolidaysView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;
import ru.rti.holidays.view.employee.EmployeeSettingsView;

public class EmployeeMenuBarLayout extends BaseVerticalLayout {
    public static final String MENU_ITEM_SYSTEM_CAPTION = "Система";
    public static final String MENU_ITEM_SYSTEM_MAIN_CAPTION = "Главная";
    public static final String MENU_ITEM_SYSTEM_SETTINGS_CAPTION = "Настройки";
    public static final String MENU_ITEM_SYSTEM_EXIT_CAPTION = "Выход";
    public static final String MENU_ITEM_HOLIDAYS_CAPTION = "Отпуска";
    public static final String MENU_ITEM_HOLIDAYS_VIEW_ALL = "Просмотреть все отпуска";
    public static final String MENU_ITEM_HOLIDAYS_VIEW_CURRENT = "Просмотреть текущие отпуска";
    public static final String MENU_ITEM_HOLIDAYS_VIEW_NEAREST = "Просмотреть ближайшие отпуска";

    protected boolean isMainMenuItemVisible = true;
    protected boolean isSettingsMenutItemVisible = true;
    protected boolean isExitMenuItemVisible = true;

    protected boolean isHolidaysViewAllMenuItemVisible = true;
    protected boolean isHolidaysViewCurrentMenuItemVisible = true;
    protected boolean isHolidaysViewNearestMenuItemVisible = true;

    public EmployeeMenuBarLayout() {
        super();
    }
    public EmployeeMenuBarLayout(boolean isApplyMargin, boolean isApplySpacing) {
        super(isApplyMargin, isApplySpacing);
    }

    public boolean isMainMenuItemVisible() {
        return isMainMenuItemVisible;
    }

    public void setMainMenuItemVisible(boolean mainMenuItemVisible) {
        isMainMenuItemVisible = mainMenuItemVisible;
    }

    public boolean isExitMenuItemVisible() {
        return isExitMenuItemVisible;
    }

    public void setExitMenuItemVisible(boolean exitMenuItemVisible) {
        isExitMenuItemVisible = exitMenuItemVisible;
    }

    public boolean isSettingsMenuItemVisible() {
        return isSettingsMenutItemVisible;
    }

    public void setSettingsMenutItemVisible(boolean settingsMenutItemVisible) {
        isSettingsMenutItemVisible = settingsMenutItemVisible;
    }

    public boolean isHolidaysViewAllMenuItemVisible() {
        return isHolidaysViewAllMenuItemVisible;
    }

    public void setHolidaysViewAllMenuItemVisible(boolean holidaysViewAllMenuItemVisible) {
        isHolidaysViewAllMenuItemVisible = holidaysViewAllMenuItemVisible;
    }

    public boolean isHolidaysViewCurrentMenuItemVisible() {
        return isHolidaysViewCurrentMenuItemVisible;
    }

    public void setHolidaysViewCurrentMenuItemVisible(boolean holidaysViewCurrentMenuItemVisible) {
        isHolidaysViewCurrentMenuItemVisible = holidaysViewCurrentMenuItemVisible;
    }

    public boolean isHolidaysViewNearestMenuItemVisible() {
        return isHolidaysViewNearestMenuItemVisible;
    }

    public void setHolidaysViewNearestMenuItemVisible(boolean holidaysViewNearestMenuItemVisible) {
        isHolidaysViewNearestMenuItemVisible = holidaysViewNearestMenuItemVisible;
    }

    @Override
    public void constructLayout() {
        try {
            MenuBar.Command cmdSystemExit = new MenuBar.Command() {
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    SessionUtils.logout();
                }
            };

            MenuBar.Command cmdSystemSettings = new MenuBar.Command() {
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    NavigationUtils.navigateToView(EmployeeSettingsView.VIEW_NAME);
                }
            };

            MenuBar.Command cmdSystemMain = new MenuBar.Command() {
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    NavigationUtils.navigateToView(EmployeeHolidaysView.VIEW_NAME);
                }
            };

            MenuBar.Command cmdHolidaysViewAll = new MenuBar.Command() {
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    NavigationUtils.navigateToView(EmployeeAllHolidaysView.VIEW_NAME);
                }
            };

            MenuBar.Command cmdHolidaysViewCurrent = new MenuBar.Command() {
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    //UI.getCurrent().getNavigator().navigateTo(EmployeeHolidaysView.VIEW_NAME);
                    //NavigationUtils.navigateToView(EmployeeSettingsView.VIEW_NAME);
                }
            };

            MenuBar.Command cmdHolidaysViewNearest = new MenuBar.Command() {
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    //UI.getCurrent().getNavigator().navigateTo(EmployeeHolidaysView.VIEW_NAME);
                    //NavigationUtils.navigateToView(EmployeeSettingsView.VIEW_NAME);
                }
            };

            MenuBar menuBar = new MenuBar();
            MenuBar.MenuItem menuItemSystem = menuBar.addItem(MENU_ITEM_SYSTEM_CAPTION, null, null);
            MenuBar.MenuItem menuItemHolidays = menuBar.addItem(MENU_ITEM_HOLIDAYS_CAPTION, null, null);

            if (isMainMenuItemVisible()) {
                MenuBar.MenuItem menuItemSystemMain = menuItemSystem.addItem(MENU_ITEM_SYSTEM_MAIN_CAPTION, null, cmdSystemMain);
            }
            if (isSettingsMenuItemVisible()) {
                MenuBar.MenuItem menuItemSystemSettings = menuItemSystem.addItem(MENU_ITEM_SYSTEM_SETTINGS_CAPTION, null, cmdSystemSettings);
            }
            if (isExitMenuItemVisible()) {
                MenuBar.MenuItem menuItemSystemExit = menuItemSystem.addItem(MENU_ITEM_SYSTEM_EXIT_CAPTION, null, cmdSystemExit);
            }
            if (isHolidaysViewAllMenuItemVisible()) {
                MenuBar.MenuItem menuItemHolidaysViewAll = menuItemHolidays.addItem(MENU_ITEM_HOLIDAYS_VIEW_ALL, null, cmdHolidaysViewAll);
            }
            if (isHolidaysViewCurrentMenuItemVisible()) {
                MenuBar.MenuItem menuItemHolidaysViewCurrent = menuItemHolidays.addItem(MENU_ITEM_HOLIDAYS_VIEW_CURRENT, null, cmdHolidaysViewCurrent);
            }
            if (isHolidaysViewNearestMenuItemVisible()) {
                MenuBar.MenuItem menuItemHolidaysViewNearest = menuItemHolidays.addItem(MENU_ITEM_HOLIDAYS_VIEW_NEAREST, null, cmdHolidaysViewNearest);
            }

            addComponent(menuBar);
        } catch (Exception e) {
            handleException(e, e.getMessage());
        }
    }

    @Override
    public void postConstructLayout() {

    }
}
