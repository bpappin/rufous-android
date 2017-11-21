package pappin.rufous.geo;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GeohashUtilTest {

    private FakeLocation fakeLocationBarrie = new FakeLocation("");
    private FakeLocation fakeLocationAllendaleGoStation = new FakeLocation("");
    private FakeLocation fakeLocationToronto = new FakeLocation("");
    private String expectedPrecisionMaximumBarrie = "dpzkd8g68xw2";
    private String expectedPrecisionMaximumAllendale = "dpzk6wj4k0q0";
    private String expectedPrecisionMaximumToronto = "dpz8byw97wpu";
    private String expectedPrecision78km = "dpz";
    private String expectedPrecision20km = "dpzk";
    private String expectedPrecision76m = "dpzkd8g";


    @Before
    public void setUp() {
        fakeLocationBarrie.testLatitude = 44.389355D;
        fakeLocationBarrie.testLongitude = -79.690331D;

        fakeLocationToronto.testLatitude = 43.761539D;
        fakeLocationToronto.testLongitude = -79.411079D;

        fakeLocationAllendaleGoStation.testLatitude = 44.374167D;
        fakeLocationAllendaleGoStation.testLongitude = -79.687778D;
    }

    @Test
    public void encodeLatLogAllendale() throws Exception {
        String actual = GeohashUtil.fromLocation(fakeLocationAllendaleGoStation);
        assertEquals(expectedPrecisionMaximumAllendale, actual);
    }

    @Test
    public void encodeLatLogBarrie() throws Exception {
        String actual = GeohashUtil.fromLocation(fakeLocationBarrie);
        assertEquals(expectedPrecisionMaximumBarrie, actual);
    }

    @Test
    public void encodeLatLogPrecisionFour() throws Exception {
        String actual = GeohashUtil.fromLocation(fakeLocationBarrie, GeohashUtil.PRECISION_20_KM);
        assertEquals(expectedPrecision20km, actual);
    }

    @Test
    public void encodeLatLogPrecisionSeven() throws Exception {
        String actual = GeohashUtil.fromLocation(fakeLocationBarrie, GeohashUtil.PRECISION_76_M);
        assertEquals(expectedPrecision76m, actual);
    }

    @Test
    public void encodeLatLogToronto() throws Exception {
        String actual = GeohashUtil.fromLocation(fakeLocationToronto);
        assertEquals(expectedPrecisionMaximumToronto, actual);
    }

    @Test
    public void torontoWithin78km() throws Exception {
        boolean actual = GeohashUtil.within(fakeLocationToronto, expectedPrecision78km);
        assertTrue("Expected Toronto to be within 78km of Barrie.", actual);
    }

    @Test
    public void torontoWithin78kmOfBarrieUsingPrecision() throws Exception {
        boolean actual = GeohashUtil.within(expectedPrecisionMaximumToronto, expectedPrecisionMaximumBarrie, GeohashUtil.PRECISION_78_KM);
        assertTrue("Expected Toronto to be within 78km of Barrie.", actual);
    }

    @Test
    public void allendaleWithin20kmOfBarrie() throws Exception {
        boolean actual = GeohashUtil.within(expectedPrecisionMaximumAllendale, expectedPrecisionMaximumBarrie, GeohashUtil.PRECISION_20_KM);
        assertTrue("Expected Allendale to be within 20km of Barrie.", actual);
    }

    @Test
    public void torontoNotWithin20kmOfBarrie() throws Exception {
        boolean actual = GeohashUtil.within(expectedPrecisionMaximumToronto, expectedPrecisionMaximumBarrie, GeohashUtil.PRECISION_20_KM);
        assertFalse("Expected Toronto to not be within 20km of Barrie.", actual);
    }


    class FakeLocation extends Location {

        double testLatitude;
        double testLongitude;

        public FakeLocation(String provider) {
            super(provider);
        }

        public FakeLocation(Location l) {
            super(l);
        }

        public double getLatitude() {
            return testLatitude;
        }


        public double getLongitude() {
            return testLongitude;
        }
    }
}
