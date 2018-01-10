package pappin.rufous.util;

import android.support.annotation.Nullable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

    public static final String TAG = "DateUtil";

    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static String getTimeDifference(Date date) {
        if (date != null) {
            final int MAX_EXPIRATION_TIME = 10000;

            long diff = Math.abs(new Date().getTime() - date.getTime());
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            if (hours > MAX_EXPIRATION_TIME) {
                return "";
            }

            String timeDiffString = "";
            if (hours >= 24) {
                return timeDiffString + days + "d";
            } else if (hours >= 1) {
                return timeDiffString + hours + "h " + minutes % 60 + "m";
            } else {
                return timeDiffString + minutes + "m";
            }
        } else {
            return "";
        }
    }

    public static String addLeadingZero(int number) {

        if (number < 10) {
            return "0" + number;
        } else {
            return String.valueOf(number);
        }
    }

    public static long[] rangeOfDay(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        long start = cal.getTimeInMillis();

        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        long end = cal.getTimeInMillis();
        return new long[]{start, end};
    }

    public static long startOfDay(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return cal.getTimeInMillis();
    }

    public static long endOfDay(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return cal.getTimeInMillis();
    }

    public static long[] rangeOfWeek(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        long start = cal.getTimeInMillis();

        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        long end = cal.getTimeInMillis();
        return new long[]{start, end};
    }


    public static long[] rangeOfMonth(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        long start = cal.getTimeInMillis();

        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        long end = cal.getTimeInMillis();
        return new long[]{start, end};
    }

    public static long[] rangeOfNextWeek(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        cal.roll(Calendar.WEEK_OF_MONTH, 1);
        return rangeOfWeek(cal.getTimeInMillis());
    }

    public static long[] rangeOfLastWeek(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        cal.roll(Calendar.WEEK_OF_MONTH, -1);
        return rangeOfWeek(cal.getTimeInMillis());
    }

    public static long[] rangeOfLastMonth(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        cal.roll(Calendar.MONTH, -1);
        return rangeOfMonth(cal.getTimeInMillis());
    }

    public static long dateInMillisWithoutTime(long millis) {
        final long LENGTH_OF_DAY = 24 * 60 * 60 * 1000;
        return  ((millis) / LENGTH_OF_DAY) * LENGTH_OF_DAY ;
    }
}
