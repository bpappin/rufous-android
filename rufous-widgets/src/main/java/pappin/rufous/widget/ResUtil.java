package pappin.rufous.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import pappin.rufous.graphics.G;


/**
 * Created by bpappin on 16-05-05.
 */
public class ResUtil {

    /**
     * Converts a colour resource into an integer colour.
     *
     * @param context
     * @param resId
     * @return
     */
    public static int toColour(Context context, @ColorRes int resId) {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            return context
                    .getResources()
                    .getColor(resId, null);
        } else {
            return context
                    .getResources()
                    .getColor(resId);
        }
    }

    /**
     * Converts a colour resoruce into its hex representation.
     * @param context
     * @param resId
     * @return
     */
    public static String toColourHex(Context context, @ColorRes int resId) {
        int intColour = toColour(context, resId);
        String hexColor = String.format("#%08X", (0xFFFFFFFF & intColour));
        return hexColor;
    }

    /**
     *
     * @param context
     * @param resId
     * @return
     */
    @Deprecated
    public static Bitmap toBitmap(Context context, @DrawableRes int resId) {
        Bitmap bitmap = null;
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            bitmap = G.drawableToBitmap(context
                                                .getResources()
                                                .getDrawable(resId, null));
        } else {
            bitmap = G.drawableToBitmap(context
                                                .getResources()
                                                .getDrawable(resId));
        }
        return bitmap;
    }
}
