package pappin.rufous.widget.text;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by bpappin on 2017-11-22.
 */

public abstract class OnChangeTextWatcher implements TextWatcher {


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        return;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        return;
    }
}
