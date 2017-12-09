package pappin.rufous.widget.fresco;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.content.res.AppCompatResources;

import pappin.rufous.R;

/**
 * Created by bpappin on 2017-12-07.
 */

public class MultiDraweeBasicDataSource implements MiltiDraweeDataSource {
    private int placeholderImageRes = R.drawable.ic_placeholder_broken_image;
    private int colourArrayResId;
    private Context context;
    private String[] imageUrls = new String[0];

    public MultiDraweeBasicDataSource(Context context, String[] imageUrls) {
        this(context, imageUrls, R.drawable.ic_placeholder_broken_image);
    }

    /**
     * @param context             the application context the componetn is working in.
     * @param imageUrls           an array of names and URLs, with the names at index 0 and the URLs at index 1, so that String[..] = {name, url}.
     * @param placeholderImageRes the default fallback placeholder image, if nothing can be generated.
     */
    public MultiDraweeBasicDataSource(Context context, String[] imageUrls, @DrawableRes int placeholderImageRes) {
        super();
        this.context = context;
        this.imageUrls = imageUrls;
        this.placeholderImageRes = placeholderImageRes;
        this.colourArrayResId = R.array.rufous_text_tile_letter_colors;
    }

    @Override
    public int getFallbackPlaceholderResource() {
        return placeholderImageRes;
    }

    @Override
    public Drawable getPlaceholderDrawable(int i) {
        return AppCompatResources.getDrawable(context, getFallbackPlaceholderResource());
    }

    @Override
    public int getImageCount() {
        return imageUrls.length;
    }

//    @Override
//    public String[] getImageUrls() {
//        return imageNamesAndUrls[];
//    }

    @Override
    public String getImageUrl(int i) {
        return imageUrls[i]; //getImageUrls()[i];
    }
}
