package pappin.rufous.widget.fresco;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.util.Log;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.MultiDraweeHolder;

import pappin.rufous.R;
import pappin.rufous.util.LogUtil;

/**
 * Created by bpappin on 2017-12-09.
 */

public class MiltiDraweeStackedLayoutManager extends MiltiDraweeAbstractLayoutManager {

    private static final String TAG = LogUtil.tag("MiltiDraweeStackedLayoutManager");
    @DimenRes
    private int imageOffset = R.dimen.rufous_multidraweeview_stacked_layout_offset;


    public MiltiDraweeStackedLayoutManager(Context context, @DimenRes int offset, boolean roundAsCircle) {
        super(context, roundAsCircle);
        this.imageOffset = offset;
    }

    public MiltiDraweeStackedLayoutManager(Context context) {
        this(context, R.dimen.rufous_multidraweeview_stacked_layout_offset, false);
    }

    public MiltiDraweeStackedLayoutManager(Context context, boolean roundAsCircle) {
        this(context, R.dimen.rufous_multidraweeview_stacked_layout_offset, roundAsCircle);
    }

    @Override
    public void configureDrawees(GenericDraweeHierarchyBuilder hierarchyBuilder) {
        super.configureDrawees(hierarchyBuilder);
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER);
    }

    @Override
    public Rect getDrawablePosition(MultiDraweeView multiDraweeView, MultiDraweeHolder<GenericDraweeHierarchy> multiDraweeHolder, int index) {
        int offset = (int) dipToPixels(getContext().getResources().getDimension(imageOffset));
        int width = multiDraweeView.getMeasuredWidth();
        int height = multiDraweeView.getMeasuredHeight();

        if (multiDraweeHolder.size() == 1) {
            return new Rect(0, 0, width, height);
        } else if (multiDraweeHolder.size() == 2) {
            switch (index) {
                case 0:
                    return new Rect(offset, 0, width, height);
                case 1:
                    return new Rect(0, offset, width, height);
            }
        } else if (multiDraweeHolder.size() == 3) {
            switch (index) {
                case 0:
                    return new Rect(offset * 2, 0, width, height);
                case 1:
                    return new Rect(offset, offset, width, height);
                case 2:
                    return new Rect(0, offset * 2, width, height);
            }
        }
        if (multiDraweeHolder.size() == 4) {

            int w = width - (offset * 4);
            int h = height - (offset * 4);
            Log.d(TAG, "count=" + multiDraweeHolder.size() + ", height=" + height + ", width=" + width + ", offset=" + offset);
            Log.d(TAG, "h=" + h + ", w=" + w + ", offset=" + offset);
            switch (index) {
//                case 0:
//                    return new Rect(0, offset * 4, w, h);
//                case 1:
//                    return new Rect(offset, offset * 3, w, h);
//                case 2:
//                    return new Rect(offset * 2, offset * 2, w, h);
//                case 3:
//                    return new Rect(offset * 3, offset * 1, w, h);
                case 0:
                    return new Rect(offset * index, offset * index, width, height);
                case 1:
                    return new Rect(offset * index, offset * index, width, height);
                case 2:
                    return new Rect(offset * index, offset * index, width, height);
                case 3:
                    return new Rect(offset * index, offset * index, width, height);
            }
        }
        return new Rect(0, 0, width, height);
    }
}
