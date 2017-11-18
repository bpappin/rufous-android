package pappin.rufous.widget;


import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import pappin.rufous.helper.TextViewHelper;


/**
 * Created by bpappin on 2017-05-19.
 */

public class DrawableTextView extends AppCompatTextView {
    public DrawableTextView(Context context) {
        super(context);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onPreDraw() {
        TextViewHelper.fixTextViewDrawableColor(this);
        return super.onPreDraw();
    }
}
