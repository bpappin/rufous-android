package pappin.rufous.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import pappin.rufous.util.StringUtil;
import pappin.rufous.widget.drawable.LetterTileDrawable;

//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.interfaces.DraweeController;
//import com.facebook.drawee.view.SimpleDraweeView;

//import fiixsoftware.ux.tiles.LetterTileProvider;

/**
 * Created by bpappin on 2017-04-13.
 */

public class ImageViewHelper {
    private static ImageViewUrlHandler urlHandler;

    public static ImageViewUrlHandler getUrlHandler() {
        return urlHandler;
    }

    public static void setUrlHandler(ImageViewUrlHandler urlHandler) {
        ImageViewHelper.urlHandler = urlHandler;
    }

    public static void setImageOrDefaultRound(Context context, ImageView view, String imageUrl, String displayName) {
        if (StringUtil.isNotEmpty(imageUrl)) {
            setImageFromUrl(view, imageUrl);
        } else {
            setImageFromTile(context, view, displayName, true);

        }
    }

    public static void setImageOrDefaultRoundWithDefault(Context context, ImageView view, String imageUrl, String displayName, String defaultName) {
        if (StringUtil.isNotEmpty(imageUrl)) {
            setImageFromUrl(view, imageUrl);
        } else if (StringUtil.isNotEmpty(displayName)) {
            setImageFromTile(context, view, displayName, true);
        } else {
            setImageFromTile(context, view, defaultName, true);
        }
    }

    public static void setImageFromTile(Context context, ImageView view, String displayName, boolean round) {
        // XXX Drawee doesnt make it easy to set dynamic bitmap drawables.
        Drawable letterTile = null;

        if (round) {
            letterTile = new LetterTileDrawable(context.getResources()).setIsCircular(true).setLetterAndColorFromContactDetails(displayName, displayName);
        } else {
            letterTile = new LetterTileDrawable(context.getResources()).setIsCircular(false).setLetterAndColorFromContactDetails(displayName, displayName);
        }

        view.setImageDrawable(letterTile);
    }

    public static void setImageFromUrl(ImageView view, String imageUrl) {


        if (urlHandler == null) {
            try {
                urlHandler = new ImageViewUrlHandlerFresco();
            } catch (NoClassDefFoundError e) {
                // ignored, not in the classpath.
            }
        }
        if (urlHandler == null) {
            try {
                urlHandler = new ImageViewUrlHandlerGlide();
            } catch (NoClassDefFoundError e) {
                // ignored, not in the classpath.
            }
        }
        if (urlHandler == null) {
            urlHandler = new DefaultImageViewUrlHandler();
        }


        if (urlHandler != null) {
            urlHandler.setImageFromUrl(view, imageUrl);
        }
        // XXX we should add code that checks for some of the common caching libs, like picasso, fresco, and Glide.
//        Uri uri = Uri.parse(imageUrl);
//        view.setImageURI(uri);

    }

    public interface ImageViewUrlHandler {
        void setImageFromUrl(ImageView view, String imageUrl);
    }

    static class DefaultImageViewUrlHandler implements ImageViewUrlHandler {
        @Override
        public void setImageFromUrl(ImageView view, String imageUrl) {
            Uri uri = Uri.parse(imageUrl);
            view.setImageURI(uri);
        }
    }
}
