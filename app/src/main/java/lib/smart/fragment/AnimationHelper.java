package lib.smart.fragment;

import android.support.v4.view.ViewCompat;
import android.view.animation.Animation;

import lib.smart.fragment.anim.FragmentAnimation;

/**
 * Created by Augustine on 2018/5/14.
 * <p>
 * email:nice_ohoh@163.com
 */

public class AnimationHelper {

    private SmartActivity smartActivity;

    private SmartFragment fragment;

    private FragmentModel model;

    public AnimationHelper(SmartActivity smartActivity,SmartFragment fragment){
        this.fragment = fragment;
        model = smartActivity.findModelByTag(fragment.getTAG());
    }

    public Animation enterAnimation(int z){
        if(model.enterAnim){
            ViewCompat.setTranslationZ(fragment.getSwipeBackLayout(),z);
            fragment.getSwipeBackLayout().startAnimation(model.enterAnimation);
        }
        return model.enterAnimation;
    }

    public Animation exitAnimation(int z){
        if(model.exitAnim){
            ViewCompat.setTranslationZ(fragment.getSwipeBackLayout(),z);
            fragment.getSwipeBackLayout().startAnimation(model.exitAnimation);
        }
        return model.exitAnimation;
    }

    public Animation popEnterAnimation(int z){
        if(model.popEnterAnim){
            ViewCompat.setTranslationZ(fragment.getSwipeBackLayout(),z);
            fragment.getSwipeBackLayout().startAnimation(model.popEnterAnimation);
        }
        return model.popEnterAnimation;
    }

    public Animation popExitAnimation(int z){
        if(model.popExitAnim){
            ViewCompat.setTranslationZ(fragment.getSwipeBackLayout(),z);
            fragment.getSwipeBackLayout().startAnimation(model.popExitAnimation);
        }
        return model.popExitAnimation;
    }

    public void setAnimtion(FragmentAnimation animation){
        model.enterAnimation = animation.enterAnimation(fragment.getContext());
        model.exitAnimation = animation.exitAnimation(fragment.getContext());
        model.popEnterAnimation = animation.popEnterAnimation(fragment.getContext());
        model.popExitAnimation = animation.popExitAnimation(fragment.getContext());
    }
}
