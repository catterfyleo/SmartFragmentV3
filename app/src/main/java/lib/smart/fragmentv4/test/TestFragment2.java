package lib.smart.fragmentv4.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import lib.smart.fragment.R;
import lib.smart.fragmentv4.SmartActivity;
import lib.smart.fragmentv4.SmartFragment;

/**
 * Created by Augustine on 2018/5/16.
 * <p>
 * email:nice_ohoh@163.com
 */

public class TestFragment2 extends SmartFragment {

    public static TestFragment2 newInstance() {

        Bundle args = new Bundle();

        TestFragment2 fragment = new TestFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    private static String[] COLORS = new String[]{
            "#8B2500", "#7A378B", "#3CB371", "#303F9F", "#8B7B8B", "#B4EEB4","#B8860B"
    };

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.test1,null);
    }

    @Override
    public void initList() {
        super.initList();
        Random random = new Random();
        TextView textView = findViewById(R.id.test1_textView);
        textView.setText(getFragmentModel().index+"--2");
        textView.setBackgroundColor(Color.parseColor(COLORS[random.nextInt(COLORS.length)]));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestFragment2 testFragment1 = TestFragment2.newInstance();
                addBrother(testFragment1,null);
                show(testFragment1,null);
            }
        });
    }
}
