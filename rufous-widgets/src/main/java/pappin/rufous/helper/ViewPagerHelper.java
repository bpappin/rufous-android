package pappin.rufous.helper;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

/**
 * Created by bpappin on 2017-01-30.
 */

public class ViewPagerHelper {

    /**
     * Set the icons on a TabLayout, optionally removing the text labels.
     *
     * @param tabLayout  TabLayout the layout to operate one.
     * @param removeText boolean should the tab text be removed? THis is usefule when used with a
     *                   ViewPager, because the text will be automaticallky set.
     * @param iconResId  int... a collection of icons to use for the tabs, in the same order as the
     *                   tabs.
     */
    public static void setTabIcons(TabLayout tabLayout, boolean removeText, @ColorRes final int colorAccent, @DrawableRes int... iconResId) {
        setTabIcons(null, null, tabLayout, removeText, colorAccent, iconResId);
    }

    /**
     * Set the icons on a TabLayout, optionally removing the text labels.
     *
     * @param tabLayout  TabLayout the layout to operate one.
     * @param removeText boolean should the tab text be removed? THis is usefule when used with a
     *                   ViewPager, because the text will be automaticallky set.
     * @param iconResId  int... a collection of icons to use for the tabs, in the same order as the
     *                   tabs.
     */
    public static void setTabIcons(final Context context, ViewPager viewPager, TabLayout tabLayout, boolean removeText, @ColorRes final int colorAccent, @DrawableRes int... iconResId) {
        // XXX This cant be unit tested at this time because of the android stubs.
        // XXX however it will be obvious during a visual check, when it's not working.
        int maxCount = tabLayout.getTabCount() >= iconResId.length ? iconResId.length : tabLayout.getTabCount();
        for (int i = 0; i < maxCount; i++) {
            setTabIcon(tabLayout, removeText, i, iconResId[i]);
        }

        if (context != null && viewPager != null) {
            tabLayout.addOnTabSelectedListener(
                    new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            super.onTabSelected(tab);
                            int tabIconColor = ContextCompat.getColor(context, colorAccent);
                            tab
                                    .getIcon()
                                    .setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {
                            super.onTabUnselected(tab);
                            int tabIconColor = ContextCompat.getColor(context, android.R.color.primary_text_dark);
                            tab
                                    .getIcon()
                                    .setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                            super.onTabReselected(tab);
                            int tabIconColor = ContextCompat.getColor(context, colorAccent);
                            tab
                                    .getIcon()
                                    .setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        }
                    }
            );
        }

        // XXX initially the selected tab will not be properly coloured.
        if (tabLayout.getSelectedTabPosition() > -1) {
            tabLayout
                    .getTabAt(tabLayout.getSelectedTabPosition())
                    .getIcon()
                    .setColorFilter(ContextCompat.getColor(context, colorAccent), PorterDuff.Mode.SRC_IN);
        }

    }

    /**
     * Set the icon for a TabLayout tab.
     *
     * @param tabLayout  TabLayout the layout to operate on.
     * @param removeText boolean should the tab text be removed? THis is usefule when used with a
     *                   ViewPager, because the text will be automaticallky set.
     * @param index      the index of the tab to add the icon to.
     * @param resId      the drawable resoruce to set as the icon.
     */
    public static void setTabIcon(TabLayout tabLayout, boolean removeText, int index, @DrawableRes int resId) {
        TabLayout.Tab tabAt = tabLayout.getTabAt(index);
        tabAt.setIcon(resId);
        if (removeText) {
            tabAt.setText(null);
        }
    }

}
