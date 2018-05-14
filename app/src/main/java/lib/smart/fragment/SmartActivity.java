package lib.smart.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import lib.smart.fragment.anim.DefaultAnimation;
import lib.smart.fragment.anim.FragmentAnimation;


/**
 * Created by Augustine on 2018/5/14.
 * <p>
 * email:nice_ohoh@163.com
 */

public class SmartActivity extends AppCompatActivity implements ISmartFunction {

    private FragmentManager fragmentManager;

    private LinkedList<FragmentModel> fragmentModelList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        fragmentModelList = new LinkedList<>();
        if(savedInstanceState != null){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for(int i = 0 ; i < fragmentManager.getFragments().size() ; i ++){
                transaction.remove(fragmentManager.getFragments().get(i));
            }
            transaction.commit();
        }
    }

    public boolean backPressed(){
        if(canBack()){
            String lastTag = fragmentModelList.getLast().tag;
            SmartFragment lastFragment = (SmartFragment) fragmentManager.findFragmentByTag(lastTag);
            if(lastFragment != null){
                remove(lastFragment,null);
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void add(final SmartFragment fragment, FragmentModel model, final ICommitCallBack commitCallBack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        addToLinkedList(model);
        if(!fragment.isAdded()){
            transaction.add(model.containerViewId,fragment,model.tag);
        }
        transaction.hide(fragment);
        transaction.commit();
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                fragment.addComplete();
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
            }
        });

    }

    @Override
    public void add(final SmartFragment fragment, int containerViewId, final ICommitCallBack commitCallBack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentModel model = new FragmentModel();
        model.tag = fragment.getTAG();
        model.isRoot = true;
        model.parentTag = null;
        model.isHidden = true;
        model.isInitList = false;
        model.swipeBack = true;
        model.containerViewId = containerViewId;
        model.enterAnim = true;
        model.exitAnim = true;
        model.popEnterAnim = true;
        model.popExitAnim = true;
        FragmentAnimation animation = new DefaultAnimation();
        model.enterAnimation = animation.enterAnimation(getApplicationContext());
        model.exitAnimation = animation.exitAnimation(getApplicationContext());
        model.popEnterAnimation = animation.popEnterAnimation(getApplicationContext());
        model.popExitAnimation = animation.popExitAnimation(getApplicationContext());
        model.index = fragmentManager.getFragments().size();
        addToLinkedList(model);
        if(!fragment.isAdded()){
            transaction.add(containerViewId,fragment,model.tag);
        }
        transaction.hide(fragment);
        transaction.commit();
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                fragment.addComplete();
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
            }
        });
    }

    @Override
    public void add(List<SmartFragment> fragmentList, int containerViewId) {
        for(SmartFragment fragment : fragmentList){
            add(fragment,containerViewId,null);
        }
    }

    @Override
    public void addChild(List<SmartFragment> fragmentList, int containerViewId, String parentTag, final ICommitCallBack commitCallBack) {
        for(SmartFragment fragment : fragmentList){
            FragmentModel model = new FragmentModel();
            model.tag = fragment.getTAG();
            model.isRoot = false;
            model.parentTag = parentTag;
            model.isHidden = true;
            model.isInitList = false;
            model.swipeBack = true;
            model.containerViewId = containerViewId;
            model.enterAnim = true;
            model.exitAnim = true;
            model.popEnterAnim = true;
            model.popExitAnim = true;
            FragmentAnimation animation = new DefaultAnimation();
            model.enterAnimation = animation.enterAnimation(getApplicationContext());
            model.exitAnimation = animation.exitAnimation(getApplicationContext());
            model.popEnterAnimation = animation.popEnterAnimation(getApplicationContext());
            model.popExitAnimation = animation.popExitAnimation(getApplicationContext());
            model.index = fragmentManager.getFragments().size();
            add(fragment, model, new ICommitCallBack() {
                @Override
                public void onCommit(SmartFragment fragment) {
                    commitCallBack.onCommit(fragment);
                }
            });
        }
    }

    @Override
    public void show(final SmartFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                if(fragment.getModel().enterAnim){
                    fragment.getAnimationHelper().enterAnimation(0);
                }
                if(!fragment.getModel().isInitList){
                    fragment.initList();
                    fragment.getModel().isInitList = true;
                }
                fragment.smartUserVisible(true);
                fragment.getModel().isHidden = false;
            }
        });
    }

    @Override
    public void hide(final SmartFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                if(fragment.getModel().exitAnim){
                    fragment.getAnimationHelper().popExitAnimation(-1);
                }
                fragment.smartUserVisible(false);
                fragment.getModel().isHidden = true;
            }
        });
    }

    @Override
    public void show(String tag) {
        SmartFragment fragment = findFragmentByTag(tag);
        show(fragment);
    }

    @Override
    public void hide(String tag) {
        SmartFragment fragment = findFragmentByTag(tag);
        hide(fragment);
    }

    @Override
    public void showHideAllChild(SmartFragment fragment) {
        FragmentModel model = findModelByTag(fragment.getTAG());
        for(int i = 0 ; i < fragmentModelList.size() ; i ++){
            FragmentModel m = fragmentModelList.get(i);
            if(!m.isRoot && m.parentTag.equals(model.parentTag)){
                if(!m.tag.equals(model.tag)){
                    hide(m.tag);
                }
            }
        }
        show(model.tag);
    }

    @Override
    public void remove(final SmartFragment fragment, final ICommitCallBack commitCallBack) {
        FragmentModel model = findModelByTag(fragment.getTAG());
        FragmentModel foreModel = findForeModelByTag(fragment.getTAG());
        final SmartFragment foreFragment = findFragmentByTag(foreModel.tag);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        foreFragment.getSwipeBackLayout().setContentViewX(999999);
        long duration = 300;
        if(model.exitAnim){
            duration = fragment.getAnimationHelper().exitAnimation(1).getDuration();
        }
        if(foreModel.popEnterAnim){
            foreFragment.getAnimationHelper().popEnterAnimation(0);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                transaction.remove(fragment);
                transaction.show(foreFragment);
                transaction.commit();
                removeTag(fragment.getTAG());
                transaction.runOnCommit(new Runnable() {
                    @Override
                    public void run() {
                        foreFragment.smartUserVisible(true);
                        if(commitCallBack != null){
                            commitCallBack.onCommit(foreFragment);
                        }
                    }
                });
            }
        },duration);


    }

    @Override
    public void remove(String tag,ICommitCallBack commitCallBack) {
        SmartFragment fragment = findFragmentByTag(tag);
        remove(fragment,commitCallBack);
    }

    //---------------------------------find-------------------------------------

    public SmartFragment findFragmentByTag(String tag){
        return (SmartFragment) fragmentManager.findFragmentByTag(tag);
    }

    public FragmentModel findModelByTag(String tag){
        if(tag == null){
            return null;
        }
        for(FragmentModel model : fragmentModelList){
            if(model.tag.equals(tag)){
                return model;
            }
        }
        return null;
    }

    public FragmentModel findForeModelByTag(String tag){
        if(fragmentModelList.size() < 1){
            return null;
        }
        if(findModelByTag(tag) == null){
            return null;
        }
        if(!findModelByTag(tag).isRoot){
            return null;
        }
        int position = fragmentModelList.size() - 2;
        while (true){
            if(position < 0){
                return null;
            }
            FragmentModel model = fragmentModelList.get(position);
            if(model.isRoot){
                return model;
            }
            position--;
        }
    }

    //----------------------------------------------------------------------
    private boolean hasTag(String tag){
        if(tag == null){
            return false;
        }
        for(FragmentModel model : fragmentModelList){
            if(model.tag.equals(tag)){
                return true;
            }
        }
        return false;
    }

    private void addToLinkedList(FragmentModel model){
        if(!hasTag(model.tag)){
            fragmentModelList.add(model);
        }
    }

    public boolean canBack(){
        int rootCount = 0;
        for(FragmentModel model : fragmentModelList){
            if(model.isRoot){
                rootCount ++;
            }
        }
        if(rootCount > 1){
            return true;
        }
        return false;
    }

    private void log(String str){
        Log.i(this.getClass().getSimpleName(),str);

    }

    private void removeTag(String tag){
        for(int i = 0 ; i < fragmentModelList.size() ; i ++){
            if(fragmentModelList.get(i).tag.equals(tag)){
                fragmentModelList.remove(i);
            }
        }
    }
}

