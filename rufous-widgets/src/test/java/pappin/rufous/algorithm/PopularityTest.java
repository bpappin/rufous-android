package pappin.rufous.algorithm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PopularityTest {

    //    @Test
    //    public void testFailPurposely() {
    //        fail("Purposely failing test to check Jenkins build.");
    //    }

    @Test
    public void testWithFixedValuesOf3() {
        long actual = Popularity.calculate(3L, 3L);
        long expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    public void testWithFixedValuesOf5() {
        long actual = Popularity.calculate(5L, 5L);
        long expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    public void testWithMixedValues() {
        long actual = Popularity.calculate(5L, 3L);
        long expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    public void testWithThenAsZero() {
        long actual = Popularity.calculate(0L, 3L);
        long expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void testWithNowAsZero() {
        long actual = Popularity.calculate(5L, 0L);
        long expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    public void testWithBothAsZero() {
        long actual = Popularity.calculate(0L, 0L);
        long expected = 0;
        assertEquals(expected, actual);
    }
}
