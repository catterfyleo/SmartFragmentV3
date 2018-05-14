package lib.smart.fragment;

import android.view.animation.Animation;

/**
 * Created by Augustine on 2018/5/11.
 * <p>
 * email:nice_ohoh@163.com
 */

public class FragmentModel {

    public String tag;

    public int index;

    public int containerViewId;

    //是否属于一个独立的fragment
    public boolean isRoot;

    //如果是fragment嵌套
    public String parentTag;

    //前一个入栈的root fragment
    public String foreFragmentTag;

    public boolean isHidden;

    public boolean swipeBack;

    //是否初始化数据
    public boolean isInitList;

    //是否开启进场动画
    public boolean enterAnim;

    public boolean exitAnim;

    public boolean popEnterAnim;

    public boolean popExitAnim;

    public Animation enterAnimation;

    public Animation exitAnimation;

    //重新进场动画
    public Animation popEnterAnimation;

    //处于隐藏状态显示的动画
    public Animation popExitAnimation;

}
