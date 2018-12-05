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

    /**
     * Return the input string, or an empty string if the input is null.
     *
     * @param value
     * @return
     */
    public static String orEmpty(@Nullable String value) {
        return orString(value, "");
    }

    public static String join(List<String> data, String separator) {
        return join(data.toArray(new String[data.size()]), separator);
    }

    public static String join(String[] data, String separator) {
        if (data == null) return "";

        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String dataValue = data[i];
            if (dataValue != null && !isDataASeparator(dataValue, separator)) {
                buff.append(dataValue);
                if (i < data.length - 1 && lastCharacterNotSeparator(dataValue, separator)) {
                    buff.append(separator);
                }
            }
        }
        return buff.toString();
    }

    private static boolean lastCharacterNotSeparator(String dataValue, String separator) {
       if (dataValue.isEmpty()) return false;
       String lastChar = String.valueOf(
               dataValue.charAt(dataValue.length()-1)
       );
       return !(lastChar.equals(separator));
    }

    private static boolean isDataASeparator(String dataValue, String separator) {
        String trimmedData = dataValue.trim();
        return trimmedData.startsWith(separator) && trimmedData.endsWith(separator);
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

    /**
     * <p>Abbreviates a String using ellipses. This will turn
     * "Now is the time for all good men" into "Now is the time for..."</p>
     * <p>
     * <p>Specifically:</p>
     * <ul>
     * <li>If the number of characters in {@code str} is less than or equal to
     * {@code maxWidth}, return {@code str}.</li>
     * <li>Else abbreviate it to {@code (substring(str, 0, max-3) + "...")}.</li>
     * <li>If {@code maxWidth} is less than {@code 4}, throw an
     * {@code IllegalArgumentException}.</li>
     * <li>In no case will it return a String of length greater than
     * {@code maxWidth}.</li>
     * </ul>
     * <p>
     * <pre>
     * StringUtils.abbreviate(null, *)      = null
     * StringUtils.abbreviate("", 4)        = ""
     * StringUtils.abbreviate("abcdefg", 6) = "abc..."
     * StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
     * StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
     * StringUtils.abbreviate("abcdefg", 4) = "a..."
     * StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
     * </pre>
     * <p>
     * <p>
     * Ported from org.apache.commons.lang3.StringUtils which has the following Apache license:
     * <p>
     * <pre>
     * Licensed to the Apache Software Foundation (ASF) under one or more
     * contributor license agreements.  See the NOTICE file distributed with
     * this work for additional information regarding copyright ownership.
     * The ASF licenses this file to You under the Apache License, Version 2.0
     * (the "License"); you may not use this file except in compliance with
     * the License.  You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     *
     * </pre>
     *
     * @param str      the String to check, may be null
     * @param maxWidth maximum length of result String, must be at least 4
     * @return abbreviated String, {@code null} if null String input
     * @throws IllegalArgumentException if the width is too small
     * @since 2.0
     */
    public static String abbreviate(final String str, final int maxWidth) {
        final String defaultAbbrevMarker = "...";
        return abbreviate(str, defaultAbbrevMarker, 0, maxWidth);
    }

    /**
     * <p>Abbreviates a String using a given replacement marker. This will turn
     * "Now is the time for all good men" into "...is the time for..." if "..." was defined
     * as the replacement marker.</p>
     * <p>
     * <p>Works like {@code abbreviate(String, String, int)}, but allows you to specify
     * a "left edge" offset.  Note that this left edge is not necessarily going to
     * be the leftmost character in the result, or the first character following the
     * replacement marker, but it will appear somewhere in the result.
     * <p>
     * <p>In no case will it return a String of length greater than {@code maxWidth}.</p>
     * <p>
     * <pre>
     * StringUtils.abbreviate(null, null, *, *)                 = null
     * StringUtils.abbreviate("abcdefghijklmno", null, *, *)    = "abcdefghijklmno"
     * StringUtils.abbreviate("", "...", 0, 4)                  = ""
     * StringUtils.abbreviate("abcdefghijklmno", "---", -1, 10) = "abcdefg---"
     * StringUtils.abbreviate("abcdefghijklmno", ",", 0, 10)    = "abcdefghi,"
     * StringUtils.abbreviate("abcdefghijklmno", ",", 1, 10)    = "abcdefghi,"
     * StringUtils.abbreviate("abcdefghijklmno", ",", 2, 10)    = "abcdefghi,"
     * StringUtils.abbreviate("abcdefghijklmno", "::", 4, 10)   = "::efghij::"
     * StringUtils.abbreviate("abcdefghijklmno", "...", 6, 10)  = "...ghij..."
     * StringUtils.abbreviate("abcdefghijklmno", "*", 9, 10)    = "*ghijklmno"
     * StringUtils.abbreviate("abcdefghijklmno", "'", 10, 10)   = "'ghijklmno"
     * StringUtils.abbreviate("abcdefghijklmno", "!", 12, 10)   = "!ghijklmno"
     * StringUtils.abbreviate("abcdefghij", "abra", 0, 4)       = IllegalArgumentException
     * StringUtils.abbreviate("abcdefghij", "...", 5, 6)        = IllegalArgumentException
     * </pre>
     * <p>
     * <p>
     * Ported from org.apache.commons.lang3.StringUtils which has the following Apache license:
     * <p>
     * <pre>
     * Licensed to the Apache Software Foundation (ASF) under one or more
     * contributor license agreements.  See the NOTICE file distributed with
     * this work for additional information regarding copyright ownership.
     * The ASF licenses this file to You under the Apache License, Version 2.0
     * (the "License"); you may not use this file except in compliance with
     * the License.  You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     *
     * </pre>
     *
     * @param str          the String to check, may be null
     * @param abbrevMarker the String used as replacement marker
     * @param offset       left edge of source String
     * @param maxWidth     maximum length of result String, must be at least 4
     * @return abbreviated String, {@code null} if null String input
     * @throws IllegalArgumentException if the width is too small
     * @since 3.6
     */
    public static String abbreviate(final String str, final String abbrevMarker, int offset, final int maxWidth) {
        if (isEmpty(str) || isEmpty(abbrevMarker)) {
            return str;
        }

        final int abbrevMarkerLength = abbrevMarker.length();
        final int minAbbrevWidth = abbrevMarkerLength + 1;
        final int minAbbrevWidthOffset = abbrevMarkerLength + abbrevMarkerLength + 1;

        if (maxWidth < minAbbrevWidth) {
            throw new IllegalArgumentException(String.format("Minimum abbreviation width is %d", minAbbrevWidth));
        }
        if (str.length() <= maxWidth) {
            return str;
        }
        if (offset > str.length()) {
            offset = str.length();
        }
        if (str.length() - offset < maxWidth - abbrevMarkerLength) {
            offset = str.length() - (maxWidth - abbrevMarkerLength);
        }
        if (offset <= abbrevMarkerLength + 1) {
            return str.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker;
        }
        if (maxWidth < minAbbrevWidthOffset) {
            throw new IllegalArgumentException(String.format("Minimum abbreviation width with offset is %d", minAbbrevWidthOffset));
        }
        if (offset + maxWidth - abbrevMarkerLength < str.length()) {
            return abbrevMarker + abbreviate(str.substring(offset), abbrevMarker, maxWidth - abbrevMarkerLength);
        }
        return abbrevMarker + str.substring(str.length() - (maxWidth - abbrevMarkerLength));
    }

    /**
     * <p>Abbreviates a String using another given String as replacement marker. This will turn "Now
     * is the time for all good men" into "Now is the time for..." if "..." was defined as the
     * replacement marker.</p>
     * <p>
     * <p>Specifically:</p> <ul> <li>If the number of characters in {@code str} is less than or
     * equal to {@code maxWidth}, return {@code str}.</li> <li>Else abbreviate it to {@code
     * (substring(str, 0, max-abbrevMarker.length) + abbrevMarker)}.</li> <li>If {@code maxWidth} is
     * less than {@code abbrevMarker.length + 1}, throw an {@code IllegalArgumentException}.</li>
     * <li>In no case will it return a String of length greater than {@code maxWidth}.</li> </ul>
     * <p>
     * <pre>
     * StringUtils.abbreviate(null, "...", *)      = null
     * StringUtils.abbreviate("abcdefg", null, *)  = "abcdefg"
     * StringUtils.abbreviate("", "...", 4)        = ""
     * StringUtils.abbreviate("abcdefg", ".", 5)   = "abcd."
     * StringUtils.abbreviate("abcdefg", ".", 7)   = "abcdefg"
     * StringUtils.abbreviate("abcdefg", ".", 8)   = "abcdefg"
     * StringUtils.abbreviate("abcdefg", "..", 4)  = "ab.."
     * StringUtils.abbreviate("abcdefg", "..", 3)  = "a.."
     * StringUtils.abbreviate("abcdefg", "..", 2)  = IllegalArgumentException
     * StringUtils.abbreviate("abcdefg", "...", 3) = IllegalArgumentException
     * </pre>
     * <p>
     * <p>
     * Ported from org.apache.commons.lang3.StringUtils which has the following Apache license:
     * <p>
     * <pre>
     * Licensed to the Apache Software Foundation (ASF) under one or more
     * contributor license agreements.  See the NOTICE file distributed with
     * this work for additional information regarding copyright ownership.
     * The ASF licenses this file to You under the Apache License, Version 2.0
     * (the "License"); you may not use this file except in compliance with
     * the License.  You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     *
     * </pre>
     *
     * @param str          the String to check, may be null
     * @param abbrevMarker the String used as replacement marker
     * @param maxWidth     maximum length of result String, must be at least {@code
     *                     abbrevMarker.length + 1}
     * @return abbreviated String, {@code null} if null String input
     * @throws IllegalArgumentException if the width is too small
     * @since 3.6
     */
    public static String abbreviate(final String str, final String abbrevMarker, final int maxWidth) {
        return abbreviate(str, abbrevMarker, 0, maxWidth);
    }
}
