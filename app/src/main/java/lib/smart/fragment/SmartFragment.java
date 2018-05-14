package lib.smart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.UUID;

import lib.smart.fragment.view.SwipeBackLayout;

/**
 * Created by Augustine on 2018/5/14.
 * <p>
 * email:nice_ohoh@163.com
 */

public class SmartFragment extends Fragment implements
        SwipeBackLayout.OnSwipeListener, ISmartFunction,ISmartFunction2{

    private String TAG = null;

    private Context context;

    private FragmentModel model;

    private SwipeBackLayout swipeBackLayout;

    private FragmentModel foreModel;

    private SmartFragment foreFragment;

    private SwipeBackLayout foreSwipeBackLayout;

    private AnimationHelper animationHelper;

    private SmartFragment parentFragment;

    public SmartFragment(){
        super();
//        TAG = getClass().getCanonicalName();
        TAG = UUID.randomUUID().toString().replaceAll("-","").substring(0,10);
    }

    /**
     * 在添加加到fragment queue时调用
     */
    public void addComplete(){
        model = getSmartActivity().findModelByTag(getTAG());
        foreModel = getSmartActivity().findForeModelByTag(model.tag);
        if(foreModel != null){
            model.foreFragmentTag = foreModel.tag;
            foreFragment = getSmartActivity().findFragmentByTag(foreModel.tag);
            foreSwipeBackLayout = foreFragment.getSwipeBackLayout();
        }
        animationHelper = new AnimationHelper(getSmartActivity(),this);
        getSwipeBackLayout().setOpenTouchSlide(model.swipeBack);
        if(model.parentTag != null){
            parentFragment = getSmartActivity().findFragmentByTag(getModel().parentTag);
        }
    }

    public void smartUserVisible(boolean visible){

    }

    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return new View(getContext());
    }


    public void initList(){

    }

    public <T extends View> T findViewById(int id) {
        return getSwipeBackLayout().findViewById(id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (swipeBackLayout != null) {
            ViewGroup parent = (ViewGroup) swipeBackLayout.getParent();
            if(parent != null){
                parent.removeView(swipeBackLayout);
            }
        }else{
            View contentView = initView(inflater,container,savedInstanceState);
            swipeBackLayout = new SwipeBackLayout(getContext());
            swipeBackLayout.setContentView(contentView);
            swipeBackLayout.setSwipeListener(this);
        }
        return swipeBackLayout;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public FragmentModel getModel() {
        return model;
    }

    public SmartActivity getSmartActivity(){
        return ((SmartActivity)getContext());
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }

    public AnimationHelper getAnimationHelper() {
        return animationHelper;
    }

    public void setAnimationHelper(AnimationHelper animationHelper) {
        this.animationHelper = animationHelper;
    }

    public FragmentModel getForeModel() {
        return foreModel;
    }

    public SmartFragment getForeFragment() {
        return foreFragment;
    }

    public SwipeBackLayout getForeSwipeBackLayout() {
        return foreSwipeBackLayout;
    }

    public SmartFragment getParentSmartFragment(){
        return parentFragment;
    }

    //--------------------------------------swipe back ---------------------------

    @Override
    public void onSwipeLayoutChange(int x) {
        try {
            if(foreFragment.isHidden()){
                show(foreModel.tag);
            }
            foreSwipeBackLayout.setContentViewX(x);
        }catch (NullPointerException n){}
    }

    @Override
    public void onSwipeLayoutClosed() {
        getModel().exitAnim = false;
        getForeModel().popEnterAnim = false;
        remove(getTAG());
        InputHelper.hideSoftInput(getSwipeBackLayout());
    }

    @Override
    public void onSwipeLayoutReset() {

    }

    //-----------------------------------------------------------------------------

    @Override
    public void add(SmartFragment fragment, FragmentModel model, ICommitCallBack commitCallBack) {
        getSmartActivity().add(fragment,model,commitCallBack);
    }

    @Override
    public void add(SmartFragment fragment, int containerViewId, ICommitCallBack commitCallBack) {
        getSmartActivity().add(fragment,containerViewId,commitCallBack);
    }

    @Override
    public void add(List<SmartFragment> fragmentList, int containerViewId) {
        getSmartActivity().add(fragmentList,containerViewId);
    }

    @Override
    public void addChild(List<SmartFragment> fragmentList, int containerViewId, String parentTag) {
        getSmartActivity().addChild(fragmentList, containerViewId, parentTag);
    }

    @Override
    public void show(SmartFragment fragment) {
        getSmartActivity().show(fragment);
    }

    @Override
    public void hide(SmartFragment fragment) {
        getSmartActivity().hide(fragment);
    }

    @Override
    public void show(String tag) {
        getSmartActivity().show(tag);
    }

    @Override
    public void hide(String tag) {
        getSmartActivity().hide(tag);
    }

    @Override
    public void showHideAllChild(SmartFragment fragment) {
        getSmartActivity().showHideAllChild(fragment);
    }

    @Override
    public void remove(SmartFragment fragment) {
        getSmartActivity().remove(fragment);
    }

    @Override
    public void remove(String tag) {
        getSmartActivity().remove(tag);
    }


    @Override
    public void startFragment(SmartFragment start) {
        add(start, getModel().containerViewId, new ICommitCallBack() {
            @Override
            public void onCommit(SmartFragment fragment) {
                getAnimationHelper().popExitAnimation(-1);
                show(fragment);
            }
        });
    }

    @Override
    public void startFragmentCloseSelf(SmartFragment start) {
        add(start, getModel().containerViewId, new ICommitCallBack() {
            @Override
            public void onCommit(SmartFragment fragment) {
                remove(getTAG());
                show(fragment);
            }
        });
    }

    @Override
    public void startFragmentNoAnim(SmartFragment start) {
        add(start, getModel().containerViewId, new ICommitCallBack() {
            @Override
            public void onCommit(SmartFragment fragment) {
                fragment.getModel().enterAnim = false;
                getModel().popExitAnim = false;
                show(fragment);
            }
        });
    }

    @Override
    public void startFragmentCloseSelfNoAnim(SmartFragment start) {
        add(start, getModel().containerViewId, new ICommitCallBack() {
            @Override
            public void onCommit(SmartFragment fragment) {
                fragment.getModel().enterAnim = false;
                getModel().exitAnim = false;
                remove(getTAG());
                show(fragment);
            }
        });
    }
}
