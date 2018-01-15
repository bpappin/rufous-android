/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pappin.rufous.widget.drawable;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.text.TextUtils;

import pappin.rufous.R;
import pappin.rufous.android.Preconditions;

//import com.google.common.base.Preconditions;

/**
 * This originally came from the Contacts application in the Android open source code.
 * See: https://android.googlesource.com/platform/packages/apps/Contacts/
 * <p>
 * Apache License, Version 2.0 applies.
 * <p>
 * A drawable that encapsulates all the functionality needed to display a letter tile to
 * represent a contact image.
 */
public class LetterTileDrawable extends Drawable {

    /**
     * Contact type constants
     */
    public static final int TYPE_PERSON = 1;
    public static final int TYPE_BUSINESS = 2;
    public static final int TYPE_GROUP = 3;
    public static final int TYPE_DEFAULT = TYPE_PERSON;
    /**
     * Reusable components to avoid new allocations
     */
    private static final Paint sPaint = new Paint();
    private static final Rect sRect = new Rect();
    private static final char[] sFirstChar = new char[1];
    /**
     * 54% opacity
     */
    private static final int ALPHA = 138;
    /**
     * Letter tile
     */
    private static TypedArray sColors;
    private static int sDefaultColor;
    private static int sTileFontColor;
    private static float sLetterToTileRatio;
    private static Bitmap DEFAULT_PERSON_AVATAR;
    private static Bitmap DEFAULT_BUSINESS_AVATAR;
    private static Bitmap DEFAULT_GROUP_AVATAR;
    private final String TAG = LetterTileDrawable.class.getSimpleName();
    private final Paint mPaint;
    private int mContactType = TYPE_DEFAULT;
    private float mScale = 1.0f;
    private float mOffset = 0.0f;
    private boolean mIsCircle = false;

    private int mColor;
    private Character mLetter = null;

    private boolean borderEnabled;
    private int borderColor;
    private int borderStrokeWidth = 2;


    public LetterTileDrawable(final Resources res) {
        this(res, R.array.rufous_letter_tile_colors);
    }

    public LetterTileDrawable(final Resources res, @ArrayRes int colourArrayId) {
        if (sColors == null) {
            sColors = res.obtainTypedArray(colourArrayId);
            sDefaultColor = res.getColor(R.color.rufous_letter_tile_default_color);
            sTileFontColor = res.getColor(R.color.rufous_letter_tile_font_color);
            sLetterToTileRatio = res.getFraction(R.fraction.rufous_letter_to_tile_ratio, 1, 1);
            DEFAULT_PERSON_AVATAR = BitmapFactory.decodeResource(res,
                    R.drawable.rufous_ic_placeholder_person);
            DEFAULT_BUSINESS_AVATAR = BitmapFactory.decodeResource(res,
                    R.drawable.rufous_ic_placeholder_business);
            DEFAULT_GROUP_AVATAR = BitmapFactory.decodeResource(res,
                    R.drawable.rufous_ic_placeholder_people);
            sPaint.setTypeface(Typeface.create(
                    res.getString(R.string.rufous_letter_tile_letter_font_family), Typeface.NORMAL));
            sPaint.setTextAlign(Align.CENTER);
            sPaint.setAntiAlias(true);
        }
        mPaint = new Paint();
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mColor = sDefaultColor;

        borderEnabled = false;
        borderColor = sTileFontColor;
        borderStrokeWidth = 2;
    }

    public boolean isBorderEnabled() {
        return borderEnabled;

    }

    public LetterTileDrawable setBorderEnabled(boolean borderEnabled) {
        this.borderEnabled = borderEnabled;
        return this;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public LetterTileDrawable setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public int getBorderStrokeWidth() {
        return borderStrokeWidth;
    }

    public LetterTileDrawable setBorderStrokeWidth(int borderStrokeWidth) {
        this.borderStrokeWidth = borderStrokeWidth;
        return this;
    }

    private static Bitmap getBitmapForContactType(int contactType) {
        switch (contactType) {
            case TYPE_PERSON:
                return DEFAULT_PERSON_AVATAR;
            case TYPE_BUSINESS:
                return DEFAULT_BUSINESS_AVATAR;
            case TYPE_GROUP:
                return DEFAULT_GROUP_AVATAR;
            default:
                return DEFAULT_PERSON_AVATAR;
        }
    }

    private static boolean isEnglishLetter(final char c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z');
    }

    /**
     * Returns the scale percentage as a float for LetterTileDrawables used in AdaptiveIcons.
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static float getAdaptiveIconScale() {
        return 1 / (1 + (2 * AdaptiveIconDrawable.getExtraInsetFraction()));
    }

    @Override
    public void draw(final Canvas canvas) {
        final Rect bounds = getBounds();
        if (!isVisible() || bounds.isEmpty()) {
            return;
        }
        // Draw letter tile.
        drawLetterTile(canvas);
    }

    @Override
    public void setAlpha(final int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(final ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return android.graphics.PixelFormat.OPAQUE;
    }

    /**
     * Draw the bitmap onto the canvas at the current bounds taking into account the current scale.
     */
    private void drawBitmap(final Bitmap bitmap, final int width, final int height,
                            final Canvas canvas) {
        // The bitmap should be drawn in the middle of the canvas without changing its width to
        // height ratio.
        final Rect destRect = copyBounds();

        // Crop the destination bounds into a square, scaled and offset as appropriate
        final int halfLength = (int) (mScale * Math.min(destRect.width(), destRect.height()) / 2);

        destRect.set(destRect.centerX() - halfLength,
                (int) (destRect.centerY() - halfLength + mOffset * destRect.height()),
                destRect.centerX() + halfLength,
                (int) (destRect.centerY() + halfLength + mOffset * destRect.height()));

        // Source rectangle remains the entire bounds of the source bitmap.
        sRect.set(0, 0, width, height);

        sPaint.setTextAlign(Align.CENTER);
        sPaint.setAntiAlias(true);
        sPaint.setAlpha(ALPHA);

        canvas.drawBitmap(bitmap, sRect, destRect, sPaint);
    }

    private void drawLetterTile(final Canvas canvas) {
        // Draw background color.
        sPaint.setColor(mColor);

        sPaint.setAlpha(mPaint.getAlpha());
        final Rect bounds = getBounds();
        final int minDimension = Math.min(bounds.width(), bounds.height());

        Paint.Style defaultStyle = sPaint.getStyle();
        float defaultStrokeWidth = sPaint.getStrokeWidth();
        int defaultColour = sPaint.getColor();
        if (mIsCircle) {
            canvas.drawCircle(bounds.centerX(), bounds.centerY(), minDimension / 2, sPaint);
            if(borderEnabled) {
                sPaint.setColor(borderColor);
                sPaint.setStrokeWidth(borderStrokeWidth);
                sPaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(bounds.centerX(), bounds.centerY(), minDimension / 2, sPaint);
            }

        } else {
            canvas.drawRect(bounds, sPaint);

            if(borderEnabled) {
                sPaint.setColor(borderColor);
                sPaint.setStrokeWidth(borderStrokeWidth);
                sPaint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(bounds, sPaint);
            }
        }
// Reset back to defaults adter borders drawn.
        sPaint.setStyle(defaultStyle);
        sPaint.setStrokeWidth(defaultStrokeWidth);
        sPaint.setColor(defaultColour);

        // Draw letter/digit only if the first character is an english letter or there's a override

        if (mLetter != null) {
            // Draw letter or digit.
            sFirstChar[0] = mLetter;

            // Scale text by canvas bounds and user selected scaling factor
            sPaint.setTextSize(mScale * sLetterToTileRatio * minDimension);
            sPaint.getTextBounds(sFirstChar, 0, 1, sRect);
            sPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
            sPaint.setColor(sTileFontColor);
            sPaint.setAlpha(ALPHA);

            // Draw the letter in the canvas, vertically shifted up or down by the user-defined
            // offset
            canvas.drawText(sFirstChar, 0, 1, bounds.centerX(),
                    bounds.centerY() + mOffset * bounds.height() - sRect.exactCenterY(),
                    sPaint);
        } else {
            // Draw the default image if there is no letter/digit to be drawn
            final Bitmap bitmap = getBitmapForContactType(mContactType);
            drawBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(),
                    canvas);
        }
    }

    public int getColor() {
        return mColor;
    }

    public LetterTileDrawable setColor(int color) {
        mColor = color;
        return this;
    }

    /**
     * Returns a deterministic color based on the provided contact identifier string.
     */
    private int pickColor(final String identifier) {
        if (TextUtils.isEmpty(identifier) || mContactType == TYPE_GROUP) {
            return sDefaultColor;
        }
        // String.hashCode() implementation is not supposed to change across java versions, so
        // this should guarantee the same email address always maps to the same color.
        // The email should already have been normalized by the ContactRequest.
        final int color = Math.abs(identifier.hashCode()) % sColors.length();
        return sColors.getColor(color, sDefaultColor);
    }

    /**
     * Scale the drawn letter tile to a ratio of its default size
     *
     * @param scale The ratio the letter tile should be scaled to as a percentage of its default
     *              size, from a scale of 0 to 2.0f. The default is 1.0f.
     */
    public LetterTileDrawable setScale(float scale) {
        mScale = scale;
        return this;
    }

    /**
     * Assigns the vertical offset of the position of the letter tile to the ContactDrawable
     *
     * @param offset The provided offset must be within the range of -0.5f to 0.5f.
     *               If set to -0.5f, the letter will be shifted upwards by 0.5 times the height of the canvas
     *               it is being drawn on, which means it will be drawn with the center of the letter starting
     *               at the top edge of the canvas.
     *               If set to 0.5f, the letter will be shifted downwards by 0.5 times the height of the canvas
     *               it is being drawn on, which means it will be drawn with the center of the letter starting
     *               at the bottom edge of the canvas.
     *               The default is 0.0f.
     */
    public LetterTileDrawable setOffset(float offset) {
        Preconditions.checkArgument(offset >= -0.5f && offset <= 0.5f);
        mOffset = offset;
        return this;
    }

    public LetterTileDrawable setLetter(Character letter) {
        mLetter = letter;
        return this;
    }

    public LetterTileDrawable setLetterAndColorFromContactDetails(final String displayName,
                                                                  final String identifier) {
        if (displayName != null && displayName.length() > 0
                && isEnglishLetter(displayName.charAt(0))) {
            mLetter = Character.toUpperCase(displayName.charAt(0));
        } else {
            mLetter = null;
        }
        mColor = pickColor(identifier);
        return this;
    }

    public LetterTileDrawable setContactType(int contactType) {
        mContactType = contactType;
        return this;
    }

    public LetterTileDrawable setIsCircular(boolean isCircle) {
        mIsCircle = isCircle;
        return this;
    }
}
