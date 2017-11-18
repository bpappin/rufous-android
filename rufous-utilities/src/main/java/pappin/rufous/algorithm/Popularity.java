package pappin.rufous.algorithm;

/**
 * Created by bpappin on 16-06-27.
 */
public class Popularity {
    public static long calculate(long timeThen, long timeNow) {
        return (timeThen + timeNow) / 2;
    }
}
