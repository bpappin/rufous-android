package pappin.rufous.widget.fresco;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.MultiDraweeHolder;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import pappin.rufous.util.LogUtil;
import pappin.rufous.util.StringUtil;


public class MultiDraweeView extends View {

    public static final String TAG = LogUtil.tag("MultiDraweeView");
    public static final boolean DBG = false;
    private final MultiDraweeHolder<GenericDraweeHierarchy> mMultiDraweeHolder = new MultiDraweeHolder<>();
    private int mColumnCount = 1;
    private int mSpaceSize;
    //    private List<String> mUris = new ArrayList<>();
//    private String[] mUris = new String[0];
    private int drawnTimes;
    private int drawnDuration;
    //    private MiltiDraweeDataSource dataSourceX;
    private MiltiDraweeLayoutManager layoutManager;


    public MultiDraweeView(Context context) {
        super(context);
        init(context);
    }

    public MultiDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultiDraweeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        layoutManager = new MiltiDraweeTiledLayoutManager(context);
        mSpaceSize = (int) dipToPixels(context, 3f);
    }

//    public void setImageUris(List<String> uris, @DrawableRes int placeholderResId) {
//        setImageUris(new MultiDraweeAssetDataSource(getContext(), uris.toArray(new String[uris.size()])));
//    }


    public MiltiDraweeLayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(MiltiDraweeLayoutManager layoutManager) {
        if (layoutManager == null) {
            throw new IllegalArgumentException("The layout manager must not be null.");
        }
        this.layoutManager = layoutManager;
    }

    public void setImageUris(MiltiDraweeDataSource source) {
//        this.dataSourceX = source;
        if (source.getImageCount() == 0) {
            return;
        }
        mMultiDraweeHolder.clear();


        GenericDraweeHierarchyBuilder hierarchyBuilder =
                new GenericDraweeHierarchyBuilder(getResources());
        hierarchyBuilder.setPlaceholderImage(source.getFallbackPlaceholderResource());
        hierarchyBuilder.setFadeDuration(0);

        layoutManager.configureDrawees(hierarchyBuilder);

        drawnTimes = 0;
        drawnDuration = 0;

        if (DBG) {
            Log.i(TAG, "Processing " + source.getImageCount() + " urls.");
        }

//        this.mUris = source.getImageUrls();
        for (int i = 0; i < source.getImageCount(); i++) {

            DraweeHolder<GenericDraweeHierarchy> draweeHolder = DraweeHolder.create(hierarchyBuilder.build(), getContext());

            String uriStr = source.getImageUrl(i);
            if (StringUtil.isEmpty(uriStr)) {
//                setupPlaceholder(source.getPlaceholderDrawable(i), draweeHolder);
                draweeHolder
                        .getHierarchy()
                        .setPlaceholderImage(source.getPlaceholderDrawable(i));

                DraweeController controller = Fresco
                        .newDraweeControllerBuilder()
                        .setOldController(draweeHolder
                                .getController())
                        .build();
                draweeHolder.setController(controller);
                draweeHolder
                        .getTopLevelDrawable()
                        .setCallback(this);
            } else {
                Uri uri = Uri.parse(uriStr);

                if (DBG) {
                    Log.i(TAG, "Processing url: " + uri.toString());
                }


                ImageRequest imageRequest =
                        ImageRequestBuilder
                                .newBuilderWithSource(uri)
                                //                            .setResizeOptions(options)
                                .setProgressiveRenderingEnabled(false)
                                .build();

                draweeHolder
                        .getHierarchy()
                        .setPlaceholderImage(source.getPlaceholderDrawable(i));
//                    .setPlaceholderImage(placeholderResId);


                DraweeController controller = Fresco
                        .newDraweeControllerBuilder()
                        .setImageRequest(imageRequest)
                        .setOldController(draweeHolder
                                .getController())
                        .build();
                draweeHolder.setController(controller);
                draweeHolder
                        .getTopLevelDrawable()
                        .setCallback(this);
            }


            mMultiDraweeHolder.add(draweeHolder);
        }

        if (mMultiDraweeHolder.size() == 0) {
            if (DBG) {
                Log.i(TAG, "No urls to draw, seting up default placeholder...");
            }
            DraweeHolder<GenericDraweeHierarchy> draweeHolder = DraweeHolder.create(hierarchyBuilder.build(), getContext());

            setupPlaceholder(source, draweeHolder);

            mMultiDraweeHolder.add(draweeHolder);
        }

        requestLayout();
    }

    private void setupPlaceholder(MiltiDraweeDataSource source, DraweeHolder<GenericDraweeHierarchy> draweeHolder) {
        draweeHolder
                .getHierarchy()
                .setPlaceholderImage(source.getFallbackPlaceholderResource());

        DraweeController controller = Fresco
                .newDraweeControllerBuilder()
                .setOldController(draweeHolder
                        .getController())
                .build();
        draweeHolder.setController(controller);
        draweeHolder
                .getTopLevelDrawable()
                .setCallback(this);
    }


    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        detach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        attach();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getMeasuredWidth() == 0 && getMeasuredHeight() == 0) {
            return;
        }
        long start = System.currentTimeMillis();
        super.onDraw(canvas);

        for (int i = 0; i < mMultiDraweeHolder.size(); i++) {

            Rect rect = getDrawablePosition(i);

            Drawable drawable = mMultiDraweeHolder
                    .get(i)
                    .getTopLevelDrawable();
            if (drawable != null) {
                drawable.setBounds(rect);
                drawable.draw(canvas);
            }
        }
        long end = System.currentTimeMillis();
        drawnTimes++;
        drawnDuration += (end - start);
        if (DBG) {
            Log.i(TAG, this.hashCode() + " onDraw() method duration = " + drawnDuration + "ms" + " draw " + drawnTimes + " times" + " drawee count " + mMultiDraweeHolder.size());
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        attach();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        detach();
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable drawable) {
        int dirtyIndex = -1;
        for (int i = 0; i < mMultiDraweeHolder.size(); i++) {
            if (drawable == mMultiDraweeHolder
                    .get(i)
                    .getTopLevelDrawable()) {
                dirtyIndex = i;
                break;
            }
        }

        if (dirtyIndex != -1) {
            Rect invalidateRect = getDrawablePosition(dirtyIndex);
            //            Rect invalidateRect = getBoundsFromIndex(dirtyIndex);
            if (invalidateRect.height() != 0 && invalidateRect.width() != 0) {
                invalidate(invalidateRect);
                if (DBG) {
                    Log.i(TAG, this.hashCode() + " drawable code " + drawable.hashCode() + " invalidateDrawable " + invalidateRect.flattenToString());
                }
            }

        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        if (DBG) {
            Log.i(TAG, this.hashCode() + "verifyDrawable");
        }
        return mMultiDraweeHolder.verifyDrawable(who) || super.verifyDrawable(who);
    }

    void attach() {
        mMultiDraweeHolder.onAttach();
    }

    void detach() {
        for (int i = 0; i < mMultiDraweeHolder.size(); i++) {
            mMultiDraweeHolder
                    .get(i)
                    .getTopLevelDrawable()
                    .setCallback(null);
        }
        mMultiDraweeHolder.onDetach();
    }

//    private Rect getDrawableSize(int index) {
//        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
//        if (mMultiDraweeHolder.size() == 1) {
//            return new Rect(0, 0, width, height);
//        } else if (mMultiDraweeHolder.size() == 2) {
//            return new Rect(0, 0, width, height / 2);
//        } else if (mMultiDraweeHolder.size() == 3) {
//            switch (index) {
//                case 0:
//                    return new Rect(0, 0, width, height / 2);
//                default:
//                    return new Rect(0, 0, width / 2, height / 2);
//            }
//        }
//        if (mMultiDraweeHolder.size() == 4) {
//            return new Rect(0, 0, width / 2, height / 2);
//        }
//        return new Rect(0, 0, width, height);
//    }

//    private int getIndexFromPoint(float x, float y) {
//        if (mUris.size() == 1) {
//            return 0;
//        }
//
//        int rowIndex = 0, columnIndex = 0, itemIndex;
//
//        for (int i = 0; i < mColumnCount; i++) {
//            int left = mDraweeSize * i + mSpaceSize * i;
//            int right = left + mDraweeSize;
//
//            if (x >= left && x < right) {
//                columnIndex = i;
//            }
//        }
//
//        while (true) {
//            int top = rowIndex * mDraweeSize + rowIndex * mSpaceSize;
//            int bottom = top + mDraweeSize;
//            if (y >= top && y < bottom) {
//                break;
//            }
//            rowIndex++;
//        }
//
//        itemIndex = rowIndex * mColumnCount + columnIndex;
//        if (itemIndex >= mUris.size()) {
//            itemIndex = -1;
//        }
//        return itemIndex;
//    }

    private Rect getDrawablePosition(int index) {
        return layoutManager.getDrawablePosition(this, mMultiDraweeHolder, index);

    }

    public float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context
                .getResources()
                .getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public interface OnItemClickListener {
        void onPicClick(int picIndex, Rect itemRect);
    }
}
