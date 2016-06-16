package org.mobiletrain.android37_materialdesigndemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.MainRecyclerAdapter;
import org.mobiletrain.android37_materialdesigndemo.application.MyApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DummyFragment extends Fragment {
    private Context mContext = null;
    private RecyclerView recyclerView_fragment;
    private int tabindex = 0;
    //每个tab页的数据集合
    private List<Map<String, Object>> totalList = new ArrayList<>();

    public static DummyFragment getInstance(int tabindex) {
        DummyFragment fragment = new DummyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabindex", tabindex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        Bundle bundle = getArguments();
        tabindex = bundle.getInt("tabindex");
        switch (tabindex) {
            case 1:
            case 2:
            case 3:
            case 4:
                totalList = MyApp.loadOutlineData(mContext);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView_fragment = (RecyclerView) inflater.inflate(R.layout.fragment_dummy, container, false);
        return recyclerView_fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView_fragment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView_fragment.setAdapter(new MainRecyclerAdapter(mContext, totalList));
    }
}
