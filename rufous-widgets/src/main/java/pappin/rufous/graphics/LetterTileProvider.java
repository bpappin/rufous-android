package pappin.rufous.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.ArrayRes;
import android.text.TextPaint;
import android.text.TextUtils;

import pappin.rufous.R;

/**
 * Used to create a {@link Bitmap} that contains a letter used in the English
 * alphabet or digit, if there is no letter or digit available, a default image
 * is shown instead
 * <p>
 * Requires some resources in the project:
 * <p>
 * <pre>
 *  <!-- The default letter tile text size -->
 *  <dimen name="tile_letter_font_size">33sp</dimen>
 *  <!-- The deafult tile size -->
 *  <dimen name="tile_letter_size">64dp</dimen>
 *
 *  <!-- All of the possible tile background colors -->
 *  <array name="tile_letter_colors">
 *  	<item>#f16364</item>
 *  	<item>#f58559</item>
 * 		<item>#f9a43e</item>
 * 		<item>#e4c62e</item>
 * 		<item>#67bf74</item>
 * 		<item>#59a2be</item>
 * 		<item>#2093cd</item>
 * 		<item>#ad62a7</item>
 * </array>
 *
 * </pre>
 */
public class LetterTileProvider {

    /** The number of available tile colors (see R.array.letter_tile_colors) */
    //private static final int NUM_OF_TILE_COLORS = 8;

    /**
     * The {@link TextPaint} used to draw the letter onto the tile
     */
    private final TextPaint mPaint = new TextPaint();
    /**
     * The bounds that enclose the letter
     */
    private final Rect mBounds = new Rect();
    /**
     * The {@link Canvas} to draw on
     */
    private final Canvas mCanvas = new Canvas();
    /**
     * The first char of the name being displayed
     */
    //private final char[] mFirstChar = new char[1];

    /** The background colors of the tile */
    //private final TypedArray mColors;
    /**
     * The font size used to display the letter
     */
    private final int mTileLetterFontSize;
    /**
     * The default image to display
     */
    private final Bitmap mDefaultBitmap;
    private final Context context;

    private BitmapFilter[] filters;

    private int colourArrayResId = -1;

    public LetterTileProvider(Context context, @ArrayRes int colourArrayResId, BitmapFilter... filters) {
        this(context, filters);
        this.colourArrayResId = colourArrayResId;
    }

    /**
     * Constructor for <code>LetterTileProvider</code>
     *
     * @param context The {@link Context} to use
     */
    public LetterTileProvider(Context context, BitmapFilter... filters) {
        this.context = context;
        this.filters = filters;
        final Resources res = context.getResources();

        mPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Align.CENTER);
        mPaint.setAntiAlias(true);

        //mColors = res.obtainTypedArray(R.array.tile_letter_colors);
        mTileLetterFontSize = res
                .getDimensionPixelSize(R.dimen.rufous_text_tile_letter_font_size);

        mDefaultBitmap = BitmapFactory.decodeResource(res,
                                                      android.R.drawable.sym_def_app_icon);
    }

    public Bitmap getLetterTile(String displayName, String key, int width,
                                int height) {
        return getLetterTile(displayName, key, 1, width,
                             height);
    }

    /**
     * @param displayName The name used to create the letter for the tile
     * @param key         The key used to generate the background color for the tile
     * @param width       The desired width of the tile
     * @param height      The desired height of the tile
     * @return A {@link Bitmap} that contains a letter used in the English alphabet or digit, if
     * there is no letter or digit available, a default image is shown instead
     */
    public Bitmap getLetterTile(String displayName, String key, int maxChars, int width,
                                int height) {
        final Bitmap bitmap = Bitmap.createBitmap(width, height,
                                                  Bitmap.Config.ARGB_8888);

        int endMax = maxChars;
        if (displayName.length() < endMax) {
            endMax = displayName.length();
        }

        // XXX endMax - 1 is already done in the TextUtils.substring method.
        String showString = TextUtils.substring(displayName, 0, endMax);
        StringBuilder buff = new StringBuilder(showString);
        //        if (StringUtil.isNotEmpty(showString)) {
        buff.setCharAt(0, Character.toUpperCase(buff.charAt(0)));
        //        }
        showString = buff.toString();


        //final char firstChar = displayName.charAt(0);


        final Canvas c = mCanvas;
        c.setBitmap(bitmap);
        if (colourArrayResId > 0) {
            c.drawColor(G.pickColor(context, colourArrayResId, key));
        } else {
            c.drawColor(G.pickColor(context, key));
        }

        if (/*StringUtil.isNotEmpty(showString) &&*/ isEnglishLetterOrDigit(showString.charAt(0))) {
            //mFirstChar[0] = Character.toUpperCase(firstChar);
            mPaint.setTextSize(mTileLetterFontSize);
            mPaint.getTextBounds(showString, 0, showString.length(), mBounds);
            c.drawText(showString, 0, showString.length(), 0 + width / 2, 0 + height / 2
                    +
                    (mBounds.bottom -
                            mBounds.top) /
                            2, mPaint);
        } else {
            c.drawBitmap(mDefaultBitmap, 0, 0, null);
        }

        Bitmap output = bitmap;
        if (filters != null && filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                // Bitmap oldBitmap = output;
                output = filters[i].filter(output);
                // oldBitmap.recycle();
            }
        }
        return output;
    }

    /**
     * @param c The char to check
     * @return True if <code>c</code> is in the English alphabet or is a digit, false otherwise
     */
    private static boolean isEnglishLetterOrDigit(char c) {
        return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z' || '0' <= c
                && c <= '9';
    }

    ///**
    // * @param key
    // *            The key used to generate the tile color
    // * @return A new or previously chosen color for <code>key</code> used as the
    // *         tile background color
    // */
    //private int pickColor(String key) {
    //	// String.hashCode() is not supposed to change across java versions, so
    //	// this should guarantee the same key always maps to the same color
    //	final int color = Math.abs(key.hashCode()) % NUM_OF_TILE_COLORS;
    //	try {
    //		return mColors.getColor(color, Color.BLACK);
    //	} finally {
    //		mColors.recycle();
    //	}
    //}

    public static final BitmapDrawable createSquareLetterTile(Context context, @ArrayRes int colourArrayResId,
                                                              String displayName) {
        return createSquareLetterTile(context, colourArrayResId, displayName, displayName);
    }

    public static final BitmapDrawable createSquareLetterTile(Context context,
                                                              String displayName) {
        return createSquareLetterTile(context, displayName, displayName);
    }

    public static final BitmapDrawable createSquareLetterTile(Context context,
                                                              String displayName, String key) {
        return createSquareLetterTile(context, R.array.rufous_text_tile_letter_colors, displayName, key);
    }

    public static final BitmapDrawable createSquareLetterTile(Context context, @ArrayRes int colourArrayResId,
                                                              String displayName, String key) {
        final Resources res = context.getResources();
        final int tileSize = res
                .getDimensionPixelSize(R.dimen.rufous_text_tile_letter_size);

        final LetterTileProvider tileProvider = new LetterTileProvider(context, colourArrayResId,
                                                                       new BitmapFilter[0]);
        final Bitmap letterTile = tileProvider.getLetterTile(displayName, key,
                                                             tileSize, tileSize);

        BitmapDrawable icon = new BitmapDrawable(context.getResources(),
                                                 letterTile);
        return icon;
    }


    public static final BitmapDrawable createRoundLetterTile(Context context, @ArrayRes int colourArrayResId,
                                                             String displayName) {
        final Resources res = context.getResources();
        final int tileSize = res
                .getDimensionPixelSize(R.dimen.rufous_text_tile_letter_size);

        final LetterTileProvider tileProvider = new LetterTileProvider(context, colourArrayResId,
                                                                       new BitmapFilter() {

                                                                           @Override
                                                                           public Bitmap filter(Bitmap bitmap) {
                                                                               return BitmapUtil.getRoundedBitmap(bitmap);
                                                                           }

                                                                       });
        final Bitmap letterTile = tileProvider.getLetterTile(displayName,
                                                             displayName, tileSize, tileSize);

        BitmapDrawable icon = new BitmapDrawable(context.getResources(),
                                                 letterTile);
        return icon;
    }

    public static final BitmapDrawable createRoundLetterTile(Context context,
                                                             String displayName) {
        return createRoundLetterTile(context, R.array.rufous_text_tile_letter_colors, displayName);
    }

}
