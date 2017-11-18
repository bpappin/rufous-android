package pappin.rufous.geo;

import android.location.Location;

import com.github.davidmoten.geo.GeoHash;

/**
 * Created by bpappin on 2017-07-11.
 */

public class GeohashUtil {


    public static final int PRECISION_2500_KM = 1;
    public static final int PRECISION_630_KM = 2;
    public static final int PRECISION_78_KM = 3;
    public static final int PRECISION_20_KM = 4;
    public static final int PRECISION_2_4_KM = 5;
    public static final int PRECISION_610_M = 6;
    public static final int PRECISION_76_M = 7;
    public static final int PRECISION_20_M = 8;
    public static final int PRECISION_2_5_M = 9;
    public static final int PRECISION_0_6_M = 10;
    public static final int PRECISION_0_074_M = 11;

    public static final int DEFAULT_PRECISION = PRECISION_76_M;
    public static final int MAX_PRECISION = 12;

    /**
     * <pre>
     * #   km           m
     * 1   ±2500        2500000
     * 2   ±630         630000
     * 3   ±78          78000
     * 4   ±20          20000
     * 5   ±2.4         2400
     * 6   ±0.61        610
     * 7   ±0.076       76
     * 8   ±0.019       20
     * 9   ±0.0024      2.5
     * 10  ±0.00060     0.6
     * 11  ±0.000074    0.074
     * </pre>
     *
     * @param location
     * @param precision
     * @return
     */
    public static String fromLocation(Location location, int precision) {
        return GeoHash.encodeHash(location.getLatitude(), location.getLongitude(), precision);
    }

    public static String fromLocation(Location location) {
        return GeoHash.encodeHash(location.getLatitude(), location.getLongitude());
    }

    /**
     * tests is the given location is bounded by the given hash.
     *
     * @param location
     * @param geoHash
     * @return true if the location is in the hash, false otherwise.
     */
    public static boolean within(Location location, String geoHash) {
        return GeoHash.hashContains(geoHash, location.getLatitude(), location.getLongitude());
    }

    public static boolean within(String geohashPoint, String geohashArea, int precision) {
        return geohashArea.startsWith(geohashPoint.substring(0, precision));
    }
}
