package demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lib.smart.fragment.R;
import lib.smart.fragment.SmartFragment;

/**
 * Created by Augustine on 2018/5/14.
 * <p>
 * email:nice_ohoh@163.com
 */

public class MainFragment extends SmartFragment {

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private List<SmartFragment> fragmentList = new ArrayList<>();

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.fragment_main,null);
    }

    @Override
    public void initList() {
        super.initList();
        fragmentList.add(Fragment1.newInstance());
        fragmentList.add(Fragment2.newInstance());
        fragmentList.add(Fragment3.newInstance());
        addChild(fragmentList,R.id.mainFG_frameLayout,getTAG());
        show(fragmentList.get(0));

        findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideAllChild(fragmentList.get(0));
            }
        });


        findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideAllChild(fragmentList.get(1));
            }
        });


        findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideAllChild(fragmentList.get(2));
            }
        });
    }
}
