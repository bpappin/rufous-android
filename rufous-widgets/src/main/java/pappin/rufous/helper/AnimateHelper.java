package pappin.rufous.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by bpappin on 2017-02-07.
 */

public class AnimateHelper {

    public static void animateViewVisibility(final View view, final int visibility) {
        // cancel runnning animations and remove and listeners
        view.animate().cancel();
        view.animate().setListener(null);

        // animate making view visible
        if (visibility == View.VISIBLE)
        {
            view.animate().alpha(1f).start();
            view.setVisibility(View.VISIBLE);
        }
        // animate making view hidden (HIDDEN or INVISIBLE)
        else
        {
            view.animate().setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    view.setVisibility(visibility);
                }
            }).alpha(0f).start();
        }
    }

    public static void toggleSlideUp (Context context, final View drawer, final ImageView indicator, @DrawableRes final int upDrawable, @DrawableRes final int downDrawable) {
        if (drawer.getVisibility() == View.GONE) {
            drawer
                    .animate()
                    .translationY(drawer.getHeight())
                    .alpha(1.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart (Animator animation) {
                            super.onAnimationStart(animation);
                            drawer.setVisibility(View.VISIBLE);
                            drawer.setAlpha(0.0f);
                        }

                        public void onAnimationEnd (Animator animation) {
                            super.onAnimationEnd(animation);
                            indicator.setImageResource(upDrawable);
                        }
                    });
        } else {
            drawer
                    .animate()
                    .translationY(0)
                    .alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd (Animator animation) {
                            super.onAnimationEnd(animation);
                            drawer.setVisibility(View.GONE);
                            indicator.setImageResource(downDrawable);
                        }
                    });


        }
    }
}
