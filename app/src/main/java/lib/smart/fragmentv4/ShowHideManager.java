package lib.smart.fragmentv4;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Augustine on 2018/5/16.
 * <p>
 * email:nice_ohoh@163.com
 *
 */

public class ShowHideManager{

    private FragmentManager fragmentManager;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    /**
     * A ——> B ——> C
     * 如果 show C ，找到 C 上一个兄弟的上一个 A，hide A
     *
     * @param fragment
     */
    public void show(SmartFragment fragment) {
        fragment.smartUserVisible(true);
        fragment.getAnimController().startEnterAnim(1);
        if(fragment.getFragmentModel().foreFragmentTag == null){
            return;
        }
        SmartFragment foreFragment =
                (SmartFragment) fragmentManager.findFragmentByTag(fragment.getFragmentModel().foreFragmentTag);
        if(foreFragment == null){
            return;
        }
        foreFragment.getAnimController().startPopExitAnim(0);
        if(foreFragment.getFragmentModel().foreFragmentTag == null){
            return;
        }
        SmartFragment fore2Fragment =
                (SmartFragment) fragmentManager.findFragmentByTag(foreFragment.getFragmentModel().foreFragmentTag);
        if(fore2Fragment == null){
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fore2Fragment);
        fore2Fragment.smartUserVisible(false);
        transaction.commit();
    }

    public void hide(SmartFragment fragment) {
        fragment.getAnimController().startPopExitAnim(0);
    }

    /**
     * A ——> B ——> C
     * C被销毁时 显示B A
     * B被销毁时 显示A
     *
     * A ——> B ——> C ——> D
     * A B 隐藏
     * C D 显示
     * D被销毁时 C B 显示
     * A隐藏
     *
     * @param fragment
     */
    public void remove(final SmartFragment fragment, final FragmentTransaction transaction) {
        fragment.getSwipeBackLayout().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        long duration = fragment.getAnimController().startExitAnim(1);
        if(fragment.getFragmentModel().parentTag == null){
            transaction.commit();
            return;
        }
        final SmartFragment foreFragment =
                (SmartFragment) fragmentManager.findFragmentByTag(fragment.getFragmentModel().foreFragmentTag);
        if(foreFragment == null){
            transaction.commit();
            return;
        }
        foreFragment.getAnimController().startPopEnterAnim(0);
        if(foreFragment.getFragmentModel().foreFragmentTag == null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    transaction.commit();
                }
            },duration);
            return;
        }
        final SmartFragment fore2Fragment =
                (SmartFragment) fragmentManager.findFragmentByTag(foreFragment.getFragmentModel().foreFragmentTag);
        if(fore2Fragment == null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    transaction.commit();
                }
            },duration);
            return;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                transaction.show(fore2Fragment);
                transaction.show(foreFragment);
                fore2Fragment.smartUserVisible(true);
                foreFragment.smartUserVisible(true);
                transaction.commit();
            }
        },duration);
    }
}
