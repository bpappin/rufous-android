package pappin.rufous.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import pappin.rufous.graphics.G;


/**
 * Created by bpappin on 16-05-05.
 */
public class ResHelper {
    /**
     * converts a resource drawable into a URI.
     *
     * @param context
     * @param resID
     * @return
     */
    public static Uri resourceToUri(Context context, @DrawableRes int resID) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID));
    }

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
     *
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
