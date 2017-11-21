package pappin.rufous.helper;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by bpappin on 2017-07-29.
 */
class ImageViewUrlHandlerGlide extends ImageViewHelper.DefaultImageViewUrlHandler {
    @Override
    public void setImageFromUrl(ImageView view, String imageUrl) {
        Glide.with(view).load(imageUrl).into(view);
    }
}
