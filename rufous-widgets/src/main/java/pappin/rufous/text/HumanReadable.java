package pappin.rufous.text;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by bpappin on 16-08-12.
 */
public class HumanReadable {
    public static String firstName(String displayName) {
        // XXX This doesn't handle other locales that might be RTL.
        if (displayName == null) {
            return null;
        }

        String[] names = displayName.split("\\s+", 2);
        if (names.length > 0) {
            return names[0];
        }

        return displayName;
    }

    public static String lastNames(String displayName) {
        if (displayName == null) {
            return null;
        }
        String[] names = displayName.split("\\s+", 2);
        //        System.out.println(names.length);
        for (String n : names) {
            System.out.println(n);
        }
        if (names.length > 1) {
            return names[1];
        }

        return "";
    }

    public static CharSequence relativeTime(long timestamp) {
        return DateUtils.getRelativeTimeSpanString(timestamp);
    }

    public static String elapsedTime(long seconds) {
        return DateUtils.formatElapsedTime(seconds);
    }


    //    /**
    //     * Return given duration in a human-friendly format. For example, "4
    //     * minutes" or "1 second". Returns only largest meaningful unit of time,
    //     * from seconds up to hours.
    //     */
    //    public static String duration(long millis) {
    //        // FIXME the code is broken in the the plurals are not properly loaded.
    //        // FIXME since I didnt need it right away, i have commented out the code,
    //        // FIXME but its a useful construct, so I'll com back to it later, when i need it next.
    //        final Resources res = Resources.getSystem();
    //        if (millis >= DateUtils.HOUR_IN_MILLIS) {
    //            final int hours = (int)((millis + 1800000) / DateUtils.HOUR_IN_MILLIS);
    //            return res.getQuantityString(
    //                    R.plurals.duration_hours, hours, hours);
    //        } else if (millis >= DateUtils.MINUTE_IN_MILLIS) {
    //            final int minutes = (int)((millis + 30000) / DateUtils.MINUTE_IN_MILLIS);
    //            return res.getQuantityString(
    //                    R.plurals.duration_minutes, minutes, minutes);
    //        } else {
    //            final int seconds = (int)((millis + 500) / DateUtils.SECOND_IN_MILLIS);
    //            return res.getQuantityString(
    //                    R.plurals.duration_seconds, seconds, seconds);
    //        }
    //    }

    //    public static String formatDurationTimeFromMillis(long millis) {
    //        return elapsedTime(TimeUnit.MILLISECONDS.toSeconds(millis));
    //    }

    public static int hoursOfElapsedTime(long millis) {
        long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        long hours = 0;
        if (elapsedSeconds >= 3600) {
            hours = elapsedSeconds / 3600;
        }
        return (int) hours;
    }

    /**
     * same as minutesOfElapsedTime(millis, false)
     *
     * @param millis
     * @return the number of minutes in the elapsed time.
     */
    public static int minutesOfElapsedTime(long millis) {
        return minutesOfElapsedTime(millis, false);
    }

    /**
     * This only returns the minutes portion of an elapsed time.
     *
     * @param millis
     * @param decimal should the minutes be expressed as a fraction of an hour, or as literal
     *                minutes?
     * @return the number of minutes in the elapsed time.
     */
    public static int minutesOfElapsedTime(long millis, boolean decimal) {
        long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        // Break the elapsed seconds into hours, minutes.
        long hours = 0;
        long minutes = 0;
        if (elapsedSeconds >= 3600) {
            hours = elapsedSeconds / 3600;
        }
        if (elapsedSeconds > 60) {
            minutes = elapsedSeconds / 60;
            if (minutes >= 100) {
                minutes = (int) (minutes / 10);
            }
        }
        if (minutes >= 60) {
            // XXX if we have 60 minutes, then it should com out as an hour.
            return 0;
        } else {
            if (decimal) {
                System.out.println("minutes: " + minutes);
                double decMins = minutes / 60.0;
                System.out.println("decMins: " + decMins);
                int output = (int) (decMins * 100);
                System.out.println("output: " + output);
                return output;
            }
        }
        return (int) minutes;
    }

    public static double elapsedTimeAsDecimalFromMillis(long millis) {

        long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        // Break the elapsed seconds into hours, minutes.
        long hours = 0;
        long minutes = 0;
        //        SplitTime st = new SplitTime();
        if (elapsedSeconds >= 3600) {
            hours = elapsedSeconds / 3600;
        }
        if (elapsedSeconds >= 60) {
            minutes = (long) Math.floor((elapsedSeconds / 60) * 100) / 100;
        }

        //        new BigDecimal(hours + "." + minutes);
        return Double.parseDouble(hours + "." + minutes);

    }

    public static String formatDurationTimeDecimalFromMillis(long millis) {
        return formatDurationTimeFromMillis(null, millis, true);
    }

    /**
     * Formats an elapsed time in the form "H" or "H:MM"
     * for display on the call-in-progress screen.
     *
     * @param millis the elapsed time in milliseconds.
     */
    public static String formatDurationTimeFromMillis(long millis) {
        return formatDurationTimeFromMillis(null, millis, false);
    }

    public static String formatDurationTimeFromMillis(long millis, boolean full) {
        return formatDurationTimeFromMillis(null, millis, false, true);
    }

    public static String formatDurationTimeFromMillis(StringBuilder recycle, long millis, boolean decimal) {
        return formatDurationTimeFromMillis(recycle, millis, decimal, false);
    }

    /**
     * Formats an elapsed time in a format like "H" or "H:MM" (using a form
     * suited to the current locale), similar to that used on the call-in-progress
     * screen.
     *
     * @param recycle {@link StringBuilder} to recycle, or null to use a temporary one.
     * @param millis  the elapsed time in milliseconds.
     * @param full    should zero minutes be stripped off or not?
     */
    public static String formatDurationTimeFromMillis(StringBuilder recycle, long millis, boolean decimal, boolean full) {

        //        long[] st = getSplitTime(millis);
        long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        // Break the elapsed seconds into hours, minutes, and seconds.
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        if (elapsedSeconds >= 3600) {
            hours = elapsedSeconds / 3600;
            elapsedSeconds -= hours * 3600;
        }
        if (elapsedSeconds >= 60) {
            minutes = elapsedSeconds / 60;
            elapsedSeconds -= minutes * 60;
        }
        seconds = elapsedSeconds;

        // Create a StringBuilder if we weren't given one to recycle.
        StringBuilder sb = recycle;
        if (sb == null) {
            sb = new StringBuilder(8);
        } else {
            sb.setLength(0);
        }

        Formatter f = new Formatter(sb, Locale.getDefault());
        if (minutes > 0 || full) {
            if (decimal) {
                double decMins = minutes / 60.0;
                int decMinsX = (int) (decMins * 100);
                return f
                        .format("%1$d.%2$02d", hours, decMinsX)
                        .toString();
            } else {
                return f
                        .format("%1$d:%2$02d", hours, minutes)
                        .toString();
            }
        } else {
            return f
                    .format("%1$d", hours)
                    .toString();
        }
    }


    @NonNull
    private static long[] getSplitTime(long millis) {
        // FIXME For some inexplicable reason, this refactoring doesn't work. I'm putting it asde until i can come back to it. - JFBP
        long[] out = new long[3];

        long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        // Break the elapsed seconds into hours, minutes, and seconds.
        long hours = 0;
        //        long minutes = 0;
        //        long seconds = 0;
        if (elapsedSeconds >= 3600) {
            out[0] = elapsedSeconds / 3600;
            elapsedSeconds -= hours * 3600;
        }
        if (elapsedSeconds >= 60) {
            out[1] = elapsedSeconds / 60;
            elapsedSeconds -= out[1] * 60;
        }
        out[2] = elapsedSeconds;
        return out;
    }

    /**
     * Formatting pattern: EEE MM/dd
     *
     * @param seconds
     * @return
     */
    public static CharSequence dueDate(Long seconds) {
        if (seconds != null) {
            // XXX it would be better to use one of the standard formats here.
            // XXX this is an expensive format.
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd");
            return sdf.format(new Date(seconds));
        }

        return null;
    }


    public static CharSequence formatDisplay(BigDecimal value, int places, String displayUnits) {
        return formatDisplay(value, places, false) + " " + displayUnits;
    }

//    public static CharSequence formatDisplay(BigDecimal value, int places, String legacyUnits) {
//        return formatDisplay(value, places, false);
//    }

    public static CharSequence formatDisplay(BigDecimal value, int places, boolean alwaysFractions) {
        if (value != null) {

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(places);
            df.setMinimumFractionDigits(0);
            if (alwaysFractions) {
                df.setMinimumFractionDigits(places);
            }
            df.setGroupingUsed(false);

            return df.format(value);
        }

        return null;
    }


    public static String prefixNumbersOnly(String prefix, String code) {

        if (code == null) {
            return code;
        }

        try {
            Integer.parseInt(code);
        } catch (NumberFormatException nfe) {
            return code;
        }
        return prefix + code;
    }
}
