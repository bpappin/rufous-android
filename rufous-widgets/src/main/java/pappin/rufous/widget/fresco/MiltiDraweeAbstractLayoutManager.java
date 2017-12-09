package pappin.rufous.widget.fresco;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;

/**
 * Created by bpappin on 2017-12-09.
 */

public abstract class MiltiDraweeAbstractLayoutManager implements MiltiDraweeLayoutManager {

    private Context context;
    private boolean roundAsCircle = false;

    public MiltiDraweeAbstractLayoutManager(Context context) {
        this.context = context;
    }

    public MiltiDraweeAbstractLayoutManager(Context context, boolean roundAsCircle) {
        this(context);
        this.roundAsCircle = roundAsCircle;
    }

    @Override
    public void configureDrawees(GenericDraweeHierarchyBuilder hierarchyBuilder) {
        hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        if (isRoundAsCircle()) {
            hierarchyBuilder.setRoundingParams(RoundingParams.asCircle().setBorder(Color.WHITE, 4));
        }
    }

    public float dipToPixels(float dipValue) {
        DisplayMetrics metrics = getContext()
                .getResources()
                .getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public Context getContext() {
        return context;
    }

    public boolean isRoundAsCircle() {
        return roundAsCircle;
    }

    public void setRoundAsCircle(boolean roundAsCircle) {
        this.roundAsCircle = roundAsCircle;
    }
}
