package pappin.rufous.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by bpappin on 2017-01-16.
 */

public class LogUtil {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static String tag(String tag) {
        if (tag.length() > 23) {
            return tag.substring(0, 22);
        }
        return tag;
    }

    public static void dump(String tag, Object entity) {
        dump(tag, ">>>> ENTITY: ", entity);
    }

    public static void dump(String tag, String prefix, Object entity) {
        Log.d(tag, prefix + gson.toJson(entity));
    }
}
