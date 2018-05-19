package lib.smart.fragmentv4;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import lib.smart.fragmentv4.view.SwipeBackLayout;
import lib.smart.fragmentv4.anim.DefaultFragmentAnimation;
import lib.smart.fragmentv4.anim.FragmentAnimController;
import lib.smart.fragmentv4.anim.VerticalFragmentAnimation;

/**
 * Created by Augustine on 2018/5/15.
 * <p>
 * email:nice_ohoh@163.com
 */

public class SmartFragment extends Fragment
        implements SwipeBackLayout.OnSwipeListener {

    private String TAG;

    private FragmentModel fragmentModel;

    private Context context;

    private SwipeBackLayout swipeBackLayout;

    private SmartFragment foreFragment;

    private FragmentModel foreModel;

    private SwipeBackLayout foreSwipeBackLayout;

    private FragmentChangedListener fragmentChangedListener;

    private FragmentAnimController animController;

    private SmartFragment parentFragment;

    public SmartFragment(){
        super();
        TAG = UUID.randomUUID().toString().replaceAll("-","").substring(0,10);
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


    @Override
    public void onDestroy() {
        if(fragmentChangedListener != null){
            fragmentChangedListener.destroy();
        }
        super.onDestroy();
    }

    //-----------------------public------------------------------------------
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return new View(getContext());
    }

    public void initList(){
        if(fragmentChangedListener != null){
            fragmentChangedListener.init(this);
        }
    }

    public <T extends View> T findViewById(int id) {
        return getSwipeBackLayout().findViewById(id);
    }

    public void smartUserVisible(boolean visible){
        Log.e(getTAG(),getFragmentModel().index+":isShow?----->>>>>"+visible);
        if(fragmentChangedListener != null){
            fragmentChangedListener.userVisible(visible);
        }
        if(visible){
            getSwipeBackLayout().setOpenTouchSlide(getFragmentModel().swipeBack);
            if(getSwipeBackLayout().getContentX() < 0){
                getSwipeBackLayout().setContentViewX(999999);
            }
        }else{
            getSwipeBackLayout().setOpenTouchSlide(false);
        }
    }

    /**
     * 关联到activity时调用，此时并没有显示出来
     */
    public void addComplete(FragmentModel model){
        setFragmentModel(model);
        if(fragmentModel.parentTag != null){
            parentFragment = (SmartFragment) getFragmentManager().findFragmentByTag(fragmentModel.parentTag);
        }
        if(fragmentModel.foreFragmentTag != null){
            foreFragment = (SmartFragment) getFragmentManager().findFragmentByTag(fragmentModel.foreFragmentTag);
            foreModel = getSmartActivity().getFragmentList().findModelByTag(fragmentModel.foreFragmentTag);
        }
        if(foreFragment != null){
            foreSwipeBackLayout = foreFragment.getSwipeBackLayout();
        }else{
            getFragmentModel().swipeBack = false;
            getSwipeBackLayout().setOpenTouchSlide(false);
        }
        animController = new FragmentAnimController(this);
        if(model.parentTag.equals("final")){
            animController.setFragmentAnim(new VerticalFragmentAnimation());
        }else{
            animController.setFragmentAnim(new DefaultFragmentAnimation());
        }

    }


    //-----------------------getter  setter --------------------------------


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

    public FragmentModel getFragmentModel() {
        return fragmentModel;
    }

    public void setFragmentModel(FragmentModel fragmentModel) {
        this.fragmentModel = fragmentModel;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }

    public FragmentChangedListener getFragmentChangedListener() {
        return fragmentChangedListener;
    }

    public void setFragmentChangedListener(FragmentChangedListener fragmentChangedListener) {
        this.fragmentChangedListener = fragmentChangedListener;
    }

    public FragmentAnimController getAnimController() {
        return animController;
    }

    public SmartActivity getSmartActivity(){
        return (SmartActivity)getContext();
    }

    public SmartFragment getForeFragment() {
        return foreFragment;
    }

    public FragmentModel getForeModel() {
        return foreModel;
    }

    public SwipeBackLayout getForeSwipeBackLayout() {
        return foreSwipeBackLayout;
    }

    public SmartFragment getParentSmartFragment() {
        return parentFragment;
    }

    //------------------------swipe back------------------------------

    @Override
    public void onSwipeLayoutChange(int x) {
        if(foreSwipeBackLayout != null){
            foreSwipeBackLayout.setContentViewX(x);
        }
    }

    @Override
    public void onSwipeLayoutClosed() {
        if(foreModel != null){
            foreModel.popEnterAnim = false;
        }
        InputHelper.hideSoftInput(getSwipeBackLayout());
        getSmartActivity().remove(this,null);
    }

    @Override
    public void onSwipeLayoutReset() {

    }


    //-----------------------------------------------------------------------------

    @Deprecated
    public void addMain(SmartFragment fragment, int containerViewId, CommitCallBack commitCallBack) {
        getSmartActivity().addMain(fragment,containerViewId,commitCallBack);
    }

    public void addChild(SmartFragment fragment, int containerViewId, CommitCallBack commitCallBack) {
        getSmartActivity().addChild(this,fragment,containerViewId,commitCallBack);
    }

    public void addBrother(SmartFragment fragment , CommitCallBack commitCallBack) {
        getSmartActivity().addBrother(this,fragment,commitCallBack);
    }

    public void addAlone(SmartFragment fragment, final CommitCallBack commitCallBack) throws Exception {
        if(getFragmentModel().parentTag.equals("final")){
            throw new Exception();
        }
        getSmartActivity().addAlone(this,fragment,commitCallBack);
    }

    public void show(SmartFragment fragment, CommitCallBack commitCallBack) {
        getSmartActivity().show(fragment,commitCallBack);
    }

    public void hide(SmartFragment fragment, CommitCallBack commitCallBack) {
        getSmartActivity().hide(fragment,commitCallBack);
    }

    public void remove(SmartFragment fragment, CommitCallBack commitCallBack) {
        getSmartActivity().remove(fragment,commitCallBack);
    }

    public void removeBrothers(SmartFragment fromFragment,CommitCallBack commitCallBack) {
        getSmartActivity().removeBrothers(fromFragment,commitCallBack);
    }

    public void startFragment(SmartFragment smartFragment){
        if(getSmartActivity().isCanStartFlag()){
            getSmartActivity().setCanStartFlag(false);
            addBrother(smartFragment, new CommitCallBack() {
                @Override
                public void onCommit(SmartFragment fragment) {
                    show(fragment, new CommitCallBack() {
                        @Override
                        public void onCommit(SmartFragment fragment) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getSmartActivity().setCanStartFlag(true);
                                }
                            },300);
                        }
                    });
                }
            });
        }
    }

    public void startFragmentNoAnim(SmartFragment smartFragment){
        if(getSmartActivity().isCanStartFlag()) {
            addBrother(smartFragment, new CommitCallBack() {
                @Override
                public void onCommit(SmartFragment fragment) {
                    fragment.getFragmentModel().enterAnim = false;
                    if (fragment.getForeModel() != null) {
                        fragment.getForeModel().popExitAnim = false;
                    }
                    show(fragment, new CommitCallBack() {
                        @Override
                        public void onCommit(SmartFragment fragment) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getSmartActivity().setCanStartFlag(true);
                                }
                            },300);
                        }
                    });
                }
            });
        }
    }

    public void startAloneFragment(SmartFragment smartFragment){
        try {
            if(getSmartActivity().isCanStartFlag()) {
                addAlone(smartFragment, new CommitCallBack() {
                    @Override
                    public void onCommit(SmartFragment fragment) {
                        if (fragment.getForeModel() != null) {
                            fragment.getForeModel().popExitAnim = false;
                        }
                        show(fragment, new CommitCallBack() {
                            @Override
                            public void onCommit(SmartFragment fragment) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getSmartActivity().setCanStartFlag(true);
                                    }
                                },300);
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void startFragmentRemoveSelf(SmartFragment smartFragment){
        if(getSmartActivity().isCanStartFlag()){
            getSmartActivity().setCanStartFlag(false);
            addBrother(smartFragment, new CommitCallBack() {
                @Override
                public void onCommit(SmartFragment fragment) {
                    show(fragment, new CommitCallBack() {
                        @Override
                        public void onCommit(SmartFragment fragment) {
                            remove(SmartFragment.this,null);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getSmartActivity().setCanStartFlag(true);
                                }
                            },300);
                        }
                    });
                }
            });
        }
    }



}
