package com.example.carepet.Community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.carepet.R;
import com.example.carepet.entity.Community;

import java.util.ArrayList;
import java.util.List;

public class LookPuppyFragment extends Fragment {

    public static LookPuppyFragment newInstance() {
        LookPuppyFragment fragment = new LookPuppyFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_layout ,container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        List<Community> list = new ArrayList<>();
        Community c1 = new Community();
        Community c2 = new Community();
        list.add(c1);
        list.add(c2);
        ListAdapter listAdapter = new ListAdapter(list,getContext()); //创建适配器，并且导入数据list
        recyclerView.setAdapter(listAdapter);//布局导入适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        return rootView;
    }
}
