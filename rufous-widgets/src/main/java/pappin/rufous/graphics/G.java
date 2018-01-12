package pappin.rufous.graphics;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pappin.rufous.R;


/**
 * Created by bpappin on 16-04-21.
 */
public class G {

    private static final String TAG = "G";

    public static ColorStateList getColorStateList(int colour) {
        //        int[][] states = new int[][]{new int[]{}};
        //        int[] colors = new int[]{colour};
        //        return new ColorStateList(states, colors);

        return ColorStateList.valueOf(colour);
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap
                    .createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void tint(ImageView view, int colour) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setImageTintList(getColorStateList(colour));
        } else {
            view.setColorFilter(colour, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static void tint(ImageView view, int colour, int alpha) {
        Drawable drawable = view.getDrawable();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(colour, PorterDuff.Mode.SRC_ATOP);
            drawable.setAlpha(alpha);
        }
    }

    public static void disabledTint(Button view) {
        if (view.isEnabled()) {
            view
                    .getBackground()
                    .setColorFilter(null);
            //view.getForeground().setColorFilter(null);
        } else {
            view
                    .getBackground()
                    .setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        }
    }

    public static void tint(MenuItem menu, int colour, int alpha) {
        Drawable drawable = menu.getIcon();
        if (drawable != null) {
            // If we don't mutate the drawable, then all drawable's with this id will have a color
            // filter applied to it.
            drawable.mutate();
            drawable.setColorFilter(colour, PorterDuff.Mode.SRC_ATOP);
            drawable.setAlpha(alpha);
        }
    }

    public static void tintDrawable(TextView view, int colour) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setCompoundDrawableTintMode(PorterDuff.Mode.SRC_ATOP);
            view.setCompoundDrawableTintList(getColorStateList(colour));
        } else {
            Drawable[] drawables = view.getCompoundDrawables();
            for (Drawable d : drawables) {
                if (d != null) {
                    tintDrawable(d, colour, 0xFF);
                }
            }
        }
    }

    public static void tintDrawable(Drawable drawable, int colour, int alpha) {
        if (drawable != null) {
            // If we don't mutate the drawable, then all drawable's with this id will have a color
            // filter applied to it.
            drawable.mutate();
            drawable.setColorFilter(colour, PorterDuff.Mode.SRC_ATOP);
            drawable.setAlpha(alpha);
        }
    }

    public static interface OnColourPaletteCallback {
        void onPaletteCreated(Palette palette);
    }

    public static void colourPalette(Context context, @DrawableRes int drawableId, OnColourPaletteCallback callback) {
        Bitmap demoImage = BitmapFactory.decodeResource(context.getResources(), drawableId);
        colourPalette(context, demoImage, callback);
    }

    public static void colourPalette(Context context, Bitmap bitmap, final OnColourPaletteCallback callback) {
        //holder.banner.setImageBitmap(demoImage);
        Palette
                .from(bitmap)
                .maximumColorCount(24)
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        callback.onPaletteCreated(palette);
                        // Get the "vibrant" color swatch based on the bitmap
                        //Palette.Swatch vibrant = palette.getLightMutedSwatch();
                        //if (vibrant != null) {
                        //    // Set the background color of a layout based on the vibrant color
                        //    holder.mBarView.setBackgroundColor(vibrant.getRgb());
                        //    // Update the title TextView with the proper text color
                        //    //                    holder.mTextView.setTextColor(vibrant.getTitleTextColor());
                        //    callback.onRelativeColourFound(vibrant.);
                        //} else {
                        //    // TODO: Fix this... urgh
                        //    holder.mBarView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark, null));
                        //}
                    }
                });
    }

    /**
     * Picks a colour from a list of colours based on the input text.
     *
     * @param key The key used to pick the tile colour
     * @return A new or previously chosen color for <code>key</code>.
     */
    public static int pickColor(Context context, String key) {
        return pickColor(context, R.array.rufous_letter_tile_colors, key);
    }

    /**
     * Picks a colour from a list of colours based on the input text.
     * <p>
     * Specify the colour list resource as a list of strings:
     * <pre>
     * &lt;array name="tile_letter_colors"&gt;
     *      &lt;item&gt;#f16364&lt;/item&gt;
     *      &lt;item&gt;#f58559&lt;/item&gt;
     *      &lt;item&gt;#f9a43e&lt;/item&gt;
     *      &lt;item&gt;#e4c62e&lt;/item&gt;
     *      &lt;item&gt;#67bf74&lt;/item&gt;
     *      &lt;item&gt;#59a2be&lt;/item&gt;
     *      &lt;item&gt;#2093cd&lt;/item&gt;
     *      &lt;item&gt;#ad62a7&lt;/item&gt;
     * &lt;/array&gt;
     * </pre>
     *
     * @param context
     * @param colourArrayId a string array of colour hex values.
     * @param key           The key used to pick the tile colour
     * @return A new or previously chosen color for <code>key</code>.
     */
    public static int pickColor(Context context, @ArrayRes int colourArrayId, String key) {
        TypedArray colors = context
                .getResources()
                .obtainTypedArray(colourArrayId);
        //        Log.d(TAG, "key: " + key);
        //        Log.d(TAG, "colors.length: " + colors.length());
        //        Log.d(TAG, "key.hashCode: " + key.hashCode());
        final int color = Math.abs(key.hashCode()) % colors.length();
        //        Log.d(TAG, "colorIndex: " + color);
        try {
            return colors.getColor(color, Color.GRAY);
        } finally {
            colors.recycle();
        }
    }

    //Bitmap demoImage = BitmapFactory.decodeResource(context.getResources(), drawableId);
    //
    //public static Bitmap overlayBitmap(Context context, Bitmap bottom, Bitmap top) {
    //    return overlayBitmap(context, bottom, top, 127);
    //}

    public static Bitmap overlayBitmap(Context context, Bitmap bottom, Bitmap top) {
        return overlayBitmap(context, bottom, top, 127);
    }

    public static Bitmap overlayBitmap(Context context, Bitmap bottom, Bitmap top, int topAlpha) {
        Bitmap resultBitmap = Bitmap
                .createBitmap(bottom.getWidth(), bottom.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(resultBitmap);

        c.drawBitmap(bottom, 0, 0, null);

        Paint p = new Paint();
        p.setAlpha(topAlpha);

        c.drawBitmap(top, 0, 0, p);

        return resultBitmap;
        // Your final bitmap is resultBitmap
    }


    public static int getPaddingInDp(Context context, int pixles) {
        //int paddingPixel = 100;
        float density = context
                .getResources()
                .getDisplayMetrics().density;
        int paddingDp = (int)(pixles * density);
        return paddingDp;
    }


    public Bitmap addOverlayGradient(Bitmap src, int gradientHeight) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap overlay = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);

        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        //LinearGradient shader = new LinearGradient(0, 0, 0, GRADIENT_HEIGHT, 0xFFFFFFFF, 0x00FFFFFF, TileMode.REPEAT);
        LinearGradient shader = new LinearGradient(0, h - gradientHeight, 0, h, 0xFFFFFFFF, 0x00FFFFFF, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, h - gradientHeight, w, h, paint);

        return overlay;
    }

    public static int complimentaryColour(int colour) {
        // XXX https://stackoverflow.com/questions/407793/programmatically-choose-high-contrast-colors
        return ~colour;
    }
}
