package pappin.rufous.widget.fresco;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;

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
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
    }

    @Override
    public Rect getDrawablePosition(MultiDraweeView multiDraweeView, MultiDraweeHolder<GenericDraweeHierarchy> multiDraweeHolder, int index) {
        int offset = (int)dipToPixels(getContext()
                                              .getResources()
                                              .getDimension(imageOffset));
        int width = multiDraweeView.getMeasuredWidth();
        int height = multiDraweeView.getMeasuredHeight();
        if (isRoundAsCircle()) {
            return getRectForCircularDrawee(multiDraweeHolder, index, offset, width, height);
        }
        return getRectForSquareDrawee(multiDraweeHolder, index, offset, width, height);

    }

    private Rect getRectForSquareDrawee(MultiDraweeHolder<GenericDraweeHierarchy> multiDraweeHolder, int index, int offset, int width, int height) {
        if (multiDraweeHolder.size() == 1) {
            return new Rect(0, 0, width, height);
        } else if (multiDraweeHolder.size() == 2) {
            switch (index) {
                case 0:
                    return new Rect(0, 0, width - (offset * 2), height - (offset * 2));
                case 1:
                    return new Rect(offset, offset, width - offset, height - offset);
            }
        } else if (multiDraweeHolder.size() == 3) {
            switch (index) {
                case 0:
                    return new Rect(0, 0, width - (offset * 3), height - (offset * 3));
                case 1:
                    return new Rect(offset, offset, width - (offset * 2), height - (offset * 2));
                case 2:
                    return new Rect(offset * 2, offset * 2, width - offset, height - offset);
            }
        }
        if (multiDraweeHolder.size() == 4) {
            //            Log.d(TAG, "count=" + multiDraweeHolder.size() + ", height=" + height + ", width=" + width + ", offset=" + offset);
            switch (index) {
                case 0:
                    return new Rect(0, 0, width - (offset * 4), height - (offset * 4));
                case 1:
                    return new Rect(offset, offset, width - (offset * 3), height - (offset * 3));
                case 2:
                    return new Rect(offset * 2, offset * 2, width - (offset * 2), height - (offset * 2));
                case 3:
                    return new Rect(offset * 3, offset * 3, width - offset, width - offset);
            }
        }
        return new Rect(0, 0, width, height);
    }

    @NonNull
    private Rect getRectForCircularDrawee(MultiDraweeHolder<GenericDraweeHierarchy> multiDraweeHolder, int index, int offset, int width, int height) {
        if (multiDraweeHolder.size() == 1) {
            return new Rect(0, 0, width, height);
        } else if (multiDraweeHolder.size() == 2) {
            switch (index) {
                case 0:
                    return new Rect(0, 0, width - offset, height);
                case 1:
                    return new Rect(offset, 0, width, height);
            }
        } else if (multiDraweeHolder.size() == 3) {
            switch (index) {
                case 0:
                    return new Rect(0, 0, width - (offset * 2), height);
                case 1:
                    return new Rect(offset, 0, width - offset, height);
                case 2:
                    return new Rect(offset * 2, 0, width, height);
            }
        }
        if (multiDraweeHolder.size() == 4) {
            //            Log.d(TAG, "count=" + multiDraweeHolder.size() + ", height=" + height + ", width=" + width + ", offset=" + offset);
            switch (index) {
                case 0:
                    return new Rect(0, 0, width - (offset * 3), height);
                case 1:
                    return new Rect(offset, 0, width - (offset * 2), height);
                case 2:
                    return new Rect(offset * 2, 0, width - (offset), height);
                case 3:
                    return new Rect(offset * 3, 0, width, height);
            }
        }
        return new Rect(0, 0, width, height);
    }
}
