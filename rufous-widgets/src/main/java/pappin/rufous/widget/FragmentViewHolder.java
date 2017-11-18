package pappin.rufous.widget;

import android.database.Cursor;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.ToggleButton;
import android.support.v7.widget.ToggleGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Same concept as ViewHolder, only named something different to make it obvious in code.
 * Created by bpappin on 2017-04-28.
 */

public abstract class FragmentViewHolder<T> {

    private final View root;

    public FragmentViewHolder(View root) {
        super();
        this.root = root;

        //        onBind(root);
    }

    public View getRoot() {
        return root;
    }

    /**
     * Bind the cursor to the UI elements. The cursor may be null if it was reset by the loader.
     *
     * @param source the object to get data from, or null if it is being reset.
     */
    protected abstract void onBind(T source);

    public void swapCursor(T source) {
        if (source != null) {
            if (source instanceof Cursor && ((Cursor)source).moveToFirst()) {
                onBind(source);
            } else {
                onBind(source);
            }
        }

    }


    protected ToggleGroup findToggleGroup(@IdRes int resId) {
        return (ToggleGroup)getRoot().findViewById(resId);
    }

    public ToggleButton findToggleGroupButton(@IdRes int resId) {
        return (ToggleButton)getRoot().findViewById(resId);
    }

    protected EditText findEditText(@IdRes int resId) {
        return (EditText)getRoot().findViewById(resId);
    }

    protected TextView findTextView(@IdRes int resId) {
        return (TextView)getRoot().findViewById(resId);
    }

    protected SimpleDraweeView findDraweeView(@IdRes int resId) {
        return (SimpleDraweeView)getRoot().findViewById(resId);
    }

    protected ImageView findImageView(@IdRes int resId) {
        return (ImageView)getRoot().findViewById(resId);
    }


    protected FloatingActionButton findFab(@IdRes int resId) {
        return (FloatingActionButton)getRoot().findViewById(resId);
    }

    protected Button findButton(@IdRes int resId) {
        return (Button)getRoot().findViewById(resId);
    }

    protected Switch findSwitch(@IdRes int resId) {
        return (Switch)getRoot().findViewById(resId);
    }
}
