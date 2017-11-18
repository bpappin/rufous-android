package pappin.rufous.graphics;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import pappin.rufous.helper.ResHelper;


public class BitmapUtil extends ResHelper {
    private static final String TAG = "BitmapUtil";

    public interface BitmapReceiver {
        /**
         * May return null if the bitmap failed to load.
         *
         * @param bitmap
         */
        public void onBitmapReceived(Bitmap bitmap);
    }

    /**
     * gets teh greyscale colour for a colour.
     * @param color
     * @return
     */
    public static int getGreyColor(int color) {
        final int alpha = color & 0xFF000000;
        final int r = (color >> 16) & 0xFF;
        final int g = (color >> 8) & 0xFF;
        final int b = color & 0xFF;

        // see: https://en.wikipedia.org/wiki/Relative_luminance
        final int luminance = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);

        return alpha | luminance << 16 | luminance << 8 | luminance;
    }

    /**
     * scale and center crop image
     */
    public static Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        return ThumbnailUtils.extractThumbnail(source, newWidth, newHeight);
    }

    /**
     * @param aURL
     * @param receiver
     * @param filters
     * @deprecated we use Glide now.
     */
    @Deprecated
    public static void getRemoteImage(final URL aURL,
                                      final BitmapReceiver receiver, final BitmapFilter... filters) {

        // Executors.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    final URLConnection conn = aURL.openConnection();
                    conn.connect();
                    final BufferedInputStream bis = new BufferedInputStream(
                            conn.getInputStream());
                    final Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    bis.close();

                    Bitmap output = bitmap;
                    if (output != null) {
                        for (int i = 0; i < filters.length; i++) {
                            output = filters[i].filter(output);
                        }
                    }
                    if (receiver != null) {
                        receiver.onBitmapReceived(output);
                    }
                    // return output;
                } catch (IOException e) {
                    Log.e(TAG, "Unable to load bitmap.", e);
                }
                if (receiver != null) {
                    receiver.onBitmapReceived(null);
                }
                // return null;

            }
        });

        thread.start();

    }

    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                                            bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xFF424242;
        final Paint paint = new Paint();
        final Rect drawRegion = new Rect(0, 0, bitmap.getWidth(),
                                         bitmap.getHeight());
        final RectF rectF = new RectF(drawRegion);
        // final float roundPx = 12;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, output.getHeight() / 2,
                             output.getWidth() / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, drawRegion, drawRegion, paint);

        return output;
    }
}
