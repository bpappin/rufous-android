package pappin.rufous.util;

import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * A companion to DateUtils in Android.
 * Created by bpappin on 2017-09-27.
 */
public class TimeUtils {

    public static boolean isYesterday(Date d) {
        //return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
        return isPastDay(1, d);
    }

    public static boolean isPastDay(int days, Date d) {
        return DateUtils.isToday(d.getTime() + (DateUtils.DAY_IN_MILLIS * days));
    }

    public static boolean isTomorrow(Date d) {
        //return DateUtils.isToday(d.getTime() - DateUtils.DAY_IN_MILLIS);
        return isFutureDay(1, d);
    }

    public static boolean isFutureDay(int days, Date d) {
        return DateUtils.isToday(d.getTime() - (DateUtils.DAY_IN_MILLIS * days));
    }

    /**
     * Tests if the first date plus "days" days, is after the second date.
     *
     * @param now  the date now
     * @param days the number of days to add
     * @param then the date to compare to.
     * @return true if now + days > then, false otherwise.
     */
    public static boolean isTimeAfter(Date now, int days, Date then) {
        return isTimeAfter(now, Calendar.DAY_OF_MONTH, days, then);
    }

    /**
     * Tests if the first date plus "value" field, is after the second date.
     *
     * @param now
     * @param field
     * @param value
     * @param then
     * @return true if now + value > then, false otherwise.
     */
    public static boolean isTimeAfter(Date now, int field, int value, Date then) {
        Calendar calThen = Calendar.getInstance();
        calThen.setTime(then);


        Calendar calNow = Calendar.getInstance();
        calNow.setTime(now);
        calNow.add(field, value);

        return calNow.after(calThen);
    }

    // XXX the long way is unit testable, where the android specific checks are not.
    //    public static boolean isYesterday(long date) {
    //        Calendar now = Calendar.getInstance();
    //        Calendar cdate = Calendar.getInstance();
    //        cdate.setTimeInMillis(date);
    //
    //        now.add(Calendar.DATE, -1);
    //
    //        return now.get(Calendar.YEAR) == cdate.get(Calendar.YEAR)
    //                && now.get(Calendar.MONTH) == cdate.get(Calendar.MONTH)
    //                && now.get(Calendar.DATE) == cdate.get(Calendar.DATE);
    //    }
    //
    //    public static boolean isTomorrow(long date) {
    //        Calendar now = Calendar.getInstance();
    //        Calendar cdate = Calendar.getInstance();
    //        cdate.setTimeInMillis(date);
    //
    //        now.add(Calendar.DATE, 1);
    //
    //        return now.get(Calendar.YEAR) == cdate.get(Calendar.YEAR)
    //                && now.get(Calendar.MONTH) == cdate.get(Calendar.MONTH)
    //                && now.get(Calendar.DATE) == cdate.get(Calendar.DATE);
    //    }
}
