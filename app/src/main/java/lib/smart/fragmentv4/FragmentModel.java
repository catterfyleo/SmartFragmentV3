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

    //如果tag == brotherParentTag，isRoot = true
    public boolean isRoot = true;

    //嵌套关系使用
    public String parentTag;

    //兄弟关系使用
    public String foreFragmentTag;

    //兄长
    //如果tag == brotherParentTag，表示自身是兄长
    public String brotherParentTag;

    public boolean isHidden = true;

    public boolean swipeBack = true;

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

    public String simpleName;

    public String className;


    @Override
    public String toString() {
        StringBuffer info = new StringBuffer();
        info.append("tag : "+tag);
        info.append("index : "+index);
        info.append("containerViewId : "+containerViewId);
        info.append("isRoot : "+isRoot);
        info.append("parentTag : "+parentTag);
        info.append("isHidden : "+isHidden);
        info.append("swipeBack : "+swipeBack);
        info.append("isInit : "+isInit);
        info.append("simpleName : "+simpleName);
        info.append("className : "+className);
        return info.toString();
    }
}
