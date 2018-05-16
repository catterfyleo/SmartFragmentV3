package lib.smart.fragmentv4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Augustine on 2018/5/15.
 * <p>
 * email:nice_ohoh@163.com
 */

public class SmartActivity extends AppCompatActivity
        implements FragmentFunction{

    private FragmentList fragmentList;

    private FragmentManager fragmentManager;

    private ShowHideManager showHideManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentList = new FragmentList();
        fragmentManager = getSupportFragmentManager();
        showHideManager = new ShowHideManager();
        showHideManager.setFragmentManager(fragmentManager);
    }

    @Override
    public void addMain(final SmartFragment fragment, int containerViewId, final CommitCallBack commitCallBack) {
        final FragmentModel model = new FragmentModel();
        model.tag = fragment.getTAG();
        model.index = fragmentList.getSize();
        model.containerViewId = containerViewId;
        model.isRoot = true;
        model.parentTag = "main";
        model.foreFragmentTag = null;
        model.isHidden = true;
        model.swipeBack = false;
        model.isInit = false;
        fragmentList.addModel(model);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(!fragment.isAdded()){
            transaction.add(model.containerViewId,fragment,model.tag);
        }
        transaction.hide(fragment);
        transaction.commit();
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
                fragment.addComplete(model);
            }
        });
    }

    @Override
    public void addChild(SmartFragment startF, final SmartFragment fragment, int containerViewId, final CommitCallBack commitCallBack) {
        final FragmentModel model = new FragmentModel();
        model.tag = fragment.getTAG();
        model.index = fragmentList.getSize();
        model.parentTag = startF.getTAG();
        model.foreFragmentTag = null;
        model.isHidden = true;
        model.swipeBack = false;
        model.isInit = false;
        model.isRoot = false;
        model.containerViewId = containerViewId;
        fragmentList.addModel(model);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(!fragment.isAdded()){
            transaction.add(model.containerViewId,fragment,model.tag);
        }
        transaction.hide(fragment);
        transaction.commit();
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
                fragment.addComplete(model);
            }
        });
    }

    @Override
    public void addBrother(SmartFragment startF, final SmartFragment fragment, int containerViewId, final CommitCallBack commitCallBack) {
        final FragmentModel model = new FragmentModel();
        model.tag = fragment.getTAG();
        model.index = fragmentList.getSize();
        model.parentTag = startF.getFragmentModel().parentTag;
        model.foreFragmentTag = startF.getTAG();
        Log.e("addBrother","foreFragmentTag:   "+model.foreFragmentTag);
        Log.e("addBrother","parentTag:   "+model.parentTag);
        model.isHidden = true;
        model.swipeBack = false;
        model.isInit = false;
        model.isRoot = false;
        model.containerViewId = containerViewId;
        fragmentList.addModel(model);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(!fragment.isAdded()){
            transaction.add(model.containerViewId,fragment,model.tag);
        }
        transaction.hide(fragment);
        transaction.commit();
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
                fragment.addComplete(model);
            }
        });
    }

    @Override
    public void show(final SmartFragment fragment, final CommitCallBack commitCallBack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
                if(!fragment.getFragmentModel().isInit){
                    fragment.initList();
                    fragment.getFragmentModel().isInit = true;
                }
                showHideManager.show(fragment);
            }
        });
    }

    @Override
    public void hide(final SmartFragment fragment, final CommitCallBack commitCallBack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
                showHideManager.hide(fragment);
            }
        });
    }

    @Override
    public void remove(final SmartFragment fragment, final CommitCallBack commitCallBack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        showHideManager.remove(fragment,transaction);
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
                fragmentList.remove(fragment.getTAG());
            }
        });
    }

//--------------------------------------------------------------------------------


    public FragmentList getFragmentList() {
        return fragmentList;
    }

    public ShowHideManager getShowHideManager() {
        return showHideManager;
    }
}
