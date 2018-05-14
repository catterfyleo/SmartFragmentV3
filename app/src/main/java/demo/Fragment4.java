package demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import lib.smart.fragment.R;
import lib.smart.fragment.SmartFragment;

/**
 * Created by Augustine on 2018/5/11.
 * <p>
 * email:nice_ohoh@163.com
 */

public class Fragment4 extends SmartFragment {
    private static String[] COLORS = new String[]{
            "#8B2500", "#7A378B", "#3CB371", "#030303", "#8B7B8B", "#B4EEB4","#B8860B"
    };

    TextView textView;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.f_4,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = findViewById(R.id.textView4);
        textView.append(":"+getSmartActivity().getSupportFragmentManager().getFragments().size()+"");
        Random random = new Random();
        findViewById(R.id.f4contentView).setBackgroundColor(Color.parseColor(COLORS[random.nextInt(COLORS.length)]));

        findViewById(R.id.textView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment4 fragment4 = new Fragment4();
                startFragment(fragment4);
            }
        });
    }
}
