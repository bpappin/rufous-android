package pappin.rufous.math;

/**
 * Created by bpappin on 2017-09-08.
 */

public class Percent {
    /**
     * return the value of a percent of a number.
     *
     * @param percentage
     * @param value
     * @return
     */
    public static int intValueOfPercent(int percentage, long value) {
        int k = (int)(value * (percentage / 100.0f));
        return k;
    }

    /**
     * returns the percent of a max for a max max.
     *
     * @param value
     * @param max
     * @return
     */
    public static int intPercentOfValue(long value, long max) {
        if (max == 0) {
            return 0;
        }
        int k = Math.round((value * 100f / max));
        return k;
    }
}
