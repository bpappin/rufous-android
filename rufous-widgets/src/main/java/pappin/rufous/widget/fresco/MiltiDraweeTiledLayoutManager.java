package pappin.rufous.widget.fresco;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.MultiDraweeHolder;

/**
 * Created by bpappin on 2017-12-09.
 */

public class MiltiDraweeTiledLayoutManager extends MiltiDraweeAbstractLayoutManager {

    public MiltiDraweeTiledLayoutManager(Context context) {
        super(context);
    }

    public MiltiDraweeTiledLayoutManager(Context context, boolean roundAsCircle) {
        super(context, roundAsCircle);
    }

    @Override
    public void configureDrawees(GenericDraweeHierarchyBuilder hierarchyBuilder) {
        super.configureDrawees(hierarchyBuilder);
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
//        if (isRoundAsCircle()) {
//            hierarchyBuilder.setRoundingParams(RoundingParams.asCircle().setBorder(Color.WHITE, 4));
//        }
    }

    @Override
    public Rect getDrawablePosition(MultiDraweeView multiDraweeView, MultiDraweeHolder<GenericDraweeHierarchy> multiDraweeHolder, int index) {
        int width = multiDraweeView.getMeasuredWidth();
        int height = multiDraweeView.getMeasuredHeight();
        if (multiDraweeHolder.size() == 1) {
            return new Rect(0, 0, width, height);
        } else if (multiDraweeHolder.size() == 2) {
            switch (index) {
                case 0:
                    return new Rect(0, 0, width / 2, height);
                case 1:
                    return new Rect(width / 2, 0, width, height);
            }
        } else if (multiDraweeHolder.size() == 3) {
            switch (index) {
                case 0:
                    return new Rect(0, 0, width / 2, height);
                case 1:
                    return new Rect(width / 2, 0, width, height / 2);
                case 2:
                    return new Rect(width / 2, height / 2, width, height);
            }
        }
        if (multiDraweeHolder.size() == 4) {

            switch (index) {
                case 0:
                    return new Rect(0, 0, width / 2, height / 2);
                case 1:
                    return new Rect(0, height / 2, width / 2, height);
                case 2:
                    return new Rect(width / 2, 0, width, height / 2);
                case 3:
                    return new Rect(width / 2, height / 2, width, height);
            }
        }
        return new Rect(0, 0, width, height);
    }
}
