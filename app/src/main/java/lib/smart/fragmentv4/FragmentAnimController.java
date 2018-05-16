package lib.smart.fragmentv4;

import android.support.v4.view.ViewCompat;

/**
 * Created by Augustine on 2018/5/15.
 * <p>
 * email:nice_ohoh@163.com
 */

public class FragmentAnimController {

    private SmartFragment fragment;

    public FragmentAnimController(SmartFragment fragment) {
        this.fragment = fragment;
    }

    public void setFragmentAnim(FragmentAnimator animator){
        fragment.getFragmentModel().enterAnimation = animator.enterAnimation(fragment.getContext());
        fragment.getFragmentModel().exitAnimation = animator.exitAnimation(fragment.getContext());
        fragment.getFragmentModel().popEnterAnimation = animator.popEnterAnimation(fragment.getContext());
        fragment.getFragmentModel().popExitAnimation = animator.popExitAnimation(fragment.getContext());
        fragment.getFragmentModel().enterAnim = true;
        fragment.getFragmentModel().exitAnim = true;
        fragment.getFragmentModel().popExitAnim = true;
        fragment.getFragmentModel().popExitAnim = true;
    }

    public long startEnterAnim(int z){
        long duration = fragment.getFragmentModel().enterAnimation.getDuration();
        if(fragment.getSwipeBackLayout() == null){
            return duration;
        }
        if(fragment.getFragmentModel().enterAnim){
            ViewCompat.setTranslationZ(fragment.getSwipeBackLayout(),z);
            fragment.getSwipeBackLayout().startAnimation(fragment.getFragmentModel().enterAnimation);
        }else{
            fragment.getFragmentModel().enterAnim = true;
        }
        return duration;
    }

    public long startExitAnim(int z){
        long duration = fragment.getFragmentModel().enterAnimation.getDuration();
        if(fragment.getSwipeBackLayout() == null){
            return duration;
        }
        if(fragment.getFragmentModel().exitAnim){
            ViewCompat.setTranslationZ(fragment.getSwipeBackLayout(),z);
            fragment.getSwipeBackLayout().startAnimation(fragment.getFragmentModel().exitAnimation);
        }else{
            fragment.getFragmentModel().exitAnim = true;
        }
        return duration;
    }

    public long startPopEnterAnim(int z){
        long duration = fragment.getFragmentModel().enterAnimation.getDuration();
        if(fragment.getSwipeBackLayout() == null){
            return duration;
        }
        if(fragment.getFragmentModel().popEnterAnim){
            ViewCompat.setTranslationZ(fragment.getSwipeBackLayout(),z);
            fragment.getSwipeBackLayout().startAnimation(fragment.getFragmentModel().popEnterAnimation);
        }else{
            fragment.getFragmentModel().popEnterAnim = true;
        }
        return duration;
    }

    public long startPopExitAnim(int z){
        long duration = fragment.getFragmentModel().enterAnimation.getDuration();
        if(fragment.getSwipeBackLayout() == null){
            return duration;
        }
        if(fragment.getFragmentModel().popExitAnim){
            ViewCompat.setTranslationZ(fragment.getSwipeBackLayout(),z);
            fragment.getSwipeBackLayout().startAnimation(fragment.getFragmentModel().popExitAnimation);
        }else{
            fragment.getFragmentModel().popExitAnim = true;
        }
        return duration;
    }
}
