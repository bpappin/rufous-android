package pappin.rufous.widget.fresco;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.content.res.AppCompatResources;

import pappin.rufous.R;
import pappin.rufous.graphics.LetterTileProvider;
import pappin.rufous.util.StringUtil;

/**
 * Created by bpappin on 2017-12-07.
 */

public class MultiDraweeLetterTileDataSource implements MiltiDraweeDataSource {
    private final boolean roundTile;
    private int placeholderImageRes = R.drawable.ic_placeholder_person;
    private int colourArrayResId;
    private Context context;
    private String[][] imageNamesAndUrls = new String[0][0];

    public MultiDraweeLetterTileDataSource(Context context, String[][] imageNamesAndUrls) {
        this(context, imageNamesAndUrls, false, R.drawable.ic_placeholder_person);
    }

    public MultiDraweeLetterTileDataSource(Context context, String[][] imageNamesAndUrls, boolean roundTile) {
        this(context, imageNamesAndUrls, roundTile, R.drawable.ic_placeholder_person);
    }

    /**
     * @param context             the application context the componetn is working in.
     * @param imageNamesAndUrls   an array of names and URLs, with the names at index 0 and the URLs at index 1, so that String[..] = {name, url}.
     * @param roundTile           draw the placeholder initials image as a round or square.
     * @param placeholderImageRes the default fallback placeholder image, if nothing can be generated.
     */
    public MultiDraweeLetterTileDataSource(Context context, String[][] imageNamesAndUrls, boolean roundTile, @DrawableRes int placeholderImageRes) {
        super();
        this.context = context;
        this.imageNamesAndUrls = imageNamesAndUrls;
        this.roundTile = roundTile;
        this.placeholderImageRes = placeholderImageRes;
        this.colourArrayResId = R.array.rufous_text_tile_letter_colors;
    }

    @Override
    public int getFallbackPlaceholderResource() {
        return placeholderImageRes;
    }

    @Override
    public Drawable getPlaceholderDrawable(int i) {
        if (i < 0 || i >= getImageCount() || StringUtil.isEmpty(imageNamesAndUrls[0][i])) {
            return AppCompatResources.getDrawable(context, getFallbackPlaceholderResource());
        }
        if (roundTile) {
            return LetterTileProvider.createRoundLetterTile(context, colourArrayResId, imageNamesAndUrls[0][i]);
        } else {
            return LetterTileProvider.createSquareLetterTile(context, colourArrayResId, imageNamesAndUrls[0][i]);
        }
    }

    @Override
    public int getImageCount() {
        if (imageNamesAndUrls.length == 0) {
            return 0;
        }
        return imageNamesAndUrls[0].length;
    }

//    @Override
//    public String[] getImageUrls() {
//        return imageNamesAndUrls[];
//    }

    @Override
    public String getImageUrl(int i) {
        return imageNamesAndUrls[1][i]; //getImageUrls()[i];
    }
}
