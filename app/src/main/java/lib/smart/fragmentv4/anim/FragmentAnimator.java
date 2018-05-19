package lib.smart.fragmentv4.anim;

import android.content.Context;
import android.view.animation.Animation;

/**
 * Created by Augustine on 2018/5/15.
 * <p>
 * email:nice_ohoh@163.com
 */

public abstract class FragmentAnimator {

    public abstract Animation enterAnimation(Context context);

    public abstract Animation exitAnimation(Context context);

    public abstract Animation popEnterAnimation(Context context);

    public abstract Animation popExitAnimation(Context context);

}
