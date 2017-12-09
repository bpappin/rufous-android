package pappin.rufous.widget.fresco;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

/**
 * Created by bpappin on 2017-12-07.
 */

public interface MiltiDraweeDataSource {

    @DrawableRes  int getFallbackPlaceholderResource();

    Drawable getPlaceholderDrawable(int i);

    int getImageCount();

    String getImageUrl(int i);
}
