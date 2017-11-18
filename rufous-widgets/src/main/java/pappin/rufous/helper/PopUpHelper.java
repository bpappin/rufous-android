package pappin.rufous.helper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PopUpHelper {

    private static final String TAG = "PopUpHelper";

    public static PopupMenu showPopUpMenuIcon(Context context, View anchor, int menuItemResourceId, PopupMenu.OnMenuItemClickListener listener) {
        PopupMenu popup = new PopupMenu(context, anchor);
        popup.setOnMenuItemClickListener(listener);
        return showPopUpMenuIcon(popup, menuItemResourceId);
    }

    public static PopupMenu showPopUpMenuIcon(PopupMenu popupMenu, int menuItemResourceId) {

        popupMenu
                .getMenuInflater()
                .inflate(menuItemResourceId, popupMenu.getMenu());

        // Sets icons to be shown
        setForceShowIcons(popupMenu);

        popupMenu.show();

        return popupMenu;
    }

    public static void setForceShowIcons(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu
                    .getClass()
                    .getDeclaredFields();
            for (Field field : fields) {
                // FIXME This will break if the mPopup variable changes.
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass()
                            .getName());
                    Method setForceIcons = classPopupHelper
                            .getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to set force icons: " + e.toString());
        }
    }

    public static android.support.v7.widget.PopupMenu showPopUpMenuIcon(android.support.v7.widget.PopupMenu popupMenu, int menuItemResourceId) {

        popupMenu
                .getMenuInflater()
                .inflate(menuItemResourceId, popupMenu.getMenu());

        // Sets icons to be shown
        setForceShowIcons(popupMenu);

        popupMenu.show();

        return popupMenu;
    }

    public static void setForceShowIcons(android.support.v7.widget.PopupMenu popup) {
        try {
            Field[] fields = popup
                    .getClass()
                    .getDeclaredFields();
            for (Field field : fields) {
                // FIXME This will break if the mPopup variable changes.
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass()
                            .getName());
                    Method setForceIcons = classPopupHelper
                            .getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to set force icons: " + e.toString());
        }
    }
}
