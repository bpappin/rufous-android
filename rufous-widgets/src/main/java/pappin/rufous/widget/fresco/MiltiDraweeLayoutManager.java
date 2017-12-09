package pappin.rufous.widget.fresco;

import android.graphics.Rect;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.MultiDraweeHolder;

/**
 * Handles the layout of the images in a MultiDraweeView.
 *
 * Created by bpappin on 2017-12-07.
 */

public interface MiltiDraweeLayoutManager {

    void configureDrawees(GenericDraweeHierarchyBuilder hierarchyBuilder);
    Rect getDrawablePosition(MultiDraweeView multiDraweeView, MultiDraweeHolder<GenericDraweeHierarchy> multiDraweeHolder, int index);
}
