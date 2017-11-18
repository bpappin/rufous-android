package pappin.rufous.helper;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import pappin.rufous.util.LogUtil;


/**
 * Created by bpappin on 2017-05-19.
 */

public class TextViewHelper {

    private static final String TAG = LogUtil.tag("TextViewHelper");

    public static void fixTextViewDrawableColor(TextView textView) {
        int currentTextColour = textView.getCurrentTextColor();
        //ColorStateList textColours = textView.getTextColors();
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(currentTextColour, PorterDuff.Mode.SRC_IN));
            }
        }
    }

    public static void makeTextViewExpandable(final TextView textView, final int maximum/*, final int minLine*/) {

        //        int minLines = textView.getMinLines();
        //        int maxLines = textView.getMaxLines();

        ViewTreeObserver vto = textView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Layout l = textView.getLayout();
                if (l != null) {
                    int lines = l.getLineCount();
                    if (lines > 0)
                        if (l.getEllipsisCount(lines - 1) > 0) {
//                            Log.d(TAG, "Text is ellipsized");
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (textView.getMaxLines() == maximum) {
                                        textView.setMaxLines(Integer.MAX_VALUE);
                                    } else {
                                        textView.setMaxLines(maximum);
                                    }
                                }
                            });
                        }

                }
            }
        });


    }


}
