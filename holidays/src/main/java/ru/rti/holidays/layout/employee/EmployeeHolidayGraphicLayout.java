package ru.rti.holidays.layout.employee;

import ru.rti.holidays.exception.LayoutConstructionException;
import ru.rti.holidays.layout.base.BaseVerticalLayout;
import ru.rti.holidays.layout.base.StandardBaseLayoutDrawer;

public class EmployeeHolidayGraphicLayout extends BaseVerticalLayout {
    @Override
    public void constructLayout() throws LayoutConstructionException {
        EmployeeHorizontalButtonMenuBarLayout employeeHorizontalButtonMenuBarLayout = new EmployeeHorizontalButtonMenuBarLayout(false, false);
        new StandardBaseLayoutDrawer(this, employeeHorizontalButtonMenuBarLayout).drawLayout();


        /*
        GridLayout calendarGraphicLayout = new GridLayout(6,2);

        InlineDateField monthJanuary = new InlineDateField();
        InlineDateField monthFebruary = new InlineDateField();
        InlineDateField monthMarch = new InlineDateField();
        InlineDateField monthApril = new InlineDateField();
        InlineDateField monthMay = new InlineDateField();
        InlineDateField monthJune = new InlineDateField();
        InlineDateField monthJuly = new InlineDateField();
        InlineDateField monthAugust = new InlineDateField();
        InlineDateField monthSeptember = new InlineDateField();
        InlineDateField monthOctober = new InlineDateField();
        InlineDateField monthNovember = new InlineDateField();
        InlineDateField monthDecember = new InlineDateField();

        monthJanuary.setRangeStart(LocalDate.of(2018, 1, 1));
        monthJanuary.setRangeEnd(LocalDate.of(2018, 1, 31));
        monthFebruary.setRangeStart(LocalDate.of(2018, 2, 1));
        monthFebruary.setRangeEnd(LocalDate.of(2018, 2, 28));

        monthFebruary.addFocusListener(focusEvent -> {
            UIHelper.showNotification("Focus occured");
        });
        monthFebruary.addBlurListener(blurEvent -> {
            UIHelper.showNotification("Blur occured");
        });
        monthFebruary.addValueChangeListener(valueChangeEvent -> {
            UIHelper.showNotification("value changed");
        });

        calendarGraphicLayout.addComponent(monthJanuary, 0, 0);
        calendarGraphicLayout.addComponent(monthFebruary, 1, 0);
        calendarGraphicLayout.addComponent(monthMarch, 2, 0);
        calendarGraphicLayout.addComponent(monthApril, 3, 0);
        calendarGraphicLayout.addComponent(monthMay, 4, 0);
        calendarGraphicLayout.addComponent(monthJune, 5, 0);
        calendarGraphicLayout.addComponent(monthJuly, 0, 1);
        calendarGraphicLayout.addComponent(monthAugust, 1, 1);
        calendarGraphicLayout.addComponent(monthSeptember, 2, 1);
        calendarGraphicLayout.addComponent(monthOctober, 3, 1);
        calendarGraphicLayout.addComponent(monthNovember, 4, 1);
        calendarGraphicLayout.addComponent(monthDecember, 5, 1);

        calendarGraphicLayout.setComponentAlignment(monthJanuary, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthFebruary, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthMarch, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthApril, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthMay, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthJune, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthJuly, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthAugust, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthSeptember, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthOctober, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthNovember, Alignment.MIDDLE_CENTER);
        calendarGraphicLayout.setComponentAlignment(monthDecember, Alignment.MIDDLE_CENTER);

        addComponent(calendarGraphicLayout);*/
    }
}
