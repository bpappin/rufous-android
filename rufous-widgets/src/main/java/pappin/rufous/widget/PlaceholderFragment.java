package pappin.rufous.widget;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pappin.rufous.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    private static final String MESSAGE_RES_ID = "messageResourceId";
    private static final String MESSAGE = "message";
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private TextView messageView;


    public PlaceholderFragment() {
        super();
    }


    public static PlaceholderFragment newInstance(CharSequence message) {
        return newInstance(message.toString());
    }

    public static PlaceholderFragment newInstance(@StringRes int messageResId) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(MESSAGE_RES_ID, messageResId);
        fragment.setArguments(args);
        return fragment;

    }

    public static PlaceholderFragment newInstance(String message) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rufous_widget_placeholder_fragment, container, false);
        messageView = (TextView)rootView.findViewById(R.id.section_label);

        if (getArguments().containsKey(MESSAGE_RES_ID)) {
            messageView.setText(getArguments().getInt(MESSAGE_RES_ID));
        } else if (getArguments().containsKey(MESSAGE)) {
            messageView.setText(getArguments().getString(MESSAGE));
        } else {
            String string = getString(R.string.rufous_widget_formatted_placeholder, "" + getId());
            messageView.setText(string);
        }

        return rootView;
    }

}
