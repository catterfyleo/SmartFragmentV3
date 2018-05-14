package demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lib.smart.fragment.ICommitCallBack;
import lib.smart.fragment.R;
import lib.smart.fragment.SmartActivity;
import lib.smart.fragment.SmartFragment;

public class MainActivity extends SmartActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = MainFragment.newInstance();
        add(mainFragment, R.id.contentFrameLayout, new ICommitCallBack() {
            @Override
            public void onCommit(SmartFragment fragment) {
                fragment.getModel().swipeBack = false;
                fragment.getSwipeBackLayout().setOpenTouchSlide(false);
                fragment.getModel().enterAnim = false;
                fragment.getModel().exitAnim = false;
                fragment.getModel().popEnterAnim = true;
                fragment.getModel().popExitAnim = true;
                show(fragment.getTAG());
            }
        });

    }


    @Override
    public void onBackPressed() {
        if(!backPressed()){
            super.onBackPressed();
        }
    }
}
