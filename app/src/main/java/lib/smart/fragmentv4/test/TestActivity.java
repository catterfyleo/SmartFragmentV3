package lib.smart.fragmentv4.test;

import android.os.Bundle;
import android.support.annotation.Nullable;


import lib.smart.fragment.R;
import lib.smart.fragmentv4.FragmentChangedListener;
import lib.smart.fragmentv4.SmartActivity;
import lib.smart.fragmentv4.SmartFragment;

/**
 * Created by Augustine on 2018/5/16.
 * <p>
 * email:nice_ohoh@163.com
 */

public class TestActivity extends SmartActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestMainFragment testMainFragment = TestMainFragment.newInstance();
        addMain(testMainFragment,R.id.contentFrameLayout,null);
        show(testMainFragment,null);
        testMainFragment.setFragmentChangedListener(new FragmentChangedListener() {
            @Override
            public void init(SmartFragment fragment) {
                fragment.getSwipeBackLayout().setOpenTouchSlide(false);
            }

            @Override
            public void userVisible(boolean visible) {

            }

            @Override
            public void destroy() {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(!canBack()){
            super.onBackPressed();
        }
    }
}
