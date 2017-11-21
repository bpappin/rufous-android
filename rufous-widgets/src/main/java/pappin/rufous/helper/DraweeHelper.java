package pappin.rufous.helper;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import pappin.rufous.graphics.LetterTileProvider;
import pappin.rufous.util.StringUtil;

/**
 * Created by bpappin on 2017-04-13.
 */

public class DraweeHelper {
    public static void setImageOrDefaultRound(Context context, SimpleDraweeView view, String imageUrl, String displayName) {
        if (StringUtil.isNotEmpty(imageUrl)) {
            setImageFromUrl(view, imageUrl);
        } else {
            setImageFromTile(context, view, displayName, true);

        }
    }

    public static void setImageOrDefaultRoundWithDefault(Context context, SimpleDraweeView view, String imageUrl, String displayName, String defaultName) {
        if (StringUtil.isNotEmpty(imageUrl)) {
            setImageFromUrl(view, imageUrl);
        } else if (StringUtil.isNotEmpty(displayName)) {
            setImageFromTile(context, view, displayName, true);
        } else {
            setImageFromTile(context, view, defaultName, true);
        }
    }

    public static void setImageFromTile(Context context, SimpleDraweeView view, String displayName, boolean round) {
        // XXX Drawee doesnt make it easy to set dynamic bitmap drawables.
        // FIXME we're circumventing Drawee to set this. review how it should be done.
        BitmapDrawable letterTile = null;
        if (round) {
            letterTile = LetterTileProvider.createRoundLetterTile(context, displayName);
        } else {
            letterTile = LetterTileProvider.createSquareLetterTile(context, displayName);
        }
        view.setImageDrawable(letterTile);
    }

    public static void setImageFromUrl(SimpleDraweeView drawee, String imageUrl) {
        Uri uri = Uri.parse(imageUrl);
        DraweeController controller = Fresco
                .newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setOldController(drawee.getController())
                //                    .setControllerListener(listener)
                .build();
        drawee.setController(controller);
    }

}
