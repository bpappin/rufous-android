package pappin.rufous.util;

import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Set;

/**
 * Created by bpappin on 2017-01-16.
 */

public class LogUtil {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Ensures that log tags do not exceed 23 chars.
     *
     * @param tag the tag to check.
     * @return the passed in tag, truncated is required.
     */
    public static String tag(String tag) {
        if (tag.length() > 23) {
            return tag.substring(0, 22);
        }
        return tag;
    }

    public static String tag(Class clazz) {
        return tag(clazz.getSimpleName());
    }

    public static void dump(String tag, Object entity) {
        dump(tag, ">>>> ENTITY: ", entity);
    }

    public static void dump(String tag, String prefix, Object entity) {
        if (entity instanceof Bundle) {
            Log.d(tag, prefix + " the " + entity.getClass().getName() + " can't be serialized into JSON.");
        } else {
            Log.d(tag, prefix + gson.toJson(entity));
        }
    }

    public static void dump(String tag, Bundle bundle) {
        dump(tag, ">>>> BUNDLE: ", bundle);
    }

    public static void dump(String tag, String prefix, Bundle bundle) {
        final StringBuilder buffer = new StringBuilder(" Bundle {");
        StringBuilderPrinter printer = new StringBuilderPrinter(buffer);
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            printer.println("  - " + key + "=" + bundle.get(key));
        }
        printer.println("}");
        Log.d(tag, prefix + buffer.toString());
    }
}
