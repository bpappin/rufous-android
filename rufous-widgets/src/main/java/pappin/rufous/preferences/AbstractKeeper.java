package pappin.rufous.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Keepers are used to store persistent arbitrary data related to user actions. Typically is allow the UX to be a little smarter.
 * Created by bpappin on 2017-03-16.
 */

public abstract class AbstractKeeper {

    private static final String PREFERENCES_FILE_KEY_PREFIX = "pappin.rufous.keeper.";
    private SharedPreferences sharedPreferences;


    public AbstractKeeper(Context context) {
        sharedPreferences = context.getSharedPreferences(
                getFileName(), Context.MODE_PRIVATE);
    }

    public String getFileName() {
        return PREFERENCES_FILE_KEY_PREFIX + getName();
    }

    protected String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    protected void setString(String key, String value) {
        sharedPreferences
                .edit()
                .putString(key, value)
                .apply();
    }

    public abstract String getName();

}
