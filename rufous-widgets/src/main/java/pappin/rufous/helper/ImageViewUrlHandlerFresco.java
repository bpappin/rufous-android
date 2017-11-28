package pappin.rufous.helper;

import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by bpappin on 2017-07-29.
 */
class ImageViewUrlHandlerFresco extends ImageViewHelper.DefaultImageViewUrlHandler {
    @Override
    public void setImageFromUrl(ImageView view, String imageUrl) {
        if (view instanceof SimpleDraweeView) {
            SimpleDraweeView drawee = (SimpleDraweeView)view;

            DraweeController controller = Fresco
                    .newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setUri(imageUrl)
                    .setTapToRetryEnabled(true)
                    .setOldController(drawee.getController())
                    // .setControllerListener(listener)
                    .build();
            drawee.setController(controller);
        } else {
            super.setImageFromUrl(view, imageUrl);
        }
    }
}
