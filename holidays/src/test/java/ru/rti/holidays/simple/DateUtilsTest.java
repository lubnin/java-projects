package ru.rti.holidays.simple;

import org.junit.Test;
import ru.rti.holidays.utility.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.*;

public class DateUtilsTest {
    private Calendar cal = GregorianCalendar.getInstance();
    private Date dateStart1;
    private Date dateEnd1;
    private Date dateStart2;
    private Date dateEnd2;

    private Date dateInsidePeriod1Start;
    private Date dateInsidePeriod1End;

    private Date dateBeforePeriod1Start;
    private Date dateBeforePeriod1End;

    private Date dateAfterPeriod1Start;
    private Date dateAfterPeriod1End;

    public DateUtilsTest() {
        System.out.println("|---- Start Constructor of DateUtilstest ----|");
        cal.set(2018, Calendar.MAY, 1);
        dateStart1 = cal.getTime();
        System.out.println("DateStart1 = " + dateStart1);

        cal.set(2018, Calendar.MAY, 5);
        dateEnd1 = cal.getTime();
        System.out.println("DateEnd1 = " + dateEnd1);

        dateStart2 = dateEnd1;
        System.out.println("DateStart2 = " + dateStart2);
        cal.set(2018, Calendar.MAY, 7);
        dateEnd2 = cal.getTime();
        System.out.println("DateEnd2 = " + dateEnd2);

        cal.set(2018, Calendar.MAY, 2);
        dateInsidePeriod1Start = cal.getTime();
        System.out.println("dateInsidePeriod1Start = " + dateInsidePeriod1Start);

        cal.set(2018, Calendar.MAY, 3);
        dateInsidePeriod1End = cal.getTime();
        System.out.println("dateInsidePeriod1End = " + dateInsidePeriod1End);

        // -----------------------------------------------------------------------/
        cal.set(2018, Calendar.APRIL, 28);
        dateBeforePeriod1Start = cal.getTime();
        System.out.println("dateBeforePeriod1Start = " + dateBeforePeriod1Start);

        cal.set(2018, Calendar.MAY, 4);
        dateBeforePeriod1End = cal.getTime();
        System.out.println("dateBeforePeriod1End = " + dateBeforePeriod1End);

        // -----------------------------------------------------------------------/
        cal.set(2018, Calendar.MAY, 3);
        dateAfterPeriod1Start = cal.getTime();
        System.out.println("dateAfterPeriod1Start = " + dateAfterPeriod1Start);

        cal.set(2018, Calendar.MAY, 13);
        dateAfterPeriod1End = cal.getTime();
        System.out.println("dateAfterPeriod1End = " + dateAfterPeriod1End);
    }


    @Test
    public void testIsIntersectionBetweenDatesWhenPeriod2IsInsidePeriod1() {
        assertThat(DateUtils.isIntersectionBetweenDates(dateStart1, dateEnd1, dateInsidePeriod1Start, dateInsidePeriod1End)).isEqualTo(true);
    }

    @Test
    public void testIsIntersectionBetweenDatesWhenPeriod2IsAfterPeriod1() {
        assertThat(DateUtils.isIntersectionBetweenDates(dateStart1, dateEnd1, dateStart2, dateEnd2)).isEqualTo(true);
    }

    @Test
    public void testIsIntersectionBetweenDatesWhenPeriod2IsBeforePeriod1() {
        assertThat(DateUtils.isIntersectionBetweenDates(dateStart2, dateEnd2, dateStart1, dateEnd1)).isEqualTo(true);
    }

    @Test
    public void testIntersectionDaysNumberWhenPeriod2IsAfterPeriod1() {
        assertThat(DateUtils.getIntersectionDaysNumber(dateStart1, dateEnd1, dateStart2, dateEnd2)).isEqualTo(1);
    }

    @Test
    public void testIntersectionDaysNumberWhenPeriod2IsBeforePeriod1() {
        assertThat(DateUtils.getIntersectionDaysNumber(dateStart2, dateEnd2, dateStart1, dateEnd1)).isEqualTo(1);
    }

    @Test
    public void testIntersectionDaysNumberWhenPeriod2IsInsidePeriod1() {
        assertThat(DateUtils.getIntersectionDaysNumber(dateStart1, dateEnd1, dateInsidePeriod1Start, dateInsidePeriod1End)).isEqualTo(2);
    }

    @Test
    public void testIntersectionDaysNumberWhenPeriod2IsBeforePeriod1_1() {
        assertThat(DateUtils.getIntersectionDaysNumber(dateStart1, dateEnd1, dateBeforePeriod1Start, dateBeforePeriod1End)).isEqualTo(4);
    }

    @Test
    public void testIntersectionDaysNumberWhenPeriod2IsAfterPeriod1_1() {
        assertThat(DateUtils.getIntersectionDaysNumber(dateStart1, dateEnd1, dateAfterPeriod1Start, dateAfterPeriod1End)).isEqualTo(3);
    }
}
