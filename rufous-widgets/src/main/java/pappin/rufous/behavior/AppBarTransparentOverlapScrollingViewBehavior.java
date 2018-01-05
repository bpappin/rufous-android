package pappin.rufous.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.util.AttributeSet;
import android.view.View;

/**
 * How to create AppBarLayout which overlaps content of CoordinatorLayout
 * <p>
 * When using a CoordinatorLayout with AppBarLayout on some activities I need the content to be
 * under the AppBarLayout, i.e. the Toolbar is using some transparent color and overlays the content.
 * By default CoordinatorLayout + AppBarLayout arrange things so that toolbar and scrolling content
 * are next to eachother, without any overlapping.
 * <p>
 * Taken from:
 * http://stackoverflow.com/questions/32761319/how-to-create-appbarlayout-which-overlaps-content-of-coordinatorlayout
 * <p>
 * Created by bpappin on 2016-10-07.
 */

public class AppBarTransparentOverlapScrollingViewBehavior extends
        AppBarLayout.ScrollingViewBehavior {
    public AppBarTransparentOverlapScrollingViewBehavior() {
        super();
    }

    public AppBarTransparentOverlapScrollingViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child,
                                          View dependency) {
        updateOffset(parent, child, dependency);
        return false;
    }

    private boolean updateOffset(CoordinatorLayout parent, View child,
                                 View dependency) {
        final CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependency
                .getLayoutParams()).getBehavior();
        if (behavior instanceof Behavior) {
            // Offset the child so that it is below the app-bar (with any
            // overlap)
            final int offset = 0;   // CHANGED TO 0
            setTopAndBottomOffset(offset);
            return true;
        }
        return false;
    }
}
