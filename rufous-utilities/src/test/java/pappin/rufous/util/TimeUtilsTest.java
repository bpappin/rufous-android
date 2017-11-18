package pappin.rufous.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TimeUtilsTest {

    private Calendar calendarNow;
    private Calendar calendarNowPlus2;
    private Calendar calendarNowPlus8;

    @Before
    public void setUp() {
        calendarNow = Calendar.getInstance();
        calendarNow.setTimeInMillis(0);
        calendarNow.set(2017, Calendar.AUGUST, 12, 12, 12, 12);

        calendarNowPlus2 = Calendar.getInstance();
        calendarNowPlus2.setTimeInMillis(0);
        calendarNowPlus2.set(2017, Calendar.AUGUST, 14, 12, 12, 12);

        calendarNowPlus8 = Calendar.getInstance();
        calendarNowPlus8.setTimeInMillis(0);
        calendarNowPlus8.set(2017, Calendar.AUGUST, 20, 12, 12, 12);

    }

    @Test
    public void testIsTimePlusSevenAfterThen() throws Exception {
        boolean actual = TimeUtils.isTimeAfter(calendarNow.getTime(), 7, calendarNowPlus2.getTime());
        assertEquals(true, actual);
    }

    @Test
    public void testIsTimePlus8AfterThen() throws Exception {
        boolean actual = TimeUtils.isTimeAfter(calendarNow.getTime(), 7, calendarNowPlus8.getTime());
        assertEquals(false, actual);
    }
}
