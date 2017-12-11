package pappin.rufous.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import pappin.rufous.R;
import pappin.rufous.util.LogUtil;

/**
 * Created by bpappin on 2017-12-11.
 */

public class LetterIndexItemDecoration extends RecyclerView.ItemDecoration {
    private final String TAG = LogUtil.tag(LetterIndexItemDecoration.class.getSimpleName());

    private final Context context;
    private float textSize = 50;
    private int groupSpacing = 0;
    private int itemsInGroup = 3;


    private Paint paint = new Paint();

    public LetterIndexItemDecoration(Context context, @ColorRes int textColour, @DimenRes int textSize) {
        this.context = context;
        this.textSize = context.getResources().getDimension(textSize);
        paint.setColor(context.getResources().getColor(textColour));
        paint.setTextSize(context.getResources().getDimension(textSize));
        paint.setFakeBoldText(true);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        String lastLetter = "";

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
//            Log.d(TAG, "View type: " + view.getClass().getSimpleName() + ", tag: " + view.getTag(R.id.name));
            String tag = (String) view.getTag(android.R.id.text1);

            if (tag != null && tag.length() > 0) {
                String currentLetter = tag.substring(0, 1);
                if (!lastLetter.equalsIgnoreCase(currentLetter)) {
                    lastLetter = currentLetter;
                    c.drawText(lastLetter, view.getLeft(),
                            view.getTop() + view.getHeight() / 2 + textSize / 2, paint);
                }

                int position = parent.getChildAdapterPosition(view);
                if (position % itemsInGroup == 0) {
//                c.drawColor(textColour);
//                    c.drawText("Group " + (position / itemsInGroup + 1), view.getLeft(),
//                            view.getTop() - groupSpacing / 2 + textSize / 3, paint);
                }
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) % itemsInGroup == 0) {
            outRect.set(0, groupSpacing, 0, 0);
        }
    }
}
