package lib.smart.fragmentv4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by Augustine on 2018/5/15.
 * <p>
 * email:nice_ohoh@163.com
 *
 * v4.2
 *  1.优化Fragment tag 命名方式，由UUID改为 simpleName + index
 *    优化前的tag在fragment对象初始化时生成
 *    优化后的tag生成在添加之前自动生成
 *  2.Android 4.4 及以下，可能会有动画错乱风险
 *    建议tab切换时使用 NoFragmentAnimation，或者使用showNoAnim & hideNoAnim
 *  3.关于Fragment的回收&恢复，4.2版本暂时不考虑此功能
 *  4.新增smartUserVisible方法
 *
 * v4.1
 *  1.优化Fragment的动画，解决部分安卓版本的错乱问题
 *  2.优化FragmentModel之间的关系（兄弟关系、嵌套关系、嵌套兄弟关系...）
 *  3.优化内存管理，当使用SwipeBackLayout时，默认显示当前fragment的前一个fragment
 *    隐藏之前的所有Fragment
 *  4.增加无限嵌套
 *
 * v4.0
 *  1.彻底解决卡顿现象，消除Model的循环遍历代码，所有Fragment在add时就完成了绑定关系
 *  2.优化代码耦合度
 */

public class SmartActivity extends AppCompatActivity
        implements FragmentFunction{

    private FragmentList fragmentList;

    private FragmentManager fragmentManager;

    private ShowHideManager showHideManager;

    //防止回退过快
    private boolean backPressed = true;

    //防止启动过快
    private boolean canStartFlag = true;

    private FragmentTaskLog taskLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentList = new FragmentList();
        fragmentManager = getSupportFragmentManager();
        showHideManager = new ShowHideManager();
        showHideManager.setFragmentManager(fragmentManager);
        taskLog = new FragmentTaskLog(fragmentList);
        if(savedInstanceState != null){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for(int i = 0 ; i < fragmentManager.getFragments().size() ; i ++){
                transaction.remove(fragmentManager.getFragments().get(i));
            }
            transaction.commit();
        }
    }

    public boolean goBack(){
        if(fragmentList.findModelsByParentTag("main").size() > 1){
            if(backPressed){
                backPressed = false;
                removeLast(new CommitCallBack() {
                    @Override
                    public void onCommit(SmartFragment fragment) {
                        backPressed = true;
                    }
                });
            }
            return true;
        }
        return false;
    }

    @Override
    public void addMain(final SmartFragment fragment, int containerViewId, final CommitCallBack commitCallBack) {
        final FragmentModel model = new FragmentModel();
//        model.tag = fragment.getTAG();
        model.index = fragmentList.getSize();
        model.containerViewId = containerViewId;
        model.isRoot = true;
        model.parentTag = "main";
        model.foreFragmentTag = null;
        model.isHidden = true;
        model.swipeBack = false;
        model.isInit = false;
        model.simpleName = fragment.getClass().getSimpleName();
        model.className = fragment.getClass().getName();
        fragment.setTAG(model.simpleName + model.index);
        model.tag = fragment.getTAG();
        model.brotherParentTag = fragment.getTAG();
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
                fragment.addComplete(model);
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
            }
        });
    }

    @Override
    public void addChild(SmartFragment startF, final SmartFragment fragment, int containerViewId, final CommitCallBack commitCallBack) {
        final FragmentModel model = new FragmentModel();
//        model.tag = fragment.getTAG();
        model.index = fragmentList.getSize();
        model.parentTag = startF.getTAG();
        model.foreFragmentTag = null;
        model.isHidden = true;
        model.swipeBack = false;
        model.isInit = false;
        model.isRoot = false;
        model.containerViewId = containerViewId;
        model.simpleName = fragment.getClass().getSimpleName();
        model.className = fragment.getClass().getName();
        fragment.setTAG(model.simpleName + model.index);
        model.tag = fragment.getTAG();
        model.brotherParentTag = fragment.getTAG();
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
                fragment.addComplete(model);
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
            }
        });
    }

    @Override
    public void addBrother(SmartFragment startF, final SmartFragment fragment, final CommitCallBack commitCallBack) {
        final FragmentModel model = new FragmentModel();
//        model.tag = fragment.getTAG();
        model.index = fragmentList.getSize();
        model.parentTag = startF.getFragmentModel().parentTag;
        model.foreFragmentTag = startF.getTAG();
        model.isHidden = true;
        model.swipeBack = true;
        model.isInit = false;
        model.isRoot = false;
        model.containerViewId = startF.getFragmentModel().containerViewId;
        model.brotherParentTag = startF.getFragmentModel().brotherParentTag;
        model.simpleName = fragment.getClass().getSimpleName();
        model.className = fragment.getClass().getName();
        fragment.setTAG(model.simpleName + model.index);
        model.tag = fragment.getTAG();
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
                fragment.addComplete(model);
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
            }
        });
    }

    @Override
    public void addAlone(SmartFragment startF, final SmartFragment fragment, final CommitCallBack commitCallBack) {
        final FragmentModel model = new FragmentModel();
//        model.tag = fragment.getTAG();
        model.parentTag = "final";
        model.brotherParentTag = "final";
        model.index = fragmentList.getSize();
        model.foreFragmentTag = null;
        model.isHidden = true;
        model.swipeBack = false;
        model.isInit = false;
        model.isRoot = true;
        model.containerViewId = startF.getFragmentModel().containerViewId;
        model.simpleName = fragment.getClass().getSimpleName();
        model.className = fragment.getClass().getName();
        fragment.setTAG(model.simpleName + model.index);
        model.tag = fragment.getTAG();
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
                fragment.addComplete(model);
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
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
                if(!fragment.getFragmentModel().isInit){
                    fragment.initList();
                    fragment.getFragmentModel().isInit = true;
                }
                showHideManager.show(fragment);
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
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
                showHideManager.hide(fragment);
                if(commitCallBack != null){
                    commitCallBack.onCommit(fragment);
                }
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
                fragmentList.remove(fragment.getTAG());
                if(commitCallBack != null){
                    commitCallBack.onCommit(null);
                }
            }
        });
    }

    @Override
    public void removeBrothers(final SmartFragment fromFragment, final CommitCallBack commitCallBack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        final List<FragmentModel> childModelList = fragmentList.findBrotherModelsByTag(fromFragment.getTAG());
        for(FragmentModel childModel : childModelList){
            SmartFragment fragment = (SmartFragment) fragmentManager.findFragmentByTag(childModel.tag);
            transaction.remove(fragment);
        }
        transaction.commit();
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                for(FragmentModel model : childModelList){
                        fragmentList.remove(model.tag);
                }
                if(commitCallBack != null){
                    commitCallBack.onCommit(null);
                }
            }
        });
    }

    @Override
    public void removeLast(CommitCallBack commitCallBack) {
        FragmentModel lastModel = fragmentList.getLastModel();
        SmartFragment lastFragment = (SmartFragment) fragmentManager.findFragmentByTag(lastModel.tag);
        remove(lastFragment,commitCallBack);
    }

    public void printTaskLog(){
        taskLog.printLog();
    }

//--------------------------------------------------------------------------------


    public FragmentList getFragmentList() {
        return fragmentList;
    }

    public ShowHideManager getShowHideManager() {
        return showHideManager;
    }

    public boolean isCanStartFlag() {
        return canStartFlag;
    }

    public void setCanStartFlag(boolean canStartFlag) {
        this.canStartFlag = canStartFlag;
    }
}
