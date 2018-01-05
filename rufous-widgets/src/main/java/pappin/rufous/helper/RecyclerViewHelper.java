package pappin.rufous.helper;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import pappin.rufous.widget.EmptyRecyclerView;

/**
 * Created by bpappin on 2017-12-09.
 */

public class RecyclerViewHelper {
    public static void linearLayout(Context context, EmptyRecyclerView list, boolean dividers) {
        linearLayout(context, list, LinearLayoutManager.VERTICAL, false, dividers, 0);
    }

    public static void linearLayout(Context context, EmptyRecyclerView list, @RecyclerView.Orientation int orientation, boolean reverseLayout, boolean dividers, @DrawableRes int dividerDrawable) {
        int scrollPosition = getScrollPosition(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, reverseLayout);
        list.setLayoutManager(layoutManager);
        if (dividers) {
            DividerItemDecoration itemDecorator = new DividerItemDecoration(context, layoutManager.getOrientation());
            if (dividerDrawable > 0) {
                // R.drawable.shape_divider_dashed
                itemDecorator.setDrawable(ContextCompat.getDrawable(list.getContext(), dividerDrawable));
            }
            list.addItemDecoration(itemDecorator);
        }

        list.scrollToPosition(scrollPosition);
    }

    public static void gridLayout(Context context, EmptyRecyclerView list, int columnCount) {
        int scrollPosition = getScrollPosition(list);
        final GridLayoutManager layoutManager = new GridLayoutManager(context, columnCount);
        list.setLayoutManager(layoutManager);
        list.scrollToPosition(scrollPosition);
    }

    /**
     * Same as staggeredGridLayout(Context, EmptyRecyclerView, int, @RecyclerView.Orientation int) but with the orientation preset to StaggeredGridLayoutManager.VERTICAL.
     *
     * @param context     the views context
     * @param list        the list that the layout is going to apply to.
     * @param columnCount the number of columns for the layout.
     */
    public static void staggeredGridLayout(Context context, EmptyRecyclerView list, int columnCount) {
        staggeredGridLayout(context, list, columnCount, StaggeredGridLayoutManager.VERTICAL);
    }

    /**
     * @param context     the views context
     * @param list        the list that the layout is going to apply to.
     * @param columnCount the number of columns for the layout. if orientation is StaggeredGridLayoutManager.VERTICAL, the this is the number of rows.
     * @param orientation the @RecyclerView.Orientation of the grid. StaggeredGridLayoutManager.VERTICAL or StaggeredGridLayoutManager.HORIZONTAL.
     */
    public static void staggeredGridLayout(Context context, EmptyRecyclerView list, int columnCount, @RecyclerView.Orientation int orientation) {
        int scrollPosition = getScrollPosition(list);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(columnCount, orientation);
        list.setLayoutManager(layoutManager);

        list.scrollToPosition(scrollPosition);
    }

    /**
     * @param list
     * @return the current scroll position of the view.
     */
    public static int getScrollPosition(EmptyRecyclerView list) {
        // If a layout manager has already been set, get current scroll position.
        if (list.getLayoutManager() != null) {
            return ((LinearLayoutManager) list.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        return 0;
    }

}
