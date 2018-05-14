package demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lib.smart.fragment.R;
import lib.smart.fragment.SmartFragment;

/**
 * Created by Augustine on 2018/5/14.
 * <p>
 * email:nice_ohoh@163.com
 */

public class Fragment3 extends SmartFragment {

    public static Fragment3 newInstance() {

        Bundle args = new Bundle();

        Fragment3 fragment = new Fragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.f_3,null);
    }

    @Override
    public void initList() {
        super.initList();
        getModel().popEnterAnim = false;
        getModel().popExitAnim = false;
        getModel().enterAnim = false;
        getModel().exitAnim = false;
    }
}
