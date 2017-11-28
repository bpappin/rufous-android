package pappin.rufous.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import pappin.rufous.graphics.ColourUtil;


/**
 * Created by bpappin on 16-07-29.
 */
public class StyleHelper {
    public static void legacyTint(int color, Drawable drawable) {
        if (drawable != null) {
            // If we don't mutate the drawable, then all drawable's with this id will have a color
            // filter applied to it.
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            //drawable.setAlpha(alpha);
        }
    }

    /**
     * @param context
     * @param tab
     * @param colourResId
     * @return TabLayout.Tab the same tab passed in, sot ath it can work inline with the tabbar add
     * method.
     */
    public static TabLayout.Tab tint(Context context, TabLayout.Tab tab, @ColorRes int colourResId) {
        if (tab != null) {
            int colour = context
                    .getResources()
                    .getColor(colourResId);
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                tab
                        .getIcon()
                        .setTint(colour);
            } else {
                Drawable drawable = tab.getIcon();
                legacyTint(colour, drawable);
            }
        }

        return tab;
    }

    /**
     * http://stackoverflow.com/questions/26780046/menuitem-tinting-on-appcompat-toolbar
     * https://gist.github.com/jaredrummler/ec7dfb73f3235ad8e951
     */
    public static class Menus {

        //        public static void tintAccent(Context context, Menu menu, @IdRes int resId) {
        //            MenuItem lsmenu = menu.findItem(resId);
        //            if (lsmenu != null) {
        //                if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
        //                    lsmenu
        //                            .getIcon()
        //                            .setTint(context
        //                                             .getResources()
        //                                             .getColor(R.color.colorAccent)
        //                            );
        //                } else {
        //                    Drawable drawable = lsmenu.getIcon();
        //                    legacyTint(context
        //                                       .getResources()
        //                                       .getColor(R.color.colorAccent), drawable);
        //                }
        //            }
        //        }

        public static void tintColour(Context context, Menu menu, @IdRes int menuResId, @ColorRes int colourResId) {
            MenuItem lsmenu = menu.findItem(menuResId);
            if (lsmenu != null) {
                int colour = context
                        .getResources()
                        .getColor(colourResId);
                if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                    lsmenu
                            .getIcon()
                            .setTint(colour);
                } else {
                    Drawable drawable = lsmenu.getIcon();
                    legacyTint(colour, drawable);
                }
            }
        }
    }

    public static class Progress {
        public static void tintBar(Context context, ProgressBar progressBar, @ColorRes int colourResId) {
            int colourInt = context
                    .getResources()
                    .getColor(colourResId);
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                progressBar.setProgressTintList(ColorStateList.valueOf(colourInt));
                //                progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(colourInt));
            } else {
                progressBar
                        .getProgressDrawable()
                        .setColorFilter(colourInt, PorterDuff.Mode.SRC_IN);
            }
        }

        public static void tintBarBackground(Context context, ProgressBar progressBar, @ColorRes int colourResId) {
            int colourInt = context
                    .getResources()
                    .getColor(colourResId);
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(colourInt));
            } else {
                // FIXME We can't seem to set the remaining bar colour. Only works for SDK 21+.
                // XXX This is going to take some effort to figure out how to colour the remaining for old devices.
                // progressBar.getIndeterminateDrawable().setColorFilter(colourInt, PorterDuff.Mode.SRC_IN);
                // progressBar.setBackgroundColor(colourInt);
                // progressBar.getBackgroundDrawable().setColorFilter(colourInt, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public static class Text {
        public static void textColour(Context context, TextView view, @ColorRes int colourResId) {
            if (VERSION.SDK_INT >= VERSION_CODES.M) {
                view.setTextColor(context.getColor(colourResId));
            } else {
                view.setTextColor(context
                                          .getResources()
                                          .getColor(colourResId));
            }
        }

        public static void tintColorCompoundDrawables(Context context, TextView textView, String hexColour) {
            tintColorCompoundDrawables(context, textView, ColourUtil.toColor(hexColour));
        }

        public static void tintColorCompoundDrawables(Context context, TextView textView, int colour) {
            for (Drawable drawable : textView.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(colour, PorterDuff.Mode.SRC_IN));
                }
            }
        }
    }

    public static class Image {
        public static void tint(Context context, ImageView view, @ColorRes int colourResId) {
            int tintColour = ContextCompat.getColor(context, colourResId);
            tintColour(context, view, tintColour);
        }

        public static void tintColour(Context context, ImageView view, @ColorInt int tintColour) {
            //            int tintColour = ContextCompat.getColor(context, colourResId);

            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                int[][] states = new int[][]{
                        new int[]{android.R.attr.state_enabled}, // enabled
                        new int[]{-android.R.attr.state_enabled} // disabled
                };
                int[] colors = new int[]{
                        tintColour,
                        ((tintColour & 0x00FFFFFF) | 0x40000000) // make the opacity 25% of the original colour
                };
                ColorStateList colourList = new ColorStateList(states, colors);
                view.setImageTintList(colourList);
            } else {
                view.setColorFilter(tintColour);
            }

        }

        public static void tintColour(Context context, ImageView view, String hexColour) {
            tintColour(context, view, ColourUtil.toColor(hexColour));

        }


    }

    public static class Graphic {
        public static Drawable getTintedDrawableOfColorResId(@NonNull Context context, @NonNull Bitmap inputBitmap, @ColorRes int colorResId) {
            return getTintedDrawable(context, new BitmapDrawable(context.getResources(), inputBitmap), ContextCompat.getColor(context, colorResId));
        }

        public static Drawable getTintedDrawable(@NonNull Context context, @NonNull Bitmap inputBitmap, @ColorInt int color) {
            return getTintedDrawable(context, new BitmapDrawable(context.getResources(), inputBitmap), color);
        }

        public static Drawable getTintedDrawable(@NonNull Context context, @NonNull Drawable inputDrawable, @ColorInt int color) {
            Drawable wrapDrawable = DrawableCompat.wrap(inputDrawable);
            DrawableCompat.setTint(wrapDrawable, color);
            DrawableCompat.setTintMode(wrapDrawable, PorterDuff.Mode.SRC_IN);
            return wrapDrawable;
        }
    }

}
