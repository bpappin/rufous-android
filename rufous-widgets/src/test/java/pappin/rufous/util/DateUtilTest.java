package pappin.rufous.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static junit.framework.Assert.assertEquals;

/**
 * Note: This test spans the daylight savings time change in the fall.
 */
public class DateUtilTest {

    private static final String TEST_TODAY_START_STR = "Thu Nov 09 00:00:00 EST 2017";
    private static final String TEST_TODAY_END_STR = "Thu Nov 09 23:59:59 EST 2017";

    private static final long TEST_EST_TODAY_START = 1510203600000L;
    private static final long TEST_EST_TODAY_END = 1510289999000L;

    private static final long TEST_EST_WEEK_START = 1509854400000L;
    private static final long TEST_EDT_WEEK_START = 1509858000000L;

    private static final long TEST_EST_WEEK_END = 1510462799000L;

    //    private static final long TEST_EST_MONTH_START = 1509508800000L;
    private static final long TEST_EDT_MONTH_START = 1509512400000L;

    private static final long TEST_EST_MONTH_END = 1512104399000L;

    private static final long TEST_EST_NEXT_WEEK_START = 1510462800000L;
    private static final long TEST_EST_NEXT_WEEK_END = 1511067599000L;

    //    private static final long TEST_EST_LAST_WEEK_START = 1509249600000L;
    private static final long TEST_EDT_LAST_WEEK_START = 1509253200000L;
    //    private static final long TEST_EST_LAST_WEEK_END = 1509854399000L;
    private static final long TEST_EDT_LAST_WEEK_END = 1509857999000L;


    //    private static final long TEST_EST_LAST_MONTH_START = 1506830400000L;
    private static final long TEST_EDT_LAST_MONTH_START = 1506834000000L;

    //    private static final long TEST_EST_LAST_MONTH_END = 1509508799000L;
    private static final long TEST_EDT_LAST_MONTH_END = 1509512399000L;


    private static Date TEST_TODAY;
    private Calendar calendar;
    private TimeZone systemTimezone;


    @Before
    public void setUp() {
        systemTimezone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));

        //        TimeZone timeZone = TimeZone.getTimeZone("EST");
        calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(0);
        calendar.set(2017, 10, 9, 13, 43, 6);

        TEST_TODAY = calendar.getTime();
    }

    @After
    public void tearDown() {
        TimeZone.setDefault(systemTimezone);
    }


    @Test
    public void dateInMillisNotTimeTest() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0); // reset
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 15);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long ymdOnly = cal.getTimeInMillis();

        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 5);
        cal.set(Calendar.SECOND, 32);
        cal.set(Calendar.MILLISECOND, 9);
        long fullDate = cal.getTimeInMillis();

        long actual = DateUtil.dateInMillisWithoutTime(fullDate);

        // XXX Verification
//        System.out.println(ymdOnly);
//        System.out.println(new Date(ymdOnly).toGMTString());
//        System.out.println(fullDate);
//        System.out.println(new Date(fullDate).toGMTString());
//        System.out.println(actual);
//        System.out.println(new Date(actual).toGMTString());

        assertEquals(ymdOnly, actual);

    }

    @Test
    public void testGetStartOfDay() {
        long actual = DateUtil.startOfDay(TEST_TODAY.getTime());
        assertEquals(TEST_EST_TODAY_START, actual);
    }

    @Test
    public void testGetEndOfDay() {
        long actual = DateUtil.endOfDay(TEST_TODAY.getTime());
        assertEquals(TEST_EST_TODAY_END, actual);
    }

    @Test
    public void testGetRangeOfDay() {
        long[] actual = DateUtil.rangeOfDay(TEST_TODAY.getTime());
        assertEquals(TEST_EST_TODAY_START, actual[0]);
        assertEquals(TEST_EST_TODAY_END, actual[1]);
    }

    @Test
    public void testGetRangeOfWeek() {
        long[] actual = DateUtil.rangeOfWeek(TEST_TODAY.getTime());
        assertEquals(TEST_EDT_WEEK_START, actual[0]);
        assertEquals(TEST_EST_WEEK_END, actual[1]);
    }

    @Test
    public void testGetRangeOfMonth() {
        long[] actual = DateUtil.rangeOfMonth(TEST_TODAY.getTime());
        assertEquals(TEST_EDT_MONTH_START, actual[0]);
        assertEquals(TEST_EST_MONTH_END, actual[1]);
    }

    @Test
    public void testGetRangeOfNextWeek() {
        long[] actual = DateUtil.rangeOfNextWeek(TEST_TODAY.getTime());
        assertEquals(TEST_EST_NEXT_WEEK_START, actual[0]);
        assertEquals(TEST_EST_NEXT_WEEK_END, actual[1]);
    }

    @Test
    public void testGetRangeOfLastWeek() {
        long[] actual = DateUtil.rangeOfLastWeek(TEST_TODAY.getTime());
        assertEquals(TEST_EDT_LAST_WEEK_START, actual[0]);
        assertEquals(TEST_EDT_LAST_WEEK_END, actual[1]);
    }

    @Test
    public void testGetRangeOfLastMonth() {
        long[] actual = DateUtil.rangeOfLastMonth(TEST_TODAY.getTime());
        assertEquals(TEST_EDT_LAST_MONTH_START, actual[0]);
        assertEquals(TEST_EDT_LAST_MONTH_END, actual[1]);
    }
}
