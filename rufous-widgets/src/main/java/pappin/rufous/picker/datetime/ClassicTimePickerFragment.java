package pappin.rufous.picker.datetime;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import pappin.rufous.R;


/**
 * Created by bpappin on 2017-12-13.
 */

public class ClassicTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private OnDateTimeSelected callback;

    public void setCallback(OnDateTimeSelected callback) {
        this.callback = callback;
    }

    public static void showTimePickerDialog(Context context, FragmentManager manager, OnDateTimeSelected callback) {
        ClassicTimePickerFragment newFragment = new ClassicTimePickerFragment();
        newFragment.setCallback(callback);
        newFragment.show(manager, "ClassicTimePickerFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), R.style.ClassicPickerDialogTheme,this, hour, minute,
                                    DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        if (callback != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            callback.onDateInMillisSelected(cal.getTimeInMillis());
        }
    }
}
