package pappin.rufous.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

/**
 * Created by bpappin on 2017-05-25.
 */

public class DisablingRadioGroup extends RadioGroup {
    public DisablingRadioGroup(Context context) {
        super(context);
    }

    public DisablingRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setEnabled(isEnabled());
        }
    }
}
