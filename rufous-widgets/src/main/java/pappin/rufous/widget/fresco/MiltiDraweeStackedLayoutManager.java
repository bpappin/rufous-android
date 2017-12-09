package pappin.rufous.widget.fresco;

import android.graphics.Rect;
import android.support.annotation.DimenRes;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.MultiDraweeHolder;

import pappin.rufous.R;

/**
 * Created by bpappin on 2017-12-09.
 */

public class MiltiDraweeStackedLayoutManager implements MiltiDraweeLayoutManager {

    @DimenRes
    private int imageOffset = R.dimen.rufous_multidraweeview_stacked_layout_offset;

    public MiltiDraweeStackedLayoutManager(@DimenRes int offset) {
        super();
        this.imageOffset = offset;
    }

    public MiltiDraweeStackedLayoutManager() {
        this(R.dimen.rufous_multidraweeview_stacked_layout_offset);
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
                    return new Rect(imageOffset, 0, width - imageOffset, height - imageOffset);
                case 1:
                    return new Rect(0, imageOffset, width - imageOffset, height - imageOffset);
            }
        } else if (multiDraweeHolder.size() == 3) {
            switch (index) {
                case 0:
                    return new Rect(imageOffset * 2, 0, width - (imageOffset * 2), height - (imageOffset * 2));
                case 1:
                    return new Rect(imageOffset, imageOffset, width - (imageOffset * 2), height - (imageOffset * 2));
                case 2:
                    return new Rect(0, imageOffset * 2, width - (imageOffset * 2), height - (imageOffset * 2));
            }
        }
        if (multiDraweeHolder.size() == 4) {

            switch (index) {
                case 0:
                    return new Rect(imageOffset * 3, 0, width - (imageOffset * 3), height - (imageOffset * 3));
                case 1:
                    return new Rect(imageOffset * 2, imageOffset, width - (imageOffset * 3), height - (imageOffset * 3));
                case 2:
                    return new Rect(imageOffset, imageOffset * 2, width - (imageOffset * 3), height - (imageOffset * 3));
                case 3:
                    return new Rect(0, imageOffset * 3, width - (imageOffset * 3), height - (imageOffset * 3));
            }
        }
        return new Rect(0, 0, width, height);
    }
}
