package pappin.rufous.widget;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pappin.rufous.helper.ImageViewHelper;
import pappin.rufous.util.StringUtil;


/**
 * Helps bind cursor data to UI elements.
 * Created by bpappin on 2017-06-19.
 */

public class Binding {


    public static Long longOrNull(String field, Cursor cursor) {
        if (!cursor.isNull(cursor.getColumnIndex(field))) {
            long value = cursor.getLong(cursor.getColumnIndex(field));
            return value;
        }
        return null;
    }

    public static void bind(TextView view, String field, Cursor cursor) {
        if (!cursor.isNull(cursor.getColumnIndex(field))) {
            final String value = cursor.getString(cursor.getColumnIndex(field));
            view.setText(value);
        } else {
            view.setText(null);
        }
    }

    /**
     * Bind the field to the component, using a default resource id, if the value is empty.
     *
     * @param view
     * @param field
     * @param cursor
     * @param defaultResId
     */
    public static void bind(TextView view, String field, Cursor cursor, @StringRes int defaultResId) {
        if (!cursor.isNull(cursor.getColumnIndex(field))) {
            final String value = cursor.getString(cursor.getColumnIndex(field));
            if (StringUtil.isNotEmpty(value)) {
                view.setText(value);
            } else {
                view.setText(defaultResId);
            }
        } else {
            view.setText(defaultResId);
        }
    }

    public static void bind(TextView view, String field, Cursor cursor, String defaultLabel) {
        if (!cursor.isNull(cursor.getColumnIndex(field))) {
            final String value = cursor.getString(cursor.getColumnIndex(field));
            if (StringUtil.isNotEmpty(value)) {
                view.setText(value);
            } else {
                view.setText(defaultLabel);
            }
        } else {
            view.setText(defaultLabel);
        }
    }


    public static void bindOrLetterTile(Context context, ImageView view, String urlColumn, String nameColumn, Cursor cursor) {
        ImageViewHelper.setImageOrDefaultRound(context, view, string(urlColumn, cursor), string(nameColumn, cursor));

        //        final String value = cursor.getString(cursor.getColumnIndex(urlColumn));
        //        view.setImageURI(value);
    }

    public static void bindOrLetterTileWithDefault(Context context, ImageView view, String urlColumn, String nameColumn, Cursor cursor, @StringRes int defaultResId) {
        bindOrLetterTileWithDefault(context, view, urlColumn, nameColumn, cursor, context.getString(defaultResId));
    }

    public static void bindOrLetterTileWithDefault(Context context, ImageView view, String urlColumn, String nameColumn, Cursor cursor, String defaultString) {
        ImageViewHelper.setImageOrDefaultRound(context, view, string(urlColumn, cursor), string(nameColumn, cursor, defaultString));

        //        final String value = cursor.getString(cursor.getColumnIndex(urlColumn));
        //        view.setImageURI(value);
    }

    public static void bindOrLetterTileOrGone(Context context, ImageView view, String urlColumn, String nameColumn, Cursor cursor) {
        bindOrLetterTileOrVisibility(context, view, urlColumn, nameColumn, cursor, View.GONE);
    }

    public static void bindOrLetterTileOrVisibility(Context context, ImageView view, String urlColumn, String nameColumn, Cursor cursor, int visibility) {
        String urlString = string(urlColumn, cursor);
        String nameString = string(nameColumn, cursor);
        if (StringUtil.isEmpty(urlString) && StringUtil.isEmpty(nameString)) {
            view.setVisibility(visibility);
        } else {
            view.setVisibility(View.VISIBLE);
            ImageViewHelper.setImageOrDefaultRound(context, view, urlString, nameString);
        }

        //        final String value = cursor.getString(cursor.getColumnIndex(urlColumn));
        //        view.setImageURI(value);
    }

    public static void bind(ImageView view, String field, Cursor cursor) {
        final String value = cursor.getString(cursor.getColumnIndex(field));
        ImageViewHelper.setImageFromUrl(view, value);
    }

    public static String string(String column, Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    public static String string(String column, Cursor cursor, String defaultString) {
        String string = cursor.getString(cursor.getColumnIndex(column));
        if (string == null) {
            string = defaultString;
        }
        return string;
    }

    /**
     * Binds one or more fields, via a formatted string, into a TextView.
     *
     * @param context
     * @param view
     * @param resId
     * @param fields
     * @param cursor
     */
    public static void bindFormat(Context context, TextView view, @StringRes int resId, String emptyValue, String[] fields, Cursor cursor) {

        Object[] args = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            args[i] = StringUtil.orString(Binding.string(fields[i], cursor), emptyValue);
        }

        view.setText(context
                .getResources()
                .getString(resId, args));
    }

    /**
     * Binds one or more fields, via a formatted string, into a TextView.
     *
     * @param context
     * @param view
     * @param resId
     * @param fields
     * @param cursor
     */
    public static void bindFormat(Context context, TextView view, @StringRes int resId, String[] fields, Cursor cursor) {

        Object[] args = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            args[i] = Binding.string(fields[i], cursor);
        }

        view.setText(context
                .getResources()
                .getString(resId, args));
    }

    /**
     * Applies the visibility to the UI element is any of the column arguments return a null.
     *
     * @param context
     * @param view
     * @param resId
     * @param fields
     * @param cursor
     * @param visibility
     */
    public static void bindFormatOrVisibility(Context context, TextView view, @StringRes int resId, String[] fields, Cursor cursor, int visibility) {

        boolean allVisible = true;
        Object[] args = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            args[i] = Binding.string(fields[i], cursor);
            if (args[i] == null) {
                allVisible = false;
                break;
            }
        }
        if (allVisible) {
            view.setText(context
                    .getResources()
                    .getString(resId, args));
        } else {
            view.setVisibility(visibility);
        }
    }

    public static long integer(String column, Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex(column));
    }

    public static void bindFormat(Context context, TextView view, @StringRes int resId, String[] args) {
        view.setText(context
                .getResources()
                .getString(resId, (Object[])args));
    }

    public static void bindString(TextView view, String value) {
        view.setText(value);
    }

}
