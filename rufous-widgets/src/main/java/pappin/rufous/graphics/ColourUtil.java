package pappin.rufous.graphics;

import android.graphics.Color;

import pappin.rufous.helper.ResHelper;


/**
 * Created by bpappin on 2017-01-16.
 */

public class ColourUtil extends ResHelper {
    public static int toColor(String hex) {
        return Color.parseColor(hex);
    }

    public static String toHex(int colour) {
        return String.format("#%06X", (0xFFFFFF & colour));
    }

}
