package pappin.rufous.picker.datetime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import java.util.Calendar;

import pappin.rufous.R;


/**
 * Created by bpappin on 2017-12-13.
 */

public class ClassicDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private OnDateTimeSelected callback;

    public void setCallback(OnDateTimeSelected callback) {
        this.callback = callback;
    }

    public static void showTimePickerDialog(Context context, FragmentManager manager, OnDateTimeSelected callback) {
        ClassicDatePickerFragment newFragment = new ClassicDatePickerFragment();
        newFragment.setCallback(callback);
        newFragment.show(manager, "ClassicDatePickerFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getTheme() {
        return super.getTheme();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        // Create a new instance of TimePickerDialog and return it
        //        R.style.ClassicPickerDialogTheme,
        return new DatePickerDialog(getActivity(), R.style.ClassicPickerDialogTheme, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        if (callback != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            callback.onDateInMillisSelected(cal.getTimeInMillis());
        }
    }

}
