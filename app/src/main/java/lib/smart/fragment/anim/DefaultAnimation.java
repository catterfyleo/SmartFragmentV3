package lib.smart.fragment.anim;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import lib.smart.fragment.R;

/**
 * Created by Augustine on 2018/5/11.
 * <p>
 * email:nice_ohoh@163.com
 */

public class DefaultAnimation extends FragmentAnimation {

    @Override
    public Animation enterAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.h_fragment_enter);
    }

    @Override
    public Animation exitAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.h_fragment_exit);
    }

    @Override
    public Animation popEnterAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.h_fragment_pop_enter);
    }

    @Override
    public Animation popExitAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.h_fragment_pop_exit);
    }
}
