package pappin.rufous.util;

import android.support.annotation.Nullable;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by bpappin on 2015-11-12.
 */
public class StringUtil {

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(@Nullable CharSequence str) {
        return !isEmpty(str);
    }

    public static String repeat(String s, int level) {
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < level; i++) {
            buff.append(s);
        }
        return buff.toString();
    }

    public static String orString(String value, String replacement) {
        return isNotEmpty(value) ? value : replacement;
    }

    public static String orEmpty(@Nullable String value) {
        return orString(value, "");
    }

    public static String join(List<String> data, String separator) {
        return join(data.toArray(new String[data.size()]), separator);
    }

    public static String join(String[] data, String separator) {
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            buff.append(data[i]);
            if (i < data.length - 1) {
                buff.append(separator);
            }
        }
        return buff.toString();
    }

    public static String orDefault(String value, String alt) {
        if (value == null) {
            return alt;
        }
        return value;
    }

    public static String invert(String value) {
        StringBuilder buffer = new StringBuilder(value);
        buffer.reverse();
        return buffer.toString();
    }

    public static String digitsOnly(String value) {
        return value != null ? value
                .replaceAll("[^\\d.]", "")
                .replace(".", "") : null;
    }

    public static String capWordFirstLetter(String fullname) {
        String result = "";
        StringTokenizer tokenizer = new StringTokenizer(fullname);
        while (tokenizer.hasMoreTokens()) {
            String s2 = tokenizer
                    .nextToken()
                    .toLowerCase();
            if (result.length() == 0) {
                result += s2
                        .substring(0, 1)
                        .toUpperCase() + s2.substring(1);
            } else {
                result += " " + s2
                        .substring(0, 1)
                        .toUpperCase() + s2.substring(1);
            }
        }

        return result;
    }

    public static String numberOnly(String value) {
        return value != null ? value
                .replaceAll("[^-?\\d.]", "") : null;
    }
}
