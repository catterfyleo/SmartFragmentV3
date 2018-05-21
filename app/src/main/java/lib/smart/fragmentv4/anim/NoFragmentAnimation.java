package lib.smart.fragmentv4.anim;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import lib.smart.fragment.R;

/**
 * Created by Augustine on 2018/5/21.
 * <p>
 * email:nice_ohoh@163.com
 */

public class NoFragmentAnimation extends FragmentAnimator {
    @Override
    public Animation enterAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.no_anim);
    }

    @Override
    public Animation exitAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.no_anim);
    }

    @Override
    public Animation popEnterAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.no_anim);
    }

    @Override
    public Animation popExitAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.no_anim);
    }
}
