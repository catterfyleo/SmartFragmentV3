package lib.smart.fragment.anim;

import android.content.Context;
import android.view.animation.Animation;

/**
 * Created by Augustine on 2018/5/11.
 * <p>
 * email:nice_ohoh@163.com
 */

public abstract class FragmentAnimation {

    public abstract Animation enterAnimation(Context context);

    public abstract Animation exitAnimation(Context context);

    public abstract Animation popEnterAnimation(Context context);

    public abstract Animation popExitAnimation(Context context);

}
