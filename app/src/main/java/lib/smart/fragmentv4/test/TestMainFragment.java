package lib.smart.fragmentv4.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lib.smart.fragment.R;
import lib.smart.fragmentv4.SmartFragment;

/**
 * Created by Augustine on 2018/5/16.
 * <p>
 * email:nice_ohoh@163.com
 */

public class TestMainFragment extends SmartFragment {


    public static TestMainFragment newInstance() {

        Bundle args = new Bundle();

        TestMainFragment fragment = new TestMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TestFragment1 testFragment1;
    private TestFragment2 testFragment2;
    private TestFragment3 testFragment3;

    private SmartFragment currentFragment;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.fragment_main,null);
    }

    @Override
    public void initList() {
        super.initList();
        testFragment1 = TestFragment1.newInstance();
        testFragment2 = TestFragment2.newInstance();
        testFragment3 = TestFragment3.newInstance();
        addChild(testFragment1,R.id.mainFG_frameLayout,null);
        addChild(testFragment2,R.id.mainFG_frameLayout,null);
        addChild(testFragment3,R.id.mainFG_frameLayout,null);
        show(testFragment1,null);
        currentFragment = testFragment1;
        findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(testFragment1,null);
                hide(currentFragment,null);
                currentFragment = testFragment1;
            }
        });


        findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show(testFragment2,null);
                hide(currentFragment,null);
                currentFragment = testFragment2;

            }
        });


        findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show(testFragment3,null);
                hide(currentFragment,null);
                currentFragment = testFragment3;
            }
        });
    }
}
