package demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

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
    private ListView f3_listView;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.f_3,null);
    }

    @Override
    public void initList() {
        super.initList();
        f3_listView = findViewById(R.id.f3_listView);
        f3_listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 15;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return convertView = View.inflate(getContext(),R.layout.item_1,null);
            }
        });
        f3_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getParentSmartFragment().startFragment(new Fragment4());
            }
        });
    }
}
