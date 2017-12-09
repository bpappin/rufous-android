package pappin.rufous.widget.fresco;

import android.graphics.Rect;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.MultiDraweeHolder;

/**
 * Created by bpappin on 2017-12-09.
 */

public class MiltiDraweeTiledLayoutManager implements MiltiDraweeLayoutManager {

//    private Rect getDrawablePosition(int index) {
//
//        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
//        if (mMultiDraweeHolder.size() == 1) {
//            return new Rect(0, 0, width, height);
//        } else if (mMultiDraweeHolder.size() == 2) {
//            switch (index) {
//                case 0:
//                    return new Rect(0, 0, width / 2, height);
//                case 1:
//                    return new Rect(width / 2, 0, width, height);
//            }
//        } else if (mMultiDraweeHolder.size() == 3) {
//            switch (index) {
//                case 0:
//                    return new Rect(0, 0, width / 2, height);
//                case 1:
//                    return new Rect(width / 2, 0, width, height / 2);
//                case 2:
//                    return new Rect(width / 2, height / 2, width, height);
//            }
//        }
//        if (mMultiDraweeHolder.size() == 4) {
//
//            switch (index) {
//                case 0:
//                    return new Rect(0, 0, width / 2, height / 2);
//                case 1:
//                    return new Rect(0, height / 2, width / 2, height);
//                case 2:
//                    return new Rect(width / 2, 0, width, height / 2);
//                case 3:
//                    return new Rect(width / 2, height / 2, width, height);
//            }
//        }
//        return new Rect(0, 0, width, height);
//    }

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
