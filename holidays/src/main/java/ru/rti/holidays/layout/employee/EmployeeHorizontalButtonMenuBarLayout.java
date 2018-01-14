package ru.rti.holidays.layout.employee;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import ru.rti.holidays.layout.base.BaseGridLayout;
import ru.rti.holidays.utility.NavigationUtils;
import ru.rti.holidays.utility.SessionUtils;
import ru.rti.holidays.view.employee.EmployeeAllHolidaysView;
import ru.rti.holidays.view.employee.EmployeeHolidayGraphicView;
import ru.rti.holidays.view.employee.EmployeeHolidaysView;
import ru.rti.holidays.view.employee.EmployeeSettingsView;

public class EmployeeHorizontalButtonMenuBarLayout extends BaseGridLayout {
    public static final String BUTTON_CAPTION_MY_PAGE = "Моя страница";
    public static final String BUTTON_CAPTION_VIEW_HOLIDAYS = "Отпуска";
    public static final String BUTTON_CAPTION_GRAPHIC = "График отпусков";
    public static final String BUTTON_CAPTION_SETTINGS = "Настройки";
    public static final String BUTTON_CAPTION_EXIT = "Выход";

    public EmployeeHorizontalButtonMenuBarLayout() {
        super();
    }
    public EmployeeHorizontalButtonMenuBarLayout(boolean isApplyMargin, boolean isApplySpacing) {
        super(isApplyMargin, isApplySpacing);
    }

    @Override
    public void constructLayout() {
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
//        setColumns(6);
        setColumns(5);
        setRows(1);
        setSizeFull();
        setSpacing(false);

        Button btnMyPage = new Button(BUTTON_CAPTION_MY_PAGE, VaadinIcons.USER);
        btnMyPage.addClickListener(clickEvent -> {
            NavigationUtils.navigateToView(EmployeeHolidaysView.VIEW_NAME);
        });

        Button btnCalendar = new Button(BUTTON_CAPTION_VIEW_HOLIDAYS, VaadinIcons.CALENDAR);
        btnCalendar.addClickListener(clickEvent -> {
            NavigationUtils.navigateToView(EmployeeAllHolidaysView.VIEW_NAME);
        });

//        Button btnGraphic = new Button(BUTTON_CAPTION_GRAPHIC, VaadinIcons.CALENDAR_CLOCK);
//        btnGraphic.addClickListener(clickEvent -> {
//            NavigationUtils.navigateToView(EmployeeHolidayGraphicView.VIEW_NAME);
//        });


        Button btnSettings = new Button(BUTTON_CAPTION_SETTINGS, VaadinIcons.WRENCH);
        btnSettings.addClickListener(clickEvent -> {
            NavigationUtils.navigateToView(EmployeeSettingsView.VIEW_NAME);
        });

        Button btnExit = new Button(BUTTON_CAPTION_EXIT, VaadinIcons.EXIT);
        btnExit.addClickListener(clickEvent -> {
            SessionUtils.logout();
        });

        addComponent(btnMyPage, 0, 0);
        addComponent(btnCalendar, 1, 0);
        //addComponent(btnGraphic, 2, 0);

        addComponent(btnSettings, 3, 0);
        addComponent(btnExit, 4, 0);
        //addComponent(btnSettings, 4, 0);
        //addComponent(btnExit, 5, 0);


        setColumnExpandRatio(2, 5);
        //setColumnExpandRatio(3, 5);

        setComponentAlignment(btnMyPage, Alignment.MIDDLE_LEFT);
        setComponentAlignment(btnCalendar, Alignment.MIDDLE_LEFT);
        //setComponentAlignment(btnGraphic, Alignment.MIDDLE_LEFT);
        setComponentAlignment(btnSettings, Alignment.MIDDLE_RIGHT);
        setComponentAlignment(btnExit, Alignment.MIDDLE_RIGHT);
    }


}
