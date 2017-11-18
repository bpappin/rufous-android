package pappin.rufous.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PercentTest {


    //    @Before
    //    public void setUp() {
    //
    //    }


    @Test
    public void valueOf100() throws Exception {
        int actual = Percent.intValueOfPercent(25, 100);
        assertEquals(25, actual);
    }

    @Test
    public void valueOf20() throws Exception {
        int actual = Percent.intValueOfPercent(25, 20);
        assertEquals(5, actual);
    }

    @Test
    public void valueOf10() throws Exception {
        int actual = Percent.intValueOfPercent(25, 10);
        assertEquals(2, actual);
    }

    @Test
    public void valueOfZero() throws Exception {
        int actual = Percent.intValueOfPercent(0, 10);
        assertEquals(0, actual);
    }

    @Test
    public void percentOfValueDivideByZero() throws Exception {
        int actual = Percent.intPercentOfValue(25, 0);
        assertEquals(0, actual);
    }

    @Test
    public void percentOfValue100() throws Exception {
        int actual = Percent.intPercentOfValue(25, 100);
        assertEquals(25, actual);
    }

    @Test
    public void percentOfValue250() throws Exception {
        int actual = Percent.intPercentOfValue(100, 250);
        assertEquals(40, actual);
    }
}
