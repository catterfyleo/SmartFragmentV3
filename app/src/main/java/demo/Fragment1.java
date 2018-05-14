package demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lib.smart.fragment.ICommitCallBack;
import lib.smart.fragment.R;
import lib.smart.fragment.SmartFragment;

/**
 * Created by Augustine on 2018/5/14.
 * <p>
 * email:nice_ohoh@163.com
 */

public class Fragment1 extends SmartFragment {

    public static Fragment1 newInstance() {

        Bundle args = new Bundle();

        Fragment1 fragment = new Fragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.f_1,null);
    }

    @Override
    public void initList() {
        super.initList();
        getModel().popEnterAnim = false;
        getModel().popExitAnim = false;
        getModel().enterAnim = false;
        getModel().exitAnim = false;
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                add(new Fragment4(), R.id.contentFrameLayout, new ICommitCallBack() {
//                    @Override
//                    public void onCommit(SmartFragment fragment) {
//                        show(fragment);
//                    }
//                });
                if(getParentSmartFragment() != null){
                    getParentSmartFragment().startFragment(new Fragment4());
                }

            }
        });
    }
}
