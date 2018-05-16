package lib.smart.fragmentv4;

import android.view.animation.Animation;

/**
 * Created by Augustine on 2018/5/15.
 * <p>
 * email:nice_ohoh@163.com
 */

public class FragmentModel {

    public String tag;

    public int index;

    public int containerViewId;

    public boolean isRoot = true;

    //嵌套关系使用
    public String parentTag;

    //兄弟关系使用
    public String foreFragmentTag;

    public boolean isHidden = true;

    public boolean swipeBack = false;

    //是否初始化数据
    public boolean isInit = false;

    //是否开启进场动画，仅临时生效
    public boolean enterAnim = true;

    public boolean exitAnim = true;

    public boolean popEnterAnim = true;

    public boolean popExitAnim = true;

    public Animation enterAnimation;

    public Animation exitAnimation;

    //重新进场动画
    public Animation popEnterAnimation;

    //处于隐藏状态显示的动画
    public Animation popExitAnimation;

}
