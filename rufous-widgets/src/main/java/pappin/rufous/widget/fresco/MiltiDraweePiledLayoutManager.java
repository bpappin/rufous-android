package pappin.rufous.widget.fresco;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.MultiDraweeHolder;

import pappin.rufous.R;
import pappin.rufous.util.LogUtil;

/**
 * Created by bpappin on 2017-12-09.
 */

public class MiltiDraweePiledLayoutManager extends MiltiDraweeAbstractLayoutManager {

    private static final String TAG = LogUtil.tag("MiltiDraweePiledLayoutManager");
    @DimenRes
    private int imageOffset = R.dimen.rufous_multidraweeview_stacked_layout_offset;


    public MiltiDraweePiledLayoutManager(Context context, @DimenRes int offset, boolean roundAsCircle) {
        super(context, roundAsCircle);
        this.imageOffset = offset;
    }

    public MiltiDraweePiledLayoutManager(Context context) {
        this(context, R.dimen.rufous_multidraweeview_stacked_layout_offset, false);
    }

    public MiltiDraweePiledLayoutManager(Context context, boolean roundAsCircle) {
        this(context, R.dimen.rufous_multidraweeview_stacked_layout_offset, roundAsCircle);
    }

    @Override
    public void configureDrawees(GenericDraweeHierarchyBuilder hierarchyBuilder) {
        super.configureDrawees(hierarchyBuilder);
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
    }

    @Override
    public Rect getDrawablePosition(MultiDraweeView multiDraweeView, MultiDraweeHolder<GenericDraweeHierarchy> multiDraweeHolder, int index) {
        int offset = (int) dipToPixels(getContext().getResources().getDimension(imageOffset));
        int width = multiDraweeView.getMeasuredWidth();
        int height = multiDraweeView.getMeasuredHeight();

        if (multiDraweeHolder.size() == 1) {
            return new Rect(0, 0, width, height);
        } else if (multiDraweeHolder.size() >= 2 && multiDraweeHolder.size() <= 4) {
            return new Rect(offset * index, offset * index, width - (offset * index), height - (offset * index));

        }
        return new Rect(0, 0, width, height);
    }
}
